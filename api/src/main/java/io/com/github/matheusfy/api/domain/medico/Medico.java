package io.com.github.matheusfy.api.domain.medico;


import io.com.github.matheusfy.api.domain.consulta.Consulta;
import io.com.github.matheusfy.api.domain.endereco.Endereco;
import io.com.github.matheusfy.api.domain.enums.TipoEspecialidade;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoCadastroDTO;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.com.github.matheusfy.api.domain.Util.Util.validString;

@Entity
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String crm;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private TipoEspecialidade especialidade;

    @Embedded
    private Endereco endereco;

    private boolean ativo;
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas = new ArrayList<>();

    public Medico(MedicoCadastroDTO medicoCadastroDTO){
        this.id =  null;
        this.nome =  medicoCadastroDTO.nome();
        this.email =  medicoCadastroDTO.email();
        this.crm =  medicoCadastroDTO.crm();
        this.telefone = medicoCadastroDTO.telefone();
        this.especialidade =  medicoCadastroDTO.especialidade();
        this.endereco =  new Endereco(medicoCadastroDTO.endereco());
        this.ativo = true;
        this.lastUpdate = LocalDateTime.now();
    }

    public void updateInfo(MedicoUpdateDTO dadosMedicoAtualizado) {
        if(validString(dadosMedicoAtualizado.nome())) {
            this.nome = dadosMedicoAtualizado.nome();
        }
        if(validString(dadosMedicoAtualizado.telefone())){
            this.telefone = dadosMedicoAtualizado.telefone();
        }
        if(dadosMedicoAtualizado.endereco() != null){
            this.endereco.atualizaEndereco(dadosMedicoAtualizado.endereco());
        }
    }

    public void excluir(){
        this.ativo = false;
    }

    public void addConsulta(Consulta consulta){
        consultas.add(consulta);
    }

    @Override
    public String toString() {
        return "Medico: " +
            "nome='" + nome + '\'' +
            ", email='" + email + '\'' +
            ", ativo=" + ativo;
    }
}
