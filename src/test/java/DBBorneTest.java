import static org.junit.Assert.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBBorne;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("On teste si...")
public class DBBorneTest {
    DBBorne dbBorne = DBBorne.getInstance();

    @Test
    @DisplayName("La vérification de l'existence d'une borne dans la base fonctionne")
    public void testExiste() {
        dbBorne.ajouter(4, "Emplacement test");
        assertTrue(DBBorne.getInstance().existe(4));
        assertFalse(dbBorne.existe(5));
    }

    @Test
    @DisplayName("L'ajout d'une borne dans la base fonctionne")
    public void testAjouter() {
        dbBorne.ajouter(1, "Emplacement test");
        assertTrue(dbBorne.existe(1));
    }

    @Test
    @DisplayName("La suppression d'une borne dans la base fonctionne")
    public void testSupprimer() {
        dbBorne.ajouter(2, "Emplacement test");
        dbBorne.supprimer(2);
        assertFalse(dbBorne.existe(2));
    }

    @Test
    @DisplayName("L'obtention de l'ensemble des bornes de la base fonctionne")
    public void testGetBornes() {
        dbBorne.ajouter(3, "Emplacement test");
        assertNotNull(dbBorne.getBornes());
    }
}
