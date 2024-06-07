package fr.ul.miage.fazzari_chartier_colombana.Util;

import java.util.regex.Pattern;

public class Immatriculations {
    public static final Pattern PLAQUE_PATTERN = Pattern.compile(
            "^(?:" +
                    "[A-Z]{2}-\\d{3}-[A-Z]{2}" +    // Format français (Nouveau)
                    "|" +
                    "\\d{3}-[A-Z]{3}-\\d{2}" +     // Format français (Ancien)
                    "|" +
                    "[A-Z]{1,3}-\\d{1,4}-[A-Z]{1,2}" + // Format allemand / britannique
                    "|" +
                    "\\d{2}-[A-Z]{3}-\\d{3}" +      // Format italien
                    "|" +
                    "\\d{1,2}-[A-Z]{3}-\\d{1,3}" +  // Format espagnol
                    "|" +
                    "[A-Z]{3}\\d{4}" +              // Format luxembourgeois
                    "|" +
                    "[A-Z]{2}\\d{2}[A-Z]{3}" +      // Format néerlandais
                    "|" +
                    "[A-Z]{1,2}\\d{1,4}[A-Z]{1,2}" +   // Format belge
                    "|" +
                    "\\d{4}[A-Z]{2}" +              // Format suisse
                    "|" +
                    "\\d{2}-\\d{2}-\\d{2}" +        // Format norvégien
                    ")$"
    );

    public static boolean plaqueValide(String plaque) {
        return Immatriculations.PLAQUE_PATTERN.matcher(plaque).matches();
    }
}
