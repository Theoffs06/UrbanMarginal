package modele;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/** Gestion de la boule **/
public class Boule extends Objet implements Global, Runnable {
	/** instance de JeuServeur pour la communication **/
	private JeuServeur jeuServeur;
	
	/** joueur qui lance la boule **/
	private Joueur attaquant;
	
	/** Collection de murs **/
	private Collection<Objet> lesMurs;
	
	/**
	 * Constructeur crée le label de la boule
	 * @param jeuServeur pour communiquer avec JeuServeur
	 */
	public Boule(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		
		jLabel = new JLabel();
		jLabel.setVisible(false);
		URL resource = getClass().getClassLoader().getResource(BOULE);
		jLabel.setIcon(new ImageIcon(resource));
		jLabel.setBounds(0, 0, LARGEURBOULE, HAUTEURBOULE);
	}
	
	/**
	 * Tire d'une boule
	 * @param attaquant joueur qui lance la boule
	 * @param lesMurs collection de murs
	 */
	public void tireBoule(Joueur attaquant, Collection<Objet> lesMurs) {
		this.lesMurs = lesMurs;
		this.attaquant = attaquant;
		
		if (attaquant.getOrientation() == GAUCHE) posX = attaquant.getPosX() - LARGEURBOULE - 1;
		else posX = attaquant.getPosX()	+ LARGEURPERSO + 1;
		
		posY = attaquant.getPosY() + HAUTEURPERSO / 2;
		new Thread(this).start();
	}

	@Override
	public void run() {
		attaquant.affiche(MARCHE, 1);
		jLabel.setVisible(true);
		jeuServeur.envoi(FIGHT);
		
		Joueur victime = null;
		int lePas = attaquant.getOrientation() == GAUCHE ? -PAS : PAS;
		
		do {
			posX += lePas;
			jLabel.setBounds(posX, posY, LARGEURBOULE, HAUTEURBOULE);
			jeuServeur.envoiJeuATous();
			
			Collection lesJoueurs = jeuServeur.getLesJoueurs();
			victime = (Joueur) toucheCollectionObjets(lesJoueurs);
		} while (posX>= 0 && posX <= LARGEURARENE && this.toucheCollectionObjets(lesMurs) == null && victime == null);
		
		if (victime != null && !victime.estMort()) {
			victime.perteVie();
			attaquant.gainVie();
			jeuServeur.envoi(HURT);
			
			for (int i = 1; i <= NBETAPESTOUCHE; i++) {
				victime.affiche(TOUCHE, i);
				pause(80, 0);
			}
			
			if (victime.estMort()) {
				jeuServeur.envoi(DEATH);
				
				for (int i = 1; i <= NBETAPESMORT; i++) {
					victime.affiche(MORT, i);
					pause(80, 0);
				}
			}
			else {
				victime.affiche(MARCHE, 1);
			}
		}
		
		jLabel.setVisible(false);
		jeuServeur.envoiJeuATous();
	}
	
	/**
	 * fais une pause (bloque le processus) d'une durée précise
	 * @param millis millisecondes
	 * @param nanos nanosecondes
	 */
	private void pause(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		} catch (InterruptedException e) {
			System.out.println("erreur pause");
		}
	}
}
