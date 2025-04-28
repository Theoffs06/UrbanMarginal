package controleur;

import modele.Jeu;
import modele.JeuServeur;
import modele.JeuClient;
import outils.connexion.*;
import vue.*;

public class Controle implements AsyncResponse, Global {
		private Jeu leJeu;
	
	private EntreeJeu frmEntreeJeu;
	private ChoixJoueur frmChoixJoueur;
	private Arene frmArene;
	
	public static void main(String[] args) {
		new Controle();
	}
	
	/** Constructeur **/
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this);
		this.frmEntreeJeu.setVisible(true);
	}
	
	public void EventEntreeJeu(String info) {
		if(info.equals("serveur")) {
			new ServeurSocket(this, PORT);
			leJeu = new JeuServeur(this);
			
			frmArene = new Arene();
			frmArene.setVisible(true);
			
			frmEntreeJeu.dispose();
			return;
		}
		
		new ClientSocket(this, info, Global.PORT);
	}
	
	public void EventChoixJoueur(String pseudo, int characterId) {
		frmArene.setVisible(true);
		frmChoixJoueur.dispose();
		((JeuClient) leJeu).envoi(PSEUDO+STRINGSEPARE+pseudo+STRINGSEPARE+characterId);
	}
	
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
			
			frmArene = new Arene();
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
