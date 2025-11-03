package view.swing.ticket;

import model.Ticket;

public interface ITicketFormView {
    Ticket getTicketFromForm();
    void setTicketInForm(Ticket ticket);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}