package fr.ul.miage.fazzari_chartier_colombana;

import fr.ul.miage.fazzari_chartier_colombana.Services.GestionnaireBorne;
import fr.ul.miage.fazzari_chartier_colombana.Services.GestionnaireReservation;
import fr.ul.miage.fazzari_chartier_colombana.Services.GestionnaireVehicule;
import fr.ul.miage.fazzari_chartier_colombana.Util.MessageBuilder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean programme = true;
        String choix;
        Scanner scanner = new Scanner(System.in);

        while (programme) {
            afficherMenu();
            choix = scanner.nextLine();
            System.out.println();
            switch (choix) {
                case "0":
                    System.out.println(new MessageBuilder().addSuccessMessage("Au revoir !").build());
                    programme = false;
                    break;
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    GestionnaireVehicule.ajouterVehicule();
                    break;
                case "6":
                    break;
                case "7":
                    GestionnaireBorne.afficherBornes();
                    break;
                case "8":
                    break;
                case "9":
                    GestionnaireBorne.ajouterBorne();
                    break;
                case "10":
                    GestionnaireBorne.supprimerBorne();
                    break;
                case "11":
                    GestionnaireReservation.afficherReservations();
                    break;
                case "12":
                    GestionnaireReservation.ajouterReservation();
                    break;
                case "13":
                    GestionnaireReservation.supprimerReservation();
                    break;
                default:
                    System.out.println(new MessageBuilder().addErrorMessage("Saisie incorrecte.").build());
                    break;
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║                  MENU                  ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Afficher la liste des clients       ║");
        System.out.println("║ 2. Ajouter un client                   ║");
        System.out.println("║ 3. Supprimer un client                 ║");
        System.out.println("║ -------------------------------------- ║");
        System.out.println("║ 4. Afficher la liste des véhicules     ║");
        System.out.println("║ 5. Ajouter un véhicule à un client     ║");
        System.out.println("║ 6. Supprimer un véhicule à un client   ║");
        System.out.println("║ -------------------------------------- ║");
        System.out.println("║ 7. Afficher la liste des bornes        ║");
        System.out.println("║ 8. Rechercher une borne disponible     ║");
        System.out.println("║ 9. Ajouter une borne                   ║");
        System.out.println("║ 10. Supprimer une borne                ║");
        System.out.println("║ ------------------------------------   ║");
        System.out.println("║ 11. Afficher la liste des réservations ║");
        System.out.println("║ 12. Ajouter une réservation            ║");
        System.out.println("║ 13. Supprimer une réservation          ║");
        System.out.println("║ -------------------------------------- ║");
        System.out.println("║ 0. Quitter                             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Saisissez votre choix : ");
    }
}