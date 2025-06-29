package com.comunicacao.sistema.controles;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comunicacao.sistema.entidades.Venda;
import com.comunicacao.sistema.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/vendas")
public class ControleVenda {

    private final RepositorioVenda repositorio;

    public ControleVenda(RepositorioVenda repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public ResponseEntity<List<Venda>> obterVendasPorPeriodo(
            @RequestParam("inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam("fim") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fim) {
        List<Venda> vendas = repositorio.findByCadastroBetween(inicio, fim);
        return ResponseEntity.ok(vendas);
    }
    
    @GetMapping("/todas")
    public ResponseEntity<List<Venda>> obterTodasVendas() {
        return ResponseEntity.ok(repositorio.findAll());
    }
}
