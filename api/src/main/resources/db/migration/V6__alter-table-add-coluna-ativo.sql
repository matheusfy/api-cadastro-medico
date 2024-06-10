alter table pacientes add column ativo boolean;
update pacientes set pacientes.ativo = true where isnull(pacientes.ativo);
alter table pacientes modify column ativo boolean not null;