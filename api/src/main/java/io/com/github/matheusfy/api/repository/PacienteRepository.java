package io.com.github.matheusfy.api.repository;

import io.com.github.matheusfy.api.domain.paciente.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable pageable);

    Paciente getReferenceByNomeIgnoreCaseAndAtivoTrue(String nome);
}
