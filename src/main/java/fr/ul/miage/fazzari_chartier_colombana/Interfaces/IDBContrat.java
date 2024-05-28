package fr.ul.miage.fazzari_chartier_colombana.Interfaces;

import org.bson.Document;

import java.util.ArrayList;

public interface IDBContrat {
    void ajouter(int id, String email, String borne, String dateA, String heureA, String dateD, String heureD, String immat);

    void supprimer(int id);

    ArrayList<Document> getContrats();

    boolean existe(int id);
}
