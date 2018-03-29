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
					if (sJouer.startsWith("20")){
						int lig =  Integer.parseInt("" + sJouer.split(":")[2].charAt(0)) - 1;
						int col =  Integer.parseInt("" +(sJouer.split(":")[2].charAt(1) - 65));
						int coin = Integer.parseInt("" + sJouer.split(":")[2].charAt(2));
						System.out.println("lig = " + lig);
						System.out.println("col = " + col);
						System.out.println("coin = " + coin);
						this.map[lig][col].getCoin(coin).setVerouille();
					}
		        }
		        int resMax = 0;
		        Coin coinMax = this.listCoin[0][0];
		        int iSave = 0;
		        int jSave = 0;
		        for (int i = 0; i < this.listCoin.length; i++) { // On parcours les lignes du tableau de coin
			        for (int j = 0; j < this.listCoin[0].length; j++) { // On parcours chaque coin de chaque ligne du tableau de coin
				        int res = 0;
						if (!this.listCoin[i][j].isVerrouille()) { // Si le coin est libre, on le prend en compte, sinon on l'ignore

							for (Conteneur cont : this.listCoin[i][j].getConteneurs()) {
								// On regarde le nombre de coins qui ne sont pas libres autour du conteneur en question
								int nbCoinsPris = 0;
								for (int z = 1; z <= 4; z++)
									if (cont.getCoin(z).getJoueur() != null)
										nbCoinsPris++;

								if (!cont.isPossede() || nbCoinsPris <= 1)
									res += cont.getValeur();
							}

							// On vérifie si la somme des valeurs des conteneurs autour du coin en question
							if (res > resMax) {
								resMax = res;
								coinMax = this.listCoin[i][j];
								iSave = i;
								jSave = j;
							}
						}
			        }
		        }
				String sSend = "";
		        if (iSave < this.map.length) sSend += (iSave+1);
		        else sSend += iSave;
		        if (jSave < this.map[0].length) sSend += (char) (jSave+65);
		        else sSend += (char) jSave+64;

		        int coin = 1;
				if (iSave >= this.map.length ) coin = 4;
				if (jSave >= this.map[0].length    ) coin = 2;
				if (iSave >= this.map.length &&
					jSave >= this.map[0].length    ) coin = 3;

				sSend += coin;
				coinMax.setVerouille();
		        System.out.println(sSend);

				send(sSend);
	        }
	        catch (Exception e) {e.printStackTrace();}
        }
        socket.close();
	    System.out.println("Lul je suis sorti de la boucle");
    }

    public boolean estEnCour() { return this.partieEnCour;}

    public static void main(String args[]) {
        new Bot("localhost").start();
    }
}
