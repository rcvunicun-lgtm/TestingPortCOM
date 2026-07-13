package com.ingecode.clases;


import java.nio.charset.Charset;

public class FormateadorDatosSerial {

    private int valorDecimal;   // decimal unsigned (base)
    private byte valorByte;     // decimal signed (para 8 bits)

    // Carácter de reemplazo (�)
    private static final String REEMPLAZO = "\uFFFD";

    public FormateadorDatosSerial() {
    }

    // ----------------------------
    // Métodos de entrada
    // ----------------------------

    // Establecer desde binario
    public void setDatoBinario(String binario) {
        if (!binario.matches("[01]+")) {
            throw new IllegalArgumentException("El valor no es un binario válido");
        }
        this.valorDecimal = Integer.parseInt(binario, 2);
        this.valorByte = (byte) valorDecimal;
    }

    // Establecer desde decimal
    public void setDatoDecimal(int decimal) {
        if (decimal < 0 || decimal > 255) {
            throw new IllegalArgumentException("El valor decimal debe estar entre 0 y 255 (1 byte)");
        }
        this.valorDecimal = decimal;
        this.valorByte = (byte) decimal;
    }

    // Establecer desde hexadecimal
    public void setDatoHexadecimal(String hex) {
        if (!hex.matches("^[0-9A-Fa-f]+$")) {
            throw new IllegalArgumentException("El valor no es un hexadecimal válido");
        }
        this.valorDecimal = Integer.parseInt(hex, 16);
        this.valorByte = (byte) valorDecimal;
    }

    // ----------------------------
    // Métodos de salida
    // ----------------------------

    // Binario
    public String getBinario() {
        return String.format("%8s", Integer.toBinaryString(valorDecimal)).replace(' ', '0');
    }

    // Decimal Unsigned
    public String getDecimalUnsigned() {
        return String.valueOf(valorDecimal);
    }

    // Decimal Signed (interpretando como byte)
    public String getDecimalSigned() {
        return String.valueOf(valorByte);
    }

    // Hexadecimal
    public String getHexadecimal() {
        return String.format("0x%02X", valorDecimal);
    }

    // ASCII (solo si está en rango imprimible)
    public String getASCII() {
        if (valorDecimal >= 32 && valorDecimal <= 126) {
            return Character.toString((char) valorDecimal);
        }
        return REEMPLAZO; // carácter no imprimible
    }

    // Unicode (UTF-16 en Java)
    public String getUnicode() {
        char c = (char) valorDecimal;
        if (Character.isISOControl(c)) {
            return REEMPLAZO; // control no imprimible
        }
        return String.valueOf(c);
    }

    // WIN1252
    public String getWin1252() {
        try {
            String s = new String(new byte[]{valorByte}, Charset.forName("windows-1252"));
            if (esImprimible(s)) return s;
        } catch (Exception ignored) {}
        return REEMPLAZO;
    }

    // CP437
    public String getCP437() {
        try {
            String s = new String(new byte[]{valorByte}, Charset.forName("CP437"));
            if (esImprimible(s)) return s;
        } catch (Exception ignored) {}
        return REEMPLAZO;
    }

    // ISO 8859-1
    public String getISO8859() {
        try {
            String s = new String(new byte[]{valorByte}, Charset.forName("ISO-8859-1"));
            if (esImprimible(s)) return s;
        } catch (Exception ignored) {}
        return REEMPLAZO;
    }

    // ----------------------------
    // Helper: verificar si un string es imprimible
    // ----------------------------
    private boolean esImprimible(String s) {
        if (s == null || s.isEmpty()) return false;
        char c = s.charAt(0);
        return !Character.isISOControl(c);
    }
}
