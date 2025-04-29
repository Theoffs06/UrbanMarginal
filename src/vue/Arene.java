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

public class Arene extends JFrame implements Global {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel wallPane;
	private JTextField txtMessage;
	
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		wallPane = new JPanel();
		wallPane.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		wallPane.setOpaque(false);
		wallPane.setLayout(null);
		contentPane.add(wallPane);
		
		txtMessage = new JTextField();
		txtMessage.setBounds(0, 600, 800, 25);
		contentPane.add(txtMessage);
		txtMessage.setColumns(10);
		
		JScrollPane spChat = new JScrollPane();
		spChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spChat.setBounds(0, 625, 800, 140);
		contentPane.add(spChat);
				
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
	
	public JPanel getWallPane() {
		return wallPane;
	}
	
	public void setWallPane(JPanel wallPane) {
		this.wallPane.add(wallPane);
		this.wallPane.repaint();
	}
}
