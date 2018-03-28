package ProjetS4.Metier;

import ProjetS4.Controleur;

import java.net.InetAddress;

public class Joueur {
    private int nbTl = 5;
    private int point = 0;
    private int id;
    private Controleur ctrl;
    String couleur;
    private InetAddress adresse;
    private int port;

    public Joueur(int id){
        switch (id){
            case 1: this.couleur = "Rouge"; break;
            case 2: this.couleur = "Vert"; break;
            case 3: this.couleur = "Bleu";  break;
            case 4: this.couleur = "Jaune"; break;
        }
    }

    public Joueur( int id,InetAddress adresse, int port){
        this.id = id;
        switch (id){
            case 1: this.couleur = "Rouge"; break;
            case 2: this.couleur = "Vert"; break;
            case 3: this.couleur = "Bleu";  break;
            case 4: this.couleur = "Jaune"; break;
        }
        this.adresse = adresse;
        this.port = port;
    }

    public String getCouleur(){
        return this.couleur;
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
        return this.point;
    }

    public int getNbTl(){
        return this.nbTl;
    }

    public void retirerTl(){
        this.nbTl--;
    }

    public int getId() {
        return this.id;
    }

    public InetAddress getAdresse() {
        return adresse;
    }

    public int getPort() {
        return port;
    }
}
