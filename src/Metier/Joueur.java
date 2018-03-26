package Metier;

public class Joueur {
    int nbPion = 20;
    int id;

    public Joueur(int id){
        this.id = id;
    }

    public int getNbPion(){
        return this.nbPion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
