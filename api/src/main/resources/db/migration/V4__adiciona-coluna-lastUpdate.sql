alter table medicos add column last_update DATETIME;
update medicos set medicos.last_update = current_date where ISNULL(medicos.last_update);