package view.swing.ticket;

import java.util.List;
import model.Ticket; // Alterado

public interface ITicketListView {
    void setTicketList(List<Ticket> tickets);
    void showMessage(String msg);
}