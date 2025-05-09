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
	
	/** nombre de boules restantes du joueur **/
	private int nbBoule;
	
	/** vie restante du joueur **/
	private int vie;
	
	/** tourné vers la gauche (0) ou vers la droite (1) **/
	private int orientation;
	
	/** Constructeur **/
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		this.vie = MAXVIE;
		this.nbBoule = MAXBOULE;
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
	public void initPerso(String pseudo, int numPerso, Collection lesJoueurs, Collection lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		System.out.println("joueur "+pseudo+" - num perso "+numPerso+" créé");
		
		jLabel = new JLabel();
		
		message = new JLabel();
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Dialog", Font.PLAIN, 8));
		
		boule = new Boule(jeuServeur);
		
		premierePosition(lesJoueurs, lesMurs);
		jeuServeur.ajoutJLabelJeuArene(jLabel);
		jeuServeur.ajoutJLabelJeuArene(message);
		jeuServeur.ajoutJLabelJeuArene(boule.getjLabel());
		affiche(MARCHE, etape);
		
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur)
	 * @param lesJoueurs collection contenant tous les joueurs
	 * @param lesMurs collection contenant les murs
	 */
	private void premierePosition(Collection<Objet> lesJoueurs, Collection<Objet> lesMurs) {
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO)) ;
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE));
		} while (toucheCollectionObjets(lesJoueurs) != null || toucheCollectionObjets(lesMurs) != null);
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
		
		message.setBounds(posX - 10, posY + HAUTEURPERSO, LARGEURPERSO + 20, HAUTEURMESSAGE);
		message.setText(pseudo + " : " + vie + " : " + nbBoule);
		jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 * @param action action a exécutée (déplacement ou tir de boule)
	 * @param lesMurs collection de murs
	 * @param lesJoueurs collection de joueurs
	 */
	public void action(Integer action, Collection lesJoueurs, Collection lesMurs) {
		if (estMort()) return;
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
		case KeyEvent.VK_SPACE:
			if (boule.getjLabel().isVisible() || nbBoule == 0) break;
			boule.tireBoule(this, lesMurs);
			perteBoule();
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
	private int deplace(int position, int action, int lepas, int max, Collection<Objet> lesJoueurs, Collection<Objet> lesMurs) {
		int oldPos = position;
		position += lepas;
		
		position = (position + lepas) % (max+1);
		if (position < 0) position += (max+1);
		
		if (action == KeyEvent.VK_LEFT || action == KeyEvent.VK_RIGHT) posX = position;
		else posY = position;
		
		if (toucheCollectionObjets(lesJoueurs) != null || toucheCollectionObjets(lesMurs) != null) position = oldPos;
		
		etape++;
		if (etape > NBETAPESMARCHE) etape = 1;
		return position;
	}
	
	/** Gain de points de vie après avoir touché un joueur **/
	public void gainVie() {
		vie += GAINVIE;
		affiche(MARCHE, etape);
	}
	
	/** Perte de points de vie après avoir été touché **/
	public void perteVie() {
		vie = Math.max(0, vie - PERTEVIE);
		affiche(MARCHE, etape);
	}
	
	/** Augmente le nombre de boules du joueur après avoir touché ou tué un autre joueur **/
	public void gainBoule() {
		nbBoule += GAINBOULE;
		affiche(MARCHE, etape);
	}
	
	/** Réduit le nombre de boules du joueur après avoir utilisé une boule **/
	public void perteBoule() {
		nbBoule = Math.max(0, nbBoule - PERTEBOULE);
		affiche(MARCHE, etape);
	}
		
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 **/
	public Boolean estMort() {
		return (vie == 0);
	}
	
	/** Le joueur disparait (ainsi que son message et sa boule) **/
	public void departJoueur() {
		if (jLabel == null) return;
		jLabel.setVisible(false);
		message.setVisible(false);
		boule.getjLabel().setVisible(false);
		jeuServeur.envoiJeuATous();
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public int getOrientation() {
		return orientation;
	}
}
