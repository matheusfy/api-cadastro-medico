package io.com.github.matheusfy.api.domain.consulta;

import io.com.github.matheusfy.api.domain.medico.Medico;
import io.com.github.matheusfy.api.domain.paciente.Paciente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import static io.com.github.matheusfy.api.domain.Util.Util.dateFromLocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private Boolean cancelado;

    private LocalDateTime dataHora;

    public Consulta(@NonNull Medico medico, @NonNull Paciente paciente, @NotBlank LocalDateTime dataHora,
            Boolean cancelado) {
        this.id = null;
        this.medico = medico;
        this.paciente = paciente;
        this.dataHora = dataHora;
        this.cancelado = cancelado;
    }

    @Override
    public String toString() {

        return """
                Medico: %s
                Paciente: %s
                Horario consulta: %s
                """.formatted(medico.getNome(), paciente.getNome(), dateFromLocalDateTime(dataHora));
    }

    public void cancelaConsulta() {
        this.cancelado = true;
    }
}
