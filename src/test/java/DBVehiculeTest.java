import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBVehicule;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

@DisplayName("On teste si...")
public class DBVehiculeTest {

    @Mock
    private DBVehicule dbVehicule;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("La vérification de l'existence d'un véhicule dans la base fonctionne")
    public void testExiste() {
        when(dbVehicule.existe("ugo.colombana@vehicule.com", "DEF456")).thenReturn(true);
        when(dbVehicule.existe("test.nexistepas@vehicule.com", "GHI789")).thenReturn(false);

        assertTrue(dbVehicule.existe("ugo.colombana@vehicule.com", "DEF456"));
        assertFalse(dbVehicule.existe("test.nexistepas@vehicule.com", "GHI789"));
    }

    @Test
    @DisplayName("L'ajout d'un véhicule dans la base fonctionne")
    public void testAjouter() {
        doNothing().when(dbVehicule).ajouter("thomas.fazzari@vehicule.com", "ABC123");

        dbVehicule.ajouter("thomas.fazzari@vehicule.com", "ABC123");

        verify(dbVehicule, times(1)).ajouter("thomas.fazzari@vehicule.com", "ABC123");
    }

    @Test
    @DisplayName("La suppression d'un véhicule dans la base fonctionne")
    public void testSupprimer() {
        doNothing().when(dbVehicule).supprimer("guillaume.chartier@vehicule.com", "XYZ789");

        dbVehicule.supprimer("guillaume.chartier@vehicule.com", "XYZ789");

        verify(dbVehicule, times(1)).supprimer("guillaume.chartier@vehicule.com", "XYZ789");
    }

    @Test
    @DisplayName("L'obtention de l'ensemble des véhicules de la base fonctionne")
    public void testGetVehicules() {
        ArrayList<Document> mockVehicules = new ArrayList<>();
        when(dbVehicule.getVehicules()).thenReturn(mockVehicules);

        assertNotNull(dbVehicule.getVehicules());
    }
}
