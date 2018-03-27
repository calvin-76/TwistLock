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
        point = new JLabel("point : "+this.ctrl.getListJoueur().get(id - 1).getPoint(id));

        JPanel panelNom = new JPanel(new BorderLayout());
        JPanel panelNbTl = new JPanel(new BorderLayout());
        JPanel panelPoint = new JPanel(new BorderLayout());

        panelNom.add(nom);
        panelNbTl.add(nbTl);
        panelPoint.add(point);

        panelNom.add(panelNbTl, BorderLayout.SOUTH);
        panelNbTl.add(panelPoint, BorderLayout.SOUTH);

        add(panelNom);
        add(new JLabel("       "),BorderLayout.WEST);
        add(new JLabel("       "),BorderLayout.EAST);
    }

    public void renouvelle(){
        point.setText("point : "+ ctrl.getListJoueur().get(0).getPoint(id));
        nbTl.setText("Nombre de Twist Lock : " + ctrl.getListJoueur().get(id-1).getNbTl());
    }
}
