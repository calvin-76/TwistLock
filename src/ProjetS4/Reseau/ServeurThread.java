package ProjetS4.Reseau;

import java.net.*;
import ProjetS4.Metier.Joueur;

public class ServeurThread extends Thread {

    private Serveur serveur;
    private DatagramSocket socket;
    private int port;

    public ServeurThread(Serveur serveur, DatagramSocket socket) {
        this.serveur = serveur;
        this.socket = socket;
        this.port = socket.getPort();
    }

    public void run() {
        while (true) {
            byte[] buffer = new byte[64000];
            DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(paquet);
                traitement(paquet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void traitement(DatagramPacket paquet) {
        Joueur joueur = serveur.getJoueur();

        try {
            if (paquet.getAddress().equals(joueur.getAdresse()) && paquet.getPort() == joueur.getPort()) {
                System.out.println("Message reçu " + new String(paquet.getData()) + " de : " + joueur.getId());
                serveur.handle(new String(paquet.getData()));
                serveur.sendBroadcast("20-coup adversaire:"+new String(paquet.getData()));
                serveur.getJoueur().retirerTl();
                serveur.repaint();
                System.out.println("NB COUP ["+serveur.getJoueur().getCouleur().toUpperCase()+"] :" +serveur.getJoueur().getNbPion());
                serveur.joueurSuivant();

            } else {
                serveur.sendErreur(paquet.getAddress(), paquet.getPort());
                System.out.println("Demande invalide : " + paquet.getAddress() + ":" + paquet.getPort());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
