package ProjetS4.IHM;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accueil extends JFrame implements ActionListener {

    private JButton creerServeur, rejoindreServer;

    public Accueil() {
        setTitle("Accueil");
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel panel = new JPanel();
        creerServeur = new JButton("Creer serveur");
        rejoindreServer = new JButton("Rejoindre serveur");

        creerServeur.addActionListener(this);
        rejoindreServer.addActionListener(this);
        panel.add(creerServeur);
        panel.add(rejoindreServer);
        add(panel);
        pack();
        setVisible(true);

    }

    public static void main(String[] args) {
        new Accueil();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.creerServeur) {
            Object[] possibilities = {"2", "3", "4"};
            String s = (String) JOptionPane.showInputDialog(
                    this,
                    "Nombre de joueur",
                    "Créer serveur",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "2");

            new ProjetS4.Reseau.Serveur(5555,Integer.parseInt(s));
        }
        if(e.getSource() == this.rejoindreServer)
        {
            String ip  = JOptionPane.showInputDialog("Enter an ip");

        }
    }
}
