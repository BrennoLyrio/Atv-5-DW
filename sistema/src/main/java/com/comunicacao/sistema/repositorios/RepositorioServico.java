package com.comunicacao.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comunicacao.sistema.entidades.Servico;

@Repository
public interface RepositorioServico extends JpaRepository<Servico, Long>{

}
