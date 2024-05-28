package fr.ul.miage.fazzari_chartier_colombana.DB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.ul.miage.fazzari_chartier_colombana.Interfaces.IDBBorne;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class DBBorne implements IDBBorne {
    private static MongoDatabase database = DBConfiguration.getDatabase();
    private static MongoCollection<Document> bornes = database.getCollection("Bornes");

    @Override
    public void ajouter(int id, String emplacement) {
        bornes.insertOne(new Document()
                .append("Id", id)
                .append("Emplacement", emplacement)
        );
    }

    @Override
    public void supprimer(int id) {
        bornes.deleteOne(new Document("Id", id));
    }

    @Override
    public ArrayList<Document> getBornes() {
        return bornes.find().into(new ArrayList<>());
    }

    @Override
    public boolean existe(int id) {
        Bson filter = Filters.eq("Id", id);
        return bornes.find(filter).first() != null;
    }
}