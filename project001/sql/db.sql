CREATE DATABASE IF NOT EXISTS testedb;

USE testedb;

CREATE TABLE IF NOT EXISTS Aluno (
    matricula SERIAL PRIMARY KEY,
    genero CHAR(1) CHECK (genero IN ('M', 'F')),
    idade INT CHECK (idade >= 0),
    nome VARCHAR(255) NOT NULL
);

