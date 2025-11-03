package model.data;

import java.util.List;
import model.ModelException;
import model.Ticket;

public interface TicketDAO {
    void save(Ticket ticket) throws ModelException;
    void update(Ticket ticket) throws ModelException;
    void delete(Ticket ticket) throws ModelException;
    List<Ticket> findAll() throws ModelException;
    List<Ticket> findByUserId(int userId) throws ModelException;
}