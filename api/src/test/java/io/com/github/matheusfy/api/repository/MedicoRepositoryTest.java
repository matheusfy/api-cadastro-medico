package io.com.github.matheusfy.api.repository;

import io.com.github.matheusfy.api.domain.consulta.Consulta;
import io.com.github.matheusfy.api.domain.endereco.EnderecoDTO;
import io.com.github.matheusfy.api.domain.enums.TipoEspecialidade;
import io.com.github.matheusfy.api.domain.medico.Medico;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoCadastroDTO;
import io.com.github.matheusfy.api.domain.paciente.Paciente;
import io.com.github.matheusfy.api.domain.paciente.dto.CadastroPacienteDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deveria retornar um médico que está disponível no horário.")
    void buscaMedicoLivreNoHorarioPorEspecialidadeCenario1() {

        LocalDateTime proximaSegundaAs10 = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10,0);

        var medico = cadastraMedico("Matheus", "matheus.yoko@voll.med", "123456", TipoEspecialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Isabela", "isabela@gemail.com", "12345678920");
        cadastrarConsulta(medico,paciente,proximaSegundaAs10, false);

        Medico medicoLivre = medicoRepository.buscaMedicoLivreNoHorarioPorEspecialidade(proximaSegundaAs10, TipoEspecialidade.CARDIOLOGIA.name());

        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria retornar um médico quando estiver livre na data.")
    void buscaMedicoLivreNoHorarioPorEspecialidadeCenario2() {

        LocalDateTime proximaSegundaAs10 = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10,0);

        var medico = cadastraMedico("Matheus", "matheus.yoko@voll.med", "123456", TipoEspecialidade.CARDIOLOGIA);
        Medico medicoLivre = medicoRepository.buscaMedicoLivreNoHorarioPorEspecialidade(proximaSegundaAs10, TipoEspecialidade.CARDIOLOGIA.name());

        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Deveria retornar que medico já possio consulta no horario.")
    void medicoPossuiConsultaNoHorario() {
        LocalDateTime proximaSegundaAs10 = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10,0);
        var medico = cadastraMedico("Matheus", "matheus.yoko@voll.med", "123456", TipoEspecialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Isabela", "isabela@gemail.com", "12345678920");
        var paciente2 = cadastrarPaciente("Matheus", "matheus@gemail.com", "12345678921");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10, false);

        Long qtdConsulta = medicoRepository.medicoPossuiConsulta(medico.getId(), proximaSegundaAs10);
        assertThat(qtdConsulta).isGreaterThan(0);
    }

    @Test
    @DisplayName("Deveria retornar que medico já possui consulta no horario pois está dentro do limiar do intervalo de 1 hora de consulta.")
    void medicoPossuiConsultaProximoAoHorario() {
        LocalDateTime consultaMarcadaSegundaAs10 = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10,0);

        LocalDateTime proximaSegundaAs10E30 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,30);
        var medico = cadastraMedico("Matheus", "matheus.yoko@voll.med", "123456", TipoEspecialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Isabela", "isabela@gemail.com", "12345678920");
        var paciente2 = cadastrarPaciente("Matheus", "matheus@gemail.com", "12345678921");
        cadastrarConsulta(medico, paciente, consultaMarcadaSegundaAs10, false);

        Long qtdConsulta = medicoRepository.medicoPossuiConsulta(medico.getId(), proximaSegundaAs10E30);
        assertThat(qtdConsulta).isGreaterThan(0);
    }

    @Test
    @DisplayName("Não deve retornar nenhuma consulta para o medico buscado")
    void medicoNaoPossuiConsultaNoHorario() {
        LocalDateTime proximaSegundaAs10 = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10,0);
        var medico = cadastraMedico("Matheus", "matheus.yoko@voll.med", "123456", TipoEspecialidade.CARDIOLOGIA);
        cadastrarPaciente("Isabela", "isabela@gemail.com", "12345678920");


        Long qtdConsulta = medicoRepository.medicoPossuiConsulta(medico.getId(), proximaSegundaAs10);
        assertThat(qtdConsulta).isEqualTo(0);
    }




    private Medico cadastraMedico(String nome, String email, String crm, TipoEspecialidade especialidade){
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        entityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf){
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        entityManager.persist(paciente);
        return paciente;
    }

    private Consulta cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime horario, Boolean cancelado){
        var consulta = new Consulta(medico, paciente, horario, cancelado);
        entityManager.persist(consulta);
        return consulta;
    }


    private MedicoCadastroDTO dadosMedico(String nome, String email, String crm, TipoEspecialidade especialidade){
        return new MedicoCadastroDTO(nome, email, crm , "43991735285",  especialidade, dadosEndereco());
    }

    private CadastroPacienteDTO dadosPaciente(String nome, String email,  String cpf){
        return new CadastroPacienteDTO(nome, email, "43991735285", cpf, dadosEndereco());
    }

    private EnderecoDTO dadosEndereco(){
        return new EnderecoDTO(
            "rua 1",
            "bairro",
            "Jesuitas",
            "PR",
            "12345678",
            "1",
            "complemento"
        );
    }

}