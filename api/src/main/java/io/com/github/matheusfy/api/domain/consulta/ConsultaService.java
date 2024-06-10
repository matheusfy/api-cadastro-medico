package io.com.github.matheusfy.api.domain.consulta;


import io.com.github.matheusfy.api.domain.medico.Medico;
import io.com.github.matheusfy.api.domain.medico.MedicoService;
import io.com.github.matheusfy.api.domain.paciente.Paciente;
import io.com.github.matheusfy.api.domain.paciente.PacienteService;
import io.com.github.matheusfy.api.repository.ConsultaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static io.com.github.matheusfy.api.domain.Util.Util.isHorarioComercial;

@Service
public class ConsultaService {

    @Autowired
    MedicoService medicoService;

    @Autowired
    PacienteService pacienteService;

    @Autowired
    ConsultaRepository repository;

    @Transactional
    public void cadastrarConsulta(CadastroConsultaDTO cadastroConsulta){
        //TODO: Não é a melhor abordagem fazer a busca por nome do medico e paciente.
        // A melhor abordagem seria que o front já possui uma lista ou consulta uma lista de medicos/pacientes ativos
        // e entao escolhe e nos enviam a solicitação de cadastro

        Medico medico = null;
        if((cadastroConsulta.medicoNome()!= null)&&(!cadastroConsulta.medicoNome().equals(""))){
            medico = medicoService.buscaMedicoPorNome(cadastroConsulta.medicoNome());
        }

        Paciente paciente = pacienteService.buscaPacientePorNome(cadastroConsulta.pacienteNome());

        medico = validaConsulta(medico, paciente, cadastroConsulta);

        Consulta consulta = new Consulta(medico,paciente,cadastroConsulta.dataHora());
        medico.addConsulta(consulta);
        paciente.addConsulta(consulta);
        repository.save(consulta);
        System.out.println("Consulta marcada com sucesso! Consulta: " + consulta);
    }

    private Medico validaConsulta(Medico medico, Paciente paciente, CadastroConsultaDTO cadastroConsulta){

        // Colocar todas regras pra validação da consulta.
        // Para ficar mais visivel, poderia colocar o erro em forma de json
        // com chave e valor <Erro, msg>

        boolean valid = true;

        String errorMsg = "";

        if (paciente == null){
            errorMsg = errorMsg + " Paciente não foi encontrado no sistema não está ativo. \n";
            valid = false;
        }

        // Validação se horario de agendamento está no horario comercial
        if(!isHorarioComercial(cadastroConsulta.dataHora())){
            errorMsg = errorMsg + "Horário de agendamento inválido. Marque no horário comercial de 7 - 19 de segunda a sabado \n ";
            valid = false;
        }

        // Valida se horario está com 30 min de antecedência!
        if(LocalDateTime.now().until(cadastroConsulta.dataHora(), ChronoUnit.MINUTES)<= 30){
            errorMsg = errorMsg + "Horário de agendamento precisa ser com 30 minutos de antecedência! \n ";
            valid = false;
        }

        if(paciente != null){
            Optional<Consulta> consultaPaciente = repository.buscaConsultaData(cadastroConsulta.dataHora(), paciente.getId());
            if(consultaPaciente.isPresent()){
                errorMsg = errorMsg + "paciente já possui horário agendado para esta data! \n ";
                valid = false;
            }
        }

        // Preenche automaticamente o medico para o paciente caso algum medico esteja livre no horario
        if (medico == null){
            List<Medico> medicos = medicoService.buscaMedicosLivres(cadastroConsulta.dataHora());
            if(medicos != null){
                medico = medicos.get(0);
                medicos.forEach(System.out::println);
            } else {
                System.out.println("nenhum medico disponivel");
                valid = false;
            }
        } else {
            // Valida se o medico possui horário para agendamento
            boolean livre = medicoService.isMedicoDisponivel(medico.getId(), cadastroConsulta.dataHora());
            if (!livre){
                valid = false;
                errorMsg = errorMsg + "Medico: " + medico.getNome() + " não está livre neste horário! \n";
                // TODO: Já que o medico nao esta livre, poderia agendar com algum outro medico
            }
        }

        if(!valid){
            throw new RuntimeException(errorMsg);
        }
        return medico;
    }
}
