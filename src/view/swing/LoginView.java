package view.swing;

import controller.UserController;
import controller.auth.SecurityUtils;
import model.ModelException;
import model.User;
import model.data.DAOFactory;
import model.data.UserDAO;
import view.swing.user.UserFormView;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JDialog {
	private boolean authenticated = false;
	private final JTextField emailField = new JTextField(20);
	private final JPasswordField passwordField = new JPasswordField(20);

	public LoginView() {
		setTitle("SupportTI - Login");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel form = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		form.add(new JLabel("Email:"), gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		form.add(emailField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		form.add(new JLabel("Senha:"), gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		form.add(passwordField, gbc);

		JPanel buttons = new JPanel();
		JButton loginBtn = new JButton("Entrar");
		JButton registerBtn = new JButton("Cadastrar-se");
		JButton forgotPasswordBtn = new JButton("Esqueceu a senha?");
		JButton cancelBtn = new JButton("Cancelar");
		buttons.add(loginBtn);
		buttons.add(registerBtn);
		buttons.add(forgotPasswordBtn);
		buttons.add(cancelBtn);

		loginBtn.addActionListener(e -> {
			String email = emailField.getText();
			String senha = new String(passwordField.getPassword());

			try {
				UserDAO userDAO = DAOFactory.createUserDAO();
				User user = userDAO.findByEmail(email);

				String hashedInput = SecurityUtils.hashPassword(senha);

				if (user != null && user.getPassword().equals(hashedInput)) {
					authenticated = true;
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} catch (ModelException me) {
				JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + me.getMessage(),
						"Erro de Conexão", JOptionPane.ERROR_MESSAGE);
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		registerBtn.addActionListener(e -> {
			UserController userController = new UserController();
			UserFormView registerView = new UserFormView(this, null, userController);
			registerView.setVisible(true);
		});

		forgotPasswordBtn.addActionListener(e -> {
			String email = emailField.getText();

			if (email == null || email.isBlank()) {
				JOptionPane.showMessageDialog(this, "Por favor, digite seu email no campo 'Email'.", "Email Vazio", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {
				UserDAO userDAO = DAOFactory.createUserDAO();
				User user = userDAO.findByEmail(email);

				if (user != null) {

					JPanel passwordPanel = new JPanel(new GridLayout(2, 2, 5, 5));
					JPasswordField newPasswordField = new JPasswordField(20);
					JPasswordField confirmPasswordField = new JPasswordField(20);
					
					passwordPanel.add(new JLabel("Nova Senha:"));
					passwordPanel.add(newPasswordField);
					passwordPanel.add(new JLabel("Confirmar Senha:"));
					passwordPanel.add(confirmPasswordField);

					int result = JOptionPane.showConfirmDialog(this, passwordPanel, "Redefinir Senha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

					if (result == JOptionPane.OK_OPTION) {
						String newPassword = new String(newPasswordField.getPassword());
						String confirmPassword = new String(confirmPasswordField.getPassword());

						if (newPassword.isBlank() || !newPassword.equals(confirmPassword)) {
							JOptionPane.showMessageDialog(this, "As senhas não conferem ou estão em branco.", "Erro", JOptionPane.ERROR_MESSAGE);
						} else {
							try {
								String hashedInput = SecurityUtils.hashPassword(newPassword);
								user.setPassword(hashedInput);
								userDAO.update(user); // Salva no banco
								
								JOptionPane.showMessageDialog(this, "Senha alterada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
								
							} catch (IllegalArgumentException iae) {
								JOptionPane.showMessageDialog(this, "Erro ao processar a senha: " + iae.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
							} catch (ModelException meUpdate) {
								JOptionPane.showMessageDialog(this, "Erro ao salvar a nova senha: " + meUpdate.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					
				} else {
					JOptionPane.showMessageDialog(this,
							"Nenhum usuário encontrado com este email.",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (ModelException me) { 
				JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + me.getMessage(),
						"Erro de Conexão", JOptionPane.ERROR_MESSAGE);
			}
		});

		cancelBtn.addActionListener(e -> {
			authenticated = false;
			dispose();
		});

		add(form, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
}