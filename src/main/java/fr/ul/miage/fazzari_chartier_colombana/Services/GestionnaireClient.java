package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBClient;

import java.util.Scanner;

public class GestionnaireClient {
    private static DBClient clients;

    public static void ajouterClient() {
        clients = new DBClient();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Nom : "); String nom = scanner.nextLine();
        System.out.print("Prénom : "); String prenom = scanner.nextLine();
        System.out.print("Adresse : "); String adresse = scanner.nextLine();
        System.out.print("Téléphone : "); String telephone = scanner.nextLine();
        System.out.print("Email : "); String email = scanner.nextLine();
        System.out.print("Numéro de carte bancaire : "); String cb = scanner.nextLine();

        if (nom.equals("")) System.out.print("❌ Le nom du client ne peut pas être vide.");
        else if (prenom.equals("")) System.out.print("❌ Le prénom du client ne peut pas être vide.");
        else if (adresse.equals("")) System.out.print("❌ L'adresse du client ne peut pas être vide.");
        else if (telephone.equals("")) System.out.print("❌ Le numéro de téléphone du client ne peut pas être vide.");
        else if (email.equals("")) System.out.print("❌ L'email du client ne peut pas être vide.");
        else if (cb.equals("")) System.out.print("❌ La carte bancaire du client ne peut pas être vide.");
        else if (clients.existe(email)) System.out.println("❌ Cet email est déjà associé à un client.");
        else {
            // Après avoir géré l'ajout des véhicule, proposer au client s'il souhaite ajouter son 1er véhicule ici !

            clients.ajouter(nom, prenom, adresse, telephone, email, cb);
            System.out.println("✅ Client ajouté avec succès.\n");
        }
    }
}