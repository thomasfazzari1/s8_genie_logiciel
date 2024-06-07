package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBReservation;
import fr.ul.miage.fazzari_chartier_colombana.Util.MessageBuilder;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Scanner;

import static fr.ul.miage.fazzari_chartier_colombana.Util.VerificationsSaisies.saisieID;

public class GestionnaireReservation {
    private static DBReservation reservations = DBReservation.getInstance();

    public GestionnaireReservation(DBReservation dbReservation) {
        reservations = dbReservation;
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


}