package ProjetS4.Metier;

import ProjetS4.Controleur;

public class Coin {
    private boolean verouille;
    private Joueur joueur;
    private Controleur ctrl;

    public boolean isVerouille(){
        return this.verouille;
    }

    public Coin(Controleur ctrl){
        this.ctrl = ctrl;
        this.verouille = false;
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

    private void setVerouille(){
        verouille = true;
    }

}
