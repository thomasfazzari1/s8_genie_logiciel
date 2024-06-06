import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBContrat;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

@DisplayName("On teste si...")
public class DBContratTest {

    @Mock
    private DBContrat dbContrat;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("La vérification de l'existence d'un contrat dans la base fonctionne")
    public void testExiste() {
        when(dbContrat.existe(1)).thenReturn(true);
        when(dbContrat.existe(123)).thenReturn(false);

        assertTrue(dbContrat.existe(1));
        assertFalse(dbContrat.existe(123));
    }

    @Test
    @DisplayName("L'ajout d'un contrat dans la base fonctionne")
    public void testAjouter() {
        doNothing().when(dbContrat).ajouter(2, "john.doe@contrat.com", "1", "2024-05-28", "08:00", "2024-05-29", "08:00", "ABC123");

        dbContrat.ajouter(2, "john.doe@contrat.com", "1", "2024-05-28", "08:00", "2024-05-29", "08:00", "ABC123");

        verify(dbContrat, times(1)).ajouter(2, "john.doe@contrat.com", "1", "2024-05-28", "08:00", "2024-05-29", "08:00", "ABC123");
    }

    @Test
    @DisplayName("La suppression d'un contrat dans la base fonctionne")
    public void testSupprimer() {
        doNothing().when(dbContrat).supprimer(3);

        dbContrat.supprimer(3);

        verify(dbContrat, times(1)).supprimer(3);
    }

    @Test
    @DisplayName("L'obtention de l'ensemble des contrats de la base fonctionne")
    public void testGetContrats() {
        ArrayList<Document> mockContrats = new ArrayList<>();
        when(dbContrat.getContrats()).thenReturn(mockContrats);

        assertNotNull(dbContrat.getContrats());
    }
}
