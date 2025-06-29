package com.comunicacao.api.modelos;

import lombok.Data;

@Data
public class Mercadoria {
    private Long id;
    private String nome;
    private long quantidade;
    private double valor;
    private String descricao;
    private String validade;
    private String fabricao;
    private String cadastro;
}
