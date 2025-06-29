package com.comunicacao.api.controles;

import java.util.List;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Mercadoria;

@RestController
@RequestMapping("/api")
public class ControleMercadoria {

    @GetMapping("/mercadorias")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Mercadoria>> obterMercadorias(@RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entidade = new HttpEntity<>(headers);

        ResponseEntity<Mercadoria[]> resposta = new RestTemplate()
            .exchange("http://localhost:8080/mercadorias", HttpMethod.GET, entidade, Mercadoria[].class);

        List<Mercadoria> mercadorias = List.of(resposta.getBody());
        return ResponseEntity.ok(mercadorias);
    }
}
