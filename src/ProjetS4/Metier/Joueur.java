package ProjetS4.Metier;

import ProjetS4.Controleur;

public class Joueur {
    private int nbtl = 20;
    private int point = 0;
    private int id;
    private Controleur ctrl;
    String couleur;

    public Joueur(Controleur ctrl, int id){
        this.ctrl = ctrl;
        this.id = id;
    }

    public String getCouleur(){
        return couleur;
    }

    public int getNbPion(){
        return this.nbtl;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
