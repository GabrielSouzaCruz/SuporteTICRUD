package model.data;

import model.data.mysql.MySQLTicketDAO; 
import model.data.mysql.MySQLUserDAO;

public final class DAOFactory {
	
	public static UserDAO createUserDAO() {
		return new MySQLUserDAO();
	}
	
	public static TicketDAO createTicketDAO() {
		return new MySQLTicketDAO();
	}
}