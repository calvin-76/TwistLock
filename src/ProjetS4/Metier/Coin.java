package ProjetS4.Metier;

import ProjetS4.Controleur;

import java.util.ArrayList;

public class Coin {
    private boolean verouille;
    private Joueur joueur;
    private Controleur ctrl;
    private ArrayList<Conteneur> listconteneur;

    public boolean isVerouille(){
        return this.verouille;
    }

    public Coin(Controleur ctrl){
        this.ctrl = ctrl;
        this.verouille = false;
        this.listconteneur = new ArrayList<Conteneur>();
    }

    public void addconteneur(Conteneur conteneur){
        this.listconteneur.add(conteneur);
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur){
        if(! this.verouille) {
            this.joueur = joueur;
            setVerouille();
        }

    }

    public ArrayList<Conteneur> getConteneur(){
        return this.listconteneur;
    }

    private void setVerouille(){
        verouille = true;
    }

}
