import static org.junit.Assert.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("On teste si...")
public class DBClientTest {
    DBClient dbClient = new DBClient();

    @Test
    @DisplayName("La vérification de l'existence d'un client dans la base fonctionne")
    public void testExiste() {
        dbClient.ajouter("Test", "Existe", "101 Rue Test", "9876543210", "test.existe@example.com", "0123456789012345");
        assertTrue(dbClient.existe("test.existe@example.com"));
        assertFalse(dbClient.existe("test.nexistepas@example.com"));
    }

    @Test
    @DisplayName("L'ajout d'un client dans la base fonctionne")
    public void testAjouter() {
        dbClient.ajouter("Fazzari", "Thomas", "123 Rue Test", "1234567890", "thomas.fazzari@test.com", "1234567890123456");
        assertTrue(dbClient.existe("thomas.fazzari@test.com"));
    }

    @Test
    @DisplayName("La suppression d'un client dans la base fonctionne")
    public void testSupprimer() {
        dbClient.ajouter("Colombana", "Ugo", "456 Rue Test", "0987654321", "ugo.colombana@test.com", "6543210987654321");
        dbClient.supprimer("ugo.colombana@test.com");
        assertFalse(dbClient.existe("ugo.colombana@test.com"));
    }

    @Test
    @DisplayName("L'obtention de l'ensemble des clients de la base fonctionne")
    public void testGetClients() {
        dbClient.ajouter("Chartier", "Guillaume", "789 Rue Test", "1357924680", "guillaume.chartier@test.com", "9876543210123456");
        assertNotNull(dbClient.getClients());
    }
}
