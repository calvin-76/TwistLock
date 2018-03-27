package ProjetS4.IHM;

import ProjetS4.Controleur;

import javax.swing.*;
import java.awt.*;

public class PanelJoueur extends JPanel {
    private JLabel point;
    private Controleur ctrl;
    private int id;
    public PanelJoueur(Controleur ctrl, int id){
        this.id = id;
        setLayout(new BorderLayout());
        JLabel nbTl = new JLabel("pion : " + ctrl.getListJoueur().get(id-1).getNbTl());
        JLabel nom = new JLabel("id : " + id);
        point = new JLabel("point : "+ctrl.getListJoueur().get(id - 1).getPoint());

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
        point.setText("point : "+ ctrl.getListJoueur().get(id - 1).getPoint());
        point.setText("pion : " + ctrl.getListJoueur().get(id - 1).getNbTl());
    }
}
