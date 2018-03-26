package Metier;

import Controleur.Controleur;

public class Coin {
    private boolean verouille;
    private Joueur joueur;
    private Controleur ctrl;
    private Conteneur conteneur;

    public boolean isVerouille(){
        return this.verouille;
    }

    public Coin(Controleur ctrl, Conteneur conteneur){
        this.ctrl = ctrl;
        this.conteneur = conteneur;
        this.verouille = false;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur){
        if(! this.verouille)
            this.joueur = joueur;
    }

    public Conteneur getConteneur(){
        return this.conteneur;
    }

    public void setVerouille(){
        verouille = false;
    }



}
