package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBClient;
import fr.ul.miage.fazzari_chartier_colombana.DB.DBVehicule;

import java.util.Scanner;
import java.util.regex.Pattern;

public class GestionnaireVehicule {
    private static DBVehicule vehicules = DBVehicule.getInstance();
    private static DBClient clients = DBClient.getInstance();
    private static final Pattern PLAQUE_FR_PATTERN = Pattern.compile("^[A-Z]{2}-\\d{3}-[A-Z]{2}$");

    // Constructeur pour les injections de dépendances (Mocks GestionnaireVehiculeTest)
    public GestionnaireVehicule(DBVehicule dbVehicule, DBClient dbClient) {
        vehicules = dbVehicule;
        clients = dbClient;
    }

    public static void ajouterVehicule() {
        Scanner scanner = new Scanner(System.in);
        String email = obtenirEmailClient(scanner);

        afficherChoixCourant(Choix.AJOUT.toString());
        System.out.print("Immatriculation du véhicule : ");
        String immat = scanner.nextLine();

        if (plaqueValide(immat)) {
            vehicules.ajouter(email, immat);
            System.out.println("✅ Véhicule ajouté avec succès.");
        } else {
            System.out.println("❌ Plaque d'immatriculation invalide.");
        }
    }

    private static String obtenirEmailClient(Scanner scanner) {
        boolean boucle = true;
        String email = null;
        while (boucle) {
            afficherChoixCourant(Choix.AJOUT.toString());
            System.out.print("Email du client souhaitant ajouter un véhicule: ");
            email = scanner.nextLine();
            if (clients.existe(email)) {
                boucle = false;
            } else {
                System.out.println("❌ Aucun utilisateur n'est associé à cet email.");
            }
        }
        return email;
    }

    private static boolean plaqueValide(String plaque) {
        return PLAQUE_FR_PATTERN.matcher(plaque).matches();
    }

    private static void afficherChoixCourant(String choix) {
        if (choix.equals(Choix.AJOUT.toString())) {
            System.out.println("╔═════════════════════════════════════╗");
            System.out.println("║ MENU                                ║");
            System.out.println("║ └ Ajouter un véhicule à un client   ║");
            System.out.println("╚═════════════════════════════════════╝");
        }
    }
}
