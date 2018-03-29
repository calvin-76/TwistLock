package ProjetS4.Metier;

public class Coin {
    private boolean verouille;
    private Joueur joueur;

    public boolean isVerrouille(){
        return this.verouille;
    }

    public Coin(){
        this.verouille = false;
    }

    public Joueur getJoueur() {
        return this.joueur;
    }

    public void setJoueur(Joueur joueur){
        if(! this.verouille) {
            this.joueur = joueur;
            setVerouille();
        }

    }

    private void setVerouille(){
        this.verouille = true;
    }

}
