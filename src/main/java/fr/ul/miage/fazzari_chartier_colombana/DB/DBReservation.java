package fr.ul.miage.fazzari_chartier_colombana.DB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.ul.miage.fazzari_chartier_colombana.Interfaces.IDBReservation;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class DBReservation implements IDBReservation {
    private static DBReservation instance;
    private static MongoDatabase database = DBConfiguration.getDatabase();
    private static MongoCollection<Document> contrats = database.getCollection("Contrats");

    private DBReservation() {
    }

    public static DBReservation getInstance() {
        if (instance == null) {
            instance = new DBReservation();
        }
        return instance;
    }

    @Override
    public void ajouter(Integer id, String email, String borne, String dateA, String heureA, String dateD, String heureD, String immat) {
        contrats.insertOne(new Document()
                .append("Id", id)
                .append("Email client", email)
                .append("Id borne", borne)
                .append("Date arrivee", dateA)
                .append("Heure arrivee", heureA)
                .append("Date depart", dateD)
                .append("Heure depart", heureD)
                .append("Immatriculation vehicule", immat)
                .append("Checking arrivee", false)
                .append("Checking depart", false)
        );
    }

    @Override
    public void supprimer(Integer id) {
        contrats.deleteOne(new Document("Id", id));
    }

    @Override
    public ArrayList<Document> getContrats() {
        return contrats.find().into(new ArrayList<>());
    }

    @Override
    public boolean existe(Integer id) {
        Bson filter = Filters.eq("Id", id);
        return contrats.find(filter).first() != null;
    }
}