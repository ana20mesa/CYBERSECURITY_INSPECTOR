package com.unad.cybersecurityinspector;

public class AlertaCarpetaDto {

    private String tipo;
    private String nombre;
    private String ruta;
    private String hora;

    public AlertaCarpetaDto(String tipo, String nombre, String ruta, String hora) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.ruta = ruta;
        this.hora = hora;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public String getHora() {
        return hora;
    }

}
