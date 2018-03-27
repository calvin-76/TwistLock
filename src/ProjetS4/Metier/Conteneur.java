package ProjetS4.Metier;

public class Conteneur {
    private int valeur;
    private Coin[] coins;

    public Conteneur(int valeur){
        this.coins = new Coin[4];
        this.valeur = valeur;
    }

    public void setCoin(Coin coin, int id){
        this.coins[id-1] = coin;
    }

    public int getAppartenir(){

        int joueur1 = 0;
        int joueur2 = 0;
        int joueur3 = 0;
        int joueur4 = 0;
        for(int i = 0; i<4; i++){

            if( coins[i].isVerouille()) {
                if (coins[i].getJoueur().getId() == 1) joueur1++;
                if (coins[i].getJoueur().getId() == 2) joueur2++;
                if (coins[i].getJoueur().getId() == 3) joueur3++;
                if (coins[i].getJoueur().getId() == 4) joueur4++;
            }

        }
        if(joueur1>joueur2 && joueur1>joueur3 && joueur1>joueur4 )
            return 1;
        if(joueur2>joueur1 && joueur2>joueur3 && joueur2>joueur4 )
            return  2;
        if(joueur3>joueur1 && joueur3>joueur2 && joueur3>joueur4 )
            return  3;
        if(joueur4>joueur1 && joueur4>joueur2 && joueur4>joueur3 )
            return  4;

        return 0;
    }

    public Coin getCoin(int id){
        return  this.coins[id - 1];
    }

    public int getValeur() {
        return this.valeur;
    }
}
