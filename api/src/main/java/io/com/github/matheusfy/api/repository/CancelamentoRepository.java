package io.com.github.matheusfy.api.repository;

import io.com.github.matheusfy.api.domain.cancelamento.Cancelamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelamentoRepository extends JpaRepository<Cancelamento, Long> {

	Page<Cancelamento> findAll(Pageable pageable);
}
