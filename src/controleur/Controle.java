package controleur;

import outils.connexion.*;
import vue.*;

public class Controle implements AsyncResponse {
	public static final int PORT = 6666;
	public static final int MAXCHARACTER = 3;
	
	private String typeJeu;
	
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
			typeJeu = "serveur";
			new ServeurSocket(this, PORT);
			
			frmArene = new Arene();
			frmArene.setVisible(true);
			
			frmEntreeJeu.dispose();
			return;
		}
		
		typeJeu = "client"; 
		new ClientSocket(this, info, PORT);
	}
	
	public void EventChoixJoueur(String pseudo, int characterId) {
		frmArene.setVisible(true);
		frmChoixJoueur.dispose();
	}
	
	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch(ordre) {
		case "connexion":
			if(typeJeu.equals("client")) {
				frmArene = new Arene();
				frmArene.setVisible(false);
				
				frmChoixJoueur = new ChoixJoueur(this);
				frmChoixJoueur.setVisible(true);
				
				frmEntreeJeu.dispose();
			}
			break;
		case "réception":
			break;
		case "déconnexion":
			break;
		}
	}
}
