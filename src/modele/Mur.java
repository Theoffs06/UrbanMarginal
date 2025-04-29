package modele;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/** Gestion des murs **/
public class Mur extends Objet implements Global {
	/** Constructeur : crée un mur (position aléatoire, image) **/
	public Mur() {
		posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURMUR));
		posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURMUR));
		
		jLabel = new JLabel();
		jLabel.setBounds(posX, posY, LARGEURMUR, HAUTEURMUR);
		URL resource = getClass().getClassLoader().getResource(MUR);
		jLabel.setIcon(new ImageIcon(resource));
	}
}
