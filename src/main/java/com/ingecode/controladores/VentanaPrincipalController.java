package com.ingecode.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.ingecode.clases.CrearArchivoExcel;
import com.ingecode.clases.CrearArchivoTexto;
import com.ingecode.clases.FormateadorDatosSerial;

import com.ingecode.clases.PortCOM;
import com.ingecode.clases.SerialConectionHight;
import com.ingecode.clases.SerialOptions;
import com.ingecode.clases.VerificarTexto;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

public class VentanaPrincipalController {
	@FXML
	private MenuBar menuBar;
	@FXML
	private MenuItem menuItemEstablecerConexion;
	@FXML
	private MenuItem menuItemCerrarConexion;
	@FXML
	private MenuItem menuItemExportarExcel;
	@FXML
	private MenuItem menuItemExportarTxt;
	@FXML
	private MenuItem menuItemExportarComo;
	@FXML
	private MenuItem menuItemLimpiar;
	@FXML
	private MenuItem menuItemSalir;
	@FXML
	private MenuItem menuItemEditarRuta;
	@FXML
	private MenuItem menuItemReiniciarRuta;
	@FXML
	private MenuItem menuItemCargarPuertos;
	@FXML
	private MenuItem menuItemSobreMi;
	//////////////////////////////////////////////////

	@FXML
	private ImageView imgConectar;
	@FXML
	private ImageView imgDesconectar;
	@FXML
	private ImageView imgExportarTXT;
	@FXML
	private ImageView imgExportarExcel;
	@FXML
	private ImageView imgLimpiar;

	//////////////////////////////////////////////////
	@FXML
	private Label labelEstado;
	@FXML
	private ComboBox<String> comboPort;
	@FXML
	private ComboBox<String> comboBaudRate;
	@FXML
	private ComboBox<String> comboDataBits;
	@FXML
	private ComboBox<String> comboStopBits;
	@FXML
	private ComboBox<String> comboParity;
	@FXML
	private ComboBox<String> comboFlowControl;
	@FXML
	private ComboBox<String> comboTimeOuts;
	@FXML
	private Spinner<Integer> spinnerReadTime;
	@FXML
	private Spinner<Integer> spinnerWriteTime;
	@FXML
	private Spinner<Integer> spinnerWaitTime;
	@FXML
	private Button btnConectar;
	@FXML
	private Button btnDesconectar;

	//////////////////////////////////////////////////

	@FXML
	private CheckBox checkBinario;
	@FXML
	private CheckBox checkDecimalSigned;
	@FXML
	private CheckBox checkDecimalUnsigned;
	@FXML
	private CheckBox checkHexadecimal;
	@FXML
	private CheckBox checkAscii;
	@FXML
	private CheckBox checkUnicode;
	@FXML
	private CheckBox checkWIN1252;
	@FXML
	private CheckBox checkCP437;
	@FXML
	private CheckBox checkISO8859;
	@FXML
	private TextField tfCBinarios;
	@FXML
	private TextField tfCDecimalSigned;
	@FXML
	private TextField tfCDecimalUnsigned;
	@FXML
	private TextField tfCHexadecimal;
	@FXML
	private TextField tfCASCII;
	@FXML
	private TextField tfCUnicode;
	@FXML
	private TextField tfCWIN1252;
	@FXML
	private TextField tfCCP437;
	@FXML
	private TextField tfCISO8859;

	/////////////////////////////////////////////////

	@FXML
	private Button btnPanelCont1;
	@FXML
	private Button btnPanelCont2;

	/////////////////////////////////////////////////
	@FXML
	private RadioButton radioBtnASCII;
	@FXML
	private RadioButton radioBtnBinario;
	@FXML
	private RadioButton radioBtnDecimal;
	@FXML
	private RadioButton radioBtnHexadecimal;

	@FXML
	private TextArea txtAreaTexto;
	@FXML
	private Button btnEnviarTexto;
	@FXML
	private Button btnLimpiarTextArea;

	/////////////////////////////////////////////////

	@FXML
	private BorderPane borderPaneContenido;

	///////////////////////////////////////////////
	@FXML
	private TextArea txtAreaTextoRecibido;

	@FXML
	private Button btnLimpiarTextoRecibido;

	///////////////////////////////////////////////
	
	// 🔹 Atributo de clase para guardar el controlador de la ventana emergente
	private VentanaEstablecerConexionControlador ventanaConexionController;

	private VentanaSobreMiController ventanaSobreMiController;
	///////////////////////////////////////////////
	private Boolean conectado = false;
	
	private String strPort = "";
	
	private String strBaudRate = "";
	
	private String strDataBits = "";
	
	private String strStopBits = "";
	
	private String strParity = "";
	
	private String strFlowControl = "";
	
	private String strTimeOuts = "";
	
	private String strReadTime = "";
	
	private String strWriteTime = "";
	
	private String strWaitTime = "";
	
	///////////////////////////////////////////////
	private ArrayList<TextField> coloresTexto = new ArrayList<>();

	private String[] encabezadosHTML = { "", "", "", "", "", "", "", "", "" };

	private String encabezadoHTML = "<html>" + "<body>";

	private String encabezadoTablaHTML = "<table id='miTabla' border='1' style='width:100%; min-width:800px; text-align:center;'>";

	private String titulos = "<th>Binario</th>" + "<th>Decimal Signed</th>" + "<th>Decimal Unsigned</th>"
			+ "<th>Hexadecimal</th>" + "<th>ASCII</th>" + "<th>Unicode</th>" + "<th>WIN1252</th>" + "<th>CP437</th>"
			+ "<th>ISO 8859</th>";

	private String titulosDinamicos = "";

	private String contenidoTablaHTML = "";

	private String contenidoTablaHTML2 = ""; // Usado para el panel 2

	private String piePaginaTablaHTML = "</tr>" + "</table>";

	private String piePaginaCuerpoHTLM = "</body>" + "</html>";

	/////////////////////////////////////////////////

	// Crear el grupo
	private ToggleGroup grupo = new ToggleGroup();

	private VerificarTexto verificarTexto = new VerificarTexto();

	/////////////////////////////////////////////////

	private Parent secondaryContent1;

	private Parent secondaryContent2;

	private PanelContenidoController PanelContenidoController1;

	private PanelContenidoController PanelContenidoController2;

	private SerialConectionHight serial;

	private FormateadorDatosSerial formatear = new FormateadorDatosSerial();

	private ArrayList<String[]> mensajesRecolectados = new ArrayList<>();

	/////////////////////////////////////////////////

	@FXML
	public void initialize() {
		inicializarVariables();
		establecerIconos();
		insertarPaneles();
		llenarComboBox();
		llenarSpinner();
		activarCampos();
		crearRutasTxt();
	}

	@FXML
	public void menuItemEstablecerConexion(ActionEvent event) {
		
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/vistas/VentanaEstablecerConexion.fxml"));
			Parent root = loader.load();

			// 🔹 Aquí obtienes el controlador del FXML
			ventanaConexionController = loader.getController();
			
			if(conectado == true)
			{
				verificarDatosConexionVentanaPrincipal();
				ventanaConexionController.desactivarCampos();
			}
			if(conectado == false)
			{
				verificarDatosConexionVentanaPrincipal();
			
			}
			
			ventanaConexionController.establecerConexion(() -> {
				establecerDatosConexion(
						ventanaConexionController.getComboPort().getValue(),
						ventanaConexionController.getComboBaudRate().getValue(),
						ventanaConexionController.getComboDataBits().getValue(),
						ventanaConexionController.getComboStopBits().getValue(),
						ventanaConexionController.getComboParity().getValue(),
						ventanaConexionController.getComboFlowControl().getValue(),
						ventanaConexionController.getComboTimeOuts().getValue(),
						String.valueOf(ventanaConexionController.getSpinnerReadTime().getValue()),
						String.valueOf(ventanaConexionController.getSpinnerWriteTime().getValue()),
						String.valueOf(ventanaConexionController.getSpinnerWaitTime().getValue())
						);
				verificarDatosConexionVentanaEstablecerConexion();
				conectarasePuertoCOM();
				
				if(conectado == true)
				{				
					ventanaConexionController.desactivarCampos();
					ventanaConexionController.getLabelEstado().setText("Conectado");
				}
			});
			
			ventanaConexionController.cerrarConexion(() -> {
				ventanaConexionController.activarCampos();
				activarCampos();
				desconectarsePuertoCOM();
				ventanaConexionController.getLabelEstado().setText("Desconectado");
			});
			Stage stage = new Stage();
			stage.setTitle("Establecer conexión");
			stage.initModality(Modality.APPLICATION_MODAL);

			Window owner = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
			stage.initOwner(owner);

			stage.setScene(new Scene(root));
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void menuItemCerrarConexion(ActionEvent event) {
		desconectarsePuertoCOM();
	}

	@FXML
	public void menuItemExportarExcel(ActionEvent event) {
		exportarComoEXCEL();
	}

	@FXML
	public void menuItemExportarTxt(ActionEvent event) {
		exportarComoTXT();
	}

	@FXML
	public void menuItemExportarComo(ActionEvent event) {
		
		/*
		// Obtener Stage de la ventana actual:
        Stage stage = (Stage) menuBar.getScene().getWindow();

        // Crear el selector de carpetas
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta");

        // Mostrar la ventana y capturar carpeta seleccionada
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            String ruta = selectedDirectory.getAbsolutePath();
            System.out.println("Carpeta seleccionada: " + ruta);
            // Aquí ya puedes usar 'ruta' para guardar lo que quieras en esa carpeta
        }
        */
		
		// Obtener Stage de la ventana actual:
		Stage stage = (Stage) menuBar.getScene().getWindow();

		// Crear el selector de archivos
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar como...");

		// Opcional: establecer carpeta inicial
		// fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

		// Agregar filtros de extensión
		fileChooser.getExtensionFilters().addAll(
		    new FileChooser.ExtensionFilter("Archivos Excel (*.xlsx)", "*.xlsx"),
		    new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt")
		    //new FileChooser.ExtensionFilter("Todos los archivos (*.*)", "*.*")
		);

		// Mostrar ventana y capturar archivo seleccionado
		File selectedFile = fileChooser.showSaveDialog(stage);

		if (selectedFile != null) {
		    // Separar en dos variables
		    String nombreArchivo = selectedFile.getName();    // Nombre con extensión
		    String rutaCarpeta = selectedFile.getParent();    // Solo la ruta de la carpeta

		    //System.out.println("Ruta de la carpeta: " + rutaCarpeta);
		    //System.out.println("Nombre del archivo: " + nombreArchivo);

		    // Si quieres la ruta completa:
		    String rutaCompleta = selectedFile.getAbsolutePath();
		    //System.out.println("Ruta completa: " + rutaCompleta);

		    // Aquí guardas tu archivo usando rutaCompleta
		  

		    if (nombreArchivo.toLowerCase().endsWith(".xlsx")) {
		        exportarComoEXCEL(rutaCarpeta, nombreArchivo);
		    } else if (nombreArchivo.toLowerCase().endsWith(".txt")) {
		    	exportarComoTXT(rutaCarpeta, nombreArchivo);
		    } else {
		        System.out.println("Otro tipo de archivo");
		    }

		}


	}

	@FXML
	public void menuItemLimpiar(ActionEvent event) {
		limpiarTodosPaneles();
	}

	@FXML
	public void menuItemSalir(ActionEvent event) {
		desconectarsePuertoCOM();
	    Platform.exit(); // Cierra la aplicación JavaFX
	    System.exit(0);  // (opcional) asegura terminar el proceso
	}

	@FXML
	public void menuItemEditarRuta(ActionEvent event) {
	    String ruta = "";

	    // Obtener Stage de la ventana actual
	    Stage stage = (Stage) menuBar.getScene().getWindow();

	    // Crear el selector de carpetas
	    DirectoryChooser directoryChooser = new DirectoryChooser();
	    directoryChooser.setTitle("Seleccionar carpeta");

	    // Mostrar la ventana y capturar carpeta seleccionada
	    File selectedDirectory = directoryChooser.showDialog(stage);

	    if (selectedDirectory != null) {
	        ruta = selectedDirectory.getAbsolutePath();
	        // Asegurar que la ruta termine en "\"
	        if (!ruta.endsWith("\\")) {
	            ruta += "\\";
	        }
	    } else {
	        Alert alerta = new Alert(Alert.AlertType.WARNING);
	        alerta.setTitle("Aviso");
	        alerta.setHeaderText(null);
	        alerta.setContentText("No se seleccionó ninguna carpeta.");
	        alerta.showAndWait();
	        return;
	    }

	    String desktopPath = System.getProperty("user.home") + "\\Desktop";

	    // Nueva información que quieres poner en el archivo
	    String nuevoContenido = "{\n" +
	            "  \"rutas\": {\n" +
	            "    \"rutaPredefinida\": \"" + desktopPath + "\\\",\n" +
	            "    \"nuevaRuta\": \"" + ruta + "\"\n" +
	            "  }\n" +
	            "}";

	    // Ruta del archivo existente
	    String basePath = System.getProperty("user.dir");
	    String rutaArchivo = basePath + "\\src\\main\\resources\\resources\\rutas.txt";


	    // Sobrescribir contenido del archivo
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
	        writer.write(nuevoContenido);
	        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
	        alerta.setTitle("Confirmación");
	        alerta.setHeaderText(null);
	        alerta.setContentText("Nueva ruta seleccionada con éxito.");
	        alerta.showAndWait();
	        return;
	    } catch (IOException e) {
	        e.printStackTrace();
	        Alert alerta = new Alert(Alert.AlertType.ERROR);
	        alerta.setTitle("Error");
	        alerta.setHeaderText(null);
	        alerta.setContentText("Error al seleccionar la nueva ruta.");
	        alerta.showAndWait();
	    }
	}

	@FXML
	public void menuItemReiniciarRuta(ActionEvent event) {
		String desktopPath = System.getProperty("user.home") + "\\Desktop";
		
		  // Contenido que quieres guardar
      String contenido = "{\n" +
	            "  \"rutas\": {\n" +
	            "    \"rutaPredefinida\": \"" + desktopPath + "\\\",\n" +
	            "    \"nuevaRuta\": \"" + "" + "\"\n" +
	            "  }\n" +
	            "}";


        // Ruta del archivo existente
	    String basePath = System.getProperty("user.dir");
	    String rutaArchivo = basePath + "\\src\\main\\resources\\resources\\rutas.txt";

        // Sobrescribir contenido del archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            writer.write(contenido);
           // System.out.println("Archivo sobrescrito con éxito en: " + rutaArchivo);
            Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmación");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("Ruta por defecto restablecida.");
			alerta.showAndWait();
			return;
        } catch (IOException e) {
            e.printStackTrace();
            	Alert alerta = new Alert(AlertType.ERROR);
     			alerta.setTitle("Error");
     			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
     			alerta.setContentText("Error al restablecer la ruta.");
     			alerta.showAndWait();
        }
	}

	@FXML
	public void menuItemCargarPuertos(ActionEvent event) {
		llenarComboBox();
	}

	@FXML
	public void menuItemSobreMi(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/vistas/VentanaSobreMi.fxml"));
			Parent root = loader.load();
			// 🔹 Aquí obtienes el controlador del FXML
			this.ventanaSobreMiController = loader.getController();
			
			Stage stage = new Stage();
			stage.setTitle("Sobre Mí");
			stage.setResizable(false); // 🔹 Aquí lo bloqueas
			stage.initModality(Modality.APPLICATION_MODAL);

			Window owner = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
			stage.initOwner(owner);

			stage.setScene(new Scene(root));
			stage.showAndWait();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@FXML
	public void imgConectar(MouseEvent event) {
		establecerDatosConexion(
				comboPort.getValue(), 
				comboBaudRate.getValue(), 
				comboDataBits.getValue(),
				comboStopBits.getValue(),
				comboParity.getValue(),
				comboFlowControl.getValue(),
				comboTimeOuts.getValue(),				
				String.valueOf(spinnerReadTime.getValue()),
				String.valueOf(spinnerWriteTime.getValue()),
				String.valueOf(spinnerWaitTime.getValue())
				);
		conectarasePuertoCOM();
	}

	@FXML
	public void imgDesconectar(MouseEvent event) {
		desconectarsePuertoCOM();
	}

	@FXML
	public void imgExportarTXT(MouseEvent event) {
		exportarComoTXT();
	}

	@FXML
	public void imgExportarExcel(MouseEvent event) {
		exportarComoEXCEL();
	}

	@FXML
	public void imgLimpiar(MouseEvent event) {
		limpiarTodosPaneles();
	}

	@FXML
	public void conectar(ActionEvent event) {
		establecerDatosConexion(
				comboPort.getValue(), 
				comboBaudRate.getValue(), 
				comboDataBits.getValue(),
				comboStopBits.getValue(),
				comboParity.getValue(),
				comboFlowControl.getValue(),
				comboTimeOuts.getValue(),				
				String.valueOf(spinnerReadTime.getValue()),
				String.valueOf(spinnerWriteTime.getValue()),
				String.valueOf(spinnerWaitTime.getValue())
				);	
				
				conectarasePuertoCOM();
		}
	// Event Listener on Button[#btnDesconectar].onAction
	@FXML
	public void desconectar(ActionEvent event) {
		desconectarsePuertoCOM();
	}

	@FXML
	public void actionCheckBinario(ActionEvent event) {
		actualizarEncabezadosTabla(checkBinario, 0, "Binario");
	}

	@FXML
	public void actionCheckDecimalSigned(ActionEvent event) {
		actualizarEncabezadosTabla(checkDecimalSigned, 1, "Decimal Signed");
	}

	@FXML
	public void actionCheckDecimalUnsigned(ActionEvent event) {
		actualizarEncabezadosTabla(checkDecimalUnsigned, 2, "Decimal Unsigned");
	}

	@FXML
	public void actionCheckHexadecimal(ActionEvent event) {
		actualizarEncabezadosTabla(checkHexadecimal, 3, "Hexadecimal");
	}

	@FXML
	public void actionCheckAscii(ActionEvent event) {
		actualizarEncabezadosTabla(checkAscii, 4, "ASCII");
	}

	@FXML
	public void actionCheckUnicode(ActionEvent event) {
		actualizarEncabezadosTabla(checkUnicode, 5, "Unicode");
	}

	@FXML
	public void actionCheckWin1252(ActionEvent event) {
		actualizarEncabezadosTabla(checkWIN1252, 6, "WIN1252");
	}

	@FXML
	public void actionCheckCp437(ActionEvent event) {
		actualizarEncabezadosTabla(checkCP437, 7, "CP437");
	}

	@FXML
	public void actionCheckIso8859(ActionEvent event) {
		actualizarEncabezadosTabla(checkISO8859, 8, "ISO 8859");
	}

	@FXML
	public void cambiarPanel1(ActionEvent event) throws IOException {

		this.borderPaneContenido.setCenter(secondaryContent1);
	}

	@FXML
	public void cambiarPanel2(ActionEvent event) throws IOException {

		this.borderPaneContenido.setCenter(secondaryContent2);
	}

	@FXML
	public void enviarTexto(ActionEvent event) throws IOException {

		String textoAenviar = "";

		RadioButton seleccionado = (RadioButton) grupo.getSelectedToggle();

		if (seleccionado.getText().equals("ASCII")) {

			textoAenviar = textoAenviar.concat(this.txtAreaTexto.getText());

		}

		if (seleccionado.getText().equals("Binario")) {
			for (String cadena : this.txtAreaTexto.getText().split(" ")) {
				if (!verificarTexto.verificarBinario(cadena)) {
					Alert alerta = new Alert(AlertType.WARNING);
					alerta.setTitle("Error");
					alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
					alerta.setContentText("Formato del contenido no válido");
					alerta.showAndWait();
					return;
				}
			}

			for (String cadena : this.txtAreaTexto.getText().split(" ")) {
				formatear.setDatoBinario(cadena);
				textoAenviar = textoAenviar.concat(formatear.getASCII());
			}

		}

		if (seleccionado.getText().equals("Decimal")) {
			for (String cadena : this.txtAreaTexto.getText().split(" ")) {
				if (!verificarTexto.verificarDecimal(cadena)) {
					Alert alerta = new Alert(AlertType.WARNING);
					alerta.setTitle("Error");
					alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
					alerta.setContentText("Formato del contenido no válido");
					alerta.showAndWait();
					return;
				}
			}

			for (String cadena : this.txtAreaTexto.getText().split(" ")) {
				formatear.setDatoDecimal(Integer.parseInt(cadena));
				textoAenviar = textoAenviar.concat(formatear.getASCII());
			}
		}

		if (seleccionado.getText().equals("Hexadecimal")) {
			for (String cadena : this.txtAreaTexto.getText().split(" ")) {
				if (!verificarTexto.verificarHexadecimal(cadena)) {
					Alert alerta = new Alert(AlertType.WARNING);
					alerta.setTitle("Error");
					alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
					alerta.setContentText("Formato del contenido no válido");
					alerta.showAndWait();
					return;
				}
			}

			for (String cadena : this.txtAreaTexto.getText().split(" ")) {
				formatear.setDatoHexadecimal(cadena);
				textoAenviar = textoAenviar.concat(formatear.getASCII());
			}
		}

		serial.enviarTexto(textoAenviar + "\r\n");
	}

	@FXML
	public void limpiarTextArea(ActionEvent event) throws IOException {
		this.txtAreaTexto.setText("");
	}

	@FXML
	public void limpiarTextoRecibido(ActionEvent event) throws IOException {
		this.txtAreaTextoRecibido.setText("");
	}

	// METODOS AUXILIARES

	private void inicializarVariables() {

		serial = new SerialConectionHight();

		// Llenamo el arrayList coloresTexto
		coloresTexto.add(tfCBinarios);
		coloresTexto.add(tfCDecimalSigned);
		coloresTexto.add(tfCDecimalUnsigned);
		coloresTexto.add(tfCHexadecimal);
		coloresTexto.add(tfCASCII);
		coloresTexto.add(tfCUnicode);
		coloresTexto.add(tfCWIN1252);
		coloresTexto.add(tfCCP437);
		coloresTexto.add(tfCISO8859);

		// Asignar cada RadioButton al grupo
		radioBtnASCII.setToggleGroup(grupo);
		radioBtnBinario.setToggleGroup(grupo);
		radioBtnDecimal.setToggleGroup(grupo);
		radioBtnHexadecimal.setToggleGroup(grupo);

		// Seleccionar un RadioButton por defecto
		radioBtnASCII.setSelected(true);
	}

	private void establecerIconos() {
		Image img1 = new Image(getClass().getResource("/resources/turn_on.png").toExternalForm());
		ImageView icono1 = new ImageView(img1);
		icono1.setFitWidth(16);
		icono1.setFitHeight(16);
		icono1.setPreserveRatio(true); // Mantiene la proporción

		menuItemEstablecerConexion.setGraphic(icono1);

		Image img2 = new Image(getClass().getResource("/resources/turn_off.png").toExternalForm());
		ImageView icono2 = new ImageView(img2);
		icono2.setFitWidth(16);
		icono2.setFitHeight(16);
		icono2.setPreserveRatio(true); // Mantiene la proporción

		menuItemCerrarConexion.setGraphic(icono2);

		Image img3 = new Image(getClass().getResource("/resources/excel.png").toExternalForm());
		ImageView icono3 = new ImageView(img3);
		icono3.setFitWidth(16);
		icono3.setFitHeight(16);
		icono3.setPreserveRatio(true); // Mantiene la proporción

		menuItemExportarExcel.setGraphic(icono3);

		Image img4 = new Image(getClass().getResource("/resources/txt2.png").toExternalForm());
		ImageView icono4 = new ImageView(img4);
		icono4.setFitWidth(16);
		icono4.setFitHeight(16);
		icono4.setPreserveRatio(true); // Mantiene la proporción

		menuItemExportarTxt.setGraphic(icono4);

		Image img5 = new Image(getClass().getResource("/resources/folder.png").toExternalForm());
		ImageView icono5 = new ImageView(img5);
		icono5.setFitWidth(16);
		icono5.setFitHeight(16);
		icono5.setPreserveRatio(true); // Mantiene la proporción

		menuItemExportarComo.setGraphic(icono5);

		Image img6 = new Image(getClass().getResource("/resources/eraser.png").toExternalForm());
		ImageView icono6 = new ImageView(img6);
		icono6.setFitWidth(16);
		icono6.setFitHeight(16);
		icono6.setPreserveRatio(true); // Mantiene la proporción

		menuItemLimpiar.setGraphic(icono6);

		Image img7 = new Image(getClass().getResource("/resources/exit.png").toExternalForm());
		ImageView icono7 = new ImageView(img7);
		icono7.setFitWidth(16);
		icono7.setFitHeight(16);
		icono7.setPreserveRatio(true); // Mantiene la proporción

		menuItemSalir.setGraphic(icono7);

		Image img8 = new Image(getClass().getResource("/resources/path.png").toExternalForm());
		ImageView icono8 = new ImageView(img8);
		icono8.setFitWidth(16);
		icono8.setFitHeight(16);
		icono8.setPreserveRatio(true); // Mantiene la proporción

		menuItemEditarRuta.setGraphic(icono8);

		Image img9 = new Image(getClass().getResource("/resources/reset.png").toExternalForm());
		ImageView icono9 = new ImageView(img9);
		icono9.setFitWidth(16);
		icono9.setFitHeight(16);
		icono9.setPreserveRatio(true); // Mantiene la proporción

		menuItemReiniciarRuta.setGraphic(icono9);

		Image img10 = new Image(getClass().getResource("/resources/reload.png").toExternalForm());
		ImageView icono10 = new ImageView(img10);
		icono10.setFitWidth(16);
		icono10.setFitHeight(16);
		icono10.setPreserveRatio(true); // Mantiene la proporción

		menuItemCargarPuertos.setGraphic(icono10);

		Image img11 = new Image(getClass().getResource("/resources/about_me.png").toExternalForm());
		ImageView icono11 = new ImageView(img11);
		icono11.setFitWidth(16);
		icono11.setFitHeight(16);
		icono11.setPreserveRatio(true); // Mantiene la proporción

		menuItemSobreMi.setGraphic(icono11);
		// Crear tooltip
		Tooltip tooltip = new Tooltip("Conectar dispositivo"); // Texto que aparece al pasar el mouse
		Tooltip.install(imgConectar, tooltip);

		Tooltip tooltip2 = new Tooltip("Desconectar dispositivo"); // Texto que aparece al pasar el mouse
		Tooltip.install(imgDesconectar, tooltip2);

		Tooltip tooltip3 = new Tooltip("Exportar cómo TXT"); // Texto que aparece al pasar el mouse
		Tooltip.install(imgExportarTXT, tooltip3);

		Tooltip tooltip4 = new Tooltip("Exportar cómo Excel"); // Texto que aparece al pasar el mouse
		Tooltip.install(imgExportarExcel, tooltip4);

		Tooltip tooltip5 = new Tooltip("Limpiar"); // Texto que aparece al pasar el mouse
		Tooltip.install(imgLimpiar, tooltip5);
	}

	private void llenarSpinner() {
		// Crea un SpinnerValueFactory con un rango de valores
		SpinnerValueFactory<Integer> valueFactoryReadTime = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5000,
				100, 100);
		spinnerReadTime.setValueFactory(valueFactoryReadTime);

		SpinnerValueFactory<Integer> valueFactoryWriteTime = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5000,
				0, 100);
		spinnerWriteTime.setValueFactory(valueFactoryWriteTime);

		SpinnerValueFactory<Integer> valueFactoryWaitTime = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5000,
				100, 100);
		spinnerWaitTime.setValueFactory(valueFactoryWaitTime);
	}

	private void llenarComboBox() {
		// Llenar los ComboBox con opciones obtenidas desde PortCOM y SerialOptions
		comboPort.setItems(FXCollections.observableArrayList(PortCOM.obtenerPuertosDisponibles()));
		comboBaudRate.setItems(FXCollections.observableArrayList(SerialOptions.getBaudRates()));
		comboDataBits.setItems(FXCollections.observableArrayList(SerialOptions.getDataBits()));
		comboStopBits.setItems(FXCollections.observableArrayList(SerialOptions.getStopBits()));
		comboParity.setItems(FXCollections.observableArrayList(SerialOptions.getParities()));
		comboFlowControl.setItems(FXCollections.observableArrayList(SerialOptions.getFlowControls()));
		comboTimeOuts.setItems(FXCollections.observableArrayList(SerialOptions.getComPortTimeouts()));

		// Establecer un valor por defecto en comboTimeOuts
		if (!comboTimeOuts.getItems().isEmpty()) {
			comboTimeOuts.setValue(comboTimeOuts.getItems().get(1)); // Establece el primer valor como el predeterminado
		}
	}
	
	private Pair<Parent, PanelContenidoController> crearPanelContenido(String ventana) throws IOException {

		// Cargar el FXML secundario
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/PanelContenido.fxml"));

		// Obtener el nodo raíz del FXML secundario (puede ser VBox, AnchorPane, etc.)
		Parent secondaryContent = loader.load();

		// Obtener el controlador
		PanelContenidoController secundarioController = loader.getController();

		// Llamar a un método del controlador secundario para configurar el botón

		return new Pair<>(secondaryContent, secundarioController);

	}

	private void insertarPaneles() {
		try {
			Pair<Parent, PanelContenidoController> result1 = crearPanelContenido("Panel1");
			secondaryContent1 = result1.getKey();
			PanelContenidoController1 = result1.getValue();

			PanelContenidoController1.cargarHTML(encabezadoHTML + encabezadoTablaHTML + titulos + contenidoTablaHTML
					+ piePaginaTablaHTML + piePaginaCuerpoHTLM);

			PanelContenidoController1.exportarComoTxt(() -> {

				exportarComoTXT();
			});

			PanelContenidoController1.exportarComoExcel(() -> {
				exportarComoEXCEL();
			});

			PanelContenidoController1.limpiarTablaHTML(() -> {
				contenidoTablaHTML = "";
				mensajesRecolectados.clear();
				PanelContenidoController1.cargarHTML(encabezadoHTML + encabezadoTablaHTML + titulos + contenidoTablaHTML
						+ piePaginaTablaHTML + piePaginaCuerpoHTLM);
				PanelContenidoController2.cargarHTML(encabezadoHTML + encabezadoTablaHTML + titulos + contenidoTablaHTML
						+ piePaginaTablaHTML + piePaginaCuerpoHTLM);
			});

			this.borderPaneContenido.setCenter(secondaryContent1);

			Pair<Parent, PanelContenidoController> result2 = crearPanelContenido("Panel2");
			secondaryContent2 = result2.getKey();
			PanelContenidoController2 = result2.getValue();

			PanelContenidoController2.exportarComoTxt(() -> {

				ArrayList<String> encabezados = new ArrayList<>();
				ArrayList<ArrayList<String>> contenido = new ArrayList<>();

				for (String titulo : encabezadosHTML) {
					String limpio = titulo.replaceAll("<.*?>", "");
					encabezados.add(limpio.trim());
				}

				// Dentro de tu bucle
				for (String[] tramo : mensajesRecolectados) {
					for (String texto : tramo) {
						formatear.setDatoBinario(texto);

						ArrayList<String> fila = new ArrayList<>();

						// Recorremos los encabezados para mantener el mismo orden
						for (String encabezado : encabezados) {
							switch (encabezado) {
							case "Binario":
								fila.add(formatear.getBinario());
								break;
							case "Decimal Signed":
								fila.add(formatear.getDecimalSigned());
								break;
							case "Decimal Unsigned":
								fila.add(formatear.getDecimalUnsigned());
								break;
							case "Hexadecimal":
								fila.add(formatear.getHexadecimal());
								break;
							case "ASCII":
								fila.add(formatear.getASCII());
								break;
							case "Unicode":
								fila.add(formatear.getUnicode());
								break;
							case "Win1252":
								fila.add(formatear.getWin1252());
								break;
							case "CP437":
								fila.add(formatear.getCP437());
								break;
							case "ISO 8859":
								fila.add(formatear.getISO8859());
								break;
							default:
								fila.add(""); // por si llega un encabezado desconocido
							}
						}

						contenido.add(fila);
					}
				}

				CrearArchivoTexto texto = new CrearArchivoTexto(encabezados, contenido, null, null);
				texto.exportarComoTexto();
			});

			PanelContenidoController2.exportarComoExcel(() -> {
				ArrayList<String> encabezados = new ArrayList<>();
				ArrayList<ArrayList<String>> contenido = new ArrayList<>();

				for (String titulo : encabezadosHTML) {
					String limpio = titulo.replaceAll("<.*?>", "");
					encabezados.add(limpio.trim());
				}

				// Dentro de tu bucle
				for (String[] tramo : mensajesRecolectados) {
					for (String texto : tramo) {
						formatear.setDatoBinario(texto);

						ArrayList<String> fila = new ArrayList<>();

						// Recorremos los encabezados para mantener el mismo orden
						for (String encabezado : encabezados) {
							switch (encabezado) {
							case "Binario":
								fila.add(formatear.getBinario());
								break;
							case "Decimal Signed":
								fila.add(formatear.getDecimalSigned());
								break;
							case "Decimal Unsigned":
								fila.add(formatear.getDecimalUnsigned());
								break;
							case "Hexadecimal":
								fila.add(formatear.getHexadecimal());
								break;
							case "ASCII":
								fila.add(formatear.getASCII());
								break;
							case "Unicode":
								fila.add(formatear.getUnicode());
								break;
							case "Win1252":
								fila.add(formatear.getWin1252());
								break;
							case "CP437":
								fila.add(formatear.getCP437());
								break;
							case "ISO 8859":
								fila.add(formatear.getISO8859());
								break;
							default:
								fila.add(""); // por si llega un encabezado desconocido
							}
						}

						contenido.add(fila);
					}
				}

				CrearArchivoExcel excel = new CrearArchivoExcel(encabezados, contenido,null,null);
				excel.exportarComoExcel();
			});

			PanelContenidoController2.limpiarTablaHTML(() -> {
				contenidoTablaHTML = "";
				mensajesRecolectados.clear();
				PanelContenidoController2.cargarHTML(encabezadoHTML + encabezadoTablaHTML + titulos + contenidoTablaHTML
						+ piePaginaTablaHTML + piePaginaCuerpoHTLM);
				PanelContenidoController1.cargarHTML(encabezadoHTML + encabezadoTablaHTML + titulos + contenidoTablaHTML
						+ piePaginaTablaHTML + piePaginaCuerpoHTLM);
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void establecerDatosConexion(String puerto, String baudRate, String dataBits, String stopBits, String parity, String flowControl, String timeOuts, String readTime, String writeTime, String waitTime) {
		this.strPort = puerto;
		this.strBaudRate = baudRate;
		this.strDataBits = dataBits;
		this.strStopBits = stopBits;
		this.strParity = parity;
		this.strFlowControl = flowControl;
		this.strTimeOuts = timeOuts;
		this.strReadTime = readTime;
		this.strWriteTime = writeTime;
		this.strWaitTime = waitTime;
	}
	
	public void verificarDatosConexionVentanaPrincipal() {
		/*
		 * Esta funcion es utilizada para establecer los campos
		 * en la ventana VentanaEstablecerConexion.fxml cada vez
		 * que esta se abre desde la VentanaPrincipal.fxml
		 */
		if (
			    this.strPort != null && !this.strPort.isEmpty() &&
			    this.strBaudRate != null && !this.strBaudRate.isEmpty() &&
			    this.strDataBits != null && !this.strDataBits.isEmpty() &&
			    this.strStopBits != null && !this.strStopBits.isEmpty() &&
			    this.strParity != null && !this.strParity.isEmpty() &&
			    this.strFlowControl != null && !this.strFlowControl.isEmpty() &&
			    this.strTimeOuts != null && !this.strTimeOuts.isEmpty() &&
			    this.strReadTime != null && !this.strReadTime.isEmpty() &&
			    this.strWriteTime != null && !this.strWriteTime.isEmpty() &&
			    this.strWaitTime != null && !this.strWaitTime.isEmpty()
			    
			) {
				
			ventanaConexionController.getComboPort().setValue(this.strPort);
			ventanaConexionController.getComboBaudRate().setValue(this.strBaudRate);
			ventanaConexionController.getComboDataBits().setValue(this.strDataBits);
			ventanaConexionController.getComboStopBits().setValue(this.strStopBits);
			ventanaConexionController.getComboParity().setValue(this.strParity);
			ventanaConexionController.getComboFlowControl().setValue(this.strFlowControl);
			ventanaConexionController.getComboTimeOuts().setValue(this.strTimeOuts);
			ventanaConexionController.getSpinnerReadTime().getValueFactory().setValue(Integer.parseInt(this.strReadTime));
			ventanaConexionController.getSpinnerWriteTime().getValueFactory().setValue(Integer.parseInt(this.strWriteTime));
			ventanaConexionController.getSpinnerWaitTime().getValueFactory().setValue(Integer.parseInt(this.strWaitTime));
			
		}
	
	}
	
	public void verificarDatosConexionVentanaEstablecerConexion() {
		/*
		 * Esta funcion es utilizada para establecer los campos
		 * en la ventana VentanaEstablecerConexion.fxml cada vez
		 * que esta se abre desde la VentanaPrincipal.fxml
		 */
		
		if (
			    this.strPort != null && !this.strPort.isEmpty() &&
			    this.strBaudRate != null && !this.strBaudRate.isEmpty() &&
			    this.strDataBits != null && !this.strDataBits.isEmpty() &&
			    this.strStopBits != null && !this.strStopBits.isEmpty() &&
			    this.strParity != null && !this.strParity.isEmpty() &&
			    this.strFlowControl != null && !this.strFlowControl.isEmpty() &&
			    this.strTimeOuts != null && !this.strTimeOuts.isEmpty() &&
			    this.strReadTime != null && !this.strReadTime.isEmpty() &&
			    this.strWriteTime != null && !this.strWriteTime.isEmpty() &&
			    this.strWaitTime != null && !this.strWaitTime.isEmpty()
			) {
			this.comboPort.setValue(this.strPort);
			this.comboBaudRate.setValue(this.strBaudRate);
			this.comboDataBits.setValue(this.strDataBits);
			this.comboStopBits.setValue(this.strStopBits);
			this.comboParity.setValue(this.strParity);
			this.comboFlowControl.setValue(this.strFlowControl);
			this.comboTimeOuts.setValue(this.strTimeOuts);
			this.spinnerReadTime.getValueFactory().setValue(Integer.parseInt(this.strReadTime));
			this.spinnerWriteTime.getValueFactory().setValue(Integer.parseInt(this.strWriteTime));
			this.spinnerWaitTime.getValueFactory().setValue(Integer.parseInt(this.strWaitTime));
		}
	}
	
	public void conectarasePuertoCOM() {
		if (this.strPort == null || this.strBaudRate == null || this.strDataBits == null
				|| this.strStopBits == null || this.strParity == null
				|| this.strFlowControl == null) {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("Campos incompletos");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("Por favor, selecciona todos los campos obligatorios.");
			alerta.showAndWait();
			return;
		}

		if (this.strPort.equalsIgnoreCase("No hay puertos disponibles")) {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("Advertencia");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("Por favor, seleccione un puerto disponible");
			alerta.showAndWait();
			return;
		}

		serial.establecerCampos(comboPort.getValue(), comboBaudRate.getValue(), comboDataBits.getValue(),
				comboStopBits.getValue(), comboParity.getValue(), comboFlowControl.getValue(), comboTimeOuts.getValue(),
				String.valueOf(spinnerReadTime.getValue()), String.valueOf(spinnerWriteTime.getValue()),
				String.valueOf(spinnerWaitTime.getValue()));

		if (serial.abrirConexion()) {

			desactivarCampos();
			this.conectado = true;
			this.labelEstado.setText("Conectado");
			
			// Iniciar la lectura y mostrar los datos en los paneles
			this.serial.iniciarLecturaDatos(data -> {
				Platform.runLater(() -> {
					String textoRecibido[] = data.trim().split(" ");
					mensajesRecolectados.add(textoRecibido);

					actualizarContenidoTablaPanel1();
					actualizarContenidoTablaPanel2();
					actualizarContenidoTextArea(data);

				});
			});
		} else {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("Error");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("No se pudo abrir el puerto: " + this.serial.getPort().getSystemPortName());
			alerta.showAndWait();
			return;
		}
	}

	public void desconectarsePuertoCOM() {
		activarCampos();
		this.serial.cerrarConexion();
		this.conectado = false;
		this.labelEstado.setText("Desconectado");
	}


	private void crearRutasTxt() {
	    // Ruta del escritorio
	    String desktopPath = System.getProperty("user.home") + "\\Desktop";

	    // Contenido del archivo
	    String contenido = "{\n" +
	            "  \"rutas\": {\n" +
	            "    \"rutaPredefinida\": \"" + desktopPath + "\\\",\n" +
	            "    \"nuevaRuta\": \"" + "" + "\"\n" +
	            "  }\n" +
	            "}";

	    // Ruta de la carpeta dentro de src/main/resources/resources
	    String basePath = System.getProperty("user.dir");
	    File carpeta = new File(basePath, "src/main/resources/resources");

	    // Crear la carpeta si no existe
	    if (!carpeta.exists()) {
	        carpeta.mkdirs();
	    }

	    // Crear el archivo rutas.txt dentro de esa carpeta
	    File archivo = new File(carpeta, "rutas.txt");

	    // Verificar si existe y escribir
	    if (!archivo.exists()) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
	            writer.write(contenido);
	            System.out.println("Archivo creado con éxito en: " + archivo.getAbsolutePath());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("El archivo ya existe, no se creó uno nuevo.");
	    }
	}



	
	private void exportarComoTXT() {
		ArrayList<String> encabezados = new ArrayList<>();
		ArrayList<ArrayList<String>> contenido = new ArrayList<>();

		encabezados.add("Binario");
		encabezados.add("Decimal Signed");
		encabezados.add("Decimal Unsigned");
		encabezados.add("Hexadecimal");
		encabezados.add("ASCII");
		encabezados.add("Unicode");
		encabezados.add("Win 1252");
		encabezados.add("CP 437");
		encabezados.add("ISO 8859");
		for (String[] tramo : mensajesRecolectados) {
			for (String texto : tramo) {
				formatear.setDatoBinario(texto);
				ArrayList<String> fila = new ArrayList<>();
				fila.add(formatear.getBinario());
				fila.add(formatear.getDecimalSigned());
				fila.add(formatear.getDecimalUnsigned());
				fila.add(formatear.getHexadecimal());
				fila.add(formatear.getASCII());
				fila.add(formatear.getUnicode());
				fila.add(formatear.getWin1252());
				fila.add(formatear.getCP437());
				fila.add(formatear.getISO8859());
				contenido.add(fila);
			}
		}

		CrearArchivoTexto texto = new CrearArchivoTexto(encabezados, contenido, null, null);
		
		// excel.exportarComoTexto() tanto crea el archivo de texto como verifica si fue creado o no con éxito
		if(texto.exportarComoTexto()) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Éxito");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("El archivo fue creado con Éxito.");
			alerta.showAndWait();
			return;
		}else {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("Error");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("Hubo un problema al crear al archivo");
			alerta.showAndWait();
			return;
		}
	}
	
	private void exportarComoTXT(String ruta, String nombreArchivo) {
		ArrayList<String> encabezados = new ArrayList<>();
		ArrayList<ArrayList<String>> contenido = new ArrayList<>();

		encabezados.add("Binario");
		encabezados.add("Decimal Signed");
		encabezados.add("Decimal Unsigned");
		encabezados.add("Hexadecimal");
		encabezados.add("ASCII");
		encabezados.add("Unicode");
		encabezados.add("Win 1252");
		encabezados.add("CP 437");
		encabezados.add("ISO 8859");
		for (String[] tramo : mensajesRecolectados) {
			for (String texto : tramo) {
				formatear.setDatoBinario(texto);
				ArrayList<String> fila = new ArrayList<>();
				fila.add(formatear.getBinario());
				fila.add(formatear.getDecimalSigned());
				fila.add(formatear.getDecimalUnsigned());
				fila.add(formatear.getHexadecimal());
				fila.add(formatear.getASCII());
				fila.add(formatear.getUnicode());
				fila.add(formatear.getWin1252());
				fila.add(formatear.getCP437());
				fila.add(formatear.getISO8859());
				contenido.add(fila);
			}
		}

		CrearArchivoTexto texto = new CrearArchivoTexto(encabezados, contenido, ruta, nombreArchivo);
		// excel.exportarComoTexto() tanto crea el archivo de texto como verifica si fue creado o no con éxito
		if (texto.exportarComoTexto()) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Éxito");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("El archivo fue creado con Éxito.");
			alerta.showAndWait();
			return;
		} else {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("Error");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("Hubo un problema al crear al archivo");
			alerta.showAndWait();
			return;
		}
	}

	private void exportarComoEXCEL() {
		ArrayList<String> encabezados = new ArrayList<>();
		ArrayList<ArrayList<String>> contenido = new ArrayList<>();

		encabezados.add("Binario");
		encabezados.add("Decimal Signed");
		encabezados.add("Decimal Unsigned");
		encabezados.add("Hexadecimal");
		encabezados.add("ASCII");
		encabezados.add("Unicode");
		encabezados.add("Win 1252");
		encabezados.add("CP 437");
		encabezados.add("ISO 8859");
		for (String[] tramo : mensajesRecolectados) {
			for (String texto : tramo) {
				formatear.setDatoBinario(texto);
				ArrayList<String> fila = new ArrayList<>();
				fila.add(formatear.getBinario());
				fila.add(formatear.getDecimalSigned());
				fila.add(formatear.getDecimalUnsigned());
				fila.add(formatear.getHexadecimal());
				fila.add(formatear.getASCII());
				fila.add(formatear.getUnicode());
				fila.add(formatear.getWin1252());
				fila.add(formatear.getCP437());
				fila.add(formatear.getISO8859());
				contenido.add(fila);
			}
		}

		CrearArchivoExcel excel = new CrearArchivoExcel(encabezados, contenido,null,null);
		// excel.exportarComoExcel() tanto crea el archivo excel como verifica si fue creado o no con éxito
		if(excel.exportarComoExcel()) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Éxito");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("El archivo fue creado con Éxito.");
			alerta.showAndWait();
			return;
		}else {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("Error");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("Hubo un problema al crear al archivo");
			alerta.showAndWait();
			return;
		}
	}
	
	private void exportarComoEXCEL(String ruta, String nombreArchivo) {
		ArrayList<String> encabezados = new ArrayList<>();
		ArrayList<ArrayList<String>> contenido = new ArrayList<>();

		encabezados.add("Binario");
		encabezados.add("Decimal Signed");
		encabezados.add("Decimal Unsigned");
		encabezados.add("Hexadecimal");
		encabezados.add("ASCII");
		encabezados.add("Unicode");
		encabezados.add("Win 1252");
		encabezados.add("CP 437");
		encabezados.add("ISO 8859");
		for (String[] tramo : mensajesRecolectados) {
			for (String texto : tramo) {
				formatear.setDatoBinario(texto);
				ArrayList<String> fila = new ArrayList<>();
				fila.add(formatear.getBinario());
				fila.add(formatear.getDecimalSigned());
				fila.add(formatear.getDecimalUnsigned());
				fila.add(formatear.getHexadecimal());
				fila.add(formatear.getASCII());
				fila.add(formatear.getUnicode());
				fila.add(formatear.getWin1252());
				fila.add(formatear.getCP437());
				fila.add(formatear.getISO8859());
				contenido.add(fila);
			}
		}

		CrearArchivoExcel excel = new CrearArchivoExcel(encabezados, contenido, ruta, nombreArchivo);
		// excel.exportarComoExcel() tanto crea el archivo excel como verifica si fue creado o no con éxito
		if(excel.exportarComoExcel()) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Éxito");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("El archivo fue creado con Éxito.");
			alerta.showAndWait();
			return;
		}else {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("Error");
			alerta.setHeaderText(null); // Puedes personalizar esto si quieres un título adicional
			alerta.setContentText("Hubo un problema al crear al archivo");
			alerta.showAndWait();
			return;
		}
	}
	
	private void actualizarEncabezadosTabla(CheckBox check, int index, String titulo) {
		if (check.isSelected()) {
			encabezadosHTML[index] = "<th>" + titulo + "</th>";
			actualizarContenidoTablaPanel2();
		} else {
			encabezadosHTML[index] = "";
			actualizarContenidoTablaPanel2();
		}

		// reconstruir el cuerpo
		titulosDinamicos = "";
		for (String th : encabezadosHTML) {
			if (th != null && !th.isEmpty()) {
				titulosDinamicos += th;
			}
		}

		// cargar en el WebView
		PanelContenidoController2.cargarHTML(
				encabezadoHTML + encabezadoTablaHTML + titulosDinamicos + contenidoTablaHTML2 + piePaginaCuerpoHTLM);

	}

	private void actualizarContenidoTablaPanel1() {
		// Texto formateado para el panel 1
		contenidoTablaHTML = "";
		for (String[] tramo : mensajesRecolectados) {
			for (String texto : tramo) {
				formatear.setDatoBinario(texto);
				contenidoTablaHTML = contenidoTablaHTML.concat("<tr>");
				contenidoTablaHTML = contenidoTablaHTML.concat("<td>" + texto + "</td>");
				contenidoTablaHTML = contenidoTablaHTML.concat("<td>" + formatear.getDecimalSigned() + "</td>");
				contenidoTablaHTML = contenidoTablaHTML.concat("<td>" + formatear.getDecimalUnsigned() + "</td>");
				contenidoTablaHTML = contenidoTablaHTML.concat("<td>" + formatear.getHexadecimal() + "</td>");
				contenidoTablaHTML = contenidoTablaHTML.concat("<td>" + formatear.getASCII() + "</td>");
				contenidoTablaHTML = contenidoTablaHTML.concat("<td>" + formatear.getUnicode() + "</td>");
				contenidoTablaHTML = contenidoTablaHTML.concat("<td>" + formatear.getWin1252() + "</td>");
				contenidoTablaHTML = contenidoTablaHTML.concat("<td>" + formatear.getCP437() + "</td>");
				contenidoTablaHTML = contenidoTablaHTML.concat("<td>" + formatear.getISO8859() + "</td>");
				contenidoTablaHTML = contenidoTablaHTML.concat("</tr>");

			}
		}
		PanelContenidoController1.cargarHTML(encabezadoHTML + encabezadoTablaHTML + titulos + contenidoTablaHTML
				+ piePaginaTablaHTML + piePaginaCuerpoHTLM);

	}

	private void actualizarContenidoTablaPanel2() {
		contenidoTablaHTML2 = "";

		for (String[] tramo : mensajesRecolectados) {
			for (String texto : tramo) {

				formatear.setDatoBinario(texto);

				// Todos los posibles valores en el mismo orden que los encabezados
				String[] valores = { texto, formatear.getDecimalSigned(), formatear.getDecimalUnsigned(),
						formatear.getHexadecimal(), formatear.getASCII(), formatear.getUnicode(),
						formatear.getWin1252(), formatear.getCP437(), formatear.getISO8859() };

				// Construcción dinámica de la fila
				StringBuilder fila = new StringBuilder("<tr>");
				for (int i = 0; i < encabezadosHTML.length; i++) {
					if (encabezadosHTML[i] != null && !encabezadosHTML[i].isEmpty()) {
						fila.append("<td style=\"color:" + coloresTexto.get(i).getText() + "\";>").append(valores[i])
								.append("</td>");
					}
				}
				fila.append("</tr>");

				contenidoTablaHTML2 += fila.toString();
			}
		}

		PanelContenidoController2.cargarHTML(encabezadoHTML + encabezadoTablaHTML + titulosDinamicos
				+ contenidoTablaHTML2 + piePaginaTablaHTML + piePaginaCuerpoHTLM);
	}

	public void actualizarContenidoTextArea(String texto) {
		for (String tramo : texto.split(" ")) {
			formatear.setDatoBinario(tramo);
			txtAreaTextoRecibido.setText(txtAreaTextoRecibido.getText() + formatear.getASCII());
		}
		txtAreaTextoRecibido.setText(txtAreaTextoRecibido.getText() + "\r\n");
	}

	private void limpiarTodosPaneles() {
		this.txtAreaTextoRecibido.setText("");
		this.txtAreaTexto.setText("");
		contenidoTablaHTML = "";
		mensajesRecolectados.clear();
		PanelContenidoController2.cargarHTML(encabezadoHTML + encabezadoTablaHTML + titulos + contenidoTablaHTML
				+ piePaginaTablaHTML + piePaginaCuerpoHTLM);
		PanelContenidoController1.cargarHTML(encabezadoHTML + encabezadoTablaHTML + titulos + contenidoTablaHTML
				+ piePaginaTablaHTML + piePaginaCuerpoHTLM);
	}

	private void desactivarCampos() {
		this.comboPort.setDisable(true);
		this.comboBaudRate.setDisable(true);
		this.comboDataBits.setDisable(true);
		this.comboStopBits.setDisable(true);
		this.comboParity.setDisable(true);
		this.comboFlowControl.setDisable(true);
		this.comboTimeOuts.setDisable(true);
		this.spinnerReadTime.setDisable(true);
		this.spinnerWriteTime.setDisable(true);
		this.spinnerWaitTime.setDisable(true);

		this.checkBinario.setDisable(false);
		this.checkDecimalSigned.setDisable(false);
		this.checkDecimalUnsigned.setDisable(false);
		this.checkHexadecimal.setDisable(false);
		this.checkAscii.setDisable(false);
		this.checkUnicode.setDisable(false);
		this.checkWIN1252.setDisable(false);
		this.checkCP437.setDisable(false);
		this.checkISO8859.setDisable(false);
		this.tfCBinarios.setDisable(false);
		this.tfCDecimalSigned.setDisable(false);
		this.tfCDecimalUnsigned.setDisable(false);
		this.tfCHexadecimal.setDisable(false);
		this.tfCASCII.setDisable(false);
		this.tfCUnicode.setDisable(false);
		this.tfCWIN1252.setDisable(false);
		this.tfCCP437.setDisable(false);
		this.tfCISO8859.setDisable(false);

		this.radioBtnASCII.setDisable(false);
		this.radioBtnBinario.setDisable(false);
		this.radioBtnDecimal.setDisable(false);
		this.radioBtnHexadecimal.setDisable(false);
		this.txtAreaTexto.setDisable(false);
		this.btnEnviarTexto.setDisable(false);
		this.btnLimpiarTextArea.setDisable(false);

		this.btnConectar.setDisable(true);
		this.btnDesconectar.setDisable(false);

		// Cambiamos los íconos de las imagenes
		Image nuevaImagen = new Image(
				getClass().getResource("/resources/turn_on_off.png").toExternalForm());
		this.imgConectar.setImage(nuevaImagen);
		this.imgConectar.setDisable(true);

		Image nuevaImagen2 = new Image(getClass().getResource("/resources/turn_off.png").toExternalForm());
		this.imgDesconectar.setImage(nuevaImagen2);
	}

	private void activarCampos() {
		this.comboPort.setDisable(false);
		this.comboBaudRate.setDisable(false);
		this.comboDataBits.setDisable(false);
		this.comboStopBits.setDisable(false);
		this.comboParity.setDisable(false);
		this.comboFlowControl.setDisable(false);
		this.comboTimeOuts.setDisable(false);
		this.spinnerReadTime.setDisable(false);
		this.spinnerWriteTime.setDisable(false);
		this.spinnerWaitTime.setDisable(false);
				
		this.checkBinario.setDisable(true);
		this.checkDecimalSigned.setDisable(true);
		this.checkDecimalUnsigned.setDisable(true);
		this.checkHexadecimal.setDisable(true);
		this.checkAscii.setDisable(true);
		this.checkUnicode.setDisable(true);
		this.checkWIN1252.setDisable(true);
		this.checkCP437.setDisable(true);
		this.checkISO8859.setDisable(true);
		this.tfCBinarios.setDisable(true);
		this.tfCDecimalSigned.setDisable(true);
		this.tfCDecimalUnsigned.setDisable(true);
		this.tfCHexadecimal.setDisable(true);
		this.tfCASCII.setDisable(true);
		this.tfCUnicode.setDisable(true);
		this.tfCWIN1252.setDisable(true);
		this.tfCCP437.setDisable(true);
		this.tfCISO8859.setDisable(true);
		
		this.radioBtnASCII.setDisable(true);
		this.radioBtnBinario.setDisable(true);
		this.radioBtnDecimal.setDisable(true);
		this.radioBtnHexadecimal.setDisable(true);
		this.txtAreaTexto.setDisable(true);
		this.btnEnviarTexto.setDisable(true);
		this.btnLimpiarTextArea.setDisable(true);
	
		this.btnConectar.setDisable(false);
		this.btnDesconectar.setDisable(true);
		
	    // Cambiamos los íconos de las imagenes
	    Image nuevaImagen = new Image(getClass().getResource("/resources/turn_on.png").toExternalForm());
	    this.imgConectar.setImage(nuevaImagen);
	    this.imgConectar.setDisable(false);
	    
	    Image nuevaImagen2 = new Image(getClass().getResource("/resources/turn_on_off.png").toExternalForm());
	    this.imgDesconectar.setImage(nuevaImagen2);
	}
}
