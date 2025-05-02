package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/** Gestion du jeu côté serveur **/
public class JeuServeur extends Jeu implements Global {
	/** Collection de murs **/
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>();
	
	/** Dictionnaire de joueurs indexé sur leur objet de connexion **/
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>();
	
	/** Constructeur **/
	public JeuServeur(Controle controle) {
		this.controle = controle;
	}
	
	@Override
	public void connexion(Connection connection) {
		lesJoueurs.put(connection, new Joueur(this));
	}

	@Override
	public void reception(Connection connection, Object info) {
		String[] infos = ((String) info).split(STRINGSEPARE);
		String ordre = infos[0];
		switch (ordre) {
		case PSEUDO:
			controle.EventJeuServeur(AJOUTPANELMURS, connection);
			String pseudo = infos[1];
			int characterId = Integer.parseInt(infos[2]);
			lesJoueurs.get(connection).initPerso(pseudo, characterId, lesJoueurs.values(), lesMurs);
			controle.EventJeuServeur(AJOUTPHRASE, "*** " + pseudo + " vient de se connecter ***");
			break;
		case TCHAT:
			String message = infos[1];
			message = lesJoueurs.get(connection).getPseudo() + " > " + message;
			controle.EventJeuServeur(AJOUTPHRASE, message);
			break;
		case ACTION:
			Integer action = Integer.parseInt(infos[1]);
			lesJoueurs.get(connection).action(action, lesJoueurs.values(), lesMurs);
			break;
		}
	}
	
	@Override
	public void deconnexion() {}

	/**
	 * Envoi d'une information vers tous les clients
	 * fais appel plusieurs fois à l'envoi de la classe Jeu
	 * @param info information à envoyer
	 */
	public void envoi(Object info) {
		for (Connection connection : lesJoueurs.keySet()) {
			super.envoi(connection, info);
		}
	}
	
	public void ajoutJLabelJeuArene(Object jLabel) {
		this.controle.EventJeuServeur(AJOUTJLABELJEU, jLabel);
	}
	
	public void envoiJeuATous() {
		for (Connection connection : this.lesJoueurs.keySet()) {
			controle.EventJeuServeur(MODIFPANELJEU, connection);
		}
	}

	/** Génération des murs **/
	public void constructionMurs() {
		for (int i = 0; i < NBMURS; i++) { 
			Mur mur = new Mur();
			lesMurs.add(mur);
			controle.EventJeuServeur(AJOUTMUR, mur.getjLabel());
		}
	}
	
	/** @return the lesJoueurs **/
	public Collection getLesJoueurs() {
		return lesJoueurs.values();
	}
}
