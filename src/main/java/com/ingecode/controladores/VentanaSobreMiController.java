package com.ingecode.controladores;

import java.awt.Desktop;
import java.net.URI;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class VentanaSobreMiController {

	@FXML
	private Label linkedin;
	@FXML
	private ImageView imageView;
	@FXML
	private AnchorPane panelPrincipal;
	
	@FXML
	public void initialize() {
		
		Image img1 = new Image(getClass().getResource("/resources/me.png").toExternalForm());
		//ImageView icono1 = new ImageView(img1);
		//icono1.setFitWidth(16);
		//icono1.setFitHeight(16);
		//icono1.setPreserveRatio(true); // Mantiene la proporción
		imageView.setImage(img1);
		
	     linkedin.setCursor(Cursor.HAND); // Cambia el cursor a mano al pasar

	        // Configurar clic directamente aquí
	        linkedin.setOnMouseClicked(e -> {
	            // Acción extra
	            System.out.println("Acción extra ejecutada");
	            // Acción principal
	            irALinkedin(e);
	        });
	}
	@FXML
	public void irALinkedin(MouseEvent event) {
		  try {
		        Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/tuusuario"));
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}
	


}
