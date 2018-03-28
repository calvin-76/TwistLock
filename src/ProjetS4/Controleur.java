package ProjetS4;

import ProjetS4.IHM.IHM;
import ProjetS4.Metier.Coin;
import ProjetS4.Metier.Conteneur;
import ProjetS4.Metier.Joueur;

import java.util.ArrayList;

public class Controleur {
    private int num_joueur = 1;

    private ArrayList<Joueur> listJoueur;
    private Conteneur[][] tablier;
    private int nbJoueurs = 2;

    private int lignes;
    private int colonnes;

    public Controleur(int nbJoueur){
        this.listJoueur = new ArrayList<>();
        creerListConteneur();
        this.nbJoueurs = nbJoueurs;
    }

    public void ajouterJoueur(Joueur joueur){
        if(listJoueur.size() < 2)
            this.listJoueur.add(joueur);
    }

    private void creerListConteneur(){
        this.lignes = (int)(Math.random() * 6 + 4);
        this.colonnes = (int)(Math.random() * 6 + 4);

        Coin[][] listCoin = new Coin[lignes + 1][colonnes + 1];

        for(int lig = 0 ; lig<lignes + 1; lig++) {
            for (int col = 0; col < colonnes + 1; col++) {
                listCoin[lig][col] = new Coin();
            }
        }
        this.tablier = new Conteneur[lignes][colonnes];

        for(int lig = 0 ; lig<lignes; lig++){
            for (int col = 0; col < colonnes; col++){
                Conteneur tmpConteneur = new Conteneur((int)(Math.random()*50+5));
                tablier[lig][col] = tmpConteneur;
                tablier[lig][col].setCoin(listCoin[lig][col],1);
                tablier[lig][col].setCoin(listCoin[lig][col + 1],2);
                tablier[lig][col].setCoin(listCoin[lig + 1][col+1],3);
                tablier[lig][col].setCoin(listCoin[lig + 1][col],4);
            }
        }
    }

    public boolean partieFinis() {
        for(int i=0; i<listJoueur.size();i++) {
            if (listJoueur.get(i).getNbPion() > 0) return false;
        }
        return true;
    }

    public void passerTour() {
        if (num_joueur >= listJoueur.size())
            num_joueur = 1;
        else num_joueur++;

        if (listJoueur.get(num_joueur-1).getNbPion() <= 0) {
            num_joueur++;
            if (num_joueur >= listJoueur.size())
                num_joueur = 1;
            if (listJoueur.get(num_joueur-1).getNbPion() <= 0)
                num_joueur++;
        }
    }

    public Conteneur[][] getTablier() {
        return tablier;
    }

    public ArrayList<Joueur> getListJoueur() {
        return listJoueur;
    }

    public int getNum_joueur(){
        return this.num_joueur;
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
