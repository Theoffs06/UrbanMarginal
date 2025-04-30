package vue;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
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
	
	private Boolean client;
	private Controle controle;
	
	/**
	 * Create the frame.
	 */
	public Arene(Controle controle, String typeJeu) {
		this.client = typeJeu.equals(CLIENT);
		this.controle = controle;
		this.getContentPane().setPreferredSize(new Dimension(LARGEURARENE, HAUTEURARENE + 25 + 140));
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
		
		if (this.client) {
			txtMessage = new JTextField();
			txtMessage.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					PressedKeySendMessage(e);
				}
			});
			txtMessage.setBounds(0, 600, 800, 25);
			contentPane.add(txtMessage);
			txtMessage.setColumns(10);
		}
		
		spChat = new JScrollPane();
		spChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spChat.setBounds(0, 625, 800, 140);
		contentPane.add(spChat);
		
		txtChat = new JTextArea();
		txtChat.setEditable(false);
		spChat.setViewportView(txtChat);
				
		JLabel lbBackground = new JLabel("");
		URL resource = getClass().getClassLoader().getResource(FONDARENE);
		lbBackground.setBounds(0, 0, 800, 600);
		lbBackground.setIcon(new ImageIcon(resource));
		contentPane.add(lbBackground);
		
	}
	
	public void PressedKeySendMessage(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (txtMessage.getText().equals("")) return;
			controle.EventArene(txtMessage.getText());
			txtMessage.setText("");
		}
	}
	
	/**
	 * Ajoute un mur dans le panel des murs
	 * @param mur le mur à ajouter
	 */
	public void ajoutMurs(Object mur) {
		wallPane.add((JLabel) mur);
		wallPane.repaint();
	}
	
	/**
	 * Ajout d'un joueur, son message ou sa boule, dans le panel de jeu
	 * @param label le label à ajouter
	 */
	public void ajoutJLabelJeu(Object label) {
		gamePane.add((JLabel) label);
		gamePane.repaint();
	}
	
	public void ajoutChat(String message) {
		txtChat.setText(this.txtChat.getText()+message+"\r\n");
		txtChat.setCaretPosition(txtChat.getDocument().getLength());
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
	
	public String getTxtChat() {
		return txtChat.getText();
	}
	
	public void setTxtChat(String txtChat) {
		this.txtChat.setText(txtChat);
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}
}
