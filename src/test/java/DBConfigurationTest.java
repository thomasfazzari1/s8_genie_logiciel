import com.mongodb.client.MongoClient;
import fr.ul.miage.fazzari_chartier_colombana.DB.DBConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("On teste si...")
public class DBConfigurationTest {

    @Test
    @DisplayName("L'obtention du MongoClient fonctionne")
    public void testGetMongoClient() {
        MongoClient client = DBConfiguration.getMongoClient();
        assertNotNull(client, "Le client ne doit pas être Null");
    }

    @Test
    @DisplayName("La fermeture de la connexion à la base fonctionne")
    public void testClose() {
        MongoClient client = DBConfiguration.getMongoClient();
        DBConfiguration.close();
        assertThrows(IllegalStateException.class, () -> {
            client.getDatabase("test").listCollectionNames().first();
        }, "IllegalStateException après la fermeture du client");
    }

}