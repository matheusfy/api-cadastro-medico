package io.com.github.matheusfy.api.domain.paciente;

import io.com.github.matheusfy.api.domain.consulta.Consulta;
import io.com.github.matheusfy.api.domain.endereco.Endereco;
import io.com.github.matheusfy.api.domain.paciente.dto.CadastroPacienteDTO;
import io.com.github.matheusfy.api.domain.paciente.dto.pacienteUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.com.github.matheusfy.api.domain.Util.Util.validString;

@Getter
@Entity
@Table(name = "pacientes")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String email;
    private String cpf;

    private boolean ativo;
    private LocalDateTime lastUpdate;

    @Embedded
    Endereco endereco;

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas = new ArrayList<>();

    public Paciente(CadastroPacienteDTO paciente){
        this.nome = paciente.nome();
        this.telefone = paciente.telefone();
        this.email = paciente.email();
        this.cpf = paciente.cpf();
        this.ativo = true;
        this.lastUpdate = LocalDateTime.now();
        this.endereco = new Endereco(paciente.endereco());
    }

    public void atualizarCadastro(pacienteUpdateDTO pacienteDTO) {
        boolean updated = false;
        if(pacienteDTO.enderecoDTO() != null){
            updated = this.endereco.atualizaEndereco(pacienteDTO.enderecoDTO());
        }
        if(validString(pacienteDTO.nome())){
            this.nome = pacienteDTO.nome();
            updated = true;
        }
        if(validString(pacienteDTO.telefone())){
            this.telefone = pacienteDTO.telefone();
            updated = true;
        }
        if(updated) this.lastUpdate = LocalDateTime.now();
    }

    public void desativar() {
        this.ativo = false;
    }

    public void addConsulta(Consulta consulta) {
        consultas.add(consulta);
    }
}
