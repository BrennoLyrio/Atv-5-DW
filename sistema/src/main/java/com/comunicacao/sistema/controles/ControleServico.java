package com.comunicacao.sistema.controles;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comunicacao.sistema.entidades.Servico;
import com.comunicacao.sistema.repositorios.RepositorioServico;


@RestController
@RequestMapping("/servicos")
public class ControleServico {
	
	private final RepositorioServico repositorio;
	
	public ControleServico(RepositorioServico repositorio) {
		this.repositorio = repositorio;
	}
	
	@GetMapping
	public ResponseEntity<List<Servico>> obterServicos() {
		List<Servico> servicos = repositorio.findAll();
		return ResponseEntity.ok(servicos);
	}

}
