package ProjetS4.IHM;

import ProjetS4.Controleur;

import javax.swing.*;
import java.awt.*;

public class PanelJoueur extends JPanel {
    private JLabel point;
    private Controleur ctrl;
    private int id;
    private JLabel nbTl;

    public PanelJoueur(Controleur ctrl, int id){
        this.ctrl = ctrl;
        this.id = id;
        setLayout(new BorderLayout());
        nbTl = new JLabel("Nombre de Twist Lock : " + this.ctrl.getListJoueur().get(id-1).getNbTl());
        JLabel nom = new JLabel("Joueur " + this.ctrl.getListJoueur().get(id-1).getCouleur());
        point = new JLabel("Nombre de points : "+this.ctrl.getListJoueur().get(id - 1).getPoint());

        JPanel panelGrid = new JPanel(new GridLayout(3,1));
        JPanel panelFlow = new JPanel();

        panelGrid.add(nom);
        panelGrid.add(nbTl);
        panelGrid.add(point);

        panelFlow.add(panelGrid);

        add(panelFlow);
        add(new JLabel("       "),BorderLayout.WEST);
        add(new JLabel("       "),BorderLayout.EAST);
    }

    public void renouvelle(){
        point.setText("Nombre de points : "+ ctrl.getListJoueur().get(id-1).getPoint());
        nbTl.setText("Nombre de Twist Lock : " + ctrl.getListJoueur().get(id-1).getNbTl());
    }
}
