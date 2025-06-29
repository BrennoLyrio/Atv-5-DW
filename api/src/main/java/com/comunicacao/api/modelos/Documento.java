package com.comunicacao.api.modelos;

import lombok.Data;

@Data
public class Documento {
    private Long id;
    private String tipo;
    private String numero;
    private String dataEmissao;
}
