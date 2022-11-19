package br.edu.devmedia.jdbc.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import br.edu.devmedia.jdbc.bo.LoginBO;
import br.edu.devmedia.jdbc.dto.LoginDTO;
import br.edu.devmedia.jdbc.exeption.NegocioExeption;
import br.edu.devmedia.jdbc.util.MessagensUtil;

import javax.swing.JPasswordField;

public class LoginJFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textLogin;
	private JPasswordField passSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginJFrame frame = new LoginJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnLogar = new JButton("logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginDTO loginDTO = new LoginDTO();
				loginDTO.setNome(textLogin.getText());
				loginDTO.setSenha(new String(passSenha.getPassword()));
				LoginBO loginBO = new LoginBO();
				
				try {
					if(loginBO.logar(loginDTO)){
						LoginJFrame.this.dispose();
						MainFrame mainFrame =  new MainFrame();
						mainFrame.setVisible(true);
						mainFrame.setLocationRelativeTo(null);
					}else{
						MessagensUtil.addMsg(LoginJFrame.this,"Dados invalidos");
					}
				} catch (NegocioExeption el) {
					el.printStackTrace();
					MessagensUtil.addMsg(LoginJFrame.this, el.getMessage());
				}
				
			}
		});
		
		JButton btnSair = new JButton("sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new TitledBorder(null, "login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(32, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnLogar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(16))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panelPrincipal, GroupLayout.PREFERRED_SIZE, 382, GroupLayout.PREFERRED_SIZE)
							.addGap(26))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(41, Short.MAX_VALUE)
					.addComponent(panelPrincipal, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSair)
						.addComponent(btnLogar))
					.addContainerGap())
		);
		
		JLabel lblLogin = new JLabel("login");
		lblLogin.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 14));
		
		JLabel lblSenha = new JLabel("senha:");
		lblSenha.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 14));
		
		textLogin = new JTextField();
		textLogin.setColumns(10);
		
		passSenha = new JPasswordField();
		GroupLayout gl_panelPrincipal = new GroupLayout(panelPrincipal);
		gl_panelPrincipal.setHorizontalGroup(
			gl_panelPrincipal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelPrincipal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSenha)
						.addComponent(lblLogin))
					.addGap(22)
					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(passSenha)
						.addComponent(textLogin, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
					.addGap(9))
		);
		gl_panelPrincipal.setVerticalGroup(
			gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPrincipal.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.BASELINE)
						.addComponent(textLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLogin))
					.addGap(28)
					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSenha)
						.addComponent(passSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(56, Short.MAX_VALUE))
		);
		panelPrincipal.setLayout(gl_panelPrincipal);
		contentPane.setLayout(gl_contentPane);
	}
}
