package controleur;

import outils.connexion.*;
import vue.*;

public class Controle implements AsyncResponse {
	private static final int PORT = 6666;
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
		if(info == "serveur") {
			typeJeu = "serveur";
			ServeurSocket serveurSocket = new ServeurSocket(this, PORT);
			
			frmArene = new Arene();
			frmArene.setVisible(true);
			
			frmEntreeJeu.dispose();
			return;
		}
		
		typeJeu = "client";
		ClientSocket clientSocket = new ClientSocket(this, info, PORT);
		
	}
	
	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch(ordre) {
		case "connexion":
			if(typeJeu == "client") {
				frmArene = new Arene();
				frmArene.setVisible(false);
				
				frmChoixJoueur = new ChoixJoueur();
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
