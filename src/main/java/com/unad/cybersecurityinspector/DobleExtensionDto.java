package com.unad.cybersecurityinspector;

public class DobleExtensionDto {

    private String nombre;
    private String ruta;
    private String extension;

    public DobleExtensionDto(String nombre, String ruta, String extension) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.extension = extension;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public String getExtension() {
        return extension;
    }

}
