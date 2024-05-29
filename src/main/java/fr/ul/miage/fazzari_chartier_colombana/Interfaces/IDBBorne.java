package fr.ul.miage.fazzari_chartier_colombana.Interfaces;

import org.bson.Document;

import java.util.ArrayList;

public interface IDBBorne {
    void ajouter(Integer id, String emplacement);

    void supprimer(Integer id);

    ArrayList<Document> getBornes();

    boolean existe(Integer id);
}
