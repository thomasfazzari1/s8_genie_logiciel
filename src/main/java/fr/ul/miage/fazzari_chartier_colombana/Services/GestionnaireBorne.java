package fr.ul.miage.fazzari_chartier_colombana.Services;

import fr.ul.miage.fazzari_chartier_colombana.DB.DBBorne;

public class GestionnaireBorne {
    private static DBBorne bornes;

    public static void ajouterBorne() {
        bornes = new DBBorne();
    }
}