package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBBorne;
import fr.ul.miage.fazzari_chartier_colombana.DB.DBReservation;
import fr.ul.miage.fazzari_chartier_colombana.Util.Choix;
import fr.ul.miage.fazzari_chartier_colombana.Util.MessageBuilder;
import org.bson.Document;

import static fr.ul.miage.fazzari_chartier_colombana.Util.VerificationsSaisies.saisieID;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class GestionnaireReservation {
    private static DBReservation reservations = DBReservation.getInstance();
    public GestionnaireReservation(DBReservation dbReservation) {
        reservations = dbReservation;
    }
    private static DBBorne bornes = DBBorne.getInstance();
    public GestionnaireReservation(DBBorne dbBorne) {
        bornes = dbBorne;
    }

    public static void ajouterReservation() {
        afficherChoixCourant(Choix.AJOUT.toString());
        Scanner scanner = new Scanner(System.in);
        boolean boucle = true;
        Integer id = saisieID(scanner, "la réservation");
        System.out.print("Email client : ");
        String email = scanner.nextLine();
        System.out.print("Id borne : ");
        String borne = scanner.nextLine();
        boucle = true; String dateA = null; LocalDate dateArrivee = null;
        while (boucle) {
            System.out.print("Date d'arrivée (format jj/mm/aaaa) : ");
            dateA = scanner.nextLine();
            if (!dateA.matches("^\\d{2}/\\d{2}/\\d{4}$")) System.out.println(new MessageBuilder().addErrorMessage("❌ La date ne respecte pas le format jj/mm/aaaa.").build());
            else {
                String[] dateParts = dateA.split("/");
                int jj = Integer.parseInt(dateParts[0]);
                int mm = Integer.parseInt(dateParts[1]);
                int aaaa = Integer.parseInt(dateParts[2]);
                if (mm < 1 || mm > 12) System.out.println(new MessageBuilder().addErrorMessage("❌ Le mois doit être compris entre 1 et 12.").build());
                else {
                    int jourMaxDuMois = Month.of(mm).length(Year.isLeap(aaaa));
                    if (jj < 1 || jj > jourMaxDuMois) System.out.println(new MessageBuilder().addErrorMessage("❌ Le jour doit être compris entre 1 et " + jourMaxDuMois + " pour le mois spécifié.").build());
                    else {
                        dateArrivee = LocalDate.of(aaaa, mm, jj);
                        LocalDate aujourdhui = LocalDate.now();
                        if (dateArrivee.isBefore(aujourdhui))
                            System.out.println(new MessageBuilder().addErrorMessage("❌ La date ne peut pas être antérieure à la date d'aujourd'hui.").build());
                        else boucle = false;
                    }
                }
            }
        }
        boucle = true; String heureA = null;
        while (boucle) {
            System.out.print("Heure d'arrivée (format hh:mm) : ");
            heureA = scanner.nextLine();
            if (!heureA.matches("^\\d{2}:\\d{2}$")) System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure d'arrivée ne respecte pas le format hh:mm.").build());
            else {
                String[] tpsDecomp = heureA.split(":");
                int hour = Integer.parseInt(tpsDecomp[0]);
                int minute = Integer.parseInt(tpsDecomp[1]);
                if (hour < 0 || hour > 23 || minute < 0 || minute > 59) System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure d'arrivée doit être comprise entre 00:00 et 23:59.").build());
                else {
                    LocalTime heureArrivee = LocalTime.of(hour, minute);
                    LocalTime heureActuelle = LocalTime.now();
                    String[] dateParts = dateA.split("/");
                    int jj = Integer.parseInt(dateParts[0]);
                    int mm = Integer.parseInt(dateParts[1]);
                    int aaaa = Integer.parseInt(dateParts[2]);
                    if (LocalDate.of(aaaa, mm, jj).equals(LocalDate.now()) && heureArrivee.isBefore(heureActuelle)) System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure d'arrivée ne peut pas être antérieure à l'heure actuelle.").build());
                    else boucle = false;
                }
            }
        }
        boucle = true; String dateD = null; LocalDate dateDepart = null;
        while (boucle) {
            System.out.print("Date de départ (format jj/mm/aaaa) : ");
            dateD = scanner.nextLine();
            if (!dateD.matches("^\\d{2}/\\d{2}/\\d{4}$")) System.out.println(new MessageBuilder().addErrorMessage("❌ La date ne respecte pas le format jj/mm/aaaa.").build());
            else {
                String[] dateParts = dateD.split("/");
                int jj = Integer.parseInt(dateParts[0]);
                int mm = Integer.parseInt(dateParts[1]);
                int aaaa = Integer.parseInt(dateParts[2]);
                if (mm < 1 || mm > 12) System.out.println(new MessageBuilder().addErrorMessage("❌ Le mois doit être compris entre 1 et 12.").build());
                else {
                    int jourMaxDuMois = Month.of(mm).length(Year.isLeap(aaaa));
                    if (jj < 1 || jj > jourMaxDuMois) System.out.println(new MessageBuilder().addErrorMessage("❌ Le jour doit être compris entre 1 et " + jourMaxDuMois + " pour le mois spécifié.").build());
                    else {
                        dateDepart = LocalDate.of(aaaa, mm, jj);
                        if (dateDepart.isBefore(dateArrivee)) System.out.println(new MessageBuilder().addErrorMessage("❌ La date de départ ne peut pas être antérieure à la date d'arrivée.").build());
                        else if (dateDepart.isAfter(dateArrivee.plusDays(1))) System.out.println(new MessageBuilder().addSuccessMessage("❌ La date de départ doit être au plus tard le lendemain de la date d'arrivée.").build());
                        else boucle = false;
                    }
                }
            }
        }
        boucle = true; String heureD = null;
        while (boucle) {
            System.out.print("Heure de départ (format hh:mm) : ");
            heureD = scanner.nextLine();
            if (!heureD.matches("^\\d{2}:\\d{2}$")) System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure de départ ne respecte pas le format hh:mm.").build());
            else {
                String[] tpsDecomp = heureD.split(":");
                int heure = Integer.parseInt(tpsDecomp[0]);
                int minute = Integer.parseInt(tpsDecomp[1]);
                if (heure < 0 || heure > 23 || minute < 0 || minute > 59) System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure de départ doit être comprise entre 00:00 et 23:59.").build());
                else {
                    LocalTime heureDepart = LocalTime.parse(heureD);
                    LocalTime heureArrivee = LocalTime.parse(heureA);
                    System.out.println(heureDepart + " " + heureArrivee);
                    if (dateA.equals(dateD) && heureDepart.isBefore(heureArrivee)) System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure de départ ne peut pas être antérieure à l'heure d'arrivée.").build());
                    else boucle = false;
                }
            }
        }
        System.out.print("Immatriculation du véhicule : ");
        String immat = scanner.nextLine();
        reservations.ajouter(id, email, borne, dateA, heureA, dateD, heureD, immat);
        System.out.println(new MessageBuilder().addSuccessMessage("✅ Contrat ajouté avec succès.\n").build());
    }

    public static void supprimerReservation() {
        afficherChoixCourant(Choix.SUPPRESSION.toString());
        Scanner scanner = new Scanner(System.in);
        boolean boucle = true;
        while (boucle) {
            Integer id = saisieID(scanner, "la réservation");
            if (reservations.existe(id)) {
                reservations.supprimer(id);
                System.out.println(new MessageBuilder().addSuccessMessage("✅ Contrat supprimée avec succès.\n").build());
                boucle = false;
            }
            else System.out.println(new MessageBuilder().addErrorMessage("❌ Aucun contrat n'est associée à cet id.").build());
        }
    }

    public static void afficherReservations() {
        List<Document> contrats = reservations.getReservations();
        if (contrats.isEmpty()) System.out.println(new MessageBuilder().addErrorMessage("❌ Aucun contrat enregistré."));
        else {
            for (Document doc : contrats) {
                System.out.println("Id contrat : " + doc.getString("Id contrat"));
                System.out.println("Email client : " + doc.getString("Email client"));
                System.out.println("Id borne : " + doc.getString("Id borne"));
                System.out.println("Arrivée : le " + doc.getString("Date arrivee") + " à " + doc.getString("Heure arrivee"));
                System.out.println("Départ : le " + doc.getString("Date depart") + " à " + doc.getString("Heure depart"));
                System.out.println();
            }
        }
    }

    public static void afficherChoixCourant(String choix) {
        if (choix.equals(Choix.AJOUT.toString())) {
            System.out.println("╔═════════════════════════════════════╗");
            System.out.println("║ MENU                                ║");
            System.out.println("║ └ Ajouter une réservation           ║");
            System.out.println("╚═════════════════════════════════════╝");
            return;
        }
        if (choix.equals(Choix.SUPPRESSION.toString())) {
            System.out.println("╔═════════════════════════════════════╗");
            System.out.println("║ MENU                                ║");
            System.out.println("║ └ Supprimer une réservation         ║");
            System.out.println("╚═════════════════════════════════════╝");
            return;
        }
        if (choix.equals(Choix.AFFICHAGE.toString())) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║ MENU                                 ║");
            System.out.println("║ └ Afficher la liste des réservations ║");
            System.out.println("╚══════════════════════════════════════╝");
        }
    }
}