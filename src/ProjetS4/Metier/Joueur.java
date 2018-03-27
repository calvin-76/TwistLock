package ProjetS4.Metier;

import ProjetS4.Controleur;

public class Joueur {
    private int nbTl = 20;
    private int point = 0;
    private int id;
    private Controleur ctrl;
    String couleur;

    public Joueur(Controleur ctrl, int id){
        this.ctrl = ctrl;
        this.id = id;
        switch (id){
            case 1: this.couleur = "Rouge"; break;
            case 2: this.couleur = "Vert"; break;
            case 3: this.couleur = "Bleu";  break;
            case 4: this.couleur = "Jaune"; break;
        }
    }

    public String getCouleur(){
        return couleur;
    }

    public int getNbPion(){
        return this.nbTl;
    }

    public void recupererPoint(){
        for(int lig = 0; lig < ctrl.getLignes(); lig++) {
            for (int col = 0; col < ctrl.getColonnes(); col++) {
                if(ctrl.getTablier()[lig][col].getIDAppartenir() == id )
                    point += ctrl.getTablier()[lig][col].getValeur();
            }
        }
    }

    public int getPoint() {
        return point;
    }

    public int getNbTl(){
        return nbTl;
    }

    public void retirerTl(){
        nbTl--;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
