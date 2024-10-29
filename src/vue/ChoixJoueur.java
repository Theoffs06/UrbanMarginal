package vue;

import controleur.Controle;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChoixJoueur extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel iconPersonnage;
	private JLabel btnPrevious;
	private JLabel btnNext;
	private JLabel btnGo;
	
	private Controle controle;
	private int characterId;
	private JTextField txtPseudo;
		
	/**
	 * Create the frame.
	 */
	public ChoixJoueur(Controle controle) {
		this.controle = controle;
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
		this.pack();
		
		setResizable(false);
		setTitle("Choice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		iconPersonnage = new JLabel("");
		iconPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
		iconPersonnage.setBounds(144, 115, 117, 122);
		contentPane.add(iconPersonnage);
		
		characterId = 1;
		RenderCharacter();
		
		btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PreviousButton();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				HandMouse();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				NormalMouse();
			}
		});
		btnPrevious.setBounds(58, 141, 50, 50);
		contentPane.add(btnPrevious);
		
		btnNext = new JLabel("");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				NextButton();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				HandMouse();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				NormalMouse();
			}
		});
		btnNext.setBounds(288, 141, 50, 50);
		contentPane.add(btnNext);
		
		btnGo = new JLabel("");
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GoButton();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				HandMouse();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				NormalMouse();
			}
		});
		btnGo.setBounds(319, 202, 50, 50);
		contentPane.add(btnGo);
		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(144, 248, 117, 16);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		URL resource = getClass().getClassLoader().getResource("fonds/fondchoix.jpg");
		
		JLabel lbBackground = new JLabel("");
		lbBackground.setBounds(0, 0, 400, 275);
		lbBackground.setIcon(new ImageIcon(resource));
		contentPane.add(lbBackground);
	}
	
	private void NormalMouse() {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	private void HandMouse() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	private void PreviousButton() {
		characterId--;
		if(characterId <= 0) characterId = 3;
		RenderCharacter();
	}
	
	private void NextButton() {
		characterId++;
		if(characterId > Controle.MAXCHARACTER) characterId = 1;
		RenderCharacter();
	}
	
	private void GoButton() {		
		if(txtPseudo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire");
			txtPseudo.requestFocus();
			return;
		}
		
		controle.EventChoixJoueur(txtPseudo.getText(), characterId);
	}
	
	private void RenderCharacter() {
		URL resource = getClass().getClassLoader().getResource("personnages/perso" + characterId + "marche" + 1  + "d" + 1 + ".gif");
		iconPersonnage.setIcon(new ImageIcon(resource));
	}
}
