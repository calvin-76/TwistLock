package IHM;

import Metier.Conteneur;

import javax.swing.*;
import java.awt.*;

public class Tablier extends JFrame {

	public Tablier(int lig, int col) {
		this.setSize(800, 600);
		this.setLayout(new FlowLayout());
		this.setVisible(true);

		JTable map = new JTable(lig, col);
		JPanel panMap = new JPanel(new BoxLayout(this, 1));

		map.setIntercellSpacing(new Dimension(25, 25));

		panMap.add(map);
		this.add(panMap);
    }

	public static void main(String[] args) {
		new Tablier(4, 4);
	}
}