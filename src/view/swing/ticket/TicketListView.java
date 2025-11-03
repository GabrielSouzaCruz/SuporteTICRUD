package view.swing.ticket;

import controller.TicketController;
import model.Ticket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TicketListView extends JDialog implements ITicketListView { 
    private TicketController controller;
    private final TicketTableModel tableModel = new TicketTableModel(); 
    private final JTable table = new JTable(tableModel);

    public TicketListView(JFrame parent) {
        super(parent, "Gerenciamento de Tickets", true); 
        this.controller = new TicketController();
        this.controller.setTicketListView(this);

        setSize(800, 500); // Tamanho ajustado
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        table.getColumnModel().getColumn(0).setPreferredWidth(40); 
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        JButton addButton = new JButton("Adicionar Ticket");
        addButton.addActionListener(e -> {
            TicketFormView form = new TicketFormView(this, null, controller);
            form.setVisible(true);
        });

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem deleteItem = new JMenuItem("Excluir");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Ticket ticket = tableModel.getTicketAt(row);
                TicketFormView form = new TicketFormView(this, ticket, controller); 
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Ticket ticket = tableModel.getTicketAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir ticket?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirTicket(ticket);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadTickets();
    }

    @Override
    public void setTicketList(List<Ticket> tickets) { 
        tableModel.setTickets(tickets);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadTickets();
    }

    static class TicketTableModel extends AbstractTableModel {
        private final String[] columns = {"ID", "Título", "Status", "Usuário Solicitante", "Data Abertura"};
        private List<Ticket> tickets = new ArrayList<>();
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
            fireTableDataChanged();
        }

        public Ticket getTicketAt(int row) {
            return tickets.get(row);
        }

        @Override public int getRowCount() { return tickets.size(); }

        @Override public int getColumnCount() { return columns.length; }

        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Ticket t = tickets.get(row);
            switch (col) {
                case 0: return t.getId();
                case 1: return t.getTitle();
                case 2: return (t.getStatus() != null) ? t.getStatus().getValue() : "N/A";
                case 3: return (t.getUser() != null) ? t.getUser().getName() : "N/A";
                case 4: return (t.getOpeningDate() != null) ? dateFormat.format(t.getOpeningDate()) : "N/A";
                default: return null;
            }
        }
        @Override public boolean isCellEditable(int row, int col) { return false; }
    }
}