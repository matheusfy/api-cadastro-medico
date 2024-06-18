package io.com.github.matheusfy.api.repository;

import io.com.github.matheusfy.api.domain.consulta.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Consulta findReferenceById(Long id);

    @Query(value = """
                    delete from consultas as c where c.id = ?1 and c.data_hora >= convert_tz(now(), @@session_user.time_zone,'America/Sao_Paulo');
            """, nativeQuery = true)
    void deletaConsulta(Long id);

    @Query(value = "select * from consultas where consultas.paciente_id = ?2 and consultas.cancelado = false and Date(consultas.data_hora) = Date(?1) limit 1", nativeQuery = true)
    Optional<Consulta> buscaConsultaData(LocalDateTime localDate, Long pacienteId);

    Page<Consulta> findAllByPacienteIdAndCanceladoFalse(Long pacienteId, Pageable pageable);
}
