package io.com.github.matheusfy.api.controller.consulta;

import io.com.github.matheusfy.api.domain.consulta.ConsultaService;
import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.domain.consulta.dto.ConsultaCadastradaDTO;
import io.com.github.matheusfy.api.domain.enums.TipoEspecialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CadastroConsultaDTO> dadosCadastroConsultaDTO;

    @Autowired
    private JacksonTester<ConsultaCadastradaDTO> dadosConsultaCadastradaDTO;

    @MockBean
    ConsultaService consultaService;


    @Test
    @DisplayName("Deveria devolver codigo 400 quando informações estão inválidas")
    @WithMockUser
    void cadastraConsultaInvalida() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("/consulta"))
            .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo 200 quando informações estão válidas")
    @WithMockUser
    void cadastraConsultaValida() throws Exception {

        LocalDateTime data = LocalDateTime.now().plusHours(1);
        TipoEspecialidade especialidade = TipoEspecialidade.CARDIOLOGIA;

        ConsultaCadastradaDTO consultaDadosEsperado = new ConsultaCadastradaDTO(null, 2L, 5L, data);
        when(consultaService.cadastrarConsulta(any())).thenReturn(consultaDadosEsperado);

        MockHttpServletResponse response = mvc.perform(post("/consulta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosCadastroConsultaDTO.write(
                    new CadastroConsultaDTO(2L, 5L, data, especialidade)
                ).getJson()))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosConsultaCadastradaDTO.write(
            consultaDadosEsperado
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}