package ProjetS4.Reseau;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TestUDP {

    public final static int port = 2345;

    public static void main(String[] args){

        Thread t = new Thread(new Runnable(){
            public void run(){
                try {

                    //Création de la connexion côté serveur, en spécifiant un port d'écoute
                    DatagramSocket server = new DatagramSocket(port);

                    while(true){

                        //On s'occupe maintenant de l'objet paquet
                        byte[] buffer = new byte[8192];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                        //Cette méthode permet de récupérer le datagramme envoyé par le client
                        //Elle bloque le thread jusqu'à ce que celui-ci ait reçu quelque chose.
                        server.receive(packet);

                        //nous récupérons le contenu de celui-ci et nous l'affichons
                        String str = new String(packet.getData());
                        System.out.println("Reçu de la part de " + packet.getAddress()
                                + " sur le port " + packet.getPort() + " : ");

                        //On réinitialise la taille du datagramme, pour les futures réceptions
                        packet.setLength(buffer.length);

                        //et nous allons répondre à notre client, donc même principe
                        byte[] buffer2 = new String("Réponse du serveur à " + str + "! ").getBytes();
                        DatagramPacket packet2 = new DatagramPacket(
                                buffer2,             //Les données
                                buffer2.length,      //La taille des données
                                packet.getAddress(), //L'adresse de l'émetteur
                                packet.getPort()     //Le port de l'émetteur
                        );

                        //Et on envoie vers l'émetteur du datagramme reçu précédemment
                        server.send(packet2);
                        packet2.setLength(buffer2.length);
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        //Lancement du serveur
        t.start();

        Thread cli1 = new Thread(new UDPClient("Cysboy", 1000));

        cli1.start();

    }

    public static class UDPClient implements Runnable{
        String name = "";
        long sleepTime = 1000;

        public UDPClient(String pName, long sleep){
            name = pName;
            sleepTime = sleep;
        }

        public void run(){
            int nbre = 0;
            while(true){
                String envoi = name + "-" + (++nbre);
                byte[] buffer = envoi.getBytes();

                try {
                    //On initialise la connexion côté client
                    DatagramSocket client = new DatagramSocket();

                    //On crée notre datagramme
                    InetAddress adresse = InetAddress.getByName("127.0.0.1");
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, port);

                    //On lui affecte les données à envoyer
                    packet.setData(buffer);

                    //On envoie au serveur
                    client.send(packet);

                    //Et on récupère la réponse du serveur
                    byte[] buffer2 = new byte[8196];
                    DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, adresse, port);
                    client.receive(packet2);
                    System.out.print(envoi + " a reçu une réponse du serveur : ");
                    System.out.println(new String(packet2.getData()));

                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
