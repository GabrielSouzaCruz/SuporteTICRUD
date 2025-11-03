package model;

import java.util.Date;

public class Ticket {

    private int id;
    private String title;
    private String description;
    private Date openingDate;
    private TicketStatus status;
    private String solution;
    private User user;

    public Ticket(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void validate() {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("O Título do ticket não pode ser vazio.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("A Descrição do ticket não pode ser vazia.");
        }
        if (status == null) {
            throw new IllegalArgumentException("O Status do ticket é inválido.");
        }
        if (user == null || user.getId() <= 0) {
            throw new IllegalArgumentException("Usuário inválido para o ticket.");
        }
    }
}