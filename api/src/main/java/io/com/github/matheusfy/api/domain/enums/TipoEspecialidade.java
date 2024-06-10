package io.com.github.matheusfy.api.domain.enums;

public enum TipoEspecialidade {
    ORTOPEDIA,
    CARDIOLOGIA,
    GINECOLOGIA,
    DERMATOLOGIA;

    private TipoEspecialidade classificacao(String especialidade){
        for(TipoEspecialidade categoria : TipoEspecialidade.values()){
            if(categoria.toString().equalsIgnoreCase(especialidade)){
                return categoria;
            }
        }
        // TODO: Criar um tipo de exceção de categoria passada nao encontrada
        throw new RuntimeException();
    }
}
