package com.comunicacao.api.modelos;

import lombok.Data;

@Data
public class Veiculo {
	private Long id;
    private String modelo;
    private String placa;
    private String tipo;
    private Usuario proprietario;
}
