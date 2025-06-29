package com.comunicacao.sistema.filtros;

public class ValidadorCabecalho {
    private String cabecalho;

    public ValidadorCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public boolean validar() {
        return cabecalho != null && cabecalho.startsWith("Bearer ");
    }
}
