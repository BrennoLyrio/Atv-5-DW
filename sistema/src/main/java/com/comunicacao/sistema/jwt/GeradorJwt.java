package com.comunicacao.sistema.jwt;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GeradorJwt {
    private String assinatura;
    private Date expiracao;

    public GeradorJwt(String assinatura, long duracao) {
        this.assinatura = assinatura;
        this.expiracao = new Date(System.currentTimeMillis() + duracao);
    }

    public String gerarJwt(String nomeUsuario) {
        return Jwts.builder()
                .setSubject(nomeUsuario)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS512, assinatura.getBytes())
                .compact();
    }
}
