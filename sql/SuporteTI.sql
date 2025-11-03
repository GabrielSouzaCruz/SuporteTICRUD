DROP DATABASE IF EXISTS supportTI;

CREATE DATABASE IF NOT EXISTS supportTI;

USE supportTI;

-- Tabela users
CREATE TABLE IF NOT EXISTS users(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(150) NOT NULL, 
	gender ENUM('M', 'F'), 
	email VARCHAR(150) NOT NULL, 
	password VARCHAR(64) NOT NULL
);

-- Tabela tickets
CREATE TABLE tickets (
	id INT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	opening_date DATE NOT NULL,
	status VARCHAR(50) NOT NULL,
	solution TEXT, user_id INT,
	FOREIGN KEY (user_id) REFERENCES users(id));

ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE tickets AUTO_INCREMENT = 1;

INSERT INTO users (name, gender, email, password) VALUES
('Ana Costa', 'F', 'ana.costa@example.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('Bruno Silva', 'M', 'bruno.silva@example.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('Carlos Dias', 'M', 'carlos.dias@example.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('Daniela Souza', 'F', 'daniela.souza@example.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');

-- INSERTS de Tickets
INSERT INTO tickets (title, description, opening_date, status, solution, user_id) VALUES
('Impressora não funciona', 'A impressora do setor de finanças não está imprimindo. A luz de erro está piscando.', CURDATE(), 'ABERTO', NULL, 1),
('Sistema de Vendas Lento', 'O sistema principal de vendas está muito lento hoje, demorando mais de 30 segundos para carregar cada tela.', CURDATE(), 'EM_ANDAMENTO', 'Verificando logs do servidor de aplicação.', 2),
('Reset de Senha', 'Esqueci a senha do meu e-mail e preciso de um reset urgente.', CURDATE(), 'FECHADO', 'Senha resetada e enviada para o e-mail alternativo do usuário.', 3),
('Mouse quebrado', 'O mouse do meu computador parou de funcionar. Já tentei trocar de porta USB.', CURDATE(), 'ABERTO', NULL, 1),
('Solicitação de Software', 'Gostaria de solicitar a instalação do Adobe Photoshop no meu computador para edição de imagens para o marketing.', CURDATE(), 'EM_ANDAMENTO', NULL, 4);