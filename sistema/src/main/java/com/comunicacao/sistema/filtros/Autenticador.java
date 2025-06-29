package com.comunicacao.sistema.filtros;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.comunicacao.sistema.entidades.CredencialUsuarioSenha;
import com.comunicacao.sistema.jwt.ProvedorJwt;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Autenticador extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager gerenciadorAutenticacao;
    private ProvedorJwt provedorJwt;

    public Autenticador(AuthenticationManager authManager, ProvedorJwt provedorJwt) {
        this.gerenciadorAutenticacao = authManager;
        this.provedorJwt = provedorJwt;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        CredencialUsuarioSenha credencial;
        try {
            credencial = new ObjectMapper().readValue(request.getInputStream(), CredencialUsuarioSenha.class);
        } catch (IOException e) {
            credencial = new CredencialUsuarioSenha();
            credencial.setNomeUsuario("");
            credencial.setSenha("");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                credencial.getNomeUsuario(), credencial.getSenha(), new ArrayList<>());

        return gerenciadorAutenticacao.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        UserDetails usuario = (UserDetails) authResult.getPrincipal();
        String jwt = provedorJwt.proverJwt(usuario.getUsername());
        response.addHeader("Authorization", "Bearer " + jwt);
    }
}
