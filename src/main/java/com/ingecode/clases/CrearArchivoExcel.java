package com.ingecode.clases;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CrearArchivoExcel {

    private ArrayList<String> encabezados;
    private ArrayList<ArrayList<String>> contenido;
	private LocalDateTime ahora = LocalDateTime.now();
	private String fechaHora;
	private String ruta;
	private String nombreArchivo;
	
    // Formato: Año-Mes-Día Hora:Minuto:Segundo
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

    public CrearArchivoExcel(ArrayList<String> encabezados, ArrayList<ArrayList<String>> contenido, String ruta, String nombreArchivo) {
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
        	this.nombreArchivo = "mensaje"+fechaHora;
        }else {
        	this.nombreArchivo = nombreArchivo;
        }
    }

    public boolean exportarComoExcel() {
        boolean exito = false; // variable para indicar si se creó correctamente

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            verificarRuta();

            // Crear hoja
            XSSFSheet sheet = workbook.createSheet("Datos Serial");

            int filaIndex = 0;

            // ----------------------
            // Encabezados
            // ----------------------
            XSSFRow filaEncabezado = sheet.createRow(filaIndex++);
            for (int i = 0; i < encabezados.size(); i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados.get(i));
            }

            // ----------------------
            // Contenido
            // ----------------------
            for (ArrayList<String> filaDatos : contenido) {
                XSSFRow fila = sheet.createRow(filaIndex++);
                for (int i = 0; i < filaDatos.size(); i++) {
                    Cell celda = fila.createCell(i);
                    celda.setCellValue(filaDatos.get(i));
                }
            }

            // Ajustar ancho de columnas automáticamente
            for (int i = 0; i < encabezados.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Donde se va a guardar el archivo
            File archivo = new File(this.ruta, this.nombreArchivo + ".xlsx");

            try (FileOutputStream fos = new FileOutputStream(archivo)) {
                workbook.write(fos);
            }

            // Si llegó aquí sin excepciones, se creó correctamente
            exito = true;

        } catch (IOException e) {
            System.err.println("Error al crear el archivo Excel: " + e.getMessage());
            exito = false; // por claridad
        }

        return exito; // retornamos el resultado
    }

    
	private void verificarRuta() {
		if (this.ruta == null && GestorRutas.existeNuevaRuta() == false) {
			this.ruta = GestorRutas.getRutaPredefinida();
		} else if (this.ruta == null && GestorRutas.existeNuevaRuta() == true) {
			this.ruta = GestorRutas.getNuevaRuta();
		} 
	}
}
