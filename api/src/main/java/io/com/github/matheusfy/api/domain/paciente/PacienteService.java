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
    public void cadastrarPaciente(CadastroPacienteDTO pacienteDTO){
        repository.save(new Paciente(pacienteDTO));
    }

    @Transactional
    public void atualizarPaciente(pacienteUpdateDTO pacienteDTO) {
        Paciente paciente = repository.getReferenceById(pacienteDTO.id());
        paciente.atualizarCadastro(pacienteDTO);
    }

    @Transactional
    public void desativarPaciente(Long id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.desativar();
    }

    public Paciente buscaPacientePorNome(String nome) {
        return repository.getReferenceByNomeIgnoreCaseAndAtivoTrue(nome);
    }
}
