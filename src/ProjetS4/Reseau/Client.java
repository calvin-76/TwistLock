package ProjetS4.Reseau;

import java.net.*;
import java.util.*;

public class Client extends Thread {

    private boolean partieEnCour = true;
    private DatagramSocket socket;
    private String ip;

    private Client(String ip) {
        try{
            this.ip = ip;
            socket = new DatagramSocket();
            ClientThread clientThread = new ClientThread(this, socket);
            clientThread.start();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Nom d'equipe : ");
        send(sc.next());
    }

    private void send(String message) {
        try {
            DatagramPacket dpEnvoi = new DatagramPacket(message.getBytes(),
                    message.length(), InetAddress.getByName(ip), 19285);
            socket.send(dpEnvoi);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(partieEnCour) {
            Scanner sc = new Scanner(System.in);
            send(sc.nextLine());
        }
        socket.close();
    }

    public boolean estEnCour() { return this.partieEnCour;}

    public static void main(String args[]) {
        new Client("localhost").start();
    }
}
