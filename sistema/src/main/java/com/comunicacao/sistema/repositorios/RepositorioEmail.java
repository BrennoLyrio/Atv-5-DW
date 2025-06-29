package com.comunicacao.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comunicacao.sistema.entidades.Email;

@Repository
public interface RepositorioEmail extends JpaRepository<Email, Long>{

}
