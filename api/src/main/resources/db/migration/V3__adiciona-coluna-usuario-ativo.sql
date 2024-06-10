alter table medicos add column ativo boolean;
update medicos set medicos.ativo = true where isnull(medicos.ativo);
alter table medicos modify column ativo boolean not null;