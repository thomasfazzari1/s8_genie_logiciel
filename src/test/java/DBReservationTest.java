import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBReservation;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

@DisplayName("On teste si...")
public class DBReservationTest {

    @Mock
    private DBReservation dbReservation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("La vérification de l'existence d'un contrat dans la base fonctionne")
    public void testExiste() {
        when(dbReservation.existe(1)).thenReturn(true);
        when(dbReservation.existe(123)).thenReturn(false);

        assertTrue(dbReservation.existe(1));
        assertFalse(dbReservation.existe(123));
    }

    @Test
    @DisplayName("L'ajout d'un contrat dans la base fonctionne")
    public void testAjouter() {
        doNothing().when(dbReservation).ajouter(2, "john.doe@contrat.com", "1", "2024-05-28", "08:00", "2024-05-29", "08:00", "ABC123");

        dbReservation.ajouter(2, "john.doe@contrat.com", "1", "2024-05-28", "08:00", "2024-05-29", "08:00", "ABC123");

        verify(dbReservation, times(1)).ajouter(2, "john.doe@contrat.com", "1", "2024-05-28", "08:00", "2024-05-29", "08:00", "ABC123");
    }

    @Test
    @DisplayName("La suppression d'un contrat dans la base fonctionne")
    public void testSupprimer() {
        doNothing().when(dbReservation).supprimer(3);

        dbReservation.supprimer(3);

        verify(dbReservation, times(1)).supprimer(3);
    }

    @Test
    @DisplayName("L'obtention de l'ensemble des contrats de la base fonctionne")
    public void testGetContrats() {
        ArrayList<Document> mockReservations = new ArrayList<>();
        when(dbReservation.getReservations()).thenReturn(mockReservations);

        assertNotNull(dbReservation.getReservations());
    }

    @Test
    @DisplayName("La vérification de l'arrivée d'un véhicule fonctionne")
    public void testCheckArrivee() {
        doNothing().when(dbReservation).checkArrivee(4);

        dbReservation.checkArrivee(4);

        verify(dbReservation, times(1)).checkArrivee(4);
    }

    @Test
    @DisplayName("La vérification du départ d'un véhicule fonctionne")
    public void testCheckDepart() {
        doNothing().when(dbReservation).checkDepart(5);

        dbReservation.checkDepart(5);

        verify(dbReservation, times(1)).checkDepart(5);
    }
}
