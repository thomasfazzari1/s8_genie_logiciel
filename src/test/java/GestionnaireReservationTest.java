import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBReservation;
import fr.ul.miage.fazzari_chartier_colombana.Services.GestionnaireReservation;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

@DisplayName("Tests du Gestionnaire de Réservation")
public class GestionnaireReservationTest {

    @Mock
    private DBReservation reservations;

    @InjectMocks
    private GestionnaireReservation gestionnaireReservation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void setEntreeSortie(String input, ByteArrayOutputStream outputStream) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        System.setOut(new PrintStream(outputStream));
    }

    // region checkingArrivee
    private void testerSaisieCheckingArrivee(String saisie, String messageAttendu) {
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        Thread testThread = new Thread(() -> {
            try {
                gestionnaireReservation.checkingArrivee();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        testThread.start();

        try {
            Thread.sleep(1000); // On laisse le temps au thread de lire l'entrée et d'afficher le message
            testThread.interrupt(); // Simulation de l'arrêt de la saisie
            testThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains(messageAttendu));
    }

    @Test
    @DisplayName("Aucune réservation en cours pour l'email saisi")
    public void testAucuneReservation() {
        when(reservations.getReservationsAVenirDuClient("test@test.com")).thenReturn(new ArrayList<>());

        testerSaisieCheckingArrivee("test@test.com\n", "❌ Aucune réservation en cours pour cet email.");
    }

    @Test
    @DisplayName("Checking d'arrivée avec réservation valide")
    public void testCheckingArriveeReservationValide() {
        ArrayList<Document> reservationsList = new ArrayList<>();
        Document reservation = new Document();
        reservation.append("Id", 1);
        reservation.append("Id borne", "B1");
        reservation.append("Immatriculation vehicule", "ABC123");
        reservation.append("Date arrivee", "2024-06-01");
        reservation.append("Heure arrivee", "10:00");
        reservation.append("Date depart", "2024-06-01");
        reservation.append("Heure depart", "12:00");
        reservationsList.add(reservation);

        when(reservations.getReservationsAVenirDuClient("test@test.com")).thenReturn(reservationsList);

        testerSaisieCheckingArrivee("test@test.com\n1\n", "✅ Checking d'arrivée effectué avec succès pour la réservation 1.");
        verify(reservations).checkArrivee(1);
    }

    @Test
    @DisplayName("Checking d'arrivée avec ID de réservation invalide")
    public void testCheckingArriveeIDReservationInvalide() {
        ArrayList<Document> reservationsList = new ArrayList<>();
        Document reservation = new Document();
        reservation.append("Id", 1);
        reservation.append("Id borne", "B1");
        reservation.append("Immatriculation vehicule", "ABC123");
        reservation.append("Date arrivee", "2024-06-01");
        reservation.append("Heure arrivee", "10:00");
        reservation.append("Date depart", "2024-06-01");
        reservation.append("Heure depart", "12:00");
        reservationsList.add(reservation);

        when(reservations.getReservationsAVenirDuClient("test@test.com")).thenReturn(reservationsList);

        testerSaisieCheckingArrivee("test@test.com\n999\n", "❌ L'ID saisi ne correspond à aucune réservation associée à cet email.");
    }
    // endregion

    // region checkingDepart
    private void testerSaisieCheckingDepart(String saisie, String messageAttendu) {
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        Thread testThread = new Thread(() -> {
            try {
                gestionnaireReservation.checkingDepart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        testThread.start();

        try {
            Thread.sleep(1000); // On laisse le temps au thread de lire l'entrée et d'afficher le message
            testThread.interrupt(); // Simulation de l'arrêt de la saisie
            testThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains(messageAttendu));
    }

    @Test
    @DisplayName("Checking de départ avec réservation valide")
    public void testCheckingDepartReservationValide() {
        ArrayList<Document> reservationsList = new ArrayList<>();
        Document reservation = new Document();
        reservation.append("Id", 1);
        reservation.append("Id borne", "B1");
        reservation.append("Immatriculation vehicule", "ABC123");
        reservation.append("Date arrivee", "2024-06-01");
        reservation.append("Heure arrivee", "10:00");
        reservation.append("Date depart", "2024-06-01");
        reservation.append("Heure depart", "12:00");
        reservationsList.add(reservation);

        when(reservations.getReservationsAVenirDuClient("test@test.com")).thenReturn(reservationsList);

        testerSaisieCheckingDepart("test@test.com\n1\n", "✅ Checking de départ effectué avec succès pour la réservation 1.");
        verify(reservations).checkDepart(1);
    }

    @Test
    @DisplayName("Checking de départ avec ID de réservation invalide")
    public void testCheckingDepartIDReservationInvalide() {
        ArrayList<Document> reservationsList = new ArrayList<>();
        Document reservation = new Document();
        reservation.append("Id", 1);
        reservation.append("Id borne", "B1");
        reservation.append("Immatriculation vehicule", "ABC123");
        reservation.append("Date arrivee", "2024-06-01");
        reservation.append("Heure arrivee", "10:00");
        reservation.append("Date depart", "2024-06-01");
        reservation.append("Heure depart", "12:00");
        reservationsList.add(reservation);

        when(reservations.getReservationsAVenirDuClient("test@test.com")).thenReturn(reservationsList);

        testerSaisieCheckingDepart("test@test.com\n999\n", "❌ L'ID saisi ne correspond à aucune réservation associée à cet email.");
    }

    // endregion
}
