CREATE TABLE IF NOT EXISTS receitas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuarios_id INT,
    valor DECIMAL(10, 2) NOT NULL,
    descricao VARCHAR(255),
    FOREIGN KEY (usuarios_id) REFERENCES usuarios(id)
);