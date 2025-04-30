package controleur;

import javax.swing.JPanel;

import modele.Jeu;
import modele.JeuServeur;
import modele.JeuClient;
import outils.connexion.*;
import vue.*;

/**
 * Contrôleur et point d'entrée de l'applicaton 
 * @author emds
 **/
public class Controle implements AsyncResponse, Global {
	/** instance du jeu (JeuServeur ou JeuClient) **/
	private Jeu leJeu;
	
	/** frame EntreeJeu **/
	private EntreeJeu frmEntreeJeu;
	
	/** frame ChoixJoueur **/
	private ChoixJoueur frmChoixJoueur;
	
	/** frame Arene **/
	private Arene frmArene;
	
	/**
	 * Méthode de démarrage
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		new Controle();
	}
	
	/** Constructeur **/
	private Controle() {
		frmEntreeJeu = new EntreeJeu(this);
		frmEntreeJeu.setVisible(true);
	}
	
	/**
	 * Demande provenant de la vue EntreeJeu
	 * @param info information à traiter
	 */
	public void EventEntreeJeu(String info) {
		if(info.equals(SERVEUR)) {
			new ServeurSocket(this, PORT);
			leJeu = new JeuServeur(this);
			
			frmArene = new Arene(this, SERVEUR);
			((JeuServeur) leJeu).constructionMurs();
			frmArene.setVisible(true);
			
			frmEntreeJeu.dispose();
			return;
		}
		
		new ClientSocket(this, info, Global.PORT);
	}
	
	/**
	 * Informations provenant de la vue ChoixJoueur
	 * @param pseudo le pseudo du joueur
	 * @param characterId le numéro du personnage choisi par le joueur
	 */
	public void EventChoixJoueur(String pseudo, int characterId) {
		frmArene.setVisible(true);
		frmChoixJoueur.dispose();
		((JeuClient) leJeu).envoi(PSEUDO+STRINGSEPARE+pseudo+STRINGSEPARE+characterId);
	}
	
	public void EventArene(String info) {
		((JeuClient) leJeu).envoi(TCHAT+STRINGSEPARE+info);
	}
	
	/**
	 * Demande provenant de JeuServeur
	 * @param ordre ordre à exécuter
	 * @param info information à traiter
	 */
	public void EventJeuServeur(String ordre, Object info) {
		switch (ordre) {
		case AJOUTMUR:
			frmArene.ajoutMurs(info);
			break;
		case AJOUTPANELMURS:
			leJeu.envoi((Connection) info, this.frmArene.getWallPane());
			break;
		case AJOUTJLABELJEU:
			this.frmArene.ajoutJLabelJeu(info);
			break;
		case MODIFPANELJEU:
			leJeu.envoi((Connection) info, this.frmArene.getGamePane());
			break;
		case AJOUTPHRASE:
			frmArene.ajoutChat((String) info);
			((JeuServeur)leJeu).envoi(frmArene.getTxtChat());
			break;
		}
	}
	
	/**
	 * Demande provenant de JeuClient
	 * @param ordre ordre à exécuter
	 * @param info information à traiter
	 */
	public void EventJeuClient(String ordre, Object info) {
		switch (ordre) {
		case AJOUTPANELMURS:
			frmArene.setWallPane((JPanel) info);
			break;
		case MODIFPANELJEU:
			frmArene.setGamePane((JPanel) info);
			break;
		case MODIFTCHAT:
			frmArene.setTxtChat((String) info);
			break;
		}
	}
	
	/**
	 * Envoi d'informations vers l'ordinateur distant
	 * @param connection objet de connexion pour l'envoi vers l'ordinateur distant
	 * @param info information à envoyer
	 */
	public void envoi(Connection connection, Object info) {
		connection.envoi(info);
	}
	
	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch(ordre) {
		case CONNEXION:
			if(leJeu instanceof JeuServeur) {
				leJeu.connexion(connection);
				return;
			}
			
			leJeu = new JeuClient(this);
			leJeu.connexion(connection);
			
			frmArene = new Arene(this, CLIENT);
			frmArene.setVisible(false);
				
			frmChoixJoueur = new ChoixJoueur(this);
			frmChoixJoueur.setVisible(true);
				
			frmEntreeJeu.dispose();
			break;
		case RECEPTION:
			leJeu.reception(connection, info);
			break;
		case DECONNEXION:
			break;
		}
	}
}
