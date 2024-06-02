package fr.ul.miage.fazzari_chartier_colombana;

import fr.ul.miage.fazzari_chartier_colombana.Services.GestionnaireBorne;

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
                    System.out.println("Au revoir !");
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
                    break;
                case "12":
                    break;
                case "13":
                    break;
                default:
                    System.out.println("Saisie incorrecte.");
                    break;
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║                 MENU                 ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ 1. Afficher la liste des clients     ║");
        System.out.println("║ 2. Ajouter un client                 ║");
        System.out.println("║ 3. Supprimer un client               ║");
        System.out.println("║ ------------------------------------ ║");
        System.out.println("║ 4. Afficher la liste des véhicules   ║");
        System.out.println("║ 5. Ajouter un véhicule à un client   ║");
        System.out.println("║ 6. Supprimer un véhicule à un client ║");
        System.out.println("║ ------------------------------------ ║");
        System.out.println("║ 7. Afficher la liste des bornes      ║");
        System.out.println("║ 8. Rechercher une borne disponible   ║");
        System.out.println("║ 9. Ajouter une borne                 ║");
        System.out.println("║ 10. Supprimer une borne              ║");
        System.out.println("║ ------------------------------------ ║");
        System.out.println("║ 11. Afficher la liste des contrats   ║");
        System.out.println("║ 12. Ajouter un contrat               ║");
        System.out.println("║ 13. Supprimer un contrat             ║");
        System.out.println("║ ------------------------------------ ║");
        System.out.println("║ 0. Quitter                           ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Saisissez votre choix : ");
    }
}