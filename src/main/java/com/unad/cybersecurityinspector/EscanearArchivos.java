package com.unad.cybersecurityinspector;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class EscanearArchivos {

    private static final Set<String> EXTENSIONES_RIESGO = Set.of(
            "exe",
            "bat",
            "cmd",
            "msi",
            "vbs",
            "js",
            "jar",
            "ps1",
            "scr",
            "com",
            "pif",
            "cpl",
            "hta",
            "wsf",
            "wsh",
            "reg",
            "dll",
            "lnk",
            "iso",
            "img"
    );

    public List<ArchivoRiesgosoDto> escanear(Path carpeta) throws IOException {

        List<ArchivoRiesgosoDto> encontrados = new ArrayList<>();
        Files.walk(carpeta)
                .filter(Files::isRegularFile)
                .forEach(archivo -> {
                    String nombre = archivo.getFileName().toString();
                    int punto = nombre.lastIndexOf('.');
                    if (punto > 0) {
                        String extension = nombre.substring(punto + 1).toLowerCase();
                        if (EXTENSIONES_RIESGO.contains(extension)) {
                            try {
                                encontrados.add(new ArchivoRiesgosoDto(nombre, archivo.toString(), extension, descipcionExtension(extension), Files.isHidden(archivo) ? "SI" : "NO"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        return encontrados;
    }

    private String descipcionExtension(String tipo) {

        String descripcion = "Informacion";
        switch (tipo) {
            case "exe":
                descripcion = "Puede ejecutar malware";
                break;
            case "bat":
                descripcion = "Puede automatizar acciones maliciosas";
                break;
            case "cmd":
                descripcion = "Puede ejecutar comandos peligrosos";
                break;
            case "msi":
                descripcion = "Puede instalar software malicioso";
                break;
            case "vbs":
                descripcion = "Puede ejecutar código malicioso";
                break;
            case "js":
                descripcion = "Puede ejecutar scripts maliciosos";
                break;
            case "jar":
                descripcion = "Puede contener código malicioso";
                break;
            case "ps1":
                descripcion = "Puede automatizar ataques en Windows";
                break;
            case "scr":
                descripcion = "Puede ocultar malware como protector de pantalla";
                break;
            case "com":
                descripcion = "Puede ejecutar programas maliciosos";
                break;
            case "pif":
                descripcion = "Puede ocultar ejecutables maliciosos";
                break;
            case "cpl":
                descripcion = "Puede ejecutar applets del Panel de Control";
                break;
            case "hta":
                descripcion = "Puede ejecutar aplicaciones HTML con privilegios";
                break;
            case "wsf":
                descripcion = "Puede ejecutar múltiples scripts maliciosos";
                break;
            case "wsh":
                descripcion = "Puede ejecutar scripts del sistema";
                break;
            case "reg":
                descripcion = "Puede modificar el Registro de Windows";
                break;
            case "dll":
                descripcion = "Puede contener código malicioso reutilizable";
                break;
            case "lnk":
                descripcion = "Puede ocultar accesos directos maliciosos";
                break;
            case "iso":
                descripcion = "Puede distribuir malware empaquetado";
                break;
            case "img":
                descripcion = "Puede contener archivos maliciosos montables";
                break;
        }
        return descripcion;
    }


    public List<AlertaPdfDto> analizar(File archivo) throws IOException {

        List<AlertaPdfDto> resultados = new ArrayList<>();
        PDDocument document = Loader.loadPDF(archivo);
        PDFTextStripper stripper = new PDFTextStripper();
        String texto = stripper.getText(document);
        document.close();
        buscarCorreos(texto, resultados);
        buscarTelefonos(texto, resultados);
        buscarCedulas(texto, resultados);
        buscarTarjetas(texto, resultados);
        buscarPalabrasClave(texto, resultados);


        List<AlertaPdfDto> listaSinDuplicados = resultados.stream()
                .collect(Collectors.toMap(
                        AlertaPdfDto::getValor,
                        alerta -> alerta,
                        (primero, segundo) -> primero))
                .values()
                .stream()
                .toList();

        return listaSinDuplicados;

    }

    private void buscarCorreos(String texto, List<AlertaPdfDto> lista) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+");
        Matcher matcher = pattern.matcher(texto);
        while (matcher.find()) {
            lista.add(new AlertaPdfDto("Correo", matcher.group()));
        }
    }

    private void buscarCedulas(String texto, List<AlertaPdfDto> lista) {
        Pattern pattern = Pattern.compile("\\b\\d{6,12}\\b");
        Matcher matcher = pattern.matcher(texto);
        while (matcher.find()) {
            lista.add(new AlertaPdfDto("Identificación", matcher.group()));
        }
    }

    private void buscarTelefonos(String texto, List<AlertaPdfDto> lista) {

        Pattern pattern = Pattern.compile("\\b3\\d{9}\\b");

        Matcher matcher = pattern.matcher(texto);

        while (matcher.find()) {
            lista.add(new AlertaPdfDto("Teléfono", matcher.group()));
        }

    }

    private void buscarTarjetas(String texto, List<AlertaPdfDto> lista) {
        Pattern pattern = Pattern.compile("\\b(?:\\d[ -]*?){13,16}\\b");
        Matcher matcher = pattern.matcher(texto);
        while (matcher.find()) {
            lista.add(new AlertaPdfDto("Tarjeta", matcher.group()));
        }
    }

    private void buscarPalabrasClave(String texto, List<AlertaPdfDto> lista) {

        String[] palabras = {
                "CONFIDENCIAL",
                "CLASIFICADO",
                "PASSWORD",
                "TOKEN",
                "ADMIN",
                "CONTRASEÑA",
                "SECRETO",
                "PRIVADO"
        };

        String contenido = texto.toUpperCase();
        for (String palabra : palabras) {
            if (contenido.contains(palabra)) {
                lista.add(new AlertaPdfDto("Palabra sensible", palabra));
            }
        }
    }



}
