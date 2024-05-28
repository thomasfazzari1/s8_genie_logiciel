import com.mongodb.client.MongoClient;
import fr.ul.miage.fazzari_chartier_colombana.DB.DBConfiguration;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DBConfigurationTest {

    @Test
    public void testGetMongoClient() {
        MongoClient client = DBConfiguration.getMongoClient();
        assertNotNull(client, "Le client ne doit pas être Null");
    }

    @Test
    public void testClose() {
        MongoClient client = DBConfiguration.getMongoClient();
        DBConfiguration.close();
        assertThrows(IllegalStateException.class, () -> {
            client.getDatabase("test").listCollectionNames().first();
        }, "IllegalStateException après la fermeture du client");
    }

}