CREATE DATABASE IF NOT EXISTS testedb;

USE testedb;

CREATE TABLE IF NOT EXISTS Aluno (
    matricula SERIAL PRIMARY KEY,
    genero CHAR(1) CHECK (genero IN ('M', 'F')),
    idade INT CHECK (idade >= 0),
    nome VARCHAR(255) NOT NULL,
    curso_sigla VARCHAR(10),
    FOREIGN KEY (curso_sigla) REFERENCES Curso(sigla)
);

CREATE TABLE IF NOT EXISTS Curso (
	sigla VARCHAR(10) PRIMARY KEY,
	nome VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS Curso_Materias (
    curso_sigla VARCHAR(10) REFERENCES Curso(sigla),
    materia VARCHAR(255) NOT NULL,
    PRIMARY KEY (curso_sigla, materia)
);