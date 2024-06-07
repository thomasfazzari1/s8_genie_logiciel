package fr.ul.miage.fazzari_chartier_colombana.Util;

import java.util.Scanner;

public class VerificationsSaisies {
    public static Integer saisieID(Scanner scanner, String saisie) {
        Integer id = -1;

        while (id.equals(-1)) {
            System.out.print("Veuillez saisir l'ID de " + saisie + " : ");
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new NumberFormatException();
                }
                long longId = Long.parseLong(input);
                if (longId < Integer.MIN_VALUE || longId > Integer.MAX_VALUE) {
                    System.out.println(new MessageBuilder().addErrorMessage("❌ Veuillez saisir un ID compris entre les limites.").build());
                } else {
                    id = (int) longId;
                    if (id <= 0) {
                        System.out.println(new MessageBuilder().addErrorMessage("❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).").build());
                        id = -1;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).").build());
            }
        }
        return id;
    }

    public static String saisieEmplacement(Scanner scanner) {
        String emplacement = "";

        while (emplacement.isEmpty()) {
            System.out.print("Veuillez saisir l'emplacement de la borne : ");
            emplacement = scanner.nextLine().trim();
            if (emplacement.isEmpty()) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ L'emplacement de la borne ne peut pas être vide.").build());
            }
        }
        return emplacement;
    }

}
