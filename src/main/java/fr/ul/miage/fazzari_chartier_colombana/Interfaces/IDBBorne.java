package fr.ul.miage.fazzari_chartier_colombana.Interfaces;

import org.bson.Document;

import java.util.ArrayList;

public interface IDBBorne {
    void ajouter(int id, String emplacement);

    void supprimer(int id);

    ArrayList<Document> getBornes();

    boolean existe(int id);
}
