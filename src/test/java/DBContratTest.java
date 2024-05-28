import static org.junit.Assert.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBContrat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@DisplayName("On teste si...")
public class DBContratTest {
    DBContrat dbContrat = new DBContrat();

    @Test
    @DisplayName("La vérification de l'existence d'un contrat dans la base fonctionne")
    public void testExiste() {
        dbContrat.ajouter(1, "colombana.ugo@contrat.com", "4", "2024-05-31", "11:00", "2024-06-01", "11:00", "GHI789");
        assertTrue(dbContrat.existe(1));
        assertFalse(dbContrat.existe(123));
    }

    @Test
    @DisplayName("L'ajout d'un contrat dans la base fonctionne")
    public void testAjouter() {
        dbContrat.ajouter(2, "john.doe@contrat.com", "1", "2024-05-28", "08:00", "2024-05-29", "08:00", "ABC123");
        assertTrue(dbContrat.existe(2));
    }

    @Test
    @DisplayName("La suppression d'un contrat dans la base fonctionne")
    public void testSupprimer() {
        dbContrat.ajouter(3, "thomas.fazzari@contrat.com", "2", "2024-05-29", "09:00", "2024-05-30", "09:00", "XYZ789");
        dbContrat.supprimer(3);
        assertFalse(dbContrat.existe(3));
    }

    @Test
    @DisplayName("L'obtention de l'ensemble des contrats de la base fonctionne")
    public void testGetContrats() {
        dbContrat.ajouter(4, "guillaume.chartier@contrat.com", "3", "2024-05-30", "10:00", "2024-05-31", "10:00", "DEF456");
        assertNotNull(dbContrat.getContrats());
    }
}
