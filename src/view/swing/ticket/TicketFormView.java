package view.swing.ticket;

import controller.TicketController;
import model.Ticket;
import model.TicketStatus; // Adicionado
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TicketFormView extends JDialog implements ITicketFormView {
    
    private final JTextField titleField = new JTextField(20);
    private final JComboBox<User> userComboBox = new JComboBox<>();
    private final JComboBox<TicketStatus> statusComboBox = new JComboBox<>(TicketStatus.values());
    private final JTextArea descriptionArea = new JTextArea(5, 20);
    private final JTextArea solutionArea = new JTextArea(5, 20);
    
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");
    
    private TicketController controller;
    private final boolean isNew;
    private final TicketListView parent;
    private Ticket ticket;

    public TicketFormView(TicketListView parent, Ticket ticket, TicketController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setTicketFormView(this);

        this.parent = parent;
        this.ticket = ticket;
        this.isNew = (ticket == null);

        setTitle(isNew ? "Novo Ticket" : "Editar Ticket");
        setSize(500, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Solicitante:"), gbc);
        gbc.gridx = 1;
        add(userComboBox, gbc);
        fillUserComboBox();

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        add(statusComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Solução:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(new JScrollPane(solutionArea), gbc);

        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER; 
        add(btnPanel, gbc);

        if (!isNew) {
            setTicketInForm(ticket);
        }

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }
    
    private void fillUserComboBox() {
        List<User> users = controller.getAllUsers();
        DefaultComboBoxModel<User> comboModel = new DefaultComboBoxModel<>();
        for (User u : users) {
            comboModel.addElement(u);
        }
        userComboBox.setModel(comboModel);
    }

    @Override
    public Ticket getTicketFromForm() {
        if (ticket == null) {
            ticket = new Ticket(0);
        }
        ticket.setTitle(titleField.getText());
        ticket.setDescription(descriptionArea.getText());
        ticket.setSolution(solutionArea.getText());
        ticket.setStatus((TicketStatus) statusComboBox.getSelectedItem());
        ticket.setUser((User) userComboBox.getSelectedItem());
        
        return ticket;
    }

    @Override
    public void setTicketInForm(Ticket ticket) {
        titleField.setText(ticket.getTitle());
        descriptionArea.setText(ticket.getDescription());
        solutionArea.setText(ticket.getSolution());
        statusComboBox.setSelectedItem(ticket.getStatus());

        if (ticket.getUser() != null) {
            userComboBox.setSelectedItem(ticket.getUser());
            if (userComboBox.getSelectedIndex() == -1) {
                ((DefaultComboBoxModel<User>)userComboBox.getModel()).addElement(ticket.getUser());
                userComboBox.setSelectedItem(ticket.getUser());
            }
        }
    }

    @Override
    public void showInfoMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void close() {
        parent.refresh();
        dispose();
    }
}