package io.com.github.matheusfy.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static io.com.github.matheusfy.api.domain.Util.Util.validString;


@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

    private String cep;

    public Endereco(EnderecoDTO enderecoDTO){
        this.logradouro = enderecoDTO.logradouro();
        this.numero = enderecoDTO.numero();
        this.complemento = enderecoDTO.complemento();
        this.bairro = enderecoDTO.bairro();
        this.cidade = enderecoDTO.cidade();
        this.uf = enderecoDTO.uf();
        this.cep = enderecoDTO.cep();
    }

    public EnderecoDTO toDTO(){
        return new EnderecoDTO(logradouro, bairro, cidade, uf, cep, numero, complemento);
    }

    public boolean atualizaEndereco(EnderecoDTO novoEndereco) {
        boolean updated = false;
        if(validString(novoEndereco.logradouro())){
            this.logradouro = novoEndereco.logradouro();
            updated = true;
        }
        if(validString(novoEndereco.numero())){
            this.numero = novoEndereco.numero();
            updated = true;
        }
        if( validString(novoEndereco.complemento())){
            this.complemento = novoEndereco.complemento();
            updated = true;
        }
        if(validString(novoEndereco.bairro())){
            this.bairro = novoEndereco.bairro();
            updated = true;
        }
        if(validString(novoEndereco.cidade())){
            this.cidade = novoEndereco.cidade();
            updated = true;
        }
        if(validString(novoEndereco.uf())){
            this.uf = novoEndereco.uf();
            updated = true;
        }
        if(validString(novoEndereco.cep())){
            this.cep = novoEndereco.cep();
            updated = true;
        }
        return updated;
    }
}
