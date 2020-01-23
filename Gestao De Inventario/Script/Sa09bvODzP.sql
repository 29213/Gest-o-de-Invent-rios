
CREATE DATABASE IF NOT EXISTS Inventarios;

USE Inventarios;

CREATE TABLE categorias (
   idCategoria int(11) NOT NULL AUTO_INCREMENT,
   descricao  varchar(50)  NOT NULL,
   CONSTRAINT pk_categorias
      PRIMARY KEY (idCategoria)
);

CREATE TABLE produtos (
   idProduto int(11) NOT NULL AUTO_INCREMENT,
   nome varchar(50) NOT NULL,
   preco float NOT NULL,
   quantidade int(11) NOT NULL,
   idCategoria int(11),
   CONSTRAINT pk_produtos
      PRIMARY KEY(idProduto),
   CONSTRAINT fk_produtos_categorias
      FOREIGN KEY(idCategoria)
      REFERENCES categorias(idCategoria)
);

CREATE TABLE colaboradores (
   idColaborador int(11) NOT NULL AUTO_INCREMENT,
   nome varchar(50) NOT NULL,
   numero int(50) NOT NULL,
   telefone varchar(50) NOT NULL,
   CONSTRAINT pk_colaboradores
      PRIMARY KEY (idColaborador)
);

CREATE TABLE pagamentos (
   idPagamento int(11) NOT NULL AUTO_INCREMENT,
   valor double NOT NULL,
   data date NULL,
   estado  enum('PAGO','PENDENTE','CANCELADO','') NULL,
   CONSTRAINT pk_pagamentos
      PRIMARY KEY (idPagamento)
);

CREATE TABLE pedidos (
   idPedido int(11) NOT NULL AUTO_INCREMENT,
   data date NOT NULL,
   valor double NOT NULL,
   idColaborador int(11),
   idPagamento int(11) DEFAULT '0',
   CONSTRAINT pk_pedidos
      PRIMARY KEY(idPedido),
   CONSTRAINT fk_pedidos_colaboradores
      FOREIGN KEY(idColaborador)
      REFERENCES colaboradores(idColaborador),
   CONSTRAINT fk_pedidos_pagamentos
      FOREIGN KEY(idPagamento)
      REFERENCES pagamentos (idPagamento)
) ;

CREATE TABLE itensdopedido (
   idItemDoPedido int(11) NOT NULL AUTO_INCREMENT,
   quantidade int(11) NOT NULL,
   valor int(11) NOT NULL,
   idProduto int(11),
   idPedido int(11),
   CONSTRAINT pk_itensdopedido
      PRIMARY KEY(idItemDoPedido),
   CONSTRAINT fk_itensdopedido_produtos
      FOREIGN KEY(idProduto)
      REFERENCES produtos(idProduto),
   CONSTRAINT fk_itensdopedido_pedidos
      FOREIGN KEY(idPedido)
      REFERENCES pedidos(idPedido)
);
