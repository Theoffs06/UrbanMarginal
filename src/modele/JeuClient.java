package modele;

import javax.swing.JPanel;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/** Gestion du jeu côté client **/
public class JeuClient extends Jeu implements Global {
	/** objet de connexion pour communiquer avec le serveur **/
	private Connection connection;
	private Boolean mursOk = false;
	
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
	public void deconnexion(Connection connection) {
		System.exit(0);
	}

	@Override
	public void reception(Connection connection, Object info) {
		if (info instanceof JPanel) {
			if (!mursOk) {
				controle.EventJeuClient(AJOUTPANELMURS, info);
				mursOk = true;
			}
			else { 
				controle.EventJeuClient(MODIFPANELJEU, info);
			}
			return;
		}
		
		if (info instanceof String) {
			controle.EventJeuClient(MODIFTCHAT, info);
			return;
		}
		
		if (info instanceof Integer) {
			controle.EventJeuClient(JOUESON, info);
		}
	}
	
	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à l'envoi dans la classe Jeu
	 * @param info information à envoyer au serveur
	 */
	public void envoi(String info) {
		super.envoi(connection, info);
	}
}
