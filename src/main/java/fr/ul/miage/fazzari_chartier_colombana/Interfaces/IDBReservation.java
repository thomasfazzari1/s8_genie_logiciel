package fr.ul.miage.fazzari_chartier_colombana.Interfaces;

import org.bson.Document;

import java.util.ArrayList;

public interface IDBReservation {
    void ajouter(Integer id, String email, String borne, String dateA, String heureA, String dateD, String heureD, String immat);

    void supprimer(Integer id);

    void checkArrivee(Integer id);

    void checkDepart(Integer id);

    ArrayList<Document> getReservations();

    boolean existe(Integer id);
}
