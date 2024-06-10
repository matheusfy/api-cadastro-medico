package io.com.github.matheusfy.api.repository;

import io.com.github.matheusfy.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable pageable);

    Medico getReferenceByNomeIgnoreCaseAndAtivoTrue(String nome);

    @Query(value = """
        select *from medicos m where m.ativo = true and m.id
            not in (
                select c.medico_id
                from consultas c
                where 1 = 1
                  and (c.data_hora between ?1 and (ADDTIME(?1, '01:00:00'))
                    or ADDTIME(c.data_hora,  '01:00:00') between ?1 and (ADDTIME(?1, '01:00:00'))
                    or ?1 between c.data_hora and ADDTIME(c.data_hora, '01:00:00')
                    or ADDTIME(?1, '01:00:00') between c.data_hora and ADDTIME(c.data_hora, '01:00:00')
                    )
            );""", nativeQuery = true)
    List<Medico> buscaMedicosLivresNoHorario(LocalDateTime horario);

    @Query(value = """
        select if(
            exists(
            select * from medicos m where m.ativo = true and m.id = ?1 and
                exists(
                    select c.medico_id
                    from consultas c
                    where 1 = 1
                      and (c.data_hora between ?2 and (ADDTIME(?2, '01:00:00'))
                        or ADDTIME(c.data_hora,  '01:00:00') between ?2 and (ADDTIME(?2, '01:00:00'))
                        or ?2 between c.data_hora and ADDTIME(c.data_hora, '01:00:00')
                        or ADDTIME(?2, '01:00:00') between c.data_hora and ADDTIME(c.data_hora, '01:00:00'))
                )
            ), false, true);""", nativeQuery = true)
    Long medicoDisponivel(Long id, LocalDateTime horario);

}
