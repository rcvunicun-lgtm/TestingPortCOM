package com.ingecode.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PanelContenidoController {

	@FXML
	private Button btnTexto;

	@FXML
	private Button btnExcel;
	
	@FXML
	private Button btnLimpiarTablaHTML;

	@FXML
	public WebView webViewEditor;

	public WebEngine webEngine;

	public String contenidoHtml;

	private boolean paginaCargada = false;

	@FXML
	public void initialize() {

		webEngine = webViewEditor.getEngine();

		// Ya no cargamos aquí un HTML fijo
		webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
				paginaCargada = true;
			}
		});
	}

	// Event Listener on Button[#btnConectar].onAction
	@FXML
	public void exportarComoTexto(ActionEvent event) {
		// System.out.println("Hola mundo");
	}
	
	@FXML
	public void exportarComoExcel(ActionEvent event) {
		// System.out.println("Hola mundo");
	}

	@FXML
	public void limpiarTablaHTML(ActionEvent event) {
		// System.out.println("Hola mundo");
	}
	
	public void exportarComoTxt(Runnable accion) {
		this.btnTexto.setOnAction(e -> {
			accion.run(); // Ejecutas la acción adicional
			exportarComoTexto(e); // Llamas al método guardarTxt
		});
	}
	
	public void exportarComoExcel(Runnable accion) {
		this.btnExcel.setOnAction(e -> {
			accion.run(); // Ejecutas la acción adicional
			exportarComoExcel(e); // Llamas al método guardarTxt
		});
	}

	public void cargarHTML(String html) {
		this.contenidoHtml = html;
		webEngine.loadContent(contenidoHtml);
	}
	
	public void limpiarTablaHTML(Runnable accion) {
		this.btnLimpiarTablaHTML.setOnAction(e -> {
			accion.run(); // Ejecutas la acción adicional
			limpiarTablaHTML(e); // Llamas al método guardarTxt
		});
	}
}
