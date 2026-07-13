package com.ingecode.clases;

/*
 *  Esta clase me permite verificar si un texto cumple con los requisitos
 *  para ser Binario, Decimal o Hexadecimal
 */
public class VerificarTexto {
    
    // Verificar Binario (exactamente 8 bits)
    public boolean verificarBinario(String texto) {
        return texto != null && texto.matches("[01]{8}");
    }

    // Verificar Decimal (0–255, porque cabe en un byte sin signo)
    public boolean verificarDecimal(String texto) {
        if (texto == null || !texto.matches("\\d+")) {
            return false;
        }
        try {
            int valor = Integer.parseInt(texto);
            return valor >= 0 && valor <= 255;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Verificar Hexadecimal (dos dígitos hex: 00–FF)
    public boolean verificarHexadecimal(String texto) {
        return texto != null && texto.matches("(?i)[0-9A-F]{2}");
    }
}
