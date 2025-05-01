package modele;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

/** Gestion des joueurs **/
public class Joueur extends Objet implements Global {	
	/** pseudo saisi **/
	private String pseudo;
	
	/** n° correspondant au personnage (avatar) pour le fichier correspondant **/
	private int numPerso; 
	
	/** message qui s'affiche sous le personnage (contenant pseudo et vie) */
	private JLabel message;
	
	/** instance de JeuServeur pour communiquer avec lui **/
	private JeuServeur jeuServeur;
	
	/** numéro d'�tape dans l'animation (de la marche, touché ou mort) **/
	private int etape;
	
	/** la boule du joueur **/
	private Boule boule;
	
	/** vie restante du joueur **/
	private int vie;
	
	/** tourné vers la gauche (0) ou vers la droite (1) **/
	private int orientation;
	
	/** Constructeur **/
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		this.vie = MAXVIE;
		this.etape = 1;
		this.orientation = DROITE;
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position, affichage, création de la boule)
	 * @param pseudo pseudo du joueur
	 * @param numPerso numéro du personnage
	 * @param lesJoueurs collection contenant tous les joueurs
	 * @param lesMurs collection contenant les murs
	 */
	public void initPerso(String pseudo, int numPerso, Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		System.out.println("joueur "+pseudo+" - num perso "+numPerso+" créé");
		
		jLabel = new JLabel();
		
		message = new JLabel();
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Dialog", Font.PLAIN, 8));
		
		premierePosition(lesJoueurs, lesMurs);
		jeuServeur.ajoutJLabelJeuArene(jLabel);
		jeuServeur.ajoutJLabelJeuArene(message);
		affiche(MARCHE, etape);
		
	}

	/** Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur) **/
	private void premierePosition(Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO)) ;
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE));
		} while (toucheJoueur(lesJoueurs) || toucheMur(lesMurs));
	}
	
	/**
	 * Affiche le personnage et son message
	 * @param etape Etape dans le mouvement du personnage
	 * @param etat etat du personnage : "marche", "touche", "mort"
	 */
	public void affiche(String etat, int etape) {
		jLabel.setBounds(posX, posY, LARGEURPERSO, HAUTEURPERSO);
		URL resource = getClass().getClassLoader().getResource(CHEMINPERSONNAGES + PERSO + numPerso + etat + etape + "d" + orientation + EXTFICHIERPERSO);
		jLabel.setIcon(new ImageIcon(resource));
		
		message.setBounds(posX - 10, posY + HAUTEURPERSO, LARGEURPERSO + 10, HAUTEURMESSAGE);
		message.setText(pseudo + " : " + vie);
		jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 * @param action action a exécutée (déplacement ou tir de boule)
	 * @param lesMurs collection de murs
	 * @param lesJoueurs collection de joueurs
	 */
	public void action(Integer action, Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		switch (action) {
		case KeyEvent.VK_LEFT:
			orientation = GAUCHE;
			posX = deplace(posX, action, -PAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_RIGHT:
			orientation = DROITE;
			posX = deplace(posX, action, PAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_UP:
			posY = deplace(posY, action, -PAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_DOWN:
			posY = deplace(posY, action, PAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
			break;
		}
		affiche(MARCHE, etape);
	}

	/**
	 * Gère le déplacement du personnage 
	 * @param position position de départ
	 * @param action gauche, droite, haut ou bas
	 * @param lepas valeur de déplacement (positif ou négatif)
	 * @param max valeur à ne pas dépasser
	 * @param lesJoueurs collection de joueurs pour éviter les collisions
	 * @param lesMurs collection de murs pour éviter les collisions
	 * @return nouvelle position
	 */
	private int deplace(int position, int action, int lepas, int max, Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		int oldPos = position;
		position += lepas;
		position = Math.max(position, 0);
		position = Math.min(position, max);
		
		if (action == KeyEvent.VK_LEFT || action == KeyEvent.VK_RIGHT) posX = position;
		else posY = position;
		
		if (toucheJoueur(lesJoueurs) || toucheMur(lesMurs)) position = oldPos;
		
		etape++;
		if (etape > NBETAPESMARCHE) etape = 1;
		return position;
	}

	/**
	 * Contrôle si le joueur touche un des autres joueurs
	 * @param lesJoueurs collection contenant tous les joueurs
	 * @return true si le joueur touche un autre joueur
	 */
	private Boolean toucheJoueur(Collection<Joueur> lesJoueurs) {
		for (Joueur joueur : lesJoueurs) {
			if (equals(joueur)) continue;
			if (super.toucheObjet(joueur)) return true;
		}
		
		return false;
	}

	/** Gain de points de vie après avoir touché un joueur **/
	public void gainVie() {}
	
	/** Perte de points de vie après avoir été touché **/
	public void perteVie() {}
	
	/**
	* Contrôle si le joueur touche un des murs
	 * @param lesMurs collection contenant tous les murs
	 * @return true si le joueur touche un mur
	 */
	private Boolean toucheMur(ArrayList<Mur> lesMurs) {
		for (Mur mur : lesMurs) {
			if (super.toucheObjet(mur)) return true;
		}
		
		return false;
	}
	
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 **/
	public Boolean estMort() {
		return null;
	}
	
	/** Le joueur se déconnecte et disparait **/
	public void departJoueur() {}
	
	public String getPseudo() {
		return pseudo;
	}
}
