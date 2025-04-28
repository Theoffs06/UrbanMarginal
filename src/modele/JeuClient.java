package modele;

import controleur.Controle;

/** Gestion du jeu côté client **/
public class JeuClient extends Jeu {
	/** Controleur **/
	public JeuClient(Controle controle) {
		this.controle = controle;
	}
	
	@Override
	public void connexion() {}
	
	@Override
	public void deconnexion() {}

	@Override
	public void reception() {}
	
	/** Envoi d'une information vers le serveur fais appel une fois à l'envoi dans la classe Jeu **/
	public void envoi() {}
}
