alter table consultas add column data_hora DATETIME;
alter table consultas modify column data_hora DATETIME not null;