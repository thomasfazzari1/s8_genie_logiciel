import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBClient;
import fr.ul.miage.fazzari_chartier_colombana.DB.DBVehicule;
import fr.ul.miage.fazzari_chartier_colombana.Services.GestionnaireVehicule;
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

@DisplayName("Tests de GestionnaireVehicule")
public class GestionnaireVehiculeTest {

    @Mock
    private DBVehicule vehicules;

    @Mock
    private DBClient clients;

    @InjectMocks
    private GestionnaireVehicule gestionnaireVehicule;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void setEntreeSortie(String input, ByteArrayOutputStream outputStream) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        System.setOut(new PrintStream(outputStream));
    }

    private void testerSaisieAjoutVehicule(String saisie, String messageAttendu) {
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        Thread testThread = new Thread(() -> {
            try {
                gestionnaireVehicule.ajouterVehicule();
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
    @DisplayName("La saisie d'un email invalide est gérée")
    public void testEmailInvalide() {
        when(clients.existe(anyString())).thenReturn(false);
        testerSaisieAjoutVehicule("email_invalide@example.com\n", "❌ Aucun utilisateur n'est associé à cet email.");
    }

    @Test
    @DisplayName("La saisie d'une plaque d'immatriculation invalide est gérée")
    public void testPlaqueInvalide() {
        when(clients.existe(anyString())).thenReturn(true);
        String saisie = "email_valide@example.com\nABC123\n";
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        gestionnaireVehicule.ajouterVehicule();

        System.setIn(System.in);
        System.setOut(System.out);

        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("❌ Plaque d'immatriculation invalide."));
    }

    @Test
    @DisplayName("L'ajout d'un véhicule avec une plaque valide fonctionne")
    public void testAjoutVehiculeValide() {
        when(clients.existe("email_valide@example.com")).thenReturn(true);

        String saisie = "email_valide@example.com\nAB-123-CD\n";
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        gestionnaireVehicule.ajouterVehicule();

        System.setIn(System.in);
        System.setOut(System.out);

        String sortie = contenuSortie.toString();
        assertTrue(sortie.contains("✅ Véhicule ajouté avec succès."));

        verify(vehicules).ajouter("email_valide@example.com", "AB-123-CD");
    }

    @Test
    @DisplayName("L'ajout d'un véhicule pour un client inexistant est géré")
    public void testAjoutVehiculeClientInexistant() {
        when(clients.existe("email_invalide@example.com")).thenReturn(false);

        String saisie = "email_invalide@example.com\n";
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        setEntreeSortie(saisie, contenuSortie);

        testerSaisieAjoutVehicule(saisie, "❌ Aucun utilisateur n'est associé à cet email.");
    }
}
