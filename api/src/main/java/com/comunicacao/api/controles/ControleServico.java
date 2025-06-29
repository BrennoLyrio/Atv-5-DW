package com.comunicacao.api.controles;

import java.util.List;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Servico;

@RestController
@RequestMapping("/api")
public class ControleServico {

    @GetMapping("/servicos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Servico>> obterServicos(@RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entidade = new HttpEntity<>(headers);

        ResponseEntity<Servico[]> resposta = new RestTemplate()
            .exchange("http://localhost:8080/servicos", HttpMethod.GET, entidade, Servico[].class);

        List<Servico> servicos = List.of(resposta.getBody());
        return ResponseEntity.ok(servicos);
    }
}
