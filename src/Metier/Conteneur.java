package Metier;

public class Conteneur {
    int valeur;
    int idJoueur;
    Coin[] coins;

    public Conteneur(int valeur){
        this.valeur = valeur;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public int getValeur() {
        return valeur;
    }
}
