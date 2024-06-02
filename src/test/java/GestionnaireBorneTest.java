import static org.junit.Assert.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBBorne;
import fr.ul.miage.fazzari_chartier_colombana.Services.GestionnaireBorne;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

@DisplayName("On teste si...")
public class GestionnaireBorneTest {
    DBBorne bornes = new DBBorne();

    private void setEntreeSortie(String input, ByteArrayOutputStream outputStream) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        System.setOut(new PrintStream(outputStream));
    }

    // region ajouterBorne
    private void testerSaisieAjout(String saisie, String messageAttendu) {
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        Thread testThread = new Thread(() -> {
            try {
                GestionnaireBorne.ajouterBorne();
            } catch (Exception e) {
            }
        });
        testThread.start();

        try {
            Thread.sleep(1000); // On laisse le temps au thread de lire l'entrée et d'afficher le message
            testThread.interrupt(); // Simulation de l'arrêt de la saisie
            testThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains(messageAttendu));
    }

    @Test
    @DisplayName("La saisie d'un ID vide est gérée")
    public void testIDVide() {
        testerSaisieAjout("\n\n", "❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).");
    }

    @Test
    @DisplayName("La saisie d'un ID négatif est gérée")
    public void testIDNegatif() {
        testerSaisieAjout("-1\n-1\n", "❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).");
    }

    @Test
    @DisplayName("La saisie d'un ID invalide est gérée")
    public void testIDInvalide() {
        testerSaisieAjout("abc\nabc\n", "❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).");
    }

    @Test
    @DisplayName("La saisie d'un emplacement vide est gérée")
    public void testEmplacementVide() {
        testerSaisieAjout("1\n\n\n", "❌ L'emplacement de la borne ne peut pas être vide.");
    }

    @Test
    @DisplayName("La saisie d'un ID dépassant la limite supérieure du type Integer est gérée")
    public void testDepassementLimiteSupID() {
        testerSaisieAjout("2147483648\n2147483648\n", "❌ Veuillez saisir un ID compris entre les limites.");
    }

    @Test
    @DisplayName("La saisie d'un ID dépassant la limite inférieure du type Integer est gérée")
    public void testDepassementLimiteInfID() {
        testerSaisieAjout("-2147483649\n-2147483649\n", "❌ Veuillez saisir un ID compris entre les limites.");
    }

    @Test
    @DisplayName("L'ajout d'une borne fonctionne")
    public void testAjoutBorne() {
        String saisie = "999999999\nEmplacement Valide";
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        GestionnaireBorne.ajouterBorne();

        System.setIn(System.in);
        System.setOut(System.out);

        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("✅ Borne ajoutée avec succès."));

        bornes.supprimer(999999999);
    }

    @Test
    @DisplayName("L'ajout d'une borne déjà existante est géré")
    public void testAjoutBorneExistante() {
        bornes.ajouter(123, "Test");

        String saisie = "123\nEmplacement Test";
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        GestionnaireBorne.ajouterBorne();

        System.setIn(System.in);
        System.setOut(System.out);

        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Une borne avec cet identifiant est déjà enregistrée."));

        bornes.supprimer(123);
    }
    // endregion

    // region supprimerBorne
    private void testerSaisieSuppression(String saisie, String messageAttendu) {
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        Thread testThread = new Thread(() -> {
            try {
                GestionnaireBorne.supprimerBorne();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        testThread.start();

        try {
            Thread.sleep(1000); // On laisse le temps au thread de lire l'entrée et d'afficher le message
            testThread.interrupt(); // Simulation de l'arrêt de la saisie
            testThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains(messageAttendu));
    }

    @Test
    @DisplayName("La suppression d'une borne existante fonctionne")
    public void testSuppressionBorneExistante() {
        bornes.ajouter(999, "testSuppressionBorneExistante");
        testerSaisieSuppression("999\n", "✅ Borne supprimée avec succès.");
        assertFalse(bornes.existe(999));
    }

    @Test
    @DisplayName("La suppression d'une borne inexistante est gérée")
    public void testSuppressionBorneInexistante() {
        bornes.supprimer(888);
        testerSaisieSuppression("888\n", "❌ Aucune borne avec cet identifiant n'a été trouvée.");
    }

    @Test
    @DisplayName("La suppression d'une borne avec un ID invalide est gérée")
    public void testSuppressionBorneIDInvalide() {
        String saisie = "abc\n";
        String messageAttendu = "❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).";
        testerSaisieSuppression(saisie, messageAttendu);
    }

    @Test
    @DisplayName("La suppression d'une borne avec un ID non numérique est gérée")
    public void testSuppressionBorneIDNonNumerique() {
        String saisie = "test\n";
        String messageAttendu = "❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).";
        testerSaisieSuppression(saisie, messageAttendu);
    }

    @Test
    @DisplayName("La suppression d'une borne avec un ID vide est gérée")
    public void testSuppressionBorneIDVide() {
        String saisie = "\n";
        String messageAttendu = "❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).";
        testerSaisieSuppression(saisie, messageAttendu);
    }

    @Test
    @DisplayName("La suppression d'une borne avec un ID négatif est gérée")
    public void testSuppressionBorneIDNegatif() {
        String saisie = "-1\n";
        String messageAttendu = "❌ Veuillez saisir un ID valide (nombre entier supérieur à 0).";
        testerSaisieSuppression(saisie, messageAttendu);
    }

    @Test
    @DisplayName("La suppression d'une borne avec un ID dépassant la limite supérieure du type Integer est gérée")
    public void testSuppressionBorneIDDepassementLimiteSup() {
        String saisie = "2147483648\n";
        String messageAttendu = "❌ Veuillez saisir un ID compris entre les limites.";
        testerSaisieSuppression(saisie, messageAttendu);
    }

    @Test
    @DisplayName("La suppression d'une borne avec un ID dépassant la limite inférieure du type Integer est gérée")
    public void testSuppressionBorneIDDepassementLimiteInf() {
        String saisie = "-2147483649\n";
        String messageAttendu = "❌ Veuillez saisir un ID compris entre les limites.";
        testerSaisieSuppression(saisie, messageAttendu);
    }

    // endregion
}
