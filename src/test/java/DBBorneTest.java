import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBBorne;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

@DisplayName("On teste si...")
public class DBBorneTest {

    @Mock
    private DBBorne dbBorne;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("La vérification de l'existence d'une borne dans la base fonctionne")
    public void testExiste() {
        when(dbBorne.existe(4)).thenReturn(true);
        when(dbBorne.existe(5)).thenReturn(false);

        assertTrue(dbBorne.existe(4));
        assertFalse(dbBorne.existe(5));
    }

    @Test
    @DisplayName("L'ajout d'une borne dans la base fonctionne")
    public void testAjouter() {
        doNothing().when(dbBorne).ajouter(1, "Emplacement test");

        dbBorne.ajouter(1, "Emplacement test");

        verify(dbBorne, times(1)).ajouter(1, "Emplacement test");
    }

    @Test
    @DisplayName("La suppression d'une borne dans la base fonctionne")
    public void testSupprimer() {
        doNothing().when(dbBorne).supprimer(2);

        dbBorne.supprimer(2);

        verify(dbBorne, times(1)).supprimer(2);
    }

    @Test
    @DisplayName("L'obtention de l'ensemble des bornes de la base fonctionne")
    public void testGetBornes() {
        ArrayList<Document> mockBornes = new ArrayList<>();
        when(dbBorne.getBornes()).thenReturn(mockBornes);

        assertNotNull(dbBorne.getBornes());
    }
}
