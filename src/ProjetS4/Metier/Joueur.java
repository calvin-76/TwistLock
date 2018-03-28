package ProjetS4.Metier;

import ProjetS4.Controleur;

import java.net.InetAddress;

public class Joueur {
    private int nbTl = 5;
    private int point = 0;
    private int id;
    private Controleur jeu;
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

    public Joueur(int id, InetAddress adresse, int port, Controleur jeu){
        this.id = id;
        switch (id){
            case 1: this.couleur = "Rouge"; break;
            case 2: this.couleur = "Vert"; break;
            case 3: this.couleur = "Bleu";  break;
            case 4: this.couleur = "Jaune"; break;
        }
        this.adresse = adresse;
        this.port = port;
        this.jeu = jeu;
    }

    public String getCouleur(){
        return this.couleur;
    }

    public int getNbPion(){
        return this.nbTl;
    }


    public int getPoint() {
        recupererPoint();
        return this.point;
    }

    public void recupererPoint(){
        for (int lig = 0; lig < jeu.getLignes(); lig++) {
            for (int col = 0; col < jeu.getColonnes(); col++) {
                System.out.println(lig + " : " + col + " appartient a "+ jeu.getTablier()[lig][col].appartientA());
            }
        }

        point = 0;
        System.out.println(" : " + jeu.getTablier()[0][0].appartientA());
        for(int lig = 0; lig < jeu.getLignes(); lig++) {
            for (int col = 0; col < jeu.getColonnes(); col++) {
                if(jeu.getTablier()[lig][col].appartientA() == id ) {
                    point += jeu.getTablier()[lig][col].getValeur();
                    System.out.println("lig : " + lig + "  col: " + col + "   " + id + "   "+ jeu.getTablier()[lig][col].appartientA() +" Oui");
                }
            }
        }
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
