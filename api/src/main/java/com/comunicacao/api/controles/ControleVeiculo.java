package com.comunicacao.api.controles;

import java.util.List;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Veiculo;

@RestController
@RequestMapping("/api")
public class ControleVeiculo {

    @GetMapping("/veiculos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Veiculo>> obterVeiculos(@RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entidade = new HttpEntity<>(headers);

        ResponseEntity<Veiculo[]> resposta = new RestTemplate()
            .exchange("http://localhost:8080/veiculos", HttpMethod.GET, entidade, Veiculo[].class);

        List<Veiculo> veiculos = List.of(resposta.getBody());
        return ResponseEntity.ok(veiculos);
    }
}
