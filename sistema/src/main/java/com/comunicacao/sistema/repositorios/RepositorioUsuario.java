package com.comunicacao.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comunicacao.sistema.entidades.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
}