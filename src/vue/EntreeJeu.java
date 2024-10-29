package vue;

import java.awt.EventQueue;

import controleur.Controle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EntreeJeu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIp;
	
	private Controle controle;
	
	/** Create the frame. **/
	public EntreeJeu(Controle controle) {
		this.controle = controle;
		
		setResizable(false);
		setTitle("Urban Marginal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 150);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JLabel label1 = new JLabel("Start a server :");
		label1.setBounds(5, 5, 85, 23);
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(label1);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle.EventEntreeJeu("serveur");
			}
		});
		btnStart.setBounds(185, 5, 90, 23);
		btnStart.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(btnStart);
		
		JLabel label2 = new JLabel("Connect an existing server:");
		label2.setBounds(5, 30, 200, 14);
		label2.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(label2);
		
		JLabel label3 = new JLabel("IP server :");
		label3.setBounds(5, 50, 58, 14);
		contentPane.add(label3);
		
		txtIp = new JTextField();
		txtIp.setBounds(70, 47, 100, 20);
		txtIp.setText("127.0.0.1");
		txtIp.setColumns(10);
		contentPane.add(txtIp);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle.EventEntreeJeu(txtIp.getText());
			}
		});
		btnConnect.setBounds(185, 46, 90, 23);
		contentPane.add(btnConnect);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExitButton();
			}
		});
		btnExit.setBounds(185, 80, 90, 23);
		contentPane.add(btnExit);
	}
	
	private void ExitButton() {
		System.exit(0);
	}
}
