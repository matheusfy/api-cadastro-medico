package io.com.github.matheusfy.api.domain.consulta;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.domain.consulta.dto.ConsultaCadastradaDTO;
import io.com.github.matheusfy.api.domain.consulta.validacao.IValidadorAgendamentoConsulta;
import io.com.github.matheusfy.api.domain.medico.Medico;
import io.com.github.matheusfy.api.domain.medico.MedicoService;
import io.com.github.matheusfy.api.domain.paciente.Paciente;
import io.com.github.matheusfy.api.domain.paciente.PacienteService;
import io.com.github.matheusfy.api.exception.MedicoNotFoundException;
import io.com.github.matheusfy.api.repository.ConsultaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    MedicoService medicoService;

    @Autowired
    PacienteService pacienteService;

    @Autowired
    ConsultaRepository repository;

    @Autowired
    private List<IValidadorAgendamentoConsulta> validaConsulta;

    @Transactional
    public ConsultaCadastradaDTO cadastrarConsulta(CadastroConsultaDTO cadastroConsulta) {

        validaConsulta(cadastroConsulta);

        Paciente paciente = pacienteService.getPacienteById(cadastroConsulta.pacienteId());
        Optional<Medico> medico = Optional.ofNullable(medicoService.getMedico(cadastroConsulta));

        if (medico.isEmpty()) {
            throw new MedicoNotFoundException("Não encontramos médicos para a especialidade: "
                    + cadastroConsulta.especialidade() + " neste horário.");
        }

        return new ConsultaCadastradaDTO(salvarConsulta(medico.get(), paciente, cadastroConsulta));
    }

    private void validaConsulta(CadastroConsultaDTO cadastroConsulta) {
        validaConsulta.forEach(validador -> {
            validador.valida(cadastroConsulta);
        });
    }

    private Consulta salvarConsulta(Medico medico, Paciente paciente, CadastroConsultaDTO cadastroConsulta) {
        Consulta consulta = new Consulta(medico, paciente, cadastroConsulta.dataHora(), false);
        medico.addConsulta(consulta);
        paciente.addConsulta(consulta);
        return repository.save(consulta);
    }

    public void removeConsulta(Long id) {
        // repository.deleteConsultaByDataHoraGreaterThanEqualTodayAndId(id);
    }

    public Page<ConsultaCadastradaDTO> buscaConsultasPaciente(Long pacienteId, Pageable pageable) {
        Page<Consulta> consultas = repository.findAllByPacienteIdAndCanceladoFalse(pacienteId, pageable);

        if (consultas.isEmpty()) {
            throw new ConsultaNotFoundException("Não encontramos consultas para o paciente informado.");
        }
        consultas.forEach(System.out::println);
        return consultas.map(ConsultaCadastradaDTO::new);
    }
}
