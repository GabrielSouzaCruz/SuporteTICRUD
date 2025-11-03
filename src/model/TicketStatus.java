package model;

public enum TicketStatus {
    ABERTO("Aberto"),
    EM_ANDAMENTO("Em Andamento"),
    FECHADO("Fechado");

    private String value;

    TicketStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public static TicketStatus fromString(String text) {
        for (TicketStatus s : TicketStatus.values()) {
            if (s.name().equalsIgnoreCase(text) || s.value.equalsIgnoreCase(text)) {
                return s;
            }
        }
        return ABERTO;
    }

    @Override
    public String toString() {
        return this.value;
    }
}