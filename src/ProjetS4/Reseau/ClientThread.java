package ProjetS4.Reseau;

import java.net.*;

public class ClientThread extends Thread{

    Client client;
    DatagramSocket socket;

    public ClientThread(Client client, DatagramSocket socket) {
        this.client = client;
        this.socket = socket;
    }


    public void run() {
        while(this.client.estEnCour()) {
            try {
                DatagramPacket dp = new DatagramPacket(new byte[64000], 64000);
                socket.receive(dp);
                System.out.println ( new String(dp.getData()) );
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
