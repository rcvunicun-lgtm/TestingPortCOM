package com.ingecode.principal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class VentanaPrincipal extends Application {

	@Override
	public void start(Stage primaryStage)  {
	
		// Obtener el área visible de la pantalla (sin la barra de tareas)
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        double ancho = visualBounds.getWidth();
        double alto = visualBounds.getHeight();
        
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/vistas/VentanaPrincipal.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("src/main/java/com/ingecode/vistas/VentanaPrincipal.fxml"));
			
			Scene scene = new Scene(root, ancho/2, alto/2);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Testing Port COM");
            // 🔹 Aquí colocas tu ícono
            primaryStage.getIcons().add(
                new Image(getClass().getResource("/resources/rs232.png").toExternalForm())
            );
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
