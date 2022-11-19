package br.edu.devmedia.jdbc.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JInternalFrame;

import br.edu.devmedia.jdbc.bo.PessoaBO;
import br.edu.devmedia.jdbc.bo.UfBO;
import br.edu.devmedia.jdbc.dao.PessoaDAO;
import br.edu.devmedia.jdbc.dao.UfDAO;
import br.edu.devmedia.jdbc.dto.EnderecoDTO;
import br.edu.devmedia.jdbc.dto.PessoaDTO;
import br.edu.devmedia.jdbc.dto.UfDTO;
import br.edu.devmedia.jdbc.exeption.NegocioExeption;
import br.edu.devmedia.jdbc.exeption.PersistenciaException;
import br.edu.devmedia.jdbc.util.MessagensUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import java.awt.Component;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5153988559303364191L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCpf;
	private JTextField txtDataNasc;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
	private JTable tableListagem;
	private JTextField textNomeConsulta;
	private JTextField textCpfConsulta;
	private JTable tableCosultar;
	private PessoaDTO pessoaDTO;
	private List <Integer> idsPessaoas = new ArrayList<Integer>();
	private List <Integer> idsUfs = new ArrayList<Integer>();
	private JTextField textFieldNomeditar;
	private JTextField textFieldCpfEditar;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFemenino;
	private JRadioButton rdbtnMasculinoEditar;
	private JRadioButton rdbtnFemeninoEditar;
    private JLabel lblCpfEditar;
    private  JLabel lblLugarIdEditar;
    ButtonGroup grpButEditar = new ButtonGroup();
    private JTextField textFieldLogradouro;
    private JTextField textFielBairro;
    private JTextField texFieldCidade;
    private JTextField textFieldNumero;
    private JTextField textFieldCEP;
    private  JComboBox comboBoxUfs;
    private JComboBox comboBoxUfEditar;
    private JComboBox<Object> comboBoxUfBuscar;
    private JTextField textFieldDtNascEditar;
    private JTextField textFieldLogradoroEditar;
    private JTextField textFieldBairroEditar;
    private JTextField textFielCidadeEditar;
    private JTextField textFieldNumeroEditar;
    private JTextField textFieldCepEditar;
   	private JButton btnDeletarTudo;	
   	private JTextField textFieldCepBuscar;
	
    
		

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	
	
	
	public MainFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 555);
		contentPane = new JPanel();
		this.setTitle("principal");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
		JTabbedPane mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mainTabbedPane, GroupLayout.PREFERRED_SIZE, 789, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addComponent(mainTabbedPane, GroupLayout.PREFERRED_SIZE, 491, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		ButtonGroup grpButCadast = new ButtonGroup();
		ButtonGroup grpSexCons = new ButtonGroup();
		ButtonGroup grpButOrdBy= new ButtonGroup();
		
		AbstractAction delecao = new AbstractAction(){

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int linha = Integer.parseInt(e.getActionCommand());
				int reposta =JOptionPane.showConfirmDialog(MainFrame.this, "Deseja realmente remmver essa pessoa? ");
				
			
				if(reposta==0){
					
					try{
					PessoaBO pessoaBO = new PessoaBO();
					pessoaDTO = pessoaBO.buscaPorId(idsPessaoas.get(linha));
					pessoaBO.removerPessoas(idsPessaoas.get(linha),recuperaIdEndeco());
					idsPessaoas.remove(linha);
					MessagensUtil.addMsg(MainFrame.this, "pessoa removida  com sucesso");
					JTable table = (JTable)e.getSource();
					((DefaultTableModel) table.getModel()).removeRow(linha);
					}catch( Exception exeption){
						exeption.printStackTrace();
						MessagensUtil.addMsg(MainFrame.this, exeption.getMessage());	
					}
				}
				
			}
			
		};
		
		AbstractAction edicao = new AbstractAction(){

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int linha  = Integer.parseInt(e.getActionCommand());
				try {
					PessoaBO pessoaBO = new PessoaBO();
					 
				     pessoaDTO = pessoaBO.buscaPorId(idsPessaoas.get(linha));
				     popularInternalFrame();
				  //  popularInternalFrame(pessoaDTO);
				     
				} catch (NegocioExeption e1) {
					e1.printStackTrace();
					MessagensUtil.addMsg(MainFrame.this,e1.getMessage());
				}
			}
			
		};
		
		
		
		JPanel panelCadastro = new JPanel();
		panelCadastro.setBorder(null);
		mainTabbedPane.addTab("Cadastro", null, panelCadastro, null);
		
			
			
			JLabel lblNewLabel = new JLabel("NOME");
			
			txtNome = new JTextField();
			txtNome.setColumns(10);
			
			JLabel lblCpf = new JLabel("CPF");
			
			txtCpf = new JTextField();
			txtCpf.setColumns(10);
			
			JLabel lblNew = new JLabel("SEXO");
			
			rdbtnMasculino= new JRadioButton("MASCULINO");
			rdbtnMasculino.setSelected(true);
			
			rdbtnFemenino = new JRadioButton("FEMENINO");
			grpButCadast.add(rdbtnMasculino);
			grpButCadast.add(rdbtnFemenino);
			JLabel lblDtnasc = new JLabel("DT_NASC.");
			
			txtDataNasc = new JTextField();
			txtDataNasc.setColumns(10);
			
			JButton btnCadastrar = new JButton("cadastrar");
			
			btnCadastrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UfDTO ufDTO  = new UfDTO();
					EnderecoDTO enderecoDTO = new  EnderecoDTO();
					PessoaDTO pessoaDTO = new PessoaDTO();
					PessoaBO pessoaBO = new PessoaBO();
					try {
						String nome = txtNome.getText();
						String cpf = txtCpf.getText();
						String nasc = txtDataNasc.getText();
						
						pessoaBO.validaNome(nome);
						pessoaBO.validaCpf(cpf);
						pessoaBO.validaDtaNasc(nasc);

						pessoaDTO.setNome(nome);
						pessoaDTO.setCpf(Long.valueOf(cpf));
						pessoaDTO.setDtNascimento(dateFormat.parse(nasc));
						char sexo = rdbtnMasculino.isSelected() ?  'M' :'F';
						pessoaDTO.setSexo(sexo);
						
						ufDTO.setIdUF(comboBoxUfs.getSelectedIndex()+1);
						enderecoDTO.setUfDTO(ufDTO);
						enderecoDTO.setLogradouro(textFieldLogradouro.getText());
						enderecoDTO.setBairro(textFielBairro.getText());
						enderecoDTO.setCep(textFieldCEP.getText().equals("") ? null: Integer.parseInt(textFieldCEP.getText()));
						enderecoDTO.setCidade(texFieldCidade.getText());
						enderecoDTO.setNumero( textFieldNumero.getText().equals("")? null:Long.parseLong(textFieldNumero.getText()));
						
						
						pessoaBO.validaEndereco(enderecoDTO);
						pessoaDTO.setEnderecoDTO(enderecoDTO);
	 
						pessoaBO.cadastrar(pessoaDTO);
						MessagensUtil.addMsg(MainFrame.this,"cadasro efuado com sucesso");
						MainFrame.this.dispose();
						main(null);
					} catch (Exception el) {
						// TODO Auto-generated catch block
						el.printStackTrace();
						MessagensUtil.addMsg(MainFrame.this, el.getMessage());
					}
					
					
					
				}
			});
			
			JButton btnLimpar = new JButton("limpar");
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtNome.setText("");
					txtCpf.setText("");
					textFieldLogradouro.setText("");
					textFieldNumero.setText("");
					texFieldCidade.setText("");
					textFielBairro.setText("");
					textFieldCEP.setText("");
					txtDataNasc.setText("");
					rdbtnMasculino.setSelected(true);
					comboBoxUfs.setSelectedIndex(0);
				
					
				}
			});
			
			JLabel lblLogradouro = new JLabel("Logradouro");
			
			JLabel lblBairro = new JLabel("Bairro");
			
			JLabel lblCidade = new JLabel("Cidade");
			
			JLabel lblNumero = new JLabel("Numero");
			
			JLabel lblCep = new JLabel("CEP");
			
			JLabel lblEstado = new JLabel("Estado");
			
			textFieldLogradouro = new JTextField();
			textFieldLogradouro.setColumns(10);
			
			textFielBairro = new JTextField();
			textFielBairro.setColumns(10);
			
			texFieldCidade = new JTextField();
			texFieldCidade.setColumns(10);
			
			textFieldNumero = new JTextField();
			textFieldNumero.setColumns(10);
			
			textFieldCEP = new JTextField();
			textFieldCEP.setColumns(10);
			
			comboBoxUfs = new JComboBox();
			UfBO ufBO = new UfBO();
			try {
				comboBoxUfs.setModel(new DefaultComboBoxModel(conveterEstados(ufBO.listaUfs())));
			} catch (NegocioExeption e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//comboBoxUfEditar.setSelectedIndex(8);
			GroupLayout gl_panelCadastro = new GroupLayout(panelCadastro);
			gl_panelCadastro.setHorizontalGroup(
				gl_panelCadastro.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panelCadastro.createSequentialGroup()
						.addGroup(gl_panelCadastro.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panelCadastro.createSequentialGroup()
								.addContainerGap(526, Short.MAX_VALUE)
								.addComponent(btnCadastrar)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnLimpar, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panelCadastro.createSequentialGroup()
								.addGap(21)
								.addGroup(gl_panelCadastro.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_panelCadastro.createSequentialGroup()
										.addGroup(gl_panelCadastro.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel)
											.addComponent(lblCpf))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_panelCadastro.createParallelGroup(Alignment.LEADING)
											.addComponent(txtCpf, 270, 270, 270)
											.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)))
									.addGroup(gl_panelCadastro.createSequentialGroup()
										.addComponent(lblNew)
										.addGap(18)
										.addComponent(rdbtnMasculino)
										.addGap(18)
										.addComponent(rdbtnFemenino))
									.addGroup(gl_panelCadastro.createSequentialGroup()
										.addComponent(lblDtnasc)
										.addGap(27)
										.addComponent(txtDataNasc)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panelCadastro.createParallelGroup(Alignment.LEADING)
									.addComponent(lblLogradouro)
									.addComponent(lblBairro)
									.addComponent(lblCidade)
									.addComponent(lblNumero)
									.addComponent(lblEstado))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panelCadastro.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panelCadastro.createSequentialGroup()
										.addGroup(gl_panelCadastro.createParallelGroup(Alignment.LEADING)
											.addComponent(comboBoxUfs, 0, 158, Short.MAX_VALUE)
											.addGroup(gl_panelCadastro.createSequentialGroup()
												.addComponent(textFieldNumero, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblCep)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(textFieldCEP, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
									.addGroup(Alignment.TRAILING, gl_panelCadastro.createSequentialGroup()
										.addGroup(gl_panelCadastro.createParallelGroup(Alignment.LEADING, false)
											.addComponent(textFieldLogradouro, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
											.addComponent(textFielBairro, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
											.addComponent(texFieldCidade, 245, 245, 245))
										.addPreferredGap(ComponentPlacement.RELATED)))))
						.addGap(152))
			);
			gl_panelCadastro.setVerticalGroup(
				gl_panelCadastro.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCadastro.createSequentialGroup()
						.addGap(24)
						.addGroup(gl_panelCadastro.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel)
							.addComponent(lblLogradouro)
							.addComponent(textFieldLogradouro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCadastro.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelCadastro.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panelCadastro.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblBairro)
									.addComponent(textFielBairro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_panelCadastro.createSequentialGroup()
								.addGap(21)
								.addComponent(txtCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panelCadastro.createSequentialGroup()
								.addGap(23)
								.addComponent(lblCpf)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelCadastro.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelCadastro.createSequentialGroup()
								.addGap(18)
								.addGroup(gl_panelCadastro.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNew)
									.addComponent(rdbtnMasculino)
									.addComponent(rdbtnFemenino))
								.addGap(18)
								.addGroup(gl_panelCadastro.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblDtnasc)
									.addComponent(txtDataNasc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_panelCadastro.createSequentialGroup()
								.addComponent(lblCidade)
								.addGap(18)
								.addGroup(gl_panelCadastro.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNumero)
									.addComponent(textFieldNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblCep)
									.addComponent(textFieldCEP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(22)
								.addGroup(gl_panelCadastro.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblEstado)
									.addComponent(comboBoxUfs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addComponent(texFieldCidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
						.addGroup(gl_panelCadastro.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnCadastrar)
							.addComponent(btnLimpar))
						.addContainerGap())
			);
			gl_panelCadastro.linkSize(SwingConstants.HORIZONTAL, new Component[] {textFieldLogradouro, textFielBairro, texFieldCidade});
			gl_panelCadastro.linkSize(SwingConstants.HORIZONTAL, new Component[] {lblLogradouro, lblBairro, lblCidade, lblNumero, lblEstado});
			gl_panelCadastro.linkSize(SwingConstants.HORIZONTAL, new Component[] {lblNewLabel, lblCpf});
			panelCadastro.setLayout(gl_panelCadastro);
		JPanel panelListagem = new JPanel();
		mainTabbedPane.addTab("listagem", null, panelListagem, null);
		
		JScrollPane scrollPaneListgem = new JScrollPane();
		btnDeletarTudo = new JButton("Deletar Tudo");
		verificarPreencherTabel();
		JInternalFrame internalFrame = new JInternalFrame("Edição");
		//internalFrame.setVisible(true);
		
		
		
		GroupLayout gl_panelListagem = new GroupLayout(panelListagem);
		gl_panelListagem.setHorizontalGroup(
			gl_panelListagem.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelListagem.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelListagem.createParallelGroup(Alignment.TRAILING)
						.addComponent(internalFrame, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
						.addComponent(scrollPaneListgem, GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
						.addComponent(btnDeletarTudo, Alignment.LEADING))
					.addContainerGap())
		);
		gl_panelListagem.setVerticalGroup(
			gl_panelListagem.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelListagem.createSequentialGroup()
					.addGap(6)
					.addComponent(btnDeletarTudo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneListgem, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(internalFrame, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
		);
		
		JLabel lblNomeEditar = new JLabel("Nome:");
		
		textFieldNomeditar = new JTextField();
		textFieldNomeditar.setColumns(10);
		
		 lblCpfEditar = new JLabel("CPF:");
		
		textFieldCpfEditar = new JTextField();
		textFieldCpfEditar.setColumns(10);
		
		JLabel lblSexoEditar = new JLabel("Sexo:");
		
		rdbtnMasculinoEditar = new JRadioButton("Masculino");
		grpButEditar.add(rdbtnMasculinoEditar);
		
	    rdbtnFemeninoEditar = new JRadioButton("Femenino");
	    grpButEditar.add(rdbtnFemeninoEditar);
		lblLugarIdEditar = new JLabel("lugar do id");
		tableListagem = new JTable();
		
		internalFrame.setVisible(true);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				UfDTO ufDTO  = new UfDTO();;
				EnderecoDTO enderecoDTO= new  EnderecoDTO();
				
				PessoaDTO pessoaDTO = new PessoaDTO();
				PessoaBO pessoaBO = new PessoaBO();
				
				try {
					String nome = textFieldNomeditar.getText();
					String cpf = textFieldCpfEditar.getText();
					String nasc = textFieldDtNascEditar.getText();
					
					pessoaBO.validaNome(nome);
					pessoaBO.validaCpf(cpf);
					pessoaBO.validaDtaNasc(nasc);
					
                    
					
					pessoaDTO.setIdPessoa(Integer.parseInt(lblLugarIdEditar.getText()));
					pessoaDTO.setNome(nome);
					pessoaDTO.setCpf(Long.valueOf(cpf));
					pessoaDTO.setDtNascimento(dateFormat.parse(nasc));
					char sexo = rdbtnMasculino.isSelected() ?  'M' :'F';
					pessoaDTO.setSexo(sexo);
					
					pessoaDTO.setEnderecoDTO(enderecoDTO);
					
					ufDTO.setIdUF(comboBoxUfEditar.getSelectedIndex()+1);
					
			     
				//	EnderecoDTO enderecoD =pessoaDT.getEnderecoDTO();
					
					///MessagensUtil.addMsg(MainFrame.this,""+pessoaDT.getEnderecoDTO().getId_endereco());
					//enderecoDTO.setId_endereco(enderecoD.getId_endereco());
					Integer index =recuperaIdEndeco(); 
					enderecoDTO.setId_endereco(index);
					enderecoDTO.setLogradouro(textFieldLogradoroEditar.getText());
					enderecoDTO.setBairro(textFieldBairroEditar.getText());
					enderecoDTO.setCep(textFieldCepEditar.getText().equals("") ? null: Integer.parseInt(textFieldCepEditar.getText()));
					enderecoDTO.setCidade(textFielCidadeEditar.getText());
					enderecoDTO.setNumero( textFieldNumeroEditar.getText().equals("")? null:Long.parseLong(textFieldNumeroEditar.getText()));
					enderecoDTO.setUfDTO(ufDTO);
					
					pessoaBO.validaEndereco(enderecoDTO);
				
					PessoaDAO pessoaDAO = new PessoaDAO();
					pessoaDAO.atualizar(pessoaDTO);
					MessagensUtil.addMsg(MainFrame.this,"Atualizado com suceso !");
					
					
					try {
						String[][] lista = pessoaBO.listagem(idsPessaoas);
					tableListagem.setModel(new DefaultTableModel(
						lista,
						new String[] {
								"ID", "NOME", "CPF", "SEXO", "Dt Nasc", "Logradouro", "Cep","UF","",""
						}
					) {
				
						private static final long serialVersionUID = 1L;
						boolean[] columnEditables = new boolean[] {
							false, false, false, false, false, false,false,false, true, true
						};
						public boolean isCellEditable(int row, int column) {
							return columnEditables[column];
						}
					});
					tableListagem.getColumnModel().getColumn(0).setPreferredWidth(25);
					} catch (NegocioExeption el) {
						el.printStackTrace();
						MessagensUtil.addMsg(MainFrame.this, el.getMessage());
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					MessagensUtil.addMsg(MainFrame.this, e1.getMessage());
				}
				
				ButtonColumn buttonEdicao = new ButtonColumn(tableListagem,edicao, 8);
				ButtonColumn buttonDelecao = new ButtonColumn(tableListagem,delecao, 9);
				buttonEdicao.setMnemonic(KeyEvent.VK_E);
				buttonDelecao.setMnemonic(KeyEvent.VK_D);
			}
		});
		
		JLabel lblInternalEditar = new JLabel("ID:");
		
		JLabel label_1 = new JLabel("Dt_nasc:");
		
		textFieldDtNascEditar = new JTextField();
		textFieldDtNascEditar.setColumns(10);
		
		JPanel panelEnderecoEditar = new JPanel();
		panelEnderecoEditar.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Endere\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, Color.ORANGE));
		GroupLayout groupLayout = new GroupLayout(internalFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblInternalEditar)
									.addGap(38)
									.addComponent(lblLugarIdEditar))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNomeEditar)
										.addComponent(lblCpfEditar))
									.addGap(12)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(textFieldNomeditar, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
										.addComponent(textFieldCpfEditar, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))
									.addGap(168)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblSexoEditar)
											.addGap(18)
											.addComponent(rdbtnMasculinoEditar)
											.addGap(18)
											.addComponent(rdbtnFemeninoEditar))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(label_1)
											.addGap(12)
											.addComponent(textFieldDtNascEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelEnderecoEditar, GroupLayout.PREFERRED_SIZE, 597, GroupLayout.PREFERRED_SIZE)
							.addGap(31)
							.addComponent(btnEditar)))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblInternalEditar)
						.addComponent(lblLugarIdEditar))
					.addGap(2)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(lblNomeEditar)
							.addGap(24)
							.addComponent(lblCpfEditar))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(16)
							.addComponent(textFieldNomeditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(textFieldCpfEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(4)
									.addComponent(lblSexoEditar))
								.addComponent(rdbtnMasculinoEditar)
								.addComponent(rdbtnFemeninoEditar))
							.addGap(8)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(label_1))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(textFieldDtNascEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelEnderecoEditar, GroupLayout.PREFERRED_SIZE, 119, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(58)
							.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {textFieldNomeditar, textFieldCpfEditar});
		
		JLabel lblLogradouro_1 = new JLabel("Logradouro");
		
		JLabel lblBairro_1 = new JLabel("Bairro");
		
		JLabel lblCidade_1 = new JLabel("Cidade");
		
		textFieldLogradoroEditar = new JTextField();
		textFieldLogradoroEditar.setColumns(10);
		
		textFieldBairroEditar = new JTextField();
		textFieldBairroEditar.setColumns(10);
		
		textFielCidadeEditar = new JTextField();
		textFielCidadeEditar.setColumns(10);
		
		JLabel lblCep_1 = new JLabel("CEP");
		
		JLabel lblNumero_1 = new JLabel("Numero");
		
		JLabel lblUf = new JLabel("UF");
		
		textFieldNumeroEditar = new JTextField();
		textFieldNumeroEditar.setColumns(10);
		
		textFieldCepEditar = new JTextField();
		textFieldCepEditar.setColumns(10);
		
		comboBoxUfEditar = new JComboBox();
		
		try {
			UfBO ufBOa = new UfBO();
			comboBoxUfEditar.setModel(new DefaultComboBoxModel(conveterEstados(ufBOa.listaUfs())));
		} catch (NegocioExeption e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		GroupLayout gl_panelEnderecoEditar = new GroupLayout(panelEnderecoEditar);
		gl_panelEnderecoEditar.setHorizontalGroup(
			gl_panelEnderecoEditar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEnderecoEditar.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBairro_1)
						.addComponent(lblLogradouro_1)
						.addComponent(lblCidade_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldBairroEditar, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
						.addComponent(textFielCidadeEditar, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
						.addComponent(textFieldLogradoroEditar, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))
					.addGap(53)
					.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelEnderecoEditar.createSequentialGroup()
							.addComponent(lblNumero_1)
							.addGap(31)
							.addComponent(textFieldNumeroEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panelEnderecoEditar.createSequentialGroup()
							.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblUf)
								.addComponent(lblCep_1))
							.addGap(31)
							.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.TRAILING)
								.addComponent(textFieldCepEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBoxUfEditar, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		gl_panelEnderecoEditar.setVerticalGroup(
			gl_panelEnderecoEditar.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelEnderecoEditar.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelEnderecoEditar.createSequentialGroup()
							.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.TRAILING)
								.addComponent(textFieldLogradoroEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLogradouro_1))
							.addGap(6)
							.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBairro_1)
								.addComponent(textFieldBairroEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelEnderecoEditar.createSequentialGroup()
							.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNumero_1)
								.addComponent(textFieldNumeroEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCep_1)
								.addComponent(textFieldCepEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelEnderecoEditar.createSequentialGroup()
							.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCidade_1)
								.addComponent(textFielCidadeEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(28, Short.MAX_VALUE))
						.addGroup(gl_panelEnderecoEditar.createSequentialGroup()
							.addGroup(gl_panelEnderecoEditar.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUf)
								.addComponent(comboBoxUfEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(42))))
		);
		gl_panelEnderecoEditar.linkSize(SwingConstants.HORIZONTAL, new Component[] {textFieldNumeroEditar, textFieldCepEditar, comboBoxUfEditar});
		gl_panelEnderecoEditar.linkSize(SwingConstants.HORIZONTAL, new Component[] {lblLogradouro_1, lblBairro_1, lblCidade_1});
		gl_panelEnderecoEditar.linkSize(SwingConstants.HORIZONTAL, new Component[] {lblCep_1, lblNumero_1, lblUf});
		panelEnderecoEditar.setLayout(gl_panelEnderecoEditar);
		internalFrame.getContentPane().setLayout(groupLayout);
		tableListagem = new JTable();
		PessoaBO pessoaBO = new PessoaBO();
		try {
			String[][] lista = pessoaBO.listagem(idsPessaoas);
			if(lista.length ==0){
				btnDeletarTudo.setEnabled(false);
			}
			
		tableListagem.setModel(new DefaultTableModel(
			lista,
			new String[] {
				"ID", "NOME", "CPF", "SEXO", "Dt Nasc", "Logradouro", "Cep","UF","","",
			}
		) {
	
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false,true,true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		} catch (NegocioExeption e) {
			e.printStackTrace();
			MessagensUtil.addMsg(MainFrame.this, e.getMessage());
		}
		//buttonDelecao.setMnemonic(KeyEvent.VK_E);
		//
		tableListagem.getColumnModel().getColumn(0).setPreferredWidth(25);
		ButtonColumn buttonEdicao = new ButtonColumn(tableListagem,edicao, 8);
		ButtonColumn buttonDelecao = new ButtonColumn(tableListagem,delecao, 9);
		buttonEdicao.setMnemonic(KeyEvent.VK_E);
		buttonDelecao.setMnemonic(KeyEvent.VK_D);

		btnDeletarTudo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PessoaBO pessoaBO = new  PessoaBO();
				try{
					
				
					int reposta  = JOptionPane.showConfirmDialog(MainFrame.this,"Deseja realmnte remover todas as pessoas ?");
					if(reposta == 0){
						pessoaBO.removerTudo();	
						MessagensUtil.addMsg(MainFrame.this, "Pessoas removidas com sucesso ");	
					
					   DefaultTableModel modelListagem  =( DefaultTableModel) tableListagem.getModel();
					   modelListagem.setRowCount(0);
					   verificarPreencherTabel();
					}else if(reposta==1){
						MessagensUtil.addMsg(MainFrame.this,"Nao faça mais isso!");	
					}
					
				} catch (NegocioExeption el) {
					el.printStackTrace();
					MessagensUtil.addMsg(MainFrame.this, el.getMessage());
				}
			}
		});
		
		scrollPaneListgem.setViewportView(tableListagem);
		panelListagem.setLayout(gl_panelListagem);
		JPanel panelTabelListagem = new JPanel();
		panelTabelListagem.setBorder(new TitledBorder(null, "Listagem", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		
		
		JPanel panelConsulta = new JPanel();
		mainTabbedPane.addTab("Consulta", null, panelConsulta, null);
		
		JLabel lblNome = new JLabel("nome");
		
		textNomeConsulta = new JTextField();
		textNomeConsulta.setColumns(10);
		
		JLabel lblCPF = new JLabel("CPF");
		
		textCpfConsulta = new JTextField();
		textCpfConsulta.setColumns(10);
		
		JLabel lbSexo = new JLabel("sexo");
		
		JRadioButton rdbtnMasculinoCon = new JRadioButton("Masculino");
		rdbtnMasculinoCon.setSelected(true);
		
		JRadioButton rdbtnFemeninoCon = new JRadioButton("FEMENINO");
		grpSexCons.add(rdbtnMasculinoCon);
		grpSexCons.add(rdbtnFemeninoCon);
		
		JButton btnConsultar = new JButton("Consultar");
		
		
		JLabel lblOrderby = new JLabel("Por Ordem do:");
		
		JRadioButton rdbtnNomeOrdBy = new JRadioButton("Nome");
		grpButOrdBy.add(rdbtnNomeOrdBy);
		
		JRadioButton rdbtnCpfOrdBy = new JRadioButton("CPF");
		grpButOrdBy.add(rdbtnCpfOrdBy);
		
		JLabel lblCep_2 = new JLabel("CEP:");
		
		JLabel lblUf_1 = new JLabel("UF:");
		
		textFieldCepBuscar = new JTextField();
		textFieldCepBuscar.setColumns(10);
		
		comboBoxUfBuscar = new JComboBox<Object>();
		try {
			comboBoxUfBuscar.setModel(new DefaultComboBoxModel(conveterEstados(ufBO.listaUfs())));
		} catch (NegocioExeption e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		GroupLayout gl_panelConsulta = new GroupLayout(panelConsulta);
		gl_panelConsulta.setHorizontalGroup(
			gl_panelConsulta.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelConsulta.createSequentialGroup()
					.addComponent(panelTabelListagem, GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panelConsulta.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_panelConsulta.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNome)
						.addComponent(lblCPF))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelConsulta.createParallelGroup(Alignment.LEADING, false)
						.addComponent(textCpfConsulta)
						.addComponent(textNomeConsulta, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
					.addGap(500))
				.addGroup(gl_panelConsulta.createSequentialGroup()
					.addGap(21)
					.addComponent(btnConsultar)
					.addGap(660))
				.addGroup(gl_panelConsulta.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_panelConsulta.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panelConsulta.createSequentialGroup()
							.addGroup(gl_panelConsulta.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCep_2)
								.addComponent(lblUf_1))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelConsulta.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBoxUfBuscar, 0, 212, Short.MAX_VALUE)
								.addComponent(textFieldCepBuscar, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)))
						.addGroup(Alignment.LEADING, gl_panelConsulta.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_panelConsulta.createSequentialGroup()
								.addComponent(lblOrderby)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(rdbtnNomeOrdBy)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(rdbtnCpfOrdBy))
							.addGroup(gl_panelConsulta.createSequentialGroup()
								.addComponent(lbSexo)
								.addGap(18)
								.addComponent(rdbtnMasculinoCon)
								.addGap(18)
								.addComponent(rdbtnFemeninoCon))))
					.addGap(732))
		);
		gl_panelConsulta.setVerticalGroup(
			gl_panelConsulta.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelConsulta.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelConsulta.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNome)
						.addComponent(textNomeConsulta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelConsulta.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCPF)
						.addComponent(textCpfConsulta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelConsulta.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnMasculinoCon)
						.addComponent(rdbtnFemeninoCon)
						.addComponent(lbSexo))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelConsulta.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnCpfOrdBy)
						.addComponent(rdbtnNomeOrdBy)
						.addComponent(lblOrderby))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelConsulta.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelConsulta.createSequentialGroup()
							.addComponent(lblCep_2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelConsulta.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUf_1)
								.addComponent(comboBoxUfBuscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(textFieldCepBuscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(54)
					.addComponent(btnConsultar)
					.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
					.addComponent(panelTabelListagem, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panelConsulta.linkSize(SwingConstants.VERTICAL, new Component[] {textNomeConsulta, textCpfConsulta});
		gl_panelConsulta.linkSize(SwingConstants.VERTICAL, new Component[] {lblNome, lblCPF, lbSexo, lblOrderby, lblCep_2, lblUf_1});
		
		JScrollPane scrollPaneConsultar = new JScrollPane();
		GroupLayout gl_panelTabelListagem = new GroupLayout(panelTabelListagem);
		gl_panelTabelListagem.setHorizontalGroup(
			gl_panelTabelListagem.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelTabelListagem.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneConsultar, GroupLayout.PREFERRED_SIZE, 748, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(232, Short.MAX_VALUE))
		);
		gl_panelTabelListagem.setVerticalGroup(
			gl_panelTabelListagem.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPaneConsultar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
		);
		
		tableCosultar = new JTable();
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nomeConsul = textNomeConsulta.getText();
				Long cpfConsul= textCpfConsulta.getText().equals("")? null : Long.parseLong( textCpfConsulta.getText());
				char sexoConsul = rdbtnMasculinoCon.isSelected() ?  'M' :'F';
				String order  =rdbtnNomeOrdBy.isSelected() ? "NOME":"CPF";

				PessoaBO pessoaBO = new PessoaBO();
				try {
					String listaConsulta [][] = pessoaBO.listaConsulta(nomeConsul, cpfConsul,sexoConsul,order );
					if(listaConsulta != null){
						tableCosultar.setModel(new DefaultTableModel(
								listaConsulta 
								,
								new String[] {
										"ID", "NOME","CPF", "SEXO", "Dt Nasc"
								}
								) {

							private static final long serialVersionUID = 1L;
							boolean[] columnEditables = new boolean[] {
									false, false ,false,false,false
							};
							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});}
				} catch (Exception ej) {
					// TODO Auto-generated catch block
					ej.printStackTrace();
					MessagensUtil.addMsg(MainFrame.this,ej.getMessage());
				}
			}
		});
		scrollPaneConsultar.setViewportView(tableCosultar);
		panelTabelListagem.setLayout(gl_panelTabelListagem);
		panelConsulta.setLayout(gl_panelConsulta);
		contentPane.setLayout(gl_contentPane);	
		
	}
	
	/**private void popularInternalFrame (PessoaDTO pessoaDTO) {
		
		lblLugarIdEditar.setText(pessoaDTO.getIdPessoa().toString());
		textFieldNomeditar.setText(pessoaDTO.getNome());
		textFieldCpfEditar.setText(pessoaDTO.getCpf().toString());
		textFieldDtNascEditar.setText(dateFormat.format(pessoaDTO.getDtNascimento()));
		rdbtnMasculinoEditar.setSelected(pessoaDTO.getSexo()=='M');
		rdbtnFemeninoEditar.setSelected(pessoaDTO.getSexo()=='F');
		
		EnderecoDTO enderecoDTO =pessoaDTO.getEnderecoDTO();
		
		textFieldLogradoroEditar.setText(enderecoDTO.getLogradouro());
		textFieldBairroEditar.setText(enderecoDTO.getBairro());
		textFielCidadeEditar.setText(pessoaDTO.getEnderecoDTO().getCidade());
		textFieldNumeroEditar.setText(enderecoDTO.getNumero().toString());
	    textFieldCepEditar.setText(enderecoDTO.getCep().toString());
	    comboBoxUfEditar.setSelectedIndex(enderecoDTO.getUfDTO().getIdUF()-1);
	    
	    
	   } */
	
private void popularInternalFrame () {
		
		lblLugarIdEditar.setText(pessoaDTO.getIdPessoa().toString());
		textFieldNomeditar.setText(pessoaDTO.getNome());
		textFieldCpfEditar.setText(pessoaDTO.getCpf().toString());
		textFieldDtNascEditar.setText(dateFormat.format(pessoaDTO.getDtNascimento()));
		rdbtnMasculinoEditar.setSelected(pessoaDTO.getSexo()=='M');
		rdbtnFemeninoEditar.setSelected(pessoaDTO.getSexo()=='F');
		
		EnderecoDTO enderecoDTO =pessoaDTO.getEnderecoDTO();
		
		textFieldLogradoroEditar.setText(enderecoDTO.getLogradouro());
		textFieldBairroEditar.setText(enderecoDTO.getBairro());
		textFielCidadeEditar.setText(pessoaDTO.getEnderecoDTO().getCidade());
		textFieldNumeroEditar.setText(enderecoDTO.getNumero().toString());
	    textFieldCepEditar.setText(enderecoDTO.getCep().toString());
	    comboBoxUfEditar.setSelectedIndex(enderecoDTO.getUfDTO().getIdUF()-1);
}
	
	private int  recuperaIdEndeco(){
		int  index = 0;
		EnderecoDTO enderecoDTO =pessoaDTO.getEnderecoDTO();
		index = enderecoDTO.getId_endereco();
		return index;
		}
	
	private void verificarPreencherTabel(){
		
		try {
			PessoaBO pessoaBO = new PessoaBO();
	
			String[][] lista = pessoaBO.listagem(idsPessaoas);
			if(lista.length ==0){
				btnDeletarTudo.setEnabled(false);
			   }
			} catch (Exception ej) {
				// TODO Auto-generated catch block
				ej.printStackTrace();
				MessagensUtil.addMsg(MainFrame.this,ej.getMessage());
			}
				
			
	}
	
	
	public String [] conveterEstados(List<UfDTO> lista){
			
	 String [] result = new String[lista.size()];
	 
	 for ( int i= 0 ; i < lista.size(); i++){
		UfDTO ufDTO = lista.get(i);
		result[i] = ufDTO.getDescricao();		
		
	 }
	 return result;
	}
}
