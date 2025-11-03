package view.swing.user;

import controller.UserController;
import controller.auth.SecurityUtils; // NOVO IMPORT
import model.User;
import model.UserGender;

import javax.swing.*;
import java.awt.*;

public class UserFormView extends JDialog implements IUserFormView {
	private final JTextField nameField = new JTextField(20);
	private final JComboBox<String> genderBox = new JComboBox<>(new String[] { "M", "F" });
	private final JTextField emailField = new JTextField(20);
	private final JPasswordField passwordField = new JPasswordField(20);
	private final JButton saveButton = new JButton("Salvar");
	private final JButton closeButton = new JButton("Fechar");
	private UserController controller;
	private final boolean isNew;
	private final Window parentWindow;
	private User user;

	public UserFormView(Window parent, User user, UserController controller) {
		super(parent, ModalityType.APPLICATION_MODAL);

		this.parentWindow = parent;
		this.controller = controller;
		this.controller.setUserFormView(this);

		this.user = user;
		this.isNew = (user == null);

		setTitle(isNew ? "Novo Usuário (Cadastro)" : "Editar Usuário");
		setSize(350, 260);
		setLocationRelativeTo(parent);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Nome:"), gbc);
		gbc.gridx = 1;
		add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Sexo:"), gbc);
		gbc.gridx = 1;
		add(genderBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Email:"), gbc);
		gbc.gridx = 1;
		add(emailField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		String passwordLabel = isNew ? "Senha:" : "Nova Senha (deixe em branco para manter a atual):";
		add(new JLabel(passwordLabel), gbc);
		gbc.gridx = 1;
		add(passwordField, gbc);

		JPanel btnPanel = new JPanel();
		btnPanel.add(saveButton);
		btnPanel.add(closeButton);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		add(btnPanel, gbc);

		if (!isNew) {
			setUserInForm(user);
		}

		saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
		closeButton.addActionListener(e -> close());
	}

	@Override
	public User getUserFromForm() {
		if (user == null) {
			user = new User(0); 
		}

		user.setName(nameField.getText());
		user.setGender(genderBox.getSelectedItem().toString().equals("M") ? UserGender.M : UserGender.F);
		user.setEmail(emailField.getText());

		String plainPassword = new String(passwordField.getPassword());

		
		if (isNew && plainPassword.isBlank()) {
			throw new IllegalArgumentException("A senha é obrigatória para novos usuários.");
		}

		if (isNew || !plainPassword.isBlank()) {
			user.setPassword(SecurityUtils.hashPassword(plainPassword));
		}
		
		return user;
	}

	@Override
	public void setUserInForm(User user) {
		this.user = user;
		nameField.setText(user.getName());
		genderBox.setSelectedItem(user.getGender().toString());
		emailField.setText(user.getEmail());
		passwordField.setText("");
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
		if (parentWindow instanceof UserListView) {
			((UserListView) parentWindow).refresh();
		}
		dispose();
	}
}