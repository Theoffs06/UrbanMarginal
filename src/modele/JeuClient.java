package modele;

import controleur.Controle;
import outils.connexion.Connection;

/** Gestion du jeu côté client **/
public class JeuClient extends Jeu {
	/** objet de connexion pour communiquer avec le serveur **/
	private Connection connection;
	
	/**
	 * Controleur
	 * @param controle instance du contrôleur pour les échanges
	 */
	public JeuClient(Controle controle) {
		this.controle = controle;
	}
	
	@Override
	public void connexion(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void deconnexion() {}

	@Override
	public void reception(Connection connection, Object info) {}
	
	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à l'envoi dans la classe Jeu
	 * @param info information à envoyer au serveur
	 */
	public void envoi(String info) {
		super.envoi(connection, info);
	}
}
