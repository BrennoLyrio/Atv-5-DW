package com.comunicacao.api.controles;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Usuario;

@RestController
public class ControleUsuario {

    @GetMapping("/todos-usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> obterUsuarios(
            @RequestHeader("Authorization") String token) {

        // Preparar headers com o token para repassar ao sistema
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entidade = new HttpEntity<>(headers);

        // Chamar o sistema
        ResponseEntity<Usuario[]> resposta = new RestTemplate()
            .exchange("http://localhost:8080/usuarios", HttpMethod.GET, entidade, Usuario[].class);

        // Converter array para lista
        List<Usuario> usuarios = List.of(resposta.getBody());

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    //ROTAS NECESSARIAS /clientes e /funcionarios
    
    @GetMapping("/api/clientes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listarClientes(@RequestHeader("Authorization") String token) {
        List<Usuario> todos = buscarUsuarios(token);
        List<Usuario> clientes = todos.stream()
            .filter(u -> u.getPerfis().stream().anyMatch(p -> p.name().equals("ROLE_CLIENTE")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/api/funcionarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listarFuncionarios(@RequestHeader("Authorization") String token) {
        List<Usuario> todos = buscarUsuarios(token);
        List<Usuario> funcionarios = todos.stream()
            .filter(u -> u.getPerfis().stream().anyMatch(p -> p.name().equals("ROLE_FUNCIONARIO")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(funcionarios);
    }

    private List<Usuario> buscarUsuarios(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entidade = new HttpEntity<>(headers);

        ResponseEntity<Usuario[]> resposta = new RestTemplate()
            .exchange("http://localhost:8080/usuarios", HttpMethod.GET, entidade, Usuario[].class);

        return List.of(resposta.getBody());
    }
}
