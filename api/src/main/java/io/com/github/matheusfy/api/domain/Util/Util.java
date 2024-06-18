package io.com.github.matheusfy.api.domain.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static boolean validString(String param) {
        return (param != null) && (!param.equals(""));
    }

    public static String dateFromLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
