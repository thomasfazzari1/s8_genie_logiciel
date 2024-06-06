package fr.ul.miage.fazzari_chartier_colombana.DB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.ul.miage.fazzari_chartier_colombana.Interfaces.IDBClient;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class DBClient implements IDBClient {
    private static DBClient instance;
    private static MongoDatabase database = DBConfiguration.getDatabase();
    private static MongoCollection<Document> clients = database.getCollection("Clients");

    private DBClient() {
    }

    public static DBClient getInstance() {
        if (instance == null) {
            instance = new DBClient();
        }
        return instance;
    }

    @Override
    public void ajouter(String nom, String prenom, String adresse, String telephone, String email, String cb) {
        clients.insertOne(new Document()
                .append("Nom", nom)
                .append("Prenom", prenom)
                .append("Adresse", adresse)
                .append("NumeroTelephone", telephone)
                .append("Email", email)
                .append("NumeroCB", cb)
        );
    }

    @Override
    public void supprimer(String email) {
        clients.deleteOne(new Document("Email", email));
    }

    @Override
    public ArrayList<Document> getClients() {
        return clients.find().into(new ArrayList<>());
    }

    @Override
    public boolean existe(String email) {
        Bson filter = Filters.eq("Email", email);
        return clients.find(filter).first() != null;
    }
}
