package ProjetS4.Reseau;

import ProjetS4.Controleur;
import ProjetS4.IHM.IHM;
import ProjetS4.Metier.Conteneur;
import ProjetS4.Metier.Joueur;

import java.net.*;
import java.io.IOException;
import java.util.ArrayList;


public class Serveur extends Thread {
    private DatagramSocket socket;

    private int joueursConnectes = 0;

    private int port;

    private Controleur partie;

    private Joueur joueur = null;

    private int jCourant = 0;

    private IHM frame;

    public Serveur(int port) {
        this.partie = new Controleur();
        this.port = port;
    }

    public void run() {
        try {
            socket = new DatagramSocket(port);

            while (joueursConnectes < 2) {
                try {
                    byte[] buffer = new byte[64000];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                    socket.receive(packet);

                    String id = new String(packet.getData());

                    System.out.println("Message reçu : " + id + " de " +
                            packet.getAddress() + ":" + packet.getPort() + "    : " + packet.getData());

                    packet.setLength(buffer.length);

                    Joueur joueur = new Joueur(++joueursConnectes, packet.getAddress(), packet.getPort(), partie);
                    partie.ajouterJoueur(joueur);

                    StringBuilder identification = new StringBuilder();
                    identification.append(joueursConnectes + "-Bonjour ");
                    identification.append(id);
                    identification.append("\nVous êtes le joueur " + joueursConnectes);
                    identification.append(" (" + joueur.getCouleur() + "), attente suite ...");
                    send(identification.toString(), packet.getAddress(), packet.getPort());

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            ServeurThread serveurThread = new ServeurThread(this, socket);
            serveurThread.start();
            initJeu();
        } catch (SocketException se) {
            se.printStackTrace();
        }

    }

    private boolean partieFinis() {
        boolean stockVide = true;
        boolean allCoinLock = true;

        for (int i = 0; i < 2; i++) {
            if (partie.getListJoueur().get(i).getNbTl() > 0) stockVide = false;
        }
        for (int lig = 0;lig<partie.getTablier().length;lig++) {
            for (int col = 0; col < partie.getTablier()[lig].length; col++) {
                for (int coin = 1; coin <= 4;coin++) {
                    if (!partie.getTablier()[lig][col].getCoin(coin).isVerrouille()) allCoinLock = false;
                }
            }
        }
        return stockVide || allCoinLock;
    }

    public void send(String message, InetAddress adresse, int port) {
        try {
            byte[] reponse = message.getBytes();

            DatagramPacket data = new DatagramPacket(
                    reponse,
                    reponse.length,
                    adresse,
                    port
            );
            data.setLength(reponse.length);
            socket.send(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBroadcast(String message) {
        try {
            byte[] buffer = message.getBytes();

            DatagramPacket packetReponse;

            for (Joueur joueur : partie.getListJoueur()) {
                packetReponse = new DatagramPacket(
                        buffer,
                        buffer.length,
                        joueur.getAdresse(),
                        joueur.getPort()
                );

                if (!this.joueur.equals(joueur)) {
                    socket.send(packetReponse);
                    packetReponse.setLength(buffer.length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendData(String message) {
        try {
            byte[] buffer = message.getBytes();

            DatagramPacket paquetReponse;

            for (Joueur joueur : partie.getListJoueur()) {
                paquetReponse = new DatagramPacket(
                        buffer,
                        buffer.length,
                        joueur.getAdresse(),
                        joueur.getPort()
                );

                socket.send(paquetReponse);
                paquetReponse.setLength(buffer.length);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initJeu() {
        StringBuilder sbMap = new StringBuilder();
        sbMap.append("MAP=");

        Conteneur[][] tablier = partie.getTablier();

        for (int i = 0; i < tablier.length; i++) {
            for (int j = 0; j < tablier[i].length; j++) {
                sbMap.append(tablier[i][j].getValeur());

                if (j != tablier[i].length - 1) sbMap.append(":");
            }

            sbMap.append("|");
        }

        String message = "01-la partie va commencer\n" +
                sbMap.toString();
        sendData(message);
        frame = new IHM(this, sbMap.toString(), partie.getListJoueur(),partie);
        suiteJeu();

    }

    public void handle(String message) {
        int ligne = Character.getNumericValue(message.charAt(0));
        int colonne = ((int) message.charAt(1) - 65);
        int numCoin = Character.getNumericValue(message.charAt(2));

        if (numCoin < 1 || numCoin > 4){
            System.out.println("Valeur de coin illégale.");
            getJoueur().retirerTl();
            send("21-Coup joué illégal", getJoueur().getAdresse(), getJoueur().getPort());
            sendBroadcast("22-Coup adversaire illégal [" + getJoueur().getCouleur().toUpperCase() + "]");
        } else if (ligne < 0 || ligne > partie.getTablier().length) {
            System.out.println("Valeur de ligne illégale.");
            getJoueur().retirerTl();
            send("21-Coup joué illégal", getJoueur().getAdresse(), getJoueur().getPort());
            sendBroadcast("22-Coup adversaire illégal [" + getJoueur().getCouleur().toUpperCase() + "]");
        } else if (colonne < 0 || colonne > partie.getTablier()[0].length) {
            System.out.println("Valeur de colonne illégale.");
            getJoueur().retirerTl();
            send("21-Coup joué illégal", getJoueur().getAdresse(), getJoueur().getPort());
            sendBroadcast("22-Coup adversaire illégal [" + getJoueur().getCouleur().toUpperCase() + "]");
        } else {
            frame.jouer(ligne - 1,colonne, numCoin);
        }
    }

    private void suiteJeu() {

        ArrayList<Joueur> joueurs = partie.getListJoueur();

        if (jCourant == joueurs.size())
            jCourant = 0;

        this.joueur = joueurs.get(jCourant);

        System.out.println("Nb coup [" + this.joueur.getCouleur() + "] :" + this.joueur.getNbPion());
        if (this.joueur.getNbPion() <= 0) {
            this.send("50-Vous ne pouvez plus jouer", joueur.getAdresse(), joueur.getPort());
            joueurSuivant();

        }
        System.out.println("JoueurActuel : " + joueur.getId());
        send("10-A vous de jouer [" + joueur.getCouleur().toUpperCase() + "] : ", joueur.getAdresse(), joueur.getPort());

    }

    public void sendErreur(InetAddress adresse, int port) {
        send("91-Demande non valide", adresse, port);
    }


    public Joueur getJoueur() {
        return this.joueur;
    }

    public int getJCourant() {
        return this.jCourant;
    }

    public void joueurSuivant() {
        if (!partieFinis()) {
            this.jCourant++;
            suiteJeu();
        } else {
            Conteneur[][] listConteneur = partie.getTablier();
            int score1 = 0;
            int score2 = 0;

            for (int i = 0; i < listConteneur.length; i++) {
                for (int j = 0; j < listConteneur[i].length; j++) {
                    if (listConteneur[i][j].appartientA() == 1)
                        score1 += listConteneur[i][j].getValeur();
                    else if(listConteneur[i][j].appartientA() == 2){
                        score2 += listConteneur[i][j].getValeur();
                    }
                }
            }
            if(score1>score2){
                this.sendData("88-Partie Terminée, le joueur rouge a gagner avec " + score1 + " points");
            }
            else{
                this.sendData("88-Partie Terminée, le joueur vert a gagner avec " + score2 + " points");
            }
            socket.close();
            System.exit(0);
        }
    }

    public void repaint() {
        frame.renouvelleJoueur();
    }

    public static void main(String[] args) {
        new Serveur(12345).start();
    }
}
