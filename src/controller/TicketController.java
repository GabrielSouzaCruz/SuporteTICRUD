package controller;

import java.util.List;
import model.Ticket; // Alterado
import model.ModelException;
import model.User;
import model.data.DAOFactory;
import model.data.TicketDAO; // Alterado
import view.swing.ticket.ITicketFormView; // Alterado
import view.swing.ticket.ITicketListView; // Alterado

public class TicketController {
    private final TicketDAO ticketDAO = DAOFactory.createTicketDAO();
    private ITicketListView ticketListView;
    private ITicketFormView ticketFormView;

    public void loadTickets() { 
        try {
            List<Ticket> tickets = ticketDAO.findAll();
            ticketListView.setTicketList(tickets);
        } catch (ModelException e) {
            ticketListView.showMessage("Erro ao carregar tickets: " + e.getMessage());
        }
    }

    public void saveOrUpdate(boolean isNew) {
        Ticket ticket = ticketFormView.getTicketFromForm();

        try {
            ticket.validate();
        } catch (IllegalArgumentException e) {
            ticketFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
                ticketDAO.save(ticket);
            } else {
                ticketDAO.update(ticket);
            }
            ticketFormView.showInfoMessage("Ticket salvo com sucesso!");
            ticketFormView.close();
        } catch (ModelException e) {
            ticketFormView.showErrorMessage("Erro ao salvar ticket: " + e.getMessage());
        }
    }

    public void excluirTicket(Ticket ticket) {
        try {
            ticketDAO.delete(ticket);
            ticketListView.showMessage("Ticket excluído!");
            loadTickets();
        } catch (ModelException e) {
            ticketListView.showMessage("Erro ao excluir ticket: " + e.getMessage());
        }
    }

    public void setTicketFormView(ITicketFormView ticketFormView) {
        this.ticketFormView = ticketFormView;
    }

    public void setTicketListView(ITicketListView ticketListView) {
        this.ticketListView = ticketListView;
    }

    public List<User> getAllUsers() {
        try {
            return DAOFactory.createUserDAO().findAll();
        } catch (ModelException e) {
            ticketFormView.showErrorMessage("Erro ao carregar usuários: " + e.getMessage());
            return List.of();
        }
    }
}