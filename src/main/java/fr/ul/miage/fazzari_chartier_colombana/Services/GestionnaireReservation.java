package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBBorne;
import fr.ul.miage.fazzari_chartier_colombana.DB.DBReservation;
import fr.ul.miage.fazzari_chartier_colombana.Util.Choix;
import fr.ul.miage.fazzari_chartier_colombana.Util.DateEtHeures;
import fr.ul.miage.fazzari_chartier_colombana.Util.MessageBuilder;
import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static fr.ul.miage.fazzari_chartier_colombana.Util.Immatriculations.plaqueValide;
import static fr.ul.miage.fazzari_chartier_colombana.Util.VerificationsSaisies.saisieID;

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

        // Génération automatique de l'ID
        Integer id = reservations.getIdDerniereReservation() + 1;

        System.out.print("Email client : ");
        String email = scanner.nextLine();
        System.out.print("Id borne : ");
        String borne = scanner.nextLine();

        LocalDate dateArrivee = null;
        while (true) {
            System.out.print("Date d'arrivée (format jj/mm/aaaa) : ");
            String dateA = scanner.nextLine();
            if (!DateEtHeures.dateValide(dateA)) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ La date d'arrivée est invalide.").build());
            } else {
                dateArrivee = DateEtHeures.parseDate(dateA);
                if (dateArrivee.isBefore(LocalDate.now())) {
                    System.out.println(new MessageBuilder().addErrorMessage("❌ La date d'arrivée ne peut pas être antérieure à aujourd'hui.").build());
                } else {
                    break;
                }
            }
        }

        LocalTime heureArrivee = null;
        while (true) {
            System.out.print("Heure d'arrivée (format hh:mm) : ");
            String heureA = scanner.nextLine();
            if (!DateEtHeures.HEURE_PATTERN.matcher(heureA).matches()) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure d'arrivée ne respecte pas le format hh:mm.").build());
            } else {
                try {
                    heureArrivee = DateEtHeures.parseTime(heureA);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure d'arrivée est invalide.").build());
                }
            }
        }

        LocalDate dateDepart = null;
        while (true) {
            System.out.print("Date de départ (format jj/mm/aaaa) : ");
            String dateD = scanner.nextLine();
            if (!DateEtHeures.dateValide(dateD)) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ La date de départ est invalide.").build());
            } else {
                dateDepart = DateEtHeures.parseDate(dateD);
                if (dateDepart.isBefore(dateArrivee)) {
                    System.out.println(new MessageBuilder().addErrorMessage("❌ La date de départ ne peut pas être antérieure à la date d'arrivée.").build());
                } else {
                    break;
                }
            }
        }

        LocalTime heureDepart = null;
        while (true) {
            System.out.print("Heure de départ (format hh:mm) : ");
            String heureD = scanner.nextLine();
            if (!DateEtHeures.HEURE_PATTERN.matcher(heureD).matches()) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure de départ ne respecte pas le format hh:mm.").build());
            } else {
                try {
                    heureDepart = DateEtHeures.parseTime(heureD);
                    if (dateArrivee.equals(dateDepart) && heureDepart.isBefore(heureArrivee)) {
                        System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure de départ ne peut pas être antérieure à l'heure d'arrivée.").build());
                    } else {
                        break;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println(new MessageBuilder().addErrorMessage("❌ L'heure de départ est invalide.").build());
                }
            }
        }

        String immat = null;
        while (true) {
            System.out.print("Immatriculation du véhicule : ");
            immat = scanner.nextLine();
            if (!plaqueValide(immat)) {
                System.out.println(new MessageBuilder().addErrorMessage("❌ L'immatriculation du véhicule est invalide.").build());
            } else {
                break;
            }
        }

        reservations.ajouter(id, email, borne, dateArrivee.toString(), heureArrivee.toString(), dateDepart.toString(), heureDepart.toString(), immat);
        System.out.println(new MessageBuilder().addSuccessMessage("✅ Réservation ajoutée avec succès.\n").build());
    }

    public static void supprimerReservation() {
        afficherChoixCourant(Choix.SUPPRESSION.toString());
        Scanner scanner = new Scanner(System.in);
        boolean boucle = true;
        while (boucle) {
            Integer id = saisieID(scanner, "la réservation");
            if (reservations.existe(id)) {
                reservations.supprimer(id);
                System.out.println(new MessageBuilder().addSuccessMessage("✅ Réservation supprimée avec succès.\n").build());
                boucle = false;
            } else
                System.out.println(new MessageBuilder().addErrorMessage("❌ Aucune réservation n'est associée à cet id.").build());
        }
    }

    public static void afficherReservations() {
        List<Document> reservationsList = reservations.getReservations();
        if (reservationsList.isEmpty())
            System.out.println(new MessageBuilder().addErrorMessage("❌ Aucune réservation enregistrée."));
        else {
            for (Document doc : reservationsList) {
                System.out.println("Id réservation : " + doc.getString("Id"));
                System.out.println("Email client : " + doc.getString("Email client"));
                System.out.println("Id borne : " + doc.getString("Id borne"));
                System.out.println("Arrivée : le " + doc.getString("Date arrivee") + " à " + doc.getString("Heure arrivee"));
                System.out.println("Départ : le " + doc.getString("Date depart") + " à " + doc.getString("Heure depart"));
                System.out.println();
            }
        }
    }

    public static void checkingArrivee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Veuillez saisir votre email : ");
        String email = scanner.nextLine();

        ArrayList<Document> currentReservations = reservations.getReservationsAVenirDuClient(email);
        if (currentReservations.isEmpty()) {
            System.out.println(new MessageBuilder().addErrorMessage("❌ Aucune réservation en cours pour cet email.").build());
        } else {
            System.out.println("Vos réservations prévues :");
            for (Document reservation : currentReservations) {
                System.out.println("ID de réservation : " + reservation.get("Id"));
                System.out.println("Numéro de la borne associée : " + reservation.get("Id borne"));
                System.out.println("Immatriculation associée : " + reservation.get("Immatriculation vehicule"));
                System.out.println(new MessageBuilder().addSuccessMessage("Arrivée prévue " + reservation.get("Date arrivee") + " à " + reservation.get("Heure arrivee")
                        + " | Départ prévu " + reservation.get("Date depart") + " à " + reservation.get("Heure depart")).build());
            }

            System.out.print("Veuillez saisir l'ID de réservation pour laquelle vous souhaitez effectuer le checking d'arrivée : ");
            Integer idReservation = saisieID(scanner, "la réservation");

            // Vérification supplémentaire si l'ID saisi correspond à une réservation de l'email courant
            boolean idReservationValide = false;
            for (Document reservation : currentReservations) {
                if (reservation.getInteger("Id").equals(idReservation)) {
                    idReservationValide = true;
                    break;
                }
            }

            if (idReservationValide) {
                reservations.checkArrivee(idReservation);
                System.out.println(new MessageBuilder().addSuccessMessage("✅ Checking d'arrivée effectué avec succès pour la réservation " + idReservation + ".").build());
            } else {
                System.out.println(new MessageBuilder().addErrorMessage("❌ L'ID saisi ne correspond à aucune réservation associée à cet email.").build());
            }
        }
    }


    public static void checkingDepart() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Veuillez saisir votre email : ");
        String email = scanner.nextLine();

        ArrayList<Document> currentReservations = reservations.getReservationsAVenirDuClient(email);
        if (currentReservations.isEmpty()) {
            System.out.println(new MessageBuilder().addErrorMessage("❌ Aucune réservation en cours pour cet email.").build());
        } else {
            System.out.println("Vos réservations prévues :");
            for (Document reservation : currentReservations) {
                System.out.println("ID de réservation : " + reservation.get("Id"));
                System.out.println("Numéro de la borne associée : " + reservation.get("Id borne"));
                System.out.println("Immatriculation associée : " + reservation.get("Immatriculation vehicule"));
                System.out.println(new MessageBuilder().addSuccessMessage("Arrivée prévue " + reservation.get("Date arrivee") + " à " + reservation.get("Heure arrivee")
                        + " | Départ prévu " + reservation.get("Date depart") + " à " + reservation.get("Heure depart")).build());
            }

            System.out.print("Veuillez saisir l'ID de réservation pour laquelle vous souhaitez effectuer le checking de départ : ");
            Integer idReservation = saisieID(scanner, "la réservation");

            boolean idReservationValide = false;
            for (Document reservation : currentReservations) {
                if (reservation.getInteger("Id").equals(idReservation)) {
                    idReservationValide = true;
                    break;
                }
            }

            if (idReservationValide) {
                reservations.checkDepart(idReservation);
                System.out.println(new MessageBuilder().addSuccessMessage("✅ Checking de départ effectué avec succès pour la réservation " + idReservation + ".").build());
            } else {
                System.out.println(new MessageBuilder().addErrorMessage("❌ L'ID saisi ne correspond à aucune réservation associée à cet email.").build());
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