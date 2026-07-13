package com.ingecode.clases;

import java.util.Arrays;
import java.util.List;

public class SerialOptions {
    public static List<String> getBaudRates() {
        return Arrays.asList("100", "300", "600", "1200", "2400", "4800", "9600", "14400", 
                             "19200", "38400", "56000", "57600", "115200", "128000", "256000");
    }

    public static List<String> getDataBits() {
        return Arrays.asList("5", "6", "7", "8");
    }

    public static List<String> getStopBits() {
        return Arrays.asList("1.0", "1.5", "2.0");
    }

    public static List<String> getParities() {
        return Arrays.asList("NO_PARITY", "EVEN_PARITY", "ODD_PARITY", "MARK_PARITY", "SPACE_PARITY");
    }

    public static List<String> getFlowControls() {
        return Arrays.asList(
            "NONE",
            "Hardware (RTS/CTS)",
            "Hardware (CTS only)",
            "Hardware (DSR/DTR)",
            "Hardware (DTR only)",
            "Soft (XON/XOFF) entrada",
            "Soft (XON/XOFF) salida",
            "Combinación RTS + CTS"
        );
    }
    
    public static List<String> getComPortTimeouts() {
        return Arrays.asList(
            "READ_BLOCKING",
            "SEMI_BLOCKING",
            "READ_NONBLOCKING",
            "WRITE_BLOCKING"
        );
    }
}
