CREATE DATABASE  IF NOT EXISTS Inventarios;

USE Inventarios;

CREATE TABLE categorias(
   idCategoria int(11) NOT NULL AUTO_INCREMENT,
   descricao  varchar(50) NOT NULL,
   CONSTRAINT pk_categorias
      PRIMARY KEY(idCategoria)
);

CREATE TABLE produtos(
   idProduto int(11) NOT NULL AUTO_INCREMENT,
   nome varchar(50) NOT NULL,
   preco float     NOT NULL,
   quantidade int     NOT NULL,
   idCategoria int,
   CONSTRAINT pk_produtos
      PRIMARY KEY(idProduto),
   CONSTRAINT fk_produtos_categorias
      FOREIGN KEY(idCategoria)
      REFERENCES categorias(idCategoria)
);

CREATE TABLE colaboradores(
   idColaborador int(11) NOT NULL AUTO_INCREMENT,
   nome varchar(50) NOT NULL,
   numero varchar(50) NOT NULL,
   telefone varchar(50) NOT NULL,
   CONSTRAINT pk_colaboradores
      PRIMARY KEY(idColaborador)
);

CREATE TABLE stocks(
   idStock int(11) NOT NULL AUTO_INCREMENT,
   data date NOT NULL,
   valor float NOT NULL,
   idColaborador int,
   CONSTRAINT pk_stocks
      PRIMARY KEY(idStock),
   CONSTRAINT fk_stocks_colaboradores
      FOREIGN KEY(idColaborador)
      REFERENCES colaboradores(idColaborador)
);

CREATE TABLE itensdestock(
   idItemDeStock int(11) NOT NULL AUTO_INCREMENT,
   quantidade int NOT NULL,
   valor float NOT NULL,
   idProduto int,
   idStock int,
   CONSTRAINT pk_itensdestock
      PRIMARY KEY(idItemDeStock),
   CONSTRAINT fk_itensdestock_produtos
      FOREIGN KEY(idProduto)
      REFERENCES produtos(idProduto),
   CONSTRAINT fk_itensdestock_stocks
      FOREIGN KEY(idStock)
      REFERENCES stocks(idStock)
);