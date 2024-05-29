import static org.junit.Assert.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBClient;
import fr.ul.miage.fazzari_chartier_colombana.Services.GestionnaireClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

@DisplayName("On teste si...")
public class GestionnaireClientTest {
    @Test @DisplayName("L'ajout d'un client fonctionne")
    public void testClient() {
        String saisie = "Chartier\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireClient.ajouterClient();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("✅ Client ajouté avec succès."));
    }

    @Test @DisplayName("La saisie d'un nom vide est gérée")
    public void testNomClientNonVide() {
        String saisie = "\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireClient.ajouterClient();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Le nom du client ne peut pas être vide."));
    }

    @Test @DisplayName("La saisie d'un prénom vide est gérée")
    public void testPrenomClientNonVide() {
        String saisie = "Chartier\n\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireClient.ajouterClient();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Le prénom du client ne peut pas être vide."));
    }

    @Test @DisplayName("la saisie d'une adresse vide est gérée")
    public void testAdresseClientNonVide() {
        String saisie = "Chartier\nGuillaume\n\n0666666666\nemail@gmail.com\n1234123412341234";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireClient.ajouterClient();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ L'adresse du client ne peut pas être vide."));
    }

    @Test @DisplayName("la saisie d'un numéro de téléphone vide et gérée")
    public void testNumeroTelephoneClientNonVide() {
        String saisie = "Chartier\nGuillaume\nGrande Rue\n\nemail@gmail.com\n1234123412341234";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireClient.ajouterClient();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Le numéro de téléphone du client ne peut pas être vide."));
    }

    @Test @DisplayName("la saisie d'un email vide est gérée")
    public void testEmailClientNonVide() {
        String saisie = "Chartier\nGuillaume\nGrande Rue\n0666666666\n\n1234123412341234";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireClient.ajouterClient();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ L'email du client ne peut pas être vide."));
    }

    @Test @DisplayName("la saisie d'une carte bancaire vide est gérée")
    public void testCarteBancaireClientNonVide() {
        String saisie = "Chartier\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n\n";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireClient.ajouterClient();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ La carte bancaire du client ne peut pas être vide."));
    }

    @Test @DisplayName("Un email déjà existant est géré")
    public void testEmailDejaExistant() {
        DBClient clients = new DBClient();
        clients.ajouter("Chartier","Guillaume","Grande Rue","0666666666","email@gmail.com","1234123412341234");
        String saisie = "Chartier\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        GestionnaireClient.ajouterClient();
        System.setIn(System.in);
        System.setOut(System.out);
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Cet email est déjà associé à un client."));
    }
}