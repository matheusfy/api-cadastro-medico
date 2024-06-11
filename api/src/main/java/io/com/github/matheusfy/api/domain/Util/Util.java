package io.com.github.matheusfy.api.domain.Util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static boolean validString(String param) {
        return (param != null) && (!param.equals(""));
    }

    public static boolean isHorarioComercial(LocalDateTime dataHora) {
        return (dataHora.getDayOfWeek() != DayOfWeek.SUNDAY) && validateHour(dataHora.getHour());
    }

    public static boolean validateHour(int hour) {
        return (hour >= 7) && (hour <= 19);
    }

    public static String dateFromLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
