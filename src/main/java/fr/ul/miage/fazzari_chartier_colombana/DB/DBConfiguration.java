package fr.ul.miage.fazzari_chartier_colombana.DB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBConfiguration {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "ProjetGL";

    private static MongoClient mongoClient = null;

    public static MongoClient getMongoClient() {
        if (mongoClient == null) mongoClient = MongoClients.create(CONNECTION_STRING);
        return mongoClient;
    }

    public static MongoDatabase getDatabase() {
        return getMongoClient().getDatabase(DATABASE_NAME);
    }

    public static void close() {
        if (mongoClient != null) mongoClient.close();
    }
}