package ProjetS4.Reseau;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class BotThread extends Thread{

    Bot bot;
    DatagramSocket socket;

    public BotThread(Bot bot, DatagramSocket socket) {
        this.bot = bot;
        this.socket = socket;
    }

    public void run() {
        while(this.bot.estEnCour()) {
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
