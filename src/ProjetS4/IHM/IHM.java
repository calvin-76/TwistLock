package ProjetS4.IHM;

import ProjetS4.Controleur;
import ProjetS4.Metier.Joueur;
import ProjetS4.Reseau.Serveur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class IHM extends JFrame {

    private int lignes;
    private int colonnes;

    private JTextField tfLigneConteneur;
    private JTextField tfColonneConteneur;
    private JTextField tfnumCoin;

    ArrayList<PanelJoueur> panelJoueur;

    private JLabel message;
    private JLabel quiJoue;

    private JLabel[][] listCoin;
    private JLabel[][] listConteneur;
    private Controleur jeu;
    private Serveur server;

    public IHM(Serveur server, String map, ArrayList<Joueur> listJoueur){
        this.server = server;
        lignes = jeu.getLignes() * 2 + 1;
        colonnes = jeu.getColonnes() * 2 + 1;

        listCoin = new JLabel[jeu.getLignes() + 1][jeu.getColonnes() + 1];
        listConteneur = new JLabel[jeu.getLignes()][jeu.getColonnes()];

        setLocation(0,0);
        setTitle("Twist Lock");

        JPanel panelGlobal = new JPanel(new BorderLayout());
        JPanel panelNord = new JPanel(new BorderLayout());
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
                    tmpLabel = new JLabel("" + jeu.getTablier()[lig / 2][col / 2].getValeur(),SwingConstants.CENTER);
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

        JPanel panelColoneLigne = new JPanel();
        panelColoneLigne.add(panelLigneConteneur);
        panelColoneLigne.add(panelColonneConteneur);
        panelColoneLigne.add(panelCoin);

        message = new JLabel(" ",SwingConstants.CENTER);

        JPanel panelMessageColonneLigne = new JPanel(new BorderLayout());
        panelMessageColonneLigne.add(message,BorderLayout.NORTH);
        panelMessageColonneLigne.add(panelColoneLigne);

        /*------------------------------------------------------------*/

        /*--------------------  Panel droit  ------------------------*/
        panelJoueur = new ArrayList<>();

        JPanel panelDroit = new JPanel(new GridLayout(jeu.getListJoueur().size(),1));
        for(int i = 1; i <= jeu.getListJoueur().size(); i++) {
            panelJoueur.add(new PanelJoueur(jeu, i));
            panelDroit.add(panelJoueur.get(i-1));
        }
        /*-----------------------------------------------------------*/
        quiJoue = new JLabel("C'est au joueur " + jeu.getListJoueur().get(jeu.getNum_joueur() - 1).getCouleur() + " de jouer !");
        panelNord.add(quiJoue);
        panelBas.add(panelMessageColonneLigne);

        JPanel paneltmpFlow = new JPanel();
        paneltmpFlow.add(panelTable);

        JScrollPane panelTablePane = new JScrollPane(paneltmpFlow);

        panelGlobal.add(panelTablePane,BorderLayout.CENTER);
        panelGlobal.add(panelBas,BorderLayout.SOUTH);
        panelGlobal.add(panelNord,BorderLayout.NORTH);
        panelGlobal.add(panelGauche,BorderLayout.WEST);
        panelGlobal.add(panelDroit,BorderLayout.EAST);
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

    /*public void actionPerformed(ActionEvent e) {
        if(!jeu.partieFinis()) {
            message.setText("");

            if(tfColonneConteneur != null && tfLigneConteneur != null && tfnumCoin != null){
                int joueurCourant = jeu.getNum_joueur() - 1;

                if (jeu.getListJoueur().get(joueurCourant).getNbTl() > 0) {
                    int ligne = Integer.parseInt(tfLigneConteneur.getText())-1;
                    int colonne = (tfColonneConteneur.getText().charAt(0))-65;
                    int numCoin = Integer.parseInt(tfnumCoin.getText());

                    jeu.getListJoueur().get(joueurCourant).retirerTl();

                    if(ligne+1<1 || colonne+1<1 || ligne+1 > jeu.getTablier().length || colonne+1 > jeu.getTablier()[0].length || numCoin > 4 || numCoin < 1){
                        jeu.getListJoueur().get(joueurCourant).retirerTl();
                        message.setText("Coordonnées incorrect. Vous avez perdu un TL.");
                    }else{
                        if (!jeu.getTablier()[ligne][colonne].getCoin(numCoin).isVerrouille()) {
                            jeu.getTablier()[ligne][colonne].getCoin(numCoin).setJoueur(jeu.getListJoueur().get(joueurCourant));

                            String img = "./img/coin" + jeu.getListJoueur().get(joueurCourant).getCouleur() + ".png";

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
                                    listCoin[ligne + 1][colonne].setIcon(getImage(img, 80, 55));
                                    break;
                            }
                        } else {
                            jeu.getListJoueur().get(joueurCourant).retirerTl();
                            message.setText("Coin déja verouillé. Vous avez perdu un TL.");
                        }
                        setAppartientConteneur();
                    }
                    jeu.passerTour();
                    renouvelleJoueur();
                    quiJoue.setText("C'est au joueur " + jeu.getListJoueur().get(jeu.getNum_joueur()-1).getCouleur() + " de jouer !");
                    this.repaint();
                    this.revalidate();
                }
            }
        } else {
            valider.setEnabled(false);
            renouvelleJoueur();
            message.setText("Partie terminée !");
            this.repaint();
            this.revalidate();
        }
    }*/

    public void jouer(int ligne, int colonne, int numCoin) {
        if(!jeu.partieFinis()) {
            int joueurCourant = jeu.getNum_joueur() - 1;

            if (jeu.getListJoueur().get(joueurCourant).getNbTl() > 0) {

                jeu.getListJoueur().get(joueurCourant).retirerTl();

                if (!jeu.getTablier()[ligne][colonne].getCoin(numCoin).isVerrouille()) {
                    jeu.getTablier()[ligne][colonne].getCoin(numCoin).setJoueur(jeu.getListJoueur().get(joueurCourant));

                    String img = "./img/coin" + jeu.getListJoueur().get(joueurCourant).getCouleur() + ".png";

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
                            listCoin[ligne + 1][colonne].setIcon(getImage(img, 80, 55));
                            break;
                    }
                } else {
                    jeu.getListJoueur().get(joueurCourant).retirerTl();
                    server.send("21-Coup joué illégal", jeu.getListJoueur().get(server.getJCourant()).getAdresse(), jeu.getListJoueur().get(server.getJCourant()).getPort());
                    server.sendBroadcast("22-Coup adversaire illégal [" + jeu.getListJoueur().get(server.getJCourant()).getCouleur().toUpperCase() + "]");
                }
                setAppartientConteneur();

                jeu.passerTour();
                renouvelleJoueur();
                this.repaint();
                this.revalidate();
            }
        } else {
            System.out.println("END");
        }
    }

    public void renouvelleJoueur() {
        for (int i = 1; i <= jeu.getListJoueur().size(); i++) {
            panelJoueur.get(i - 1).renouvelle();
        }
    }

    private void setAppartientConteneur() {
        for (int lig = 0; lig < jeu.getLignes(); lig++) {
            for (int col = 0; col < jeu.getColonnes(); col++) {
                switch (jeu.getTablier()[lig][col].getAppartenir()) {
                    case 0 :
                        listConteneur[lig][col].setBackground(Color.white);
                        listConteneur[lig][col].setOpaque(false);
                        break;
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
