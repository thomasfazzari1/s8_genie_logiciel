package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBBorne;

import fr.ul.miage.fazzari_chartier_colombana.Util.Choix;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Scanner;

public class GestionnaireBorne {
    private static DBBorne bornes = DBBorne.getInstance();

    // Constructeur pour les injections de dépendances (Mocks GestionnaireBorneTest)
    public GestionnaireBorne(DBBorne dbBorne) {
        bornes = dbBorne;
    }

    public static void ajouterBorne() {
        Scanner scanner = new Scanner(System.in);
        afficherChoixCourant(Choix.AJOUT.toString());
        Integer id = saisieID(scanner);
        String emplacement = saisieEmplacement(scanner);

        if (bornes.existe(id)) {
            System.out.println("❌ Une borne avec cet identifiant est déjà enregistrée.");
        } else {
            bornes.ajouter(id, emplacement);
            System.out.println("✅ Borne ajoutée avec succès.");
        }
    }

    public static void supprimerBorne() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            afficherChoixCourant(Choix.SUPPRESSION.toString());
            System.out.print("Saisir l'ID de la borne à supprimer : ");
            Integer id = saisieID(scanner);
            if (bornes.existe(id)) {
                bornes.supprimer(id);
                System.out.println("✅ Borne supprimée avec succès.\n");
                break;
            } else {
                System.out.println("❌ Aucune borne avec cet identifiant n'a été trouvée.");
            }
        }
    }

    public static void afficherBornes() {
        ArrayList<Document> allBornes = bornes.getBornes();
        if (allBornes.isEmpty()) {
            System.out.println("❌ Aucune borne enregistrée.");
        } else {
            for (Document borne : allBornes) {
                System.out.println("Id : " + borne.get("Id").toString());
                System.out.println("Emplacement : " + borne.getString("Emplacement"));
                System.out.println();
            }
        }
    }

    private static Integer saisieID(Scanner scanner) {
        Integer id = -1;

        while (id.equals(-1)) {
            System.out.print("Veuillez saisir l'ID de la borne : ");
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new NumberFormatException();
                }
                long longId = Long.parseLong(input);
                if (longId < Integer.MIN_VALUE || longId > Integer.MAX_VALUE) {
                    System.out.println("❌ Veuillez saisir un ID compris entre les limites.");
                } else {
                    id = (int) longId;
                    if (id <= 0) {
                        System.out.println("❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).");
                        id = -1;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).");
            }
        }
        return id;
    }

    private static String saisieEmplacement(Scanner scanner) {
        String emplacement = "";

        while (emplacement.isEmpty()) {
            System.out.print("Veuillez saisir l'emplacement de la borne : ");
            emplacement = scanner.nextLine().trim();
            if (emplacement.isEmpty()) {
                System.out.println("❌ L'emplacement de la borne ne peut pas être vide.");
            }
        }
        return emplacement;
    }

    public static void afficherChoixCourant(String choix) {
        if (choix.equals(Choix.AJOUT.toString())) {
            System.out.println("╔═════════════════════════════════════╗");
            System.out.println("║ MENU                                ║");
            System.out.println("║ └ Ajouter une borne                 ║");
            System.out.println("╚═════════════════════════════════════╝");
            return;
        }
        if (choix.equals(Choix.SUPPRESSION.toString())) {
            System.out.println("╔═════════════════════════════════════╗");
            System.out.println("║ MENU                                ║");
            System.out.println("║ └ Supprimer une borne               ║");
            System.out.println("╚═════════════════════════════════════╝");
            return;
        }
        if (choix.equals(Choix.AFFICHAGE.toString())) {
            System.out.println("╔═════════════════════════════════════╗");
            System.out.println("║ MENU                                ║");
            System.out.println("║ └ Afficher la liste des bornes      ║");
            System.out.println("╚═════════════════════════════════════╝");
            return;
        }
    }
}