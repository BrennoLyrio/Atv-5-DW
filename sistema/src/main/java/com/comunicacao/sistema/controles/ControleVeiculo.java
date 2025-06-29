package com.comunicacao.sistema.controles;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comunicacao.sistema.entidades.Veiculo;
import com.comunicacao.sistema.repositorios.RepositorioVeiculo;

@RestController
@RequestMapping("/veiculos")
public class ControleVeiculo {

    private final RepositorioVeiculo repositorio;

    public ControleVeiculo(RepositorioVeiculo repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> obterVeiculos() {
        List<Veiculo> veiculos = repositorio.findAll();
        return ResponseEntity.ok(veiculos);
    }
}
