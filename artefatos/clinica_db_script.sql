create database if not exists clinica;

use clinica;

-- inicio comandos 'medico'
create table medico(
id_medico integer auto_increment,
crm varchar(6) not null,
nome varchar(200) not null,
especialidade varchar(100) not null,
constraint pk_medico primary key(id_medico),
constraint un_crm_medico unique(crm)
);

insert into medico
(crm, nome, especialidade) values
('123456', 'Joana Almeida', 'Clinico Geral');

insert into medico
(crm, nome, especialidade) values
('111456', 'Alberto Gomes', 'Oftalmologista');

select * from medico;

delete from medico where id_medico = 10;
-- fim comandos 'medico'

-- inicio comandos 'convenio'
create table convenio(
id_convenio integer auto_increment,
codigo char(6) not null,
nome varchar(100) not null,
concedente varchar(200) not null,
constraint pk_convenio primary key(id_convenio),
constraint un_codigo_convenio unique(codigo)
);

insert into convenio
(codigo, nome, concedente) values
('444555', 'Unimed Basic', 'Unimed');

insert into convenio
(codigo, nome, concedente) values
('123123', 'Golden Cross Premium', 'Golden Cross');

select * from convenio;
-- fim comandos 'convenio'

-- inicio comandos 'paciente'
create table paciente(
id_paciente integer auto_increment,
cpf char(11) not null,
nome varchar(200) not null,
sexo char(1) not null check(sexo in ('F','M', 'f', 'm')),
dataNascimento DATE not null,
id_convenio integer,
constraint pk_paciente primary key(id_paciente),
constraint fk_paciente_convenio foreign key(id_convenio) references convenio(id_convenio),
constraint un_cpf_paciente unique(cpf)
);

insert into paciente
(cpf, nome, sexo, dataNascimento) values
('12345678900', 'Gabriel Cardoso', 'M', '2017-08-08');

insert into paciente
(cpf, nome, sexo, dataNascimento) values
('12345678911', 'Jose Cardoso', 'M', '2018-12-31');

insert into paciente
(cpf, nome, sexo, dataNascimento, id_convenio) values
('11122233300', 'Maria', 'F', '1998-03-31', 3);

select * from paciente;
-- fim comandos 'paciente'

-- inicio comandos 'consulta'
create table consulta(
id_consulta integer auto_increment,
id_paciente integer not null,
id_medico integer not null,
data date not null,
horario time not null,
constraint pk_consulta primary key(id_consulta),
constraint fk_consulta_paciente foreign key(id_paciente) references paciente(id_paciente),
constraint fk_consulta_medico foreign key(id_medico) references medico(id_medico),
constraint un_tbpaciente_consulta unique (id_paciente, data, horario),
constraint un_tbmedico_consulta unique (id_medico, data, horario)
);

insert into consulta
(id_paciente, id_medico, data, horario) values
(1, 1, '2018-01-01', '11:00:00');

insert into consulta
(id_paciente, id_medico, data, horario) values
(3, 1, '2018-01-01', '11:30:00');

insert into consulta
(id_paciente, id_medico, data, horario) values
(3, 3, '2018-01-02', '11:30:00');

select * from consulta;
-- fim comandos 'consulta'

--
#create table teste(
#tempo time
#);

#insert into teste VALUES(NOW());

#SELECT * FROM teste;
#drop table teste;
--
-- inicio comandos 'paciente_convenio'
#create table paciente_convenio(
#id_paciente_convenio integer auto_increment,
#id_paciente integer not null,
#id_convenio integer not null,
#constraint pk_paciente_convenio primary key(id_paciente_convenio),
#constraint fk_paciente_convenio_paciente foreign key(id_paciente) references paciente(id_paciente),
#constraint fk_paciente_convenio_convenio foreign key(id_convenio) references convenio(id_convenio)
#);

#insert into paciente_convenio
#(id_paciente, id_convenio) values
#(1,1);

#insert into paciente_convenio
#(id_paciente, id_convenio) values
#(1,3);

#insert into paciente_convenio
#(id_paciente, id_convenio) values
#(3,3);

#select pc.id_paciente_convenio, p.nome, c.nome from paciente as p
#join paciente_convenio as pc on
#p.id_paciente = pc.id_paciente
#join convenio as c on
#pc.id_convenio = c.id_convenio;

#drop table paciente_convenio;
-- fim comandos 'paciente_convenio'