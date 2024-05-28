package fr.ul.miage.fazzari_chartier_colombana.Interfaces;

import org.bson.Document;

import java.util.ArrayList;

public interface IDBVehicule {
    void ajouter(String email, String immat);

    void supprimer(String email, String immat);

    ArrayList<Document> getVehicules();

}
