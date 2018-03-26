package Metier;

import javax.swing.*;
import java.awt.*;

public class Conteneur extends JPanel{
    int valeur;
    int idJoueur;
    Coin[] coins;

    public Conteneur(int valeur){
        setLayout(new GridLayout(1,0));
        this.valeur = valeur;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public int getValeur() {
        return valeur;
    }
}
