import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBClient;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

@DisplayName("On teste si...")
public class DBClientTest {

    @Mock
    private DBClient dbClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("La vérification de l'existence d'un client dans la base fonctionne")
    public void testExiste() {
        when(dbClient.existe("test.existe@example.com")).thenReturn(true);
        when(dbClient.existe("test.nexistepas@example.com")).thenReturn(false);

        assertTrue(dbClient.existe("test.existe@example.com"));
        assertFalse(dbClient.existe("test.nexistepas@example.com"));
    }

    @Test
    @DisplayName("L'ajout d'un client dans la base fonctionne")
    public void testAjouter() {
        doNothing().when(dbClient).ajouter("Fazzari", "Thomas", "123 Rue Test", "1234567890", "thomas.fazzari@test.com", "1234567890123456");

        dbClient.ajouter("Fazzari", "Thomas", "123 Rue Test", "1234567890", "thomas.fazzari@test.com", "1234567890123456");

        verify(dbClient, times(1)).ajouter("Fazzari", "Thomas", "123 Rue Test", "1234567890", "thomas.fazzari@test.com", "1234567890123456");
    }

    @Test
    @DisplayName("La suppression d'un client dans la base fonctionne")
    public void testSupprimer() {
        doNothing().when(dbClient).supprimer("ugo.colombana@test.com");

        dbClient.supprimer("ugo.colombana@test.com");

        verify(dbClient, times(1)).supprimer("ugo.colombana@test.com");
    }

    @Test
    @DisplayName("L'obtention de l'ensemble des clients de la base fonctionne")
    public void testGetClients() {
        ArrayList<Document> mockClients = new ArrayList<>();
        when(dbClient.getClients()).thenReturn(mockClients);

        assertNotNull(dbClient.getClients());
    }
}
