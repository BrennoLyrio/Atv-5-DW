package com.comunicacao.sistema.controles;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comunicacao.sistema.entidades.Mercadoria;
import com.comunicacao.sistema.repositorios.RepositorioMercadoria;

@RestController
@RequestMapping("/mercadorias")
public class ControleMercadoria {
	
	private final RepositorioMercadoria repositorio;

    public ControleMercadoria(RepositorioMercadoria repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public ResponseEntity<List<Mercadoria>> obterMercadorias() {
        List<Mercadoria> mercadorias = repositorio.findAll();
        return ResponseEntity.ok(mercadorias);
    }

}
