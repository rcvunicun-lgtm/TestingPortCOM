package com.ingecode.clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CrearArchivoTexto {

	private ArrayList<String> encabezados;
	private ArrayList<ArrayList<String>> contenido;
	private LocalDateTime ahora = LocalDateTime.now();
	private String fechaHora;
	private String ruta;
	private String nombreArchivo;

    // Formato: Año-Mes-Día Hora:Minuto:Segundo
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

	public CrearArchivoTexto(ArrayList<String> encabezados, ArrayList<ArrayList<String>> contenido, String ruta,
			String nombreArchivo) {
		this.encabezados = encabezados;
		this.contenido = contenido;
		
		this.fechaHora = ahora.format(formato);
		if (ruta == null && GestorRutas.existeNuevaRuta() == false) {
			this.ruta = GestorRutas.getRutaPredefinida();
		} else if (ruta == null && GestorRutas.existeNuevaRuta() == true) {
			this.ruta = GestorRutas.getNuevaRuta();
		} else {
			this.ruta = ruta;
		}

		if (nombreArchivo == null) {
			this.nombreArchivo = "mensaje" + fechaHora + ".txt";
		} else {
			this.nombreArchivo = nombreArchivo;
		}
	}

	public boolean exportarComoTexto() {
	    boolean exito = false; // variable para saber si todo salió bien

	    try {
	        verificarRuta();

	        // Crear objeto File con la ruta completa
	        File archivo = new File(this.ruta, this.nombreArchivo);

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
	            // Definir formato de columnas (ajusta los anchos a tu gusto)
	            String formato = "%-12s %-15s %-18s %-12s %-10s %-10s %-10s %-10s %-10s%n";

	            // Escribir encabezados
	            writer.write(String.format(formato, encabezados.toArray(new String[0])));

	            // Escribir contenido fila por fila
	            for (ArrayList<String> fila : contenido) {
	                writer.write(String.format(formato, fila.toArray(new String[0])));
	            }
	        }

	        // Si llegó aquí, no hubo errores
	        exito = true;

	    } catch (IOException e) {
	        System.err.println("Error al crear el archivo: " + e.getMessage());
	        exito = false; // opcional por claridad
	    }

	    return exito; // retornamos si fue exitoso o no
	}

	
	private void verificarRuta() {
		if (this.ruta == null && GestorRutas.existeNuevaRuta() == false) {
			this.ruta = GestorRutas.getRutaPredefinida();
		} else if (this.ruta == null && GestorRutas.existeNuevaRuta() == true) {
			this.ruta = GestorRutas.getNuevaRuta();
		} 
	}
}
