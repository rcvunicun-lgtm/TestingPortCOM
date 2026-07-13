package com.ingecode.controladores;

import com.ingecode.clases.PortCOM;
import com.ingecode.clases.SerialOptions;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class VentanaEstablecerConexionControlador {
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
	
	@FXML
	public void initialize() {
		llenarComboBox();
		llenarSpinner();
		activarCampos();
	}
	
	@FXML
	public void conectar(ActionEvent event) {
		
	}
	
	@FXML
	public void desconectar(ActionEvent event) {

	}
	
	public void llenarComboBox() {
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
	
	public void llenarSpinner() {
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
	public void desactivarCampos() {
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
				
		this.btnConectar.setDisable(true);
		this.btnDesconectar.setDisable(false);
		
	}

	public void activarCampos() {
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
				
		this.btnConectar.setDisable(false);
		this.btnDesconectar.setDisable(true);
			    
	}
	
	public void establecerConexion(Runnable accion) {
		this.btnConectar.setOnAction(e -> {
			accion.run(); // Ejecutas la acción adicional
			conectar(e); // Llamas al método guardarTxt
		});
	}
	
	public void cerrarConexion(Runnable accion) {
		this.btnDesconectar.setOnAction(e -> {
			accion.run(); // Ejecutas la acción adicional
			desconectar(e); // Llamas al método guardarTxt
		});
	}

	
	
	public Label getLabelEstado() {
		return labelEstado;
	}

	public void setLabelEstado(Label labelEstado) {
		this.labelEstado = labelEstado;
	}

	public ComboBox<String> getComboPort() {
		return comboPort;
	}

	public void setComboPort(ComboBox<String> comboPort) {
		this.comboPort = comboPort;
	}

	public ComboBox<String> getComboBaudRate() {
		return comboBaudRate;
	}

	public void setComboBaudRate(ComboBox<String> comboBaudRate) {
		this.comboBaudRate = comboBaudRate;
	}

	public ComboBox<String> getComboDataBits() {
		return comboDataBits;
	}

	public void setComboDataBits(ComboBox<String> comboDataBits) {
		this.comboDataBits = comboDataBits;
	}

	public ComboBox<String> getComboStopBits() {
		return comboStopBits;
	}

	public void setComboStopBits(ComboBox<String> comboStopBits) {
		this.comboStopBits = comboStopBits;
	}

	public ComboBox<String> getComboParity() {
		return comboParity;
	}

	public void setComboParity(ComboBox<String> comboParity) {
		this.comboParity = comboParity;
	}

	public ComboBox<String> getComboFlowControl() {
		return comboFlowControl;
	}

	public void setComboFlowControl(ComboBox<String> comboFlowControl) {
		this.comboFlowControl = comboFlowControl;
	}

	public ComboBox<String> getComboTimeOuts() {
		return comboTimeOuts;
	}

	public void setComboTimeOuts(ComboBox<String> comboTimeOuts) {
		this.comboTimeOuts = comboTimeOuts;
	}

	public Spinner<Integer> getSpinnerReadTime() {
		return spinnerReadTime;
	}

	public void setSpinnerReadTime(Spinner<Integer> spinnerReadTime) {
		this.spinnerReadTime = spinnerReadTime;
	}

	public Spinner<Integer> getSpinnerWriteTime() {
		return spinnerWriteTime;
	}

	public void setSpinnerWriteTime(Spinner<Integer> spinnerWriteTime) {
		this.spinnerWriteTime = spinnerWriteTime;
	}

	public Spinner<Integer> getSpinnerWaitTime() {
		return spinnerWaitTime;
	}

	public void setSpinnerWaitTime(Spinner<Integer> spinnerWaitTime) {
		this.spinnerWaitTime = spinnerWaitTime;
	}

	public Button getBtnConectar() {
		return btnConectar;
	}

	public void setBtnConectar(Button btnConectar) {
		this.btnConectar = btnConectar;
	}

	public Button getBtnDesconectar() {
		return btnDesconectar;
	}

	public void setBtnDesconectar(Button btnDesconectar) {
		this.btnDesconectar = btnDesconectar;
	}
	
	
}
