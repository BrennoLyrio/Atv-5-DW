package com.comunicacao.api.modelos;

import lombok.Data;

import java.util.Set;

@Data
public class Venda {
    private Long id;
    private String identificacao;
    private String cadastro;

    private Usuario cliente;
    private Usuario funcionario;
    private Veiculo veiculo;

    private Set<Servico> servicos;
    private Set<Mercadoria> mercadorias;
}
