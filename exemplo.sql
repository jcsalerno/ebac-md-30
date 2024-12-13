CREATE TABLE tb_cliente (
    id bigint,
    nome varchar(50) NOT NULL,
    cpf bigint NOT NULL,
    tel bigint NOT NULL,
    endereco varchar(50) NOT NULL,
    numero bigint NOT NULL,
    cidade varchar(50) NOT NULL,
    estado varchar(50) NOT NULL,
    email varchar(100) NOT NULL,
    CONSTRAINT pk_id_cliente PRIMARY KEY(id),
    CONSTRAINT uq_email UNIQUE (email)
);
