package ProjetS4;

import ProjetS4.Metier.Coin;
import ProjetS4.Metier.Conteneur;
import ProjetS4.Metier.Joueur;

import java.util.ArrayList;

public class Controleur {

    private ArrayList<Joueur> listJoueur;
    private ArrayList<Coin> listCoin;
    private Conteneur[][] tablier;

    private int lignes;
    private int colonnes;

    public Controleur(int nbJoueur){
        this.listJoueur = new ArrayList<Joueur>();
        this.listCoin = new ArrayList<Coin>();

        creerListJoueur(nbJoueur);
        creerListConteneur();
    }

    private void creerListJoueur(int nb){
        for(int i=0; i< nb; i++){
            this.listJoueur.add(new Joueur(this,i+1));
        }
    }

    private void creerListConteneur(){
        this.lignes = (int)Math.random() * 15 ;
        this.colonnes = (int)Math.random() * 15 ;

        this.tablier = new Conteneur[lignes][colonnes];

        for(int lig = 0 ; lig<lignes; lig++){
            for (int col = 0; col < colonnes; col++){
                Conteneur tmpConteneur = new Conteneur(this,(int)Math.random());
                tmpConteneur.setCoin(new Coin(this, tmpConteneur));
                tablier[lig][col] = tmpConteneur;
            }
        }
    }

    public boolean selectCoin(int lig, int col, int numcoin){
        if(! tablier[lig][col].getCoin(numcoin -1).isVerouille()) {
            tablier[lig][col].getCoin(numcoin - 1).setVerouille();
            return true;
        }
        return false;
    }

    public Conteneur[][] getTablier() {
        return tablier;
    }

    public int getLignes(){
        return  this.lignes;
    }

    public int getColonnes(){
        return  this.colonnes;
    }

    public static void main(String[] args){
        new Controleur(2);
    }
}
