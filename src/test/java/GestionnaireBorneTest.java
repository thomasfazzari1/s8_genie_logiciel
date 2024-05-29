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
    @Test
    @DisplayName("La saisie d'un ID vide est gérée")
    public void testIDVide() {
        String saisie = "\n\nEmplacementTest";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireBorne.ajouterBorne();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Veuillez saisir un ID valide (nombre entier supérieur à 0)."));
    }

    @Test
    @DisplayName("La saisie d'un ID négatif est gérée")
    public void testIDNegatif() {
        String saisie = "-4\n\nEmplacementTest";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireBorne.ajouterBorne();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Veuillez saisir un ID valide (nombre entier supérieur à 0)."));
    }

    @Test
    @DisplayName("La saisie d'un ID invalide est gérée")
    public void testIDInvalide() {
        String saisie = "ChaineDeCaractère\n\nEmplacementTest";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireBorne.ajouterBorne();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Veuillez saisir un ID valide (nombre entier supérieur à 0)."));
    }

    @Test
    @DisplayName("La saisie d'un emplacement vide est gérée")
    public void testEmplacementVide() {
        String saisie = "1\n\n";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireBorne.ajouterBorne();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ L'emplacement de la borne ne peut pas être vide."));
    }

    @Test
    @DisplayName("La saisie d'un ID dépassant la limite supérieure du type Integer est gérée")
    public void testDepassementLimiteSupID() {
        long idDepassantMax = (long) Integer.MAX_VALUE + 1;
        String saisie = idDepassantMax + "\nEmplacementTest";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireBorne.ajouterBorne();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Veuillez saisir un ID compris entre les limites."));
    }

    @Test
    @DisplayName("La saisie d'un ID dépassant la limite inférieure du type Integer est gérée")
    public void testDepassementLimiteInfID() {
        long idDepassantMax = (long) Integer.MIN_VALUE - 1;
        String saisie = idDepassantMax + "\nEmplacementTest";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireBorne.ajouterBorne();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Veuillez saisir un ID compris entre les limites."));
    }

    @Test
    @DisplayName("L'ajout d'une borne fonctionne")
    public void testAjoutBorne() {
        String saisie = "12345\nEmplacement Valide";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireBorne.ajouterBorne();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("✅ Borne ajouté avec succès."));
    }

    @Test
    @DisplayName("L'ajout d'une borne déjà existante fonctionne")
    public void testAjoutBorneExistante() {
        DBBorne bornes = new DBBorne();
        bornes.ajouter(123, "Test");

        String saisie = "123\nEmplacement Test";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);

        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));

        GestionnaireBorne.ajouterBorne();

        System.setIn(System.in);
        System.setOut(System.out);

        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Une borne avec cet identifiant est déjà enregistrée."));
    }
}