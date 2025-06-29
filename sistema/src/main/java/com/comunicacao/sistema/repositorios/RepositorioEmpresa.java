package com.comunicacao.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comunicacao.sistema.entidades.Empresa;

public interface RepositorioEmpresa extends JpaRepository<Empresa, Long>{
	public Empresa findByRazaoSocial(String nome);
}
