package io.com.github.matheusfy.api.domain.medico;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoCadastroDTO;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoDetalheDTO;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoListagemDTO;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoUpdateDTO;
import io.com.github.matheusfy.api.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicoService {

    @Autowired
    MedicoRepository repository;

    public Page<MedicoListagemDTO> buscaListaMedicos(Pageable pageable) {
        Page<Medico> medicos = repository.findAllByAtivoTrue(pageable);
        return medicos.map(MedicoListagemDTO::new);
    }

    @Transactional
    public Medico cadastrarMedico(MedicoCadastroDTO medicoCadastroDTO) {
        return repository.save(new Medico(medicoCadastroDTO));
    }

    @Transactional
    public MedicoDetalheDTO updateMedico(MedicoUpdateDTO dadosMedicoAtualizado) {

        Medico medicoBuscado = repository.getReferenceByIdAndAtivoTrue(dadosMedicoAtualizado.id());
        medicoBuscado.updateInfo(dadosMedicoAtualizado);
        medicoBuscado.setLastUpdate(LocalDateTime.now());
        return new MedicoDetalheDTO(medicoBuscado);
    }

    @Transactional
    public void desativaMedico(Long id) {
        Medico medicoBuscado = repository.getReferenceById(id);
        medicoBuscado.excluir();
        medicoBuscado.setLastUpdate(LocalDateTime.now());
    }

    public MedicoListagemDTO buscaMedicoById(Long id) {
        Medico medico = repository.getReferenceById(id);
        return new MedicoListagemDTO(medico);
    }

    public Medico buscaMedicoPorNome(String nome) {
        return repository.getReferenceByNomeIgnoreCaseAndAtivoTrue(nome);
    }

    public Medico getMedicoById(Long id) {
        return repository.getReferenceById(id);
    }

    public Medico getMedico(CadastroConsultaDTO cadastroConsulta) {

        if (cadastroConsulta.medicoId() != null) {
            return getMedicoById(cadastroConsulta.medicoId());
        }
        return repository.buscaMedicoLivreNoHorarioPorEspecialidade(cadastroConsulta.dataHora(),
                cadastroConsulta.especialidade().toString());
    }

    public List<Medico> getMedicosPorEspecialidade(CadastroConsultaDTO cadastroConsulta) {
        return repository.getMedicoByEspecialidade(cadastroConsulta.especialidade());
    }
}
