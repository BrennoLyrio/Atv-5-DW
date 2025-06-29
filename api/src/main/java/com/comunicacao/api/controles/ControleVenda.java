package com.comunicacao.api.controles;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Venda;

@RestController
@RequestMapping("/api")
public class ControleVenda {

	@GetMapping("/vendas")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Venda>> obterVendas(
	        @RequestHeader("Authorization") String token,
	        @RequestParam(value = "inicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String inicio,
	        @RequestParam(value = "fim", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String fim) {

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<String> entidade = new HttpEntity<>(headers);

	    String url;
	    if (inicio != null && fim != null) {
	        url = "http://localhost:8080/vendas?inicio=" + inicio + "&fim=" + fim;
	    } else {
	        url = "http://localhost:8080/vendas/todas";
	    }

	    ResponseEntity<Venda[]> resposta = new RestTemplate()
	            .exchange(url, HttpMethod.GET, entidade, Venda[].class);

	    List<Venda> vendas = List.of(resposta.getBody());
	    return ResponseEntity.ok(vendas);
	}

}

//Exemplo que ficará: GET http://localhost:8081/api/vendas?inicio=2025-06-01&fim=2025-06-21
