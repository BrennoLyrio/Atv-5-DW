package com.comunicacao.api.filtros;

public class AnalisadorCabecalho {
    private String cabecalho;

    public AnalisadorCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public String obterJwt() {
        return cabecalho.split(" ")[1];
    }
}
