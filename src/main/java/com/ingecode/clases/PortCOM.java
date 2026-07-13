package com.ingecode.clases;

import java.util.ArrayList;

import com.fazecast.jSerialComm.SerialPort;

public class PortCOM {
    // Retornar los nombres de los puertos disponibles
    public static ArrayList<String> obtenerPuertosDisponibles() {
        ArrayList<String> listaPuertos = new ArrayList<>();
        SerialPort[] availablePorts = SerialPort.getCommPorts();

        if (availablePorts.length == 0) {
            listaPuertos.add("No hay puertos disponibles");
        } else {
            for (SerialPort port : availablePorts) {
                listaPuertos.add(port.getSystemPortName());
            }
        }
        return listaPuertos;
    }
}
