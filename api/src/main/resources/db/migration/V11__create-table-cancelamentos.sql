CREATE TABLE cancelamentos (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	consulta_id bigint NOT NULL,
	motivo VARCHAR (255) NOT NULL,
	FOREIGN KEY (consulta_id) REFERENCES consultas (id)
);