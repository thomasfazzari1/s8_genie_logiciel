package fr.ul.miage.fazzari_chartier_colombana.Interfaces;

import org.bson.Document;

import java.util.ArrayList;

public interface IDBContrat {
    void ajouter(Integer id, String email, String borne, String dateA, String heureA, String dateD, String heureD, String immat);

    void supprimer(Integer id);

    ArrayList<Document> getContrats();

    boolean existe(Integer id);
}
