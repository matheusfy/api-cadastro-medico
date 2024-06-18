package io.com.github.matheusfy.api.domain.paciente;

import io.com.github.matheusfy.api.domain.paciente.dto.CadastroPacienteDTO;
import io.com.github.matheusfy.api.domain.paciente.dto.PacienteListagemDTO;
import io.com.github.matheusfy.api.domain.paciente.dto.pacienteUpdateDTO;
import io.com.github.matheusfy.api.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository repository;

    public Page<PacienteListagemDTO> listarPacientes(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(PacienteListagemDTO::new);
    }

    @Transactional
    public PacienteListagemDTO cadastrarPaciente(CadastroPacienteDTO pacienteDTO) {
        Paciente paciente = repository.save(new Paciente(pacienteDTO));
        return new PacienteListagemDTO(paciente);
    }

    @Transactional
    public pacienteUpdateDTO atualizarPaciente(pacienteUpdateDTO pacienteDTO) {
        Paciente paciente = repository.getReferenceById(pacienteDTO.id());
        boolean atualizado = paciente.atualizarCadastro(pacienteDTO);

        if (!atualizado) {
            throw new NotUpdatedException("Paciente não atualizado pois não houve alterações.");
        }
        return new pacienteUpdateDTO(paciente);
    }

    @Transactional
    public void desativarPaciente(Long id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.desativar();
    }

    public Paciente buscaPacientePorNome(String nome) {
        return repository.getReferenceByNomeIgnoreCaseAndAtivoTrue(nome);
    }

    public Paciente getPacienteById(Long id) {
        return repository.getReferenceById(id);
    }
}
