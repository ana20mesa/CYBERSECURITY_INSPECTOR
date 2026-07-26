package com.unad.cybersecurityinspector;

public class AlertaPdfDto {

    private String tipo;
    private String valor;

    public AlertaPdfDto(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }
}
