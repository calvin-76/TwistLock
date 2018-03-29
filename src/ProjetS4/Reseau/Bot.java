package ProjetS4.Reseau;

import ProjetS4.Metier.Coin;
import ProjetS4.Metier.Conteneur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class Bot extends Thread {

    private boolean partieEnCour = true;
    private String sMap;
    private Conteneur[][] map;
    private Coin[][] listCoin;
    private DatagramSocket socket;
    private String ip;

    private Bot(String ip) {
        try{
            this.ip = ip;
            socket = new DatagramSocket();
            BotThread botThread = new BotThread(this, socket);
            botThread.start();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	    System.out.println("Nom d'équipe du bot : X.A.N.A.");
        send("X.A.N.A.");

	    String rawMap = "";
        DatagramPacket dpRecevoir = new DatagramPacket(new byte[64000], 64000);
        try{
	        while(!rawMap.contains("MAP=")) {
		        socket.receive(dpRecevoir);
		        System.out.println(new String(dpRecevoir.getData()));
		        rawMap = new String(dpRecevoir.getData());
	        }
        }
        catch (Exception e) {}

	    String[] tabRawMap = rawMap.split("MAP=");
	    this.sMap = tabRawMap[tabRawMap.length - 1];

	    initTabMap();
    }

	private void initTabMap() {
		String[]    tabMap1 = this.sMap.trim().split("\\|");
		String[][]  tabMap2 = new String[tabMap1.length][tabMap1[0].split(":").length];

		for (int i = 0; i < tabMap1.length; i++)
			tabMap2[i] = tabMap1[i].split(":");

		this.listCoin = new Coin[tabMap2.length + 1][tabMap2[0].length + 1];

		for(int lig = 0 ; lig < listCoin.length; lig++)
			for (int col = 0; col < listCoin[0].length; col++)
				listCoin[lig][col] = new Coin();


		this.map = new Conteneur[tabMap2.length][tabMap2[0].length];

		for(int lig = 0 ; lig < this.map.length; lig++){
			for (int col = 0; col < this.map[0].length; col++){
				this.map[lig][col] = new Conteneur(Integer.parseInt(tabMap2[lig][col]));
				this.map[lig][col].setCoin(listCoin[lig][col],1);
				this.map[lig][col].setCoin(listCoin[lig][col + 1],2);
				this.map[lig][col].setCoin(listCoin[lig + 1][col+1],3);
				this.map[lig][col].setCoin(listCoin[lig + 1][col],4);
			}
		}
	}

	private void send(String message) {
        try {
            DatagramPacket dpEnvoi = new DatagramPacket(message.getBytes(),
                    message.length(), InetAddress.getByName(ip), 8000);
            socket.send(dpEnvoi);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(partieEnCour) {
			String sJouer = "";
	        DatagramPacket dpRecevoir = new DatagramPacket(new byte[64000], 64000);
	        try {
		        while (!sJouer.startsWith("10-")) {
			        socket.receive(dpRecevoir);
			        System.out.println(new String(dpRecevoir.getData()));
					sJouer = new String(dpRecevoir.getData());
		        }

		        int resMax = 0;
		        int coinMax;
		        ArrayList<Coin> coinsBannis = new ArrayList<Coin>();
		        for (int i = 0; i < this.map.length; i+=2) {
			        for (int j = 0; j < this.map[0].length; j++) {
				        for (int k = 1; k <= 4; k++) {

				        }
			        }
		        }
				        
			        
		        
	        }
	        catch (Exception e) {}
        }
        socket.close();
    }

    public boolean estEnCour() { return this.partieEnCour;}

    public static void main(String args[]) {
        new Bot("localhost").start();
    }
}
