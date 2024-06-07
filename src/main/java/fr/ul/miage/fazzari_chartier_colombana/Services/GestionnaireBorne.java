package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBBorne;
import fr.ul.miage.fazzari_chartier_colombana.Util.Choix;
import fr.ul.miage.fazzari_chartier_colombana.Util.MessageBuilder;
import fr.ul.miage.fazzari_chartier_colombana.Util.VerificationsSaisies;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Scanner;

import static fr.ul.miage.fazzari_chartier_colombana.Util.VerificationsSaisies.saisieID;

public class GestionnaireBorne {
    private static DBBorne bornes = DBBorne.getInstance();

    // Constructeur pour les injections de dépendances (Mocks GestionnaireBorneTest)
    public GestionnaireBorne(DBBorne dbBorne) {
        bornes = dbBorne;
    }

    public static void ajouterBorne() {
        Scanner scanner = new Scanner(System.in);
        afficherChoixCourant(Choix.AJOUT.toString());
        Integer id = saisieID(scanner, "la borne");
        String emplacement = VerificationsSaisies.saisieEmplacement(scanner);

        if (bornes.existe(id)) {
            System.out.println(new MessageBuilder().addErrorMessage("❌ Une borne avec cet identifiant est déjà enregistrée.").build());
        } else {
            bornes.ajouter(id, emplacement);
            System.out.println(new MessageBuilder().addSuccessMessage("✅ Borne ajoutée avec succès.").build());
        }
    }

    public static void supprimerBorne() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            afficherChoixCourant(Choix.SUPPRESSION.toString());
            System.out.print("Saisir l'ID de la borne à supprimer : ");
            Integer id = saisieID(scanner, "la borne");
            if (bornes.existe(id)) {
                bornes.supprimer(id);
                System.out.println(new MessageBuilder().addSuccessMessage("✅ Borne supprimée avec succès.").build());
                break;
            } else {
                System.out.println(new MessageBuilder().addErrorMessage("❌ Aucune borne avec cet identifiant n'a été trouvée.").build());
            }
        }
    }

    public static void afficherBornes() {
        ArrayList<Document> allBornes = bornes.getBornes();
        if (allBornes.isEmpty()) {
            System.out.println(new MessageBuilder().addErrorMessage("❌ Aucune borne enregistrée.").build());
        } else {
            for (Document borne : allBornes) {
                System.out.println("Id : " + borne.get("Id").toString());
                System.out.println("Emplacement : " + borne.getString("Emplacement"));
                System.out.println();
            }
        }
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
        }
    }
}
