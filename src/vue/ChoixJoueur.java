package vue;

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
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChoixJoueur extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPseudo;
	private JLabel btnNext;
	private JLabel btnGo;
		
	/**
	 * Create the frame.
	 */
	public ChoixJoueur() {
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
		this.pack();
		
		setResizable(false);
		setTitle("Choice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		URL resource = getClass().getClassLoader().getResource("fonds/fondchoix.jpg");
				
		txtPseudo = new JTextField();
		txtPseudo.setBounds(144, 244, 117, 20);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		JLabel btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PreviousButton();
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
		});
		btnNext.setBounds(288, 141, 50, 50);
		contentPane.add(btnNext);
		
		btnGo = new JLabel("");
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GoButton();
			}
		});
		btnGo.setBounds(309, 190, 68, 74);
		contentPane.add(btnGo);
		
		JLabel lbBackground = new JLabel("");
		lbBackground.setBounds(0, 0, 400, 275);
		lbBackground.setIcon(new ImageIcon(resource));
		contentPane.add(lbBackground);
	}
	
	private void PreviousButton() {
		System.out.println("Previous");
	}
	
	private void NextButton() {
		System.out.println("Next");
	}
	
	private void GoButton() {
		Arene frmArene = new Arene();
		frmArene.setVisible(true);
		
		this.dispose();
	}
}
