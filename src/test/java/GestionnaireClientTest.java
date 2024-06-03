import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBClient;
import fr.ul.miage.fazzari_chartier_colombana.Services.GestionnaireClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

@DisplayName("On teste si...")
public class GestionnaireClientTest {

    @Mock
    private DBClient clients;

    @InjectMocks
    private GestionnaireClient gestionnaireClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void setEntreeSortie(String input, ByteArrayOutputStream outputStream) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        System.setOut(new PrintStream(outputStream));
    }

    private void testerSaisie(String saisie, String messageAttendu) {
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);
        Thread testThread = new Thread(() -> {
            try {
                gestionnaireClient.ajouterClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        testThread.start();
        try {
            Thread.sleep(1000);
            testThread.interrupt();
            testThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains(messageAttendu));
    }

    @Test
    @DisplayName("L'ajout d'un client fonctionne")
    public void testClient() {
        when(clients.existe(anyString())).thenReturn(false);
        testerSaisie("Chartier\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234\n", "✅ Client ajouté avec succès.");
        verify(clients).ajouter("Chartier", "Guillaume", "Grande Rue", "0666666666", "email@gmail.com", "1234123412341234");
    }

    @Test
    @DisplayName("La saisie d'un nom vide est gérée")
    public void testNomClientNonVide() {
        testerSaisie("\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234\n", "❌ Le nom du client ne peut pas être vide.");
    }

    @Test
    @DisplayName("La saisie d'un prénom vide est gérée")
    public void testPrenomClientNonVide() {
        testerSaisie("Chartier\n\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234\n", "❌ Le prénom du client ne peut pas être vide.");
    }

    @Test
    @DisplayName("la saisie d'une adresse vide est gérée")
    public void testAdresseClientNonVide() {
        testerSaisie("Chartier\nGuillaume\n\n0666666666\nemail@gmail.com\n1234123412341234\n", "❌ L'adresse du client ne peut pas être vide.");
    }

    @Test
    @DisplayName("la saisie d'un numéro de téléphone vide est gérée")
    public void testNumeroTelephoneClientNonVide() {
        testerSaisie("Chartier\nGuillaume\nGrande Rue\n\nemail@gmail.com\n1234123412341234\n", "❌ Le numéro de téléphone du client ne peut pas être vide.");
    }

    @Test
    @DisplayName("la saisie d'un email vide est gérée")
    public void testEmailClientNonVide() {
        testerSaisie("Chartier\nGuillaume\nGrande Rue\n0666666666\n\n1234123412341234\n", "❌ L'email du client ne peut pas être vide.");
    }

    @Test
    @DisplayName("la saisie d'une carte bancaire vide est gérée")
    public void testCarteBancaireClientNonVide() {
        testerSaisie("Chartier\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n\n", "❌ La carte bancaire du client ne peut pas être vide.");
    }

    @Test
    @DisplayName("Un email déjà existant est géré")
    public void testEmailDejaExistant() {
        when(clients.existe("email@gmail.com")).thenReturn(true);
        testerSaisie("Chartier\nGuillaume\nGrande Rue\n0666666666\nemail@gmail.com\n1234123412341234\n", "❌ Cet email est déjà associé à un client.");
    }
}
