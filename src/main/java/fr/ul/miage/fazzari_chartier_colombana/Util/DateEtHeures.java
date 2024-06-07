package fr.ul.miage.fazzari_chartier_colombana.Util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class DateEtHeures {
    // jj/mm/aaaa
    public static final Pattern DATE_PATTERN = Pattern.compile("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}");
    // hh:mm
    public static final Pattern HEURE_PATTERN = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER_NO_ZERO = DateTimeFormatter.ofPattern("H:mm");

    public static boolean dateValide(String date) {
        if (!DATE_PATTERN.matcher(date).matches()) {
            return false;
        }
        try {
            LocalDate.parse(date, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return LocalTime.parse(time, TIME_FORMATTER_NO_ZERO);
        }
    }

    public static boolean bisextile(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
