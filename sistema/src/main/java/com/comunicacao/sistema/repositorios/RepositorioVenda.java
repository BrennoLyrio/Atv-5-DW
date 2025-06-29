package com.comunicacao.sistema.repositorios;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comunicacao.sistema.entidades.Venda;

@Repository
public interface RepositorioVenda extends JpaRepository<Venda, Long>{

	List<Venda> findByCadastroBetween(java.util.Date inicio, java.util.Date fim);
}
