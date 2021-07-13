create database escola;
use escola;

CREATE TABLE `escola`.`professor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(200) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `escola`.`disciplina` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NOT NULL,
  `id_professor` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_disciplina_professor`
    FOREIGN KEY (`id_professor`)
    REFERENCES `escola`.`professor` (`id`)
);

CREATE TABLE `escola`.`aluno` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(200) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `escola`.`aluno_disciplina` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_aluno` INT NOT NULL,
  `id_disciplina` INT NOT NULL,
  `nota_1` DOUBLE NULL,
  `nota_2` DOUBLE NULL,
  `nota_3` DOUBLE NULL,
  `nota_4` DOUBLE NULL,
  `nota_recuperacao` DECIMAL(2,2) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_aluno_disciplina_aluno`
    FOREIGN KEY (`id_aluno`)
    REFERENCES `escola`.`aluno` (`id`),
  CONSTRAINT `fk_aluno_disciplina_disciplina`
    FOREIGN KEY (`id_disciplina`)
    REFERENCES `escola`.`disciplina` (`id`)
);