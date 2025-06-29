package com.comunicacao.api.modelos;

import java.util.Set;

import com.comunicacao.api.enumeracao.PerfilUsuario;

import lombok.Data;

@Data
public class Usuario {
	private Long id;
    private String nome;
    private String nomeSocial;
    private Set<PerfilUsuario> perfis;
    private Set<Documento> documentos;
    private Set<Telefone> telefones;
    private Set<Email> emails;
    private Endereco endereco;
    private Veiculo veiculos;
    private Set<Mercadoria> mercadorias;
    private Set <Venda> vendas;
	
}