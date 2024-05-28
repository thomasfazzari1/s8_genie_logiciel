import static org.junit.Assert.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBVehicule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("On teste si...")
public class DBVehiculeTest {
    DBVehicule dbVehicule = new DBVehicule();

    @Test
    @DisplayName("La vérification de l'existence d'un véhicule dans la base fonctionne")
    public void testExiste() {
        dbVehicule.ajouter("ugo.colombana@vehicule.com", "DEF456");
        assertTrue(dbVehicule.existe("ugo.colombana@vehicule.com", "DEF456"));
    }

    @Test
    @DisplayName("L'ajout d'un véhicule dans la base fonctionne")
    public void testAjouter() {
        dbVehicule.ajouter("thomas.fazzari@vehicule.com", "ABC123");
        assertTrue(dbVehicule.existe("thomas.fazzari@vehicule.com", "ABC123"));
    }

    @Test
    @DisplayName("La suppression d'un véhicule dans la base fonctionne")
    public void testSupprimer() {
        dbVehicule.ajouter("guillaume.chartier@vehicule.com", "XYZ789");
        dbVehicule.supprimer("guillaume.chartier@vehicule.com", "XYZ789");
        assertFalse(dbVehicule.existe("guillaume.chartier@vehicule.com", "XYZ789"));
    }

    @Test
    @DisplayName("L'obtention de l'ensemble des véhicules de la base fonctionne")
    public void testGetVehicules() {
        dbVehicule.ajouter("ugo.colombana@vehicule.com", "ABW456");
        assertNotNull(dbVehicule.getVehicules());
    }
}
