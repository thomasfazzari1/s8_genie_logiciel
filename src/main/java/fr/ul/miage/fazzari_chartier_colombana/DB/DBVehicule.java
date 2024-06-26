package fr.ul.miage.fazzari_chartier_colombana.DB;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.ul.miage.fazzari_chartier_colombana.Interfaces.IDBVehicule;
import org.bson.Document;

import java.util.ArrayList;

public class DBVehicule implements IDBVehicule {
    private static DBVehicule instance;
    private static MongoDatabase database = DBConfiguration.getDatabase();
    private static MongoCollection<Document> vehicules = database.getCollection("Vehicules");

    private DBVehicule() {
    }

    public static DBVehicule getInstance() {
        if (instance == null) {
            instance = new DBVehicule();
        }
        return instance;
    }

    @Override
    public void ajouter(String email, String immat) {
        vehicules.insertOne(new Document()
                .append("Email client", email)
                .append("Immatriculation vehicule", immat)
        );
    }

    @Override
    public void supprimer(String email, String immat) {
        vehicules.deleteOne(new Document("Email client", email).append("Immatriculation vehicule", immat));
    }

    @Override
    public ArrayList<Document> getVehicules() {
        return vehicules.find().into(new ArrayList<>());
    }

    @Override
    public boolean existe(String email, String immat) {
        Document query = new Document("Email client", email).append("Immatriculation vehicule", immat);
        return vehicules.find(query).first() != null;
    }

}