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
    DBClient clients = new DBClient();

    private void setEntreeSortie(String input, ByteArrayOutputStream outputStream) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        System.setOut(new PrintStream(outputStream));
    }

    private void testerSaisie(String saisie, String messageAttendu) {
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);
        Thread testThread = new Thread(() -> {
            try {GestionnaireClient.ajouterClient();}
            catch (Exception e) {}
        });
        testThread.start();
        try {
            Thread.sleep(1000);
            testThread.interrupt();
            testThread.join();
        }
        catch (InterruptedException e) {e.printStackTrace();}
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains(messageAttendu));
    }

    @Test @DisplayName("L'ajout d'un client fonctionne")
    public void testClient() {
        testerSaisie("Chartier\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234", "✅ Client ajouté avec succès.");
        clients.supprimer("email@gmail.com");
    }

    @Test @DisplayName("La saisie d'un nom vide est gérée")
    public void testNomClientNonVide() {
        testerSaisie("\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234\n", "❌ Le nom du client ne peut pas être vide.");
    }

    @Test @DisplayName("La saisie d'un prénom vide est gérée")
    public void testPrenomClientNonVide() {
        testerSaisie("Chartier\n\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234\n", "❌ Le prénom du client ne peut pas être vide.");
    }

    @Test @DisplayName("la saisie d'une adresse vide est gérée")
    public void testAdresseClientNonVide() {
        testerSaisie("Chartier\nGuillaume\n\n0666666666\nemail@gmail.com\n1234123412341234\n", "❌ L'adresse du client ne peut pas être vide.");
    }

    @Test @DisplayName("la saisie d'un numéro de téléphone vide et gérée")
    public void testNumeroTelephoneClientNonVide() {
        testerSaisie("Chartier\nGuillaume\nGrande Rue\n\nemail@gmail.com\n1234123412341234\n", "❌ Le numéro de téléphone du client ne peut pas être vide.");
    }

    @Test @DisplayName("la saisie d'un email vide est gérée")
    public void testEmailClientNonVide() {
        testerSaisie("Chartier\nGuillaume\nGrande Rue\n0666666666\n\n1234123412341234\n", "❌ L'email du client ne peut pas être vide.");
    }

    @Test @DisplayName("la saisie d'une carte bancaire vide est gérée")
    public void testCarteBancaireClientNonVide() {
        testerSaisie("Chartier\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n\n", "❌ La carte bancaire du client ne peut pas être vide.");
    }

    @Test @DisplayName("Un email déjà existant est géré")
    public void testEmailDejaExistant() {
        clients.ajouter("Chartier","Guillaume","Grande Rue","0666666666","email@gmail.com","1234123412341234");
        String saisie = "Chartier\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234";
        InputStream in = new ByteArrayInputStream(saisie.getBytes());
        System.setIn(in);
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(contenuSortie));
        System.setIn(System.in);
        System.setOut(System.out);
        Thread testThread = new Thread(() -> {
            try {GestionnaireClient.ajouterClient();}
            catch (Exception e) {}
        });
        testThread.start();
        try {
            Thread.sleep(1000);
            testThread.interrupt();
            testThread.join();
        }
        catch (InterruptedException e) {e.printStackTrace();}
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Cet email est déjà associé à un client."));
        clients.supprimer("email@gmail.com");
    }
}