package com.comunicacao.api.filtros;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.comunicacao.api.jwt.ProvedorJwt;

public class Autorizador extends BasicAuthenticationFilter {
    private ProvedorJwt provedorJwt;
    private UserDetailsService servico;

    public Autorizador(AuthenticationManager authManager, ProvedorJwt provedorJwt, UserDetailsService servico) {
        super(authManager);
        this.provedorJwt = provedorJwt;
        this.servico = servico;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String cabecalho = request.getHeader("Authorization");
        ValidadorCabecalho validador = new ValidadorCabecalho(cabecalho);
        if (validador.validar()) {
            String jwt = new AnalisadorCabecalho(cabecalho).obterJwt();
            AutenticadorJwt autenticadorJwt = new AutenticadorJwt(jwt, provedorJwt, servico);
            UsernamePasswordAuthenticationToken autenticacao = autenticadorJwt.obterAutenticacao();
            if (autenticacao != null) {
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
        }
        chain.doFilter(request, response);
    }
}
