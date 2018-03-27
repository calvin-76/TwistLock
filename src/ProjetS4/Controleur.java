package ProjetS4;

import ProjetS4.IHM.IHM;
import ProjetS4.Metier.Coin;
import ProjetS4.Metier.Conteneur;
import ProjetS4.Metier.Joueur;

import java.util.ArrayList;

public class Controleur {
    public static int TOUR = 1;

    private ArrayList<Joueur> listJoueur;
    private Coin[][] listCoin;
    private Conteneur[][] tablier;

    private int lignes;
    private int colonnes;

    public Controleur(int nbJoueur){
        this.listJoueur = new ArrayList<Joueur>();
        creerListJoueur(nbJoueur);
        creerListConteneur();

        new IHM(this);
    }

    private void creerListJoueur(int nb){
        for(int i=1; i<= nb; i++){
            this.listJoueur.add(new Joueur(this,i));
        }
    }

    private void creerListConteneur(){
        this.lignes = (int)(Math.random() * 6 + 4);
        this.colonnes = (int)(Math.random() * 6 + 4);

        this.listCoin = new Coin[lignes + 1][colonnes + 1];

        for(int lig = 0 ; lig<lignes; lig++) {
            for (int col = 0; col < colonnes; col++) {
                listCoin[lig][col] = new Coin(this);;
            }
        }
        this.tablier = new Conteneur[lignes][colonnes];

        for(int lig = 0 ; lig<lignes; lig++){
            for (int col = 0; col < colonnes; col++){
                Conteneur tmpConteneur = new Conteneur(this,(int)(Math.random()*50+5));
                tablier[lig][col] = tmpConteneur;
                tablier[lig][col].setCoin(listCoin[lig][col],1);
                tablier[lig][col].setCoin(listCoin[lig][col + 1],2);
                tablier[lig][col].setCoin(listCoin[lig + 1][col+1],3);
                tablier[lig][col].setCoin(listCoin[lig + 1][col],4);
            }
        }
    }

    public void passerTour() {
        if(TOUR == listJoueur.size() ) TOUR = 1;
        else TOUR++;
    }

    public Conteneur[][] getTablier() {
        return tablier;
    }

    public ArrayList<Joueur> getListJoueur() {
        return listJoueur;
    }

    public int getLignes(){
        return  this.lignes;
    }

    public int getColonnes(){
        return  this.colonnes;
    }

    public ArrayList<Joueur> getlistJoueur(){
        return  this.listJoueur;
    }

    public static void main(String[] args){
        new Controleur(2);
    }
}
