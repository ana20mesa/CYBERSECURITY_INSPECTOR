package com.unad.cybersecurityinspector;

public class ArchivoRiesgosoDto {

    private String nombre;
    private String ruta;
    private String extension;
    private String descripcion;
    private String oculto;

    public ArchivoRiesgosoDto(String nombre, String ruta, String extension, String descripcion, String oculto) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.extension = extension;
        this.descripcion = descripcion;
        this.oculto = oculto;
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

    public String getDescripcion() {
        return descripcion;
    }

    public String getOculto() {
        return oculto;
    }

}
