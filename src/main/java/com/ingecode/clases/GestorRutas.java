package com.ingecode.clases;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GestorRutas {

    // Nueva ruta del archivo en src/main/resources/resources
    private static final String RUTA_ARCHIVO = System.getProperty("user.dir")
            + "\\src\\main\\resources\\resources\\rutas.txt";

    private static String contenidoArchivo() {
        try {
            return new String(Files.readAllBytes(Paths.get(RUTA_ARCHIVO)));
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de rutas: " + e.getMessage());
            return "";
        }
    }

    public static String getRutaPredefinida() {
        String contenido = contenidoArchivo();
        // Buscamos la cadena entre "rutaPredefinida":" y la siguiente "
        return extraerValor(contenido, "\"rutaPredefinida\"\\s*:\\s*\"", "\"");
    }

    public static String getNuevaRuta() {
        String contenido = contenidoArchivo();
        return extraerValor(contenido, "\"nuevaRuta\"\\s*:\\s*\"", "\"");
    }

    public static boolean existeNuevaRuta() {
        return !getNuevaRuta().isEmpty();
    }

    // Método auxiliar para extraer valores
    private static String extraerValor(String texto, String inicioRegex, String finRegex) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(inicioRegex + "(.*?)" + finRegex);
        java.util.regex.Matcher m = p.matcher(texto);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
}
