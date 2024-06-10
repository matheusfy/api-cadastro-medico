package io.com.github.matheusfy.api.domain.medico;

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
    public Medico cadastrarMedico(MedicoCadastroDTO medicoCadastroDTO){
        return repository.save(new Medico(medicoCadastroDTO));
    }

    @Transactional
    public MedicoDetalheDTO updateMedico(MedicoUpdateDTO dadosMedicoAtualizado) {

        //TODO: Tratar caso aconteça de tentar atualizar um cadastro inativo.
        Medico medicoBuscado = repository.getReferenceById(dadosMedicoAtualizado.id());
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

    public Medico buscaMedicoPorNome(String nome){
        return repository.getReferenceByNomeIgnoreCaseAndAtivoTrue(nome);
    }

    public List<Medico> buscaMedicosLivres(LocalDateTime horario) {
        return repository.buscaMedicosLivresNoHorario(horario);
    }

    public boolean isMedicoDisponivel(Long id, LocalDateTime horaAgendamento) {
        return repository.medicoDisponivel(id, horaAgendamento) == 1;
    }
}
