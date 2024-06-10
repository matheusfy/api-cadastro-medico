alter table pacientes add column last_update DATETIME;
update pacientes set pacientes.last_update = current_date where ISNULL(pacientes.last_update);