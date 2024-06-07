package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBReservation;

public class GestionnaireReservation {
    private static DBReservation reservations = DBReservation.getInstance();
    public GestionnaireReservation(DBReservation dbReservation) {
        reservations = dbReservation;
    }
}