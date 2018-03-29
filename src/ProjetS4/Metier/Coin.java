package ProjetS4.Metier;

import java.util.ArrayList;

public class Coin {
    private boolean verouille;
    private Joueur joueur;
    private ArrayList<Conteneur> conteneurs;

    public boolean isVerrouille(){
        return this.verouille;
    }

    public Coin(){
        this.verouille = false;
        this.conteneurs = new ArrayList<Conteneur>();
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

	public ArrayList<Conteneur> getConteneurs() {return conteneurs;}

	public void addConteneurs(Conteneur conteneur) {if (this.conteneurs.size() < 4) this.conteneurs.add(conteneur);}

	public void setVerouille(){
        this.verouille = true;
    }

}
