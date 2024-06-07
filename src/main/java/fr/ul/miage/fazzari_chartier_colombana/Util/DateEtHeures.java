package fr.ul.miage.fazzari_chartier_colombana.Util;

import java.util.regex.Pattern;

public class DateEtHeures {
    // jj/mm/aaaa
    public static final Pattern DATE_PATTERN = Pattern.compile("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}");
    // hh:mm
    public static final Pattern HEURE_PATTERN = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
}
