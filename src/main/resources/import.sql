INSERT INTO authority (name) VALUES ('admin');
INSERT INTO authority (name) VALUES ('user');

INSERT INTO users (username, "admin-password", cpf, email, phone)
VALUES ('admin victor', '$2a$10$5Va5P9Ey6jwnLUjHMnKX8.Jcqc2C977O.AuyUparVGmbHo/949C16', '00000000011', 'admin@gmail.com', '(62)900000000');

INSERT INTO user_authorities (user_cpf, authorities_id) values ('00000000011', 1);