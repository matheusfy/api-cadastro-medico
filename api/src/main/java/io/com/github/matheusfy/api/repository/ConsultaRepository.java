package io.com.github.matheusfy.api.repository;

import io.com.github.matheusfy.api.domain.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query(value = "select * from consultas where consultas.paciente_id = ?2 and Date(consultas.data_hora) = Date(?1) limit 1", nativeQuery = true)
    Optional<Consulta> buscaConsultaData(LocalDateTime localDate, Long pacienteId);
}
