package com.ingecode.principal;

import javafx.scene.image.Image;

public class Main {
public static void main(String[] args) {
		
		VentanaPrincipal.launch(VentanaPrincipal.class, args);
	}
}

/*

clean package
java -jar TestingPortCom-0.0.1-SNAPSHOT-jar-with-dependencies.jar


#### Pasos para exportar un proyecto de escritorio con Java y Maven #### 

	## Consideraciones a tener en cuenta ##
	
		# Todos los recursos externos que vamos a usar en nuestro programa como:
		  Librerias, Imagenes, archivos txt, excel, y si usamos JavaFx los archivos .fxml y .css
		  deben estar alojados dentro de la ruta de nuestro proyecto (java/main/resources)
		  
		  Si los alojamos dentro de (src/main/java) y creamos una propia carpeta para guardar esos 
		  recursos externos en el compilador de java va a funcionar perfecto, pero a la hora de 
		  generar el ejecutable (.exe) para nuestro escritorio no va a funcionar.
		  
		  Dentro de la ruta (src//main/java) solo debemos alojar los paquetes y archivos .java
		  
		  Veamos el siguiente ejemplo
		  Image img1 = new Image(getClass().getResource("/resources/me.png").toExternalForm());
		  el metodo getResource() busca dentro de la ruta (java/main/resources) y a partir de ahi debemos 
		  contunuar con un slash (/) y colocar el nombre de la carpeta donde esta alojado nuestro recurso.
		 
		  Tambien debemos tener los siguientes puguins en nuestro archivo pom.xml
		  Para ver el contenido ver en el archivo pom.xml
		  
		   	<!-- Compilador -->
		   	<!-- JavaFX Maven Plugin (para correr desde mvn javafx:run) -->
		   	<!-- Assembly plugin para crear JAR ejecutable con dependencias -->
		   	
		  Posiblemente tambien debamos instalar una version actual de Maven en nuestro entorno de desarrollo
		  de Eclipse.
		   	
		# Paso 1 (Limpiar nuestro proyecto)
			- Seleccionar la pestaña Project de nuestro entorno de desarrollo de eclipse.
			- Seleccionar la opción de clean
			- Seleccionar nuestro proyecto
		   
		# Paso 2 (Verificar que esten instaladas las dependencias del archivo pom.xml)
			- Clic derecho sobre nuestro proyecto, seleccionar la opcion Maven.
			- Seleccionar Update Project.
			- Seleccionar nuestro proyecto.
			- Verificar que esten seleccionadas las casillas de:
				- Force Update of Snapshots / Releases
				- Update projecto configuration from pom.xml
				- Refresh workpace resources from local filesystem
				- Clean projects
		# Paso 3 (Crear el archivo jar de nuestro proyecto usando Maven)
			
			- Forma 1
				- Clic derecho en nuestro proyecto
				- Seleccionar la opcion Run as
				- Seleccionar Maven Clean
				- Nuevamente ir a Run As
				- Seleccionar Maven Install
				
						
			- Forma 2
				- Clic derecho en nuestro proyecto
				- Seleccionar la opcion Run as
				- Seleccionar Maven build
				- En el campo Goals escribir "clean package"
				- Dar click en el boton de Run.
				
				
		# Paso 4 verificar creacion de archivo .jar
		
			- Dentro de la carpeta target de nuestro proyecto se tuvieron que
			  haber creado algunas carpetas y dos ficheros .jar
			  uno de esos fichero tiene el nombre del proyecto tunto con la palabra 
			  -with-dependencies.jar ese archivo es el que vamos a ejecutar en cmd como administrador
			  para verificar que si haya quedado bien construido el proyecto
			  
			  java -jar TestingPortCom-0.0.1-SNAPSHOT-jar-with-dependencies.jar
			  
		# Paso 5 crear el .exe con el programa launch4j
		
		# Paso 6 crear el instalador con innosetup.
		
			

 ##### Otra fora para crear un jar ejecutable #####
 	
 	
 	##### Este es el comando que estoy usando en VM arguments de eclipse #####

	--module-path "D:\Proyectos personales\Programacion\Java Eclipse 2024\01_segundo_proyectoCon_maven_y_JavaFX\src\main\java\lib\javafx-sdk-11.0.2\lib" 
	--add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web,javafx.swt


	--module-path "src\main\java\com\ingecode\lib\javafx-sdk-11.0.2\lib" 
	--add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web,javafx.swt
	
 	
 	1-) Verificar que en run configurations -> vm arguments (esten los modulos añadidos)
 	2-) Tener maven instalado (verificar en cmd con mvn-v)
 	3-) Dirigirnos con cmd a la raiz de nuestro proyecto (donde esta nuestro archivo pom.xml) "D:\Proyectos personales\Programacion\Java Eclipse 2024\01_segundo_proyectoCon_maven_y_JavaFX"
	4-) escribir este comando (mvn clean package)
		
		-> Una vez escrito el comando dentro de la carpeta target se nos crearan unas carpetas de mas y lo que nos importa DOS ARCHIVOS .JAR
		
	5-) desde cmd estando en la misma ruta de nuestro proyecto entramos a la carpeta target (cd target)
	6-) Escribimos el comando:
	
		java --module-path "D:\Proyectos personales\Programacion\Java Eclipse 2024\01_segundo_proyectoCon_maven_y_JavaFX\src\main\java\lib\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar 01_segundo_proyectoCon_maven_y_JavaFX-1.0.0.jar
	
		-> Este comando debe contener:
			1-) la ruta de los modulos de javaFX (--module-path "")
			2-) Los modulos requeridos (--add-modules javafx.controls,javafx.fxml)
			3-) El comando de java que ejecuta el jar junto con el nombre de nuestro .jar previamente creado (-jar 01_segundo_proyectoCon_maven_y_JavaFX-1.0.0.jar)
	
*/

/*
 ##### Otra fora para crear un jar ejecutable (ESTA FORMA ES RECOMENDADA CUANDO NO ESTAMOS USANDO MAVEN) #####
 
 	Tener en Run -> Run configurations -> Arguments 
 	los siguientes lineas para decirle a eclipse que estamaos usando JafaFx
	(RECORDAR QUE ESOS JAR EXTERNOS DEBEN ESTAR DESCARGADOS Y COLOCADOS EN LA CARPETA QUE VAYAMOS A UTILIZAR)
	(LA RUTA DE ESA CARPETA ES LA QUE DEBEMOS COLOCAR EN EL COMANDO DE ABAJO.)

		--module-path "src\lib\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib" 
		--add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web,javafx.swt
		
	Dar clic derecho en nuestro proyecto Exportar
	
		- Buscar la opcion de Java
		- escoger la opcion de -> Runnable Jar file
		
	##### Para que el comando de ejecucion de java funcione el archivo .jar debe estar extraido #####
	
		Este comando se debe usar cuando ejecutamos la aplicación desde CMD
	
			java --module-path "lib\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib" --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web -classpath "lib/jSerialComm-2.11.0.jar;lib/poi-3.16.jar" -jar AplicacionDePesaje.jar 
		
			java --module-path "D:\Descargas\Eclipse Complementos\javaFX\JavaFx11.0.2\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib" --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web -classpath "lib/jSerialComm-2.11.0.jar;lib/poi-3.16.jar" -jar AplicacionDePesaje.jar 
	
	
	##### Este comando se debe usar cuando estemos pasando el parametro de JVM options en Launch4j #####
	
		--module-path "lib\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib" 
		--add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web,javafx.swt

*/

