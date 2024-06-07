package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBClient;
import fr.ul.miage.fazzari_chartier_colombana.Util.MessageBuilder;

import java.util.Scanner;

public class GestionnaireClient {
    private static DBClient clients = DBClient.getInstance();

    // Constructeur pour les injections de dépendances (Mocks GestionnaireClientTest)
    public GestionnaireClient(DBClient dbClient) {
        clients = dbClient;
    }

    public static void ajouterClient() {
        Scanner scanner = new Scanner(System.in);
        boolean boucle = true;
        String nom = null;
        while (boucle) {
            System.out.print("Nom : ");
            nom = scanner.nextLine();
            if (nom.equals("")) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ Le nom du client ne peut pas être vide.").build());
            } else {
                boucle = false;
            }
        }
        boucle = true;
        String prenom = null;
        while (boucle) {
            System.out.print("Prénom : ");
            prenom = scanner.nextLine();
            if (prenom.equals("")) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ Le prénom du client ne peut pas être vide.").build());
            } else {
                boucle = false;
            }
        }
        boucle = true;
        String adresse = null;
        while (boucle) {
            System.out.print("Adresse : ");
            adresse = scanner.nextLine();
            if (adresse.equals("")) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ L'adresse du client ne peut pas être vide.").build());
            } else {
                boucle = false;
            }
        }
        boucle = true;
        String telephone = null;
        while (boucle) {
            System.out.print("Numéro de téléphone : ");
            telephone = scanner.nextLine();
            if (telephone.equals("")) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ Le numéro de téléphone du client ne peut pas être vide.").build());
            } else {
                boucle = false;
            }
        }
        boucle = true;
        String email = null;
        while (boucle) {
            System.out.print("Email : ");
            email = scanner.nextLine();
            if (email.equals("")) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ L'email du client ne peut pas être vide.").build());
            } else if (clients.existe(email)) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ Cet email est déjà associé à un client.").build());
            } else {
                boucle = false;
            }
        }
        boucle = true;
        String cb = null;
        while (boucle) {
            System.out.print("Carte bancaire : ");
            cb = scanner.nextLine();
            if (cb.equals("")) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ La carte bancaire du client ne peut pas être vide.").build());
            } else {
                boucle = false;
            }
        }

        clients.ajouter(nom, prenom, adresse, telephone, email, cb);
        System.out.println(new MessageBuilder().addSuccessMessage("✅ Client ajouté avec succès.").build());
    }
}
