package modele;

import controleur.Controle;
import outils.connexion.Connection;

/** Gestion du jeu côté client **/
public class JeuClient extends Jeu {
	private Connection connection;
	
	/** Controleur **/
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
	
	/** Envoi d'une information vers le serveur fais appel une fois à l'envoi dans la classe Jeu **/
	public void envoi(String info) {
		super.envoi(connection, info);
	}
}
