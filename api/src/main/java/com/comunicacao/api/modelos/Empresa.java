package com.comunicacao.api.modelos;

import java.sql.Date;

import lombok.Data;

@Data
public class Empresa {
	private Long id;
	private String razaoSocial;
	private String nomeFantasia;
	private Date cadastro;

}
