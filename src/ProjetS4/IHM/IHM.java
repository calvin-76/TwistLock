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
    ArrayList<PanelJoueur> panelJoueur;

    private JLabel quiJoue;

    private JLabel[][] listCoin;
    private JLabel[][] listConteneur;
    private Controleur partie;
    private Serveur server;

    public IHM(Serveur server, String map, ArrayList<Joueur> listJoueur, Controleur partie){
        this.partie = partie;
        this.server = server;
        this.lignes = this.partie.getLignes() * 2 + 1;
        this.colonnes = this.partie.getColonnes() * 2 + 1;
        this.listCoin = new JLabel[this.partie.getLignes() + 1][this.partie.getColonnes() + 1];
        this.listConteneur = new JLabel[this.partie.getLignes()][this.partie.getColonnes()];

        setLocation(0,0);
        setTitle("Twist Lock");

        JPanel panelGlobal = new JPanel(new BorderLayout());
        JPanel panelNord = new JPanel(new BorderLayout());
        JPanel panelGauche = new JPanel(new BorderLayout());

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
                    tmpLabel = new JLabel("" + this.partie.getTablier()[lig / 2][col / 2].getValeur(),SwingConstants.CENTER);
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

        /*------------------------------------------------------------*/
        /*--------------------  Panel droit  ------------------------*/
        panelJoueur = new ArrayList<>();

        JPanel panelDroit = new JPanel(new GridLayout(this.partie.getListJoueur().size(),1));
        for(int i = 1; i <= this.partie.getListJoueur().size(); i++) {
            panelJoueur.add(new PanelJoueur(this.partie, i));
            panelDroit.add(panelJoueur.get(i-1));
        }
        /*-----------------------------------------------------------*/
        quiJoue = new JLabel("C'est au joueur " + this.partie.getListJoueur().get(this.partie.getNum_joueur() - 1).getCouleur() + " de jouer !");
        panelNord.add(quiJoue);

        JPanel paneltmpFlow = new JPanel();
        paneltmpFlow.add(panelTable);

        JScrollPane panelTablePane = new JScrollPane(paneltmpFlow);

        panelGlobal.add(panelTablePane,BorderLayout.CENTER);
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

    public void jouer(int ligne, int colonne, int numCoin) {
        if(!partie.partieFinis()) {
            int joueurCourant = partie.getNum_joueur() - 1;

            if (partie.getListJoueur().get(joueurCourant).getNbTl() > 0) {

                System.out.println(numCoin);

                if (!partie.getTablier()[ligne][colonne].getCoin(numCoin).isVerrouille()) {
                    partie.getTablier()[ligne][colonne].getCoin(numCoin).setJoueur(partie.getListJoueur().get(joueurCourant));

                    String img = "./img/coin" + partie.getListJoueur().get(joueurCourant).getCouleur() + ".png";

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
                    partie.getListJoueur().get(joueurCourant).retirerTl();
                    server.send("21-Coup joué illégal", partie.getListJoueur().get(server.getJCourant()).getAdresse(), partie.getListJoueur().get(server.getJCourant()).getPort());
                    server.sendBroadcast("22-Coup adversaire illégal [" + partie.getListJoueur().get(server.getJCourant()).getCouleur().toUpperCase() + "]");
                }
                setAppartientConteneur();

                partie.passerTour();
                renouvelleJoueur();
                this.repaint();
                this.revalidate();
            }
        } else {
            System.out.println("END");
        }
    }

    public void renouvelleJoueur() {
        for (int i = 1; i <= partie.getListJoueur().size(); i++) {
            panelJoueur.get(i - 1).renouvelle();
        }
    }

    private void setAppartientConteneur() {
        for (int lig = 0; lig < partie.getLignes(); lig++) {
            for (int col = 0; col < partie.getColonnes(); col++) {
                switch (partie.getTablier()[lig][col].appartientA()) {
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
