package com.ingecode.clases;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class SerialConectionHight {
	// Variable de instancia para acumular datos para abrir el puerto COM
	private volatile boolean running = true;
	private SerialPort port;
	private int baudRate;
	private int dataBits;
	private int stopBits;
	private int parity;
	private String flowControlStr;
	private String timeOut;
	private String readTimeStr;
	private String writeTimeStr;
	private int waitingTime;

	// Variable de instancia para acumular datos entre llamadas
	private final StringBuffer rawDataBuffer = new StringBuffer();
	private final StringBuffer binaryBuffer = new StringBuffer();
	private boolean debugMode = false; // Variable de clase

	public SerialConectionHight(String portName, String baudRateStr, String dataBitsStr, String stopBitsStr,
			String parityStr, String flowControlStr, String timeOut, String readTimeStr, String writeTimeStr,
			String waitingTimeStr) {

		this.port = SerialPort.getCommPort(portName);
		this.baudRate = parseBaudRate(baudRateStr);
		this.dataBits = parseDataBits(dataBitsStr);
		this.stopBits = parseStopBits(stopBitsStr);
		this.parity = parseParity(parityStr);
		this.flowControlStr = flowControlStr;
		this.timeOut = timeOut;
		this.readTimeStr = readTimeStr;
		this.writeTimeStr = writeTimeStr;
		this.waitingTime = parseWaitingTime(waitingTimeStr);
	}
	
	public SerialConectionHight() {
		this.port = null;
		this.baudRate = 0;
		this.dataBits = 0;
		this.stopBits = 0;
		this.parity = 0;
		this.flowControlStr = null;
		this.timeOut = null;
		this.readTimeStr = null;
		this.writeTimeStr = null;
		this.waitingTime = 0;
	}
	
	public void establecerCampos(String portName, String baudRateStr, String dataBitsStr, String stopBitsStr,
			String parityStr, String flowControlStr, String timeOut, String readTimeStr, String writeTimeStr,
			String waitingTimeStr) {
		this.port = SerialPort.getCommPort(portName);
		this.baudRate = parseBaudRate(baudRateStr);
		this.dataBits = parseDataBits(dataBitsStr);
		this.stopBits = parseStopBits(stopBitsStr);
		this.parity = parseParity(parityStr);
		this.flowControlStr = flowControlStr;
		this.timeOut = timeOut;
		this.readTimeStr = readTimeStr;
		this.writeTimeStr = writeTimeStr;
		this.waitingTime = parseWaitingTime(waitingTimeStr);
	}

	private int parseBaudRate(String baudRate) {
		try {
			return Integer.parseInt(baudRate);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Velocidad de baudios no válida: " + baudRate);
		}
	}

	private int parseDataBits(String dataBits) {
		switch (dataBits) {
		case "5":
			return 5;
		case "6":
			return 6;
		case "7":
			return 7;
		case "8":
			return 8;
		default:
			throw new IllegalArgumentException("Número de Data Bits no válido: " + dataBits);
		}
	}

	private int parseStopBits(String stopBits) {
		switch (stopBits) {
		case "1.0":
			return SerialPort.ONE_STOP_BIT;
		case "1.5":
			return SerialPort.ONE_POINT_FIVE_STOP_BITS;
		case "2.0":
			return SerialPort.TWO_STOP_BITS;
		default:
			throw new IllegalArgumentException("Valor de Stop Bits no válido: " + stopBits);
		}
	}

	private int parseParity(String parity) {
		switch (parity) {
		case "NO_PARITY":
			return SerialPort.NO_PARITY;
		case "EVEN_PARITY":
			return SerialPort.EVEN_PARITY;
		case "ODD_PARITY":
			return SerialPort.ODD_PARITY;
		case "MARK_PARITY":
			return SerialPort.MARK_PARITY;
		case "SPACE_PARITY":
			return SerialPort.SPACE_PARITY;
		default:
			throw new IllegalArgumentException("Paridad no válida: " + parity);
		}
	}

	private void setFlowControl(String flowControl) {
		switch (flowControl) {
		case "Hardware (RTS/CTS)":
		case "Combinación RTS + CTS":
			port.setFlowControl(SerialPort.FLOW_CONTROL_RTS_ENABLED | SerialPort.FLOW_CONTROL_CTS_ENABLED);
			break;
		case "Hardware (CTS only)":
			port.setFlowControl(SerialPort.FLOW_CONTROL_CTS_ENABLED);
			break;
		case "Hardware (DSR/DTR)":
			port.setFlowControl(SerialPort.FLOW_CONTROL_DSR_ENABLED | SerialPort.FLOW_CONTROL_DTR_ENABLED);
			break;
		case "Hardware (DTR only)":
			port.setFlowControl(SerialPort.FLOW_CONTROL_DTR_ENABLED);
			break;
		case "Soft (XON/XOFF) entrada":
			port.setFlowControl(SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED);
			break;
		case "Soft (XON/XOFF) salida":
			port.setFlowControl(SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED);
			break;
		case "NONE":
			port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
			break;
		default:
			throw new IllegalArgumentException("Control de flujo no válido: " + flowControl);
		}
	}

	private void setTimeOut(String timeOut, String timeRead, String timeWrite) {

		int timeReadInt, timeWriteInt;

		try {
			timeReadInt = Integer.parseInt(timeRead);
			timeWriteInt = Integer.parseInt(timeWrite);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Tiempo de espera no válido: " + timeRead + " o " + timeWrite);
		}

		switch (timeOut) {
		case "READ_BLOCKING":
			port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, // Modo de espera de lectura
					timeReadInt, // Tiempo de espera de lectura
					timeWriteInt // Tiempo de espera de escritura
			);
			break;
		case "SEMI_BLOCKING":
			port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, // Modo de espera de lectura
					timeReadInt, // Tiempo de espera de lectura
					timeWriteInt // Tiempo de espera de escritura
			);
			break;
		case "READ_NONBLOCKING":
			port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, // Modo de espera de lectura
					timeReadInt, // Tiempo de espera de lectura
					timeWriteInt // Tiempo de espera de escritura
			);
			break;

		case "WRITE_BLOCKING":
			port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, // Modo de espera de lectura
					timeReadInt, // Tiempo de espera de lectura
					timeWriteInt // Tiempo de espera de escritura
			);
			break;
		case "SCANNER": // NO LA ESTOY USANDO EN MI CODIGO (LA DEJO POR SI ACASO)
			port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, // Modo de espera de lectura
					timeReadInt, // Tiempo de espera de lectura
					timeWriteInt // Tiempo de espera de escritura
			);
			break;
		default:
			throw new IllegalArgumentException("Modo de timeout no válido: " + timeOut);
		}
	}

	private int parseWaitingTime(String waitingTime) {
		try {
			return Integer.parseInt(waitingTime);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Velocidad de espera no válida: " + waitingTime);
		}
	}

	public boolean abrirConexion() {
		if (port != null) {
			if (port.isOpen()) {
				// Ya está abierto
				return true;
			}

			// Configurar los parámetros del puerto antes de abrir la conexión
			port.setComPortParameters(this.baudRate, this.dataBits, this.stopBits, this.parity);
			setFlowControl(this.flowControlStr);
			setTimeOut(this.timeOut, this.readTimeStr, this.writeTimeStr);

			boolean abierto = port.openPort();
			if (!abierto) {
				System.err.println("[ERROR] Error de conexión No se pudo abrir el puerto de comunicación" + this.port.getDescriptivePortName() + ".");
				return false;
			}

			if (!running) {
				running = true;
			}

			return true;
		}

		return false; // Si port es null
	}

	public void cerrarConexion() {
		running = false;
		if (port != null && port.isOpen()) {
			boolean cerrado = port.closePort();
			port = null;
			if (!cerrado) {
		
				System.err.println("[ERROR] Cierre de puerto No se pudo cerrar el puerto correctamente.");
			}
		}
	}
	
	public void iniciarLecturaDatos(Consumer<String> dataHandler) {
	    // Validación inicial
	    if (port == null) {
	        throw new IllegalArgumentException("El puerto no puede ser nulo");
	    }
	    if (dataHandler == null) {
	        throw new IllegalArgumentException("El manejador de datos no puede ser nulo");
	    }
	    if (!port.isOpen()) {
	        throw new IllegalStateException("El puerto debe estar abierto antes de iniciar la lectura");
	    }

	    new Thread(() -> {
	        final byte[] buffer = new byte[1024]; // Buffer aumentado para mejor rendimiento
	        int idleCycles = 0;
	        final int MAX_IDLE_CYCLES = 5;

	        // Configuración del listener 
	        this.port.addDataListener(new SerialPortDataListener() {
	            private final String ERROR_PREFIX = "\n[ERROR] ";
	            private final String HW_ALERT_PREFIX = "\n[ALERTA HW] ";
	            private final String EVENT_PREFIX = "\n[EVENTO] ";
	            
	            @Override
	            public int getListeningEvents() {
	                return SerialPort.LISTENING_EVENT_PARITY_ERROR |
	                       SerialPort.LISTENING_EVENT_FRAMING_ERROR |
	                       SerialPort.LISTENING_EVENT_BREAK_INTERRUPT |
	                       SerialPort.LISTENING_EVENT_CTS |  // EVENTOS QUE SOLO SE EJECUTAN CUANDO HAY ERRORES DE HARDWARE
	                       SerialPort.LISTENING_EVENT_DSR |  // EVENTOS QUE SOLO SE EJECUTAN CUANDO HAY ERRORES DE HARDWARE
	                       SerialPort.LISTENING_EVENT_CARRIER_DETECT;  // EVENTOS QUE SOLO SE EJECUTAN CUANDO HAY ERRORES DE HARDWARE
	            }

	            @Override
	            public void serialEvent(SerialPortEvent event) {
	                if (!running || !port.isOpen()) return;

	                try {
	                    switch (event.getEventType()) {
                        case SerialPort.LISTENING_EVENT_PARITY_ERROR:
                            dataHandler.accept(ERROR_PREFIX + "Error de paridad");
                            break;
                        case SerialPort.LISTENING_EVENT_FRAMING_ERROR:
                            dataHandler.accept(ERROR_PREFIX + "Error de trama");
                            break;
                        case SerialPort.LISTENING_EVENT_BREAK_INTERRUPT:
                            dataHandler.accept(EVENT_PREFIX + "Señal BREAK");
                            break;
                            
                        // EVENTOS QUE SOLO SE EJECUTAN CUANDO HAY ERRORES DE HARDWARE
                        case SerialPort.LISTENING_EVENT_CTS:
                            if (debugMode && !port.getCTS()) {
                                dataHandler.accept(HW_ALERT_PREFIX + "CTS inactivo");
                            }
                            break;
                        case SerialPort.LISTENING_EVENT_DSR:
                            if (debugMode && !port.getDSR()) {
                                dataHandler.accept(HW_ALERT_PREFIX + "DSR inactivo");
                            }
                            break;
                        case SerialPort.LISTENING_EVENT_CARRIER_DETECT:
                            if (debugMode && !port.getDCD()) {
                                dataHandler.accept(HW_ALERT_PREFIX + "Portadora perdida");
                            }
                            break;
                    }
	                } catch (Exception e) {
	                    System.err.println("Error en evento serial: " + e.getMessage());
	                }
	            }
	        });

	        while (running && port.isOpen()) {
	            try {
	                int availableBytes = port.bytesAvailable();
	                
	                if (availableBytes > 0) {
	                    idleCycles = 0;
	                    // Esta es la funcion que procesara los datos!
	                    processData(buffer, Math.min(buffer.length, availableBytes), dataHandler);
	                } else if (++idleCycles > MAX_IDLE_CYCLES) {
	                    idleCycles = 0;
	                    if (debugMode) {
	                        dataHandler.accept("\n[INFO] Esperando datos...");
	                    }
	                }
	                
	                Thread.sleep(waitingTime);
	            } catch (Exception e) {
	                handleError(e, dataHandler);
	            }
	        }
	        
	        cleanup();
	        
	    }).start();
	}
	

	// METODOS AUXILIARES PARA EL METODO iniciarLecturaDatos()

	 	private synchronized void processData(byte[] buffer, int maxLength, Consumer<String> dataHandler) 
	    throws IOException {
	    Arrays.fill(buffer, 0, maxLength, (byte) 0);
	    int bytesRead = port.readBytes(buffer, maxLength);
	    
	    StringBuilder binaryData = new StringBuilder();
	    StringBuilder rawData = new StringBuilder();
	    
	    for (int i = 0; i < bytesRead; i++) {
	        int byteValue = buffer[i] & 0xFF;
	        binaryData.append(String.format("%8s", Integer.toBinaryString(byteValue))
	                 .replace(' ', '0'))
	                 .append(' ');
	        rawData.append((char) byteValue);
	    }

	    binaryBuffer.append(binaryData);
	    rawDataBuffer.append(rawData);
	    
	    if (binaryBuffer.length() > 0) {
	        dataHandler.accept(binaryBuffer.toString().trim());
	        clearBuffers();
	    }
	}
	
	private void handleError(Exception e, Consumer<String> dataHandler) {
	    String errorMsg = "[ERROR] " + (e.getMessage() != null ? e.getMessage() : "Error desconocido");
	    System.err.println(errorMsg);
	    dataHandler.accept("\n" + errorMsg + "\n");
	    running = false;
	}

	private void cleanup() {
	    try {
	        if (port != null) {
	            try {
	                port.removeDataListener();
	            } catch (Exception e) {
	                System.err.println("Error removiendo listener: " + e.getMessage());
	            }
	            if (port.isOpen()) {
	                port.closePort();
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("Error en limpieza: " + e.getMessage());
	    } finally {
	        clearBuffers();
	    }
	}
	
	public int enviarDatos(byte[] data) {
	    if (port != null && port.isOpen()) {
	        try {
	            return port.writeBytes(data, data.length);
	        } catch (Exception e) {
	            System.err.println("[ERROR] No se pudo enviar datos: " + e.getMessage());
	            return -1;
	        }
	    } else {
	        System.err.println("[ERROR] El puerto no está abierto.");
	        return -1;
	    }
	}
	
	public int enviarTexto(String texto) {
	    if (texto == null) return -1;
	    return enviarDatos(texto.getBytes());
	}

	private synchronized void clearBuffers() {
	    binaryBuffer.setLength(0);
	    rawDataBuffer.setLength(0);
	}
	
	
	// METODOS GETTER
	
	public SerialPort getPort() {
		return port;
	}
}
