CREATE TABLE customers (
    id UUID NOT NULL PRIMARY KEY,
    nome VARCHAR(255),
    telephone VARCHAR(11) UNIQUE,
    create_em TIMESTAMP
)