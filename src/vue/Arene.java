package vue;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Global;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Arene extends JFrame implements Global {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel gamePane;
	private JPanel wallPane;
	
	private JTextField txtMessage;
	private JScrollPane spChat;
	private JTextArea txtChat;
	
	/**
	 * Create the frame.
	 */
	public Arene() {
		this.getContentPane().setPreferredSize(new Dimension(800, 600 + 25 + 140));
		this.pack();
		
		setResizable(false);
		setTitle("Arena");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		gamePane = new JPanel();
		gamePane.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		gamePane.setOpaque(false);
		gamePane.setLayout(null);
		contentPane.add(gamePane);
		
		wallPane = new JPanel();
		wallPane.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		wallPane.setOpaque(false);
		wallPane.setLayout(null);
		contentPane.add(wallPane);
		
		txtMessage = new JTextField();
		txtMessage.setBounds(0, 600, 800, 25);
		contentPane.add(txtMessage);
		txtMessage.setColumns(10);
		
		spChat = new JScrollPane();
		spChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spChat.setBounds(0, 625, 800, 140);
		contentPane.add(spChat);
		
		txtChat = new JTextArea();
		spChat.setViewportView(txtChat);
				
		JLabel lbBackground = new JLabel("");
		URL resource = getClass().getClassLoader().getResource(FONDARENE);
		lbBackground.setBounds(0, 0, 800, 600);
		lbBackground.setIcon(new ImageIcon(resource));
		contentPane.add(lbBackground);
		
	}
	
	/**
	 * Ajoute un mur dans le panel des murs
	 * @param unMur le mur à ajouter
	 */
	public void ajoutMurs(Object mur) {
		wallPane.add((JLabel) mur);
		wallPane.repaint();
	}
	
	/**
	 * Ajout d'un joueur, son message ou sa boule, dans le panel de jeu
	 * @param unJLabel le label à ajouter
	 */
	public void ajoutJLabelJeu(Object label) {
		gamePane.add((JLabel) label);
		gamePane.repaint();
	}
	
	public JPanel getWallPane() {
		return wallPane;
	}
	
	public void setWallPane(JPanel wallPane) {
		this.wallPane.add(wallPane);
		this.wallPane.repaint();
	}
	
	public JPanel getGamePane() {
		return gamePane;
	}
	
	public void setGamePane(JPanel gamePane) {
		this.gamePane.removeAll();
		this.gamePane.add(gamePane);
		this.gamePane.repaint();
	}
}
