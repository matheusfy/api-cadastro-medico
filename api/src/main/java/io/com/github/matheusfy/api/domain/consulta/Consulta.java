package io.com.github.matheusfy.api.domain.consulta;


import io.com.github.matheusfy.api.domain.medico.Medico;
import io.com.github.matheusfy.api.domain.paciente.Paciente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalField;
import java.util.Locale;
import java.util.TimeZone;

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

    private LocalDateTime dataHora;

    public Consulta(@NonNull Medico medico, @NonNull Paciente paciente, @NotBlank LocalDateTime dataHora){
        this.id = null;
        this.medico = medico;
        this.paciente = paciente;
        this.dataHora = dataHora;
    }

    @Override
    public String toString(){

        return """
            Medico: %s
            Paciente: %s
            Horario consulta: %s
            """.formatted(medico.getNome(), paciente.getNome(), dateFromLocalDateTime(dataHora));
    }

}
