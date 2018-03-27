package ProjetS4.IHM;

import ProjetS4.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class IHM extends JFrame implements ActionListener{

    private int lignes;
    private int colonnes;

    private JTextField tfLigneConteneur;
    private JTextField tfColonneConteneur;
    private JTextField tfnumCoin;

    ArrayList<PanelJoueur> panelJoueur;

    private JLabel message;

    private JButton valider;

    private JLabel[][] listCoin;
    private JLabel[][] listConteneur;
    private Controleur ctrl;

    public IHM(Controleur ctrl){
        this.ctrl = ctrl;

        lignes = ctrl.getLignes() * 2 + 1;
        colonnes = ctrl.getColonnes() * 2 + 1;

        listCoin = new JLabel[ctrl.getLignes() + 1][ctrl.getColonnes() + 1];
        listConteneur = new JLabel[ctrl.getLignes()][ctrl.getColonnes()];

        setLocation(0,0);
        setTitle("Twist Lock");

        JPanel panelGlobal = new JPanel(new BorderLayout());
        JPanel panelNord = new JPanel(new BorderLayout());
        JPanel panelDroite = new JPanel(new BorderLayout());
        JPanel panelGauche = new JPanel(new BorderLayout());
        JPanel panelBas = new JPanel(new BorderLayout());

        JPanel panelTable = new JPanel(new GridLayout(this.lignes, this.colonnes));

        panelTable.setBackground(Color.WHITE);

        JPanel tmpPanel;
        JLabel tmpLabel;
        for(int lig=0; lig<this.lignes; lig++) {
            for (int col = 0; col < this.colonnes; col++) {
                if(lig % 2 == 0 && col % 2 ==0){
                    tmpLabel = new JLabel(getImage("img/coinVide.png", 80,  55) );
                    listCoin[lig / 2 ][col / 2 ] = tmpLabel;
                    tmpPanel = new JPanel(new BorderLayout());
                    tmpPanel.add(tmpLabel);
                    tmpPanel.setBackground(Color.WHITE);
                    panelTable.add(tmpPanel);
                }else if(lig % 2 == 1 && col % 2 == 1){
                    tmpLabel = new JLabel("" + ctrl.getTablier()[lig / 2][col / 2].getValeur(),SwingConstants.CENTER);
                    listConteneur[lig / 2 ][col / 2] = tmpLabel;
                    tmpPanel = new JPanel(new BorderLayout());
                    tmpPanel.setBackground(Color.WHITE);
                    tmpPanel.add(tmpLabel);
                    panelTable.add(tmpPanel);
                }
                else if(lig % 2 == 0){
                    tmpLabel = new JLabel(getImage("./img/horizontal.png",80,55));
                    tmpPanel = new JPanel(new BorderLayout());
                    tmpPanel.setBackground(Color.WHITE);
                    tmpPanel.add(tmpLabel);
                    panelTable.add(tmpPanel);
                }
                else{
                    tmpLabel = new JLabel(getImage("./img/vertical.png",80,55));
                    tmpPanel = new JPanel(new BorderLayout());
                    tmpPanel.setBackground(Color.WHITE);
                    tmpPanel.add(tmpLabel);
                    panelTable.add(tmpPanel);
                }
            }
        }


        /*--------------panel bas-----------------*/
        JPanel panelLigneConteneur = new JPanel();
        JPanel panelColonneConteneur = new JPanel();
        JPanel panelCoin = new JPanel();

        JLabel labelLigneConteneur = new JLabel("Ligne : ");
        JLabel labelColoneConteneur = new JLabel("Colonne : ");
        JLabel labelnumCoin = new JLabel("Coin : ");

        tfLigneConteneur = new JTextField(4);
        tfColonneConteneur = new JTextField(4);
        tfnumCoin = new JTextField(4);

        panelLigneConteneur.add(labelLigneConteneur);
        panelLigneConteneur.add(tfLigneConteneur);

        panelColonneConteneur.add(labelColoneConteneur);
        panelColonneConteneur.add(tfColonneConteneur);

        panelCoin.add(labelnumCoin);
        panelCoin.add(tfnumCoin);

        valider = new JButton("Valider");
        valider.addActionListener(this);

        JPanel panelColoneLigne = new JPanel();
        panelColoneLigne.add(panelLigneConteneur);
        panelColoneLigne.add(panelColonneConteneur);
        panelColoneLigne.add(panelCoin);
        panelColoneLigne.add(valider);

        message = new JLabel(" ",SwingConstants.CENTER);

        JPanel panelMessageColonneLigne = new JPanel(new BorderLayout());
        panelMessageColonneLigne.add(message,BorderLayout.NORTH);
        panelMessageColonneLigne.add(panelColoneLigne);

        /*------------------------------------------------------------*/

        /*--------------------  Panel droit  ------------------------*/
        panelJoueur = new ArrayList<PanelJoueur>();

        JPanel panelGridJoueur = new JPanel(new GridLayout(ctrl.getListJoueur().size(),1));
        for(int i =1; i <= ctrl.getListJoueur().size(); i++) {
            panelJoueur.add(new PanelJoueur(ctrl, i));
            panelGridJoueur.add(panelJoueur.get(i-1));
        }


        /*-----------------------------------------------------------*/
        panelNord.add(new JLabel("  "));
        panelBas.add(panelMessageColonneLigne);
        panelGauche.add(new JLabel("OK"));
        panelDroite.add(new JLabel("OK"));

        JPanel paneltmpFlow = new JPanel();
        paneltmpFlow.add(panelTable);

        JScrollPane panelTablePane = new JScrollPane(paneltmpFlow);

        panelGlobal.add(panelTablePane,BorderLayout.CENTER);
        panelGlobal.add(panelBas,BorderLayout.SOUTH);
        panelGlobal.add(panelNord,BorderLayout.NORTH);
        panelGlobal.add(panelGauche,BorderLayout.WEST);
        panelGlobal.add(panelGridJoueur,BorderLayout.EAST);
        add(panelGlobal);

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        setVisible(true);
        pack();
    }

    private ImageIcon getImage(String path,int iconWidth, int iconHeight){
        ImageIcon imageIcon   = new ImageIcon(path);
        Image image           = imageIcon.getImage();
        Image smallIcon       = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH );
        ImageIcon imageCharge = new ImageIcon(smallIcon);

        return imageCharge;
    }

    public void actionPerformed(ActionEvent e) {
        if(tfColonneConteneur != null && tfLigneConteneur != null && tfnumCoin != null){
                int joueurCourant = Controleur.TOUR - 1;

                if (ctrl.getListJoueur().get(joueurCourant).getNbTl() > 0) {
                    int ligne = Integer.parseInt(tfLigneConteneur.getText());
                    int colonne = Integer.parseInt(tfColonneConteneur.getText());
                    int numCoin = Integer.parseInt(tfnumCoin.getText());

                    ctrl.getListJoueur().get(joueurCourant).retirerTl();

                    if (!ctrl.getTablier()[ligne][colonne].getCoin(numCoin - 1).isVerouille()) {
                        ctrl.getTablier()[ligne][colonne].getCoin(numCoin - 1).setJoueur(ctrl.getListJoueur().get(joueurCourant));

                        String img = "./img/coin" + ctrl.getListJoueur().get(joueurCourant).getCouleur() + ".png";

                        switch (numCoin) {
                            case 1:
                                listCoin[ligne][colonne].setIcon(getImage(img, 80, 55));
                                break;
                            case 2:
                                listCoin[ligne][colonne + 1].setIcon(getImage(img, 80, 55));
                                break;
                            case 3:
                                listCoin[ligne + 1][colonne + 1].setIcon(getImage(img, 80, 55));
                                break;
                            case 4:
                                System.out.println(img);
                                listCoin[ligne + 1][colonne].setIcon(getImage(img, 80, 55));
                                break;
                        }
                    }

                    renouvelleJoueur();
                    setAppartientConteneur();

                    this.repaint();
                    this.revalidate();
                    ctrl.passerTour();

                }

        }
    }

    private void renouvelleJoueur() {
        for (int i = 1; i <= ctrl.getListJoueur().size(); i++) {
           // panelJoueur.get(i - 1).renouvelle();
        }
    }

    private void setAppartientConteneur() {
        for (int lig = 0; lig < ctrl.getLignes(); lig++) {
            for (int col = 0; col < ctrl.getColonnes(); col++) {
                System.out.println(lig + "  " + col);

                switch (ctrl.getTablier()[lig][col].getAppartenir()) {
                    case 1:
                        listConteneur[lig][col].setBackground(Color.red);
                        listConteneur[lig][col].setOpaque(true);

                        break;
                    case 2:
                        listConteneur[lig][col].setBackground(Color.green);
                        listConteneur[lig][col].setOpaque(true);

                        break;
                    case 3:
                        listConteneur[lig][col].setBackground(Color.blue);
                        listConteneur[lig][col].setOpaque(true);

                        break;
                    case 4:
                        listConteneur[lig][col].setBackground(Color.yellow);
                        listConteneur[lig][col].setOpaque(true);

                        break;
                }
            }
        }
    }
}
