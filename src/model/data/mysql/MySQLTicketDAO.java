package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ModelException;
import model.Ticket; // Alterado de Post para Ticket
import model.TicketStatus; // Importar o Enum
import model.User;
import model.data.DAOFactory;
import model.data.DAOUtils;
import model.data.TicketDAO; // Alterado de PostDAO para TicketDAO
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLTicketDAO implements TicketDAO {

	@Override
	public void save(Ticket ticket) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();
			String sqlIsert = " INSERT INTO tickets " + 
			                  " (title, description, opening_date, status, user_id) " +
			                  " VALUES (?, ?, CURDATE(), ?, ?); ";

			preparedStatement = connection.prepareStatement(sqlIsert);
			preparedStatement.setString(1, ticket.getTitle());
			preparedStatement.setString(2, ticket.getDescription());
			preparedStatement.setString(3, ticket.getStatus().name());
			preparedStatement.setInt(4, ticket.getUser().getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao inserir ticket no BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void update(Ticket ticket) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();
			
			String sqlUpdate = " UPDATE tickets "
					+ " SET "
					+ " title = ?, "
					+ " description = ?, "
					+ " status = ?, "
					+ " solution = ?, "
					+ " user_id = ? "
					+ " WHERE id = ?; ";
			
			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setString(1, ticket.getTitle());
			preparedStatement.setString(2, ticket.getDescription());
			preparedStatement.setString(3, ticket.getStatus().name());
			preparedStatement.setString(4, ticket.getSolution());
			preparedStatement.setInt(5, ticket.getUser().getId());
			preparedStatement.setInt(6, ticket.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar ticket do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void delete(Ticket ticket) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();  

			String sqlDelete = "delete from tickets where id = ?;";

			preparedStatement = connection.prepareStatement(sqlDelete);
			preparedStatement.setInt(1, ticket.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao excluir ticket do BD.", sqle);
		} finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public List<Ticket> findAll() throws ModelException {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		List<Ticket> ticketsList = new ArrayList<>();

		try {
			connection = MySQLConnectionFactory.getConnection();

			statement = connection.createStatement();
			String sqlSeletc = " SELECT * FROM tickets order by opening_date desc ; ";

			rs = statement.executeQuery(sqlSeletc);

			setUpTickets(rs, ticketsList);
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao carregar tickets do BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(statement);
			DAOUtils.close(connection);
		}

		return ticketsList;
	}

	@Override
	public List<Ticket> findByUserId(int userId) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Ticket> ticketsList = new ArrayList<>();

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlSeletc = " SELECT * FROM tickets WHERE user_id = ?; ";
			preparedStatement = connection.prepareStatement(sqlSeletc);
			preparedStatement.setInt(1, userId);

			rs = preparedStatement.executeQuery();

			setUpTickets(rs, ticketsList);

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao carregar tickets do BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}

		return ticketsList;
	}

	private void setUpTickets(ResultSet rs, List<Ticket> ticketsList)
            throws SQLException, ModelException {
		
		while (rs.next()) {
			int ticketId = rs.getInt("id"); 
			String title = rs.getString("title");
			String description = rs.getString("description");
			Date openingDate = rs.getDate("opening_date");
			String statusStr = rs.getString("status");
			String solution = rs.getString("solution");
			int userId = rs.getInt("user_id");

			Ticket newTicket = new Ticket(ticketId);
			newTicket.setTitle(title);
			newTicket.setDescription(description);
			newTicket.setOpeningDate(openingDate);
			newTicket.setStatus(TicketStatus.fromString(statusStr));
			newTicket.setSolution(solution);

			User ticketUser = DAOFactory.createUserDAO().findById(userId);
			newTicket.setUser(ticketUser);
			
			ticketsList.add(newTicket);
		}
	}
}