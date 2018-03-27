package ProjetS4.Metier;

import ProjetS4.Controleur;

public class Joueur {
    private int nbTl = 5;
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

    private void recupererPoint(int id){
        point = 0;
        for(int lig = 0; lig < ctrl.getLignes(); lig++) {
            for (int col = 0; col < ctrl.getColonnes(); col++) {
                if(ctrl.getTablier()[lig][col].getAppartenir() == id ) {
                    point += ctrl.getTablier()[lig][col].getValeur();
                    System.out.println("lig : " + lig + "  col: " + col + "   " + id + "   "+ ctrl.getTablier()[lig][col].getAppartenir() +" Oui");
                }
            }
        }
    }

    public int getPoint(int id) {
        recupererPoint(id);
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
}
