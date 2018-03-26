package IHM;

import Metier.Conteneur;

import javax.swing.*;
import java.awt.*;

public class Tablier extends JPanel {
    public Tablier(int lig, int col) {
        setLayout(new GridLayout(10, 7));
        for (int i = 0; i < lig; i++) {
            for (int j = 0; j < col; j++) {
                setBackground(Color.GRAY);
            }
        }
    }

}
