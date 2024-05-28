package fr.ul.miage.fazzari_chartier_colombana.Interfaces;

import org.bson.Document;

import java.util.ArrayList;

public interface IDBClient {
    void ajouter(String nom, String prenom, String adresse, String telephone, String email, String cb);

    void supprimer(String email);

    ArrayList<Document> getClients();

    boolean existe(String email);
}

