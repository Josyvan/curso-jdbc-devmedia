package br.edu.devmedia.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



import br.edu.devmedia.jdbc.ConexaoUtil;
import br.edu.devmedia.jdbc.dto.EnderecoDTO;
import br.edu.devmedia.jdbc.dto.PessoaDTO;
import br.edu.devmedia.jdbc.dto.UfDTO;
import br.edu.devmedia.jdbc.exeption.PersistenciaException;

public class PessoaDAO implements GenericDAO<PessoaDTO>{

	@Override
	public void inserirPesssoa(PessoaDTO pessoadto) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	    int chaveDoObjet = insereEndereco(pessoadto.getEnderecoDTO());
		try {
		Connection connection =ConexaoUtil.getInstance(). getConnection();
		String sql ="INSERT INTO TB_PESSOA(NOME,CPF,SEXO,DT_NASC,COD_ENDERECO) VALUES(?,?,?,?,?)";
		PreparedStatement statement= connection.prepareStatement(sql);	
		statement.setString(1,pessoadto.getNome());
		statement.setLong(2,pessoadto.getCpf());
		statement.setString(3,String.valueOf(pessoadto.getSexo()));
		statement.setDate(4,new Date(pessoadto.getDtNascimento().getTime()));
		statement.setInt(5,chaveDoObjet);
		statement.execute();
		connection.close();
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),e);
			
		}
		
	}
	/**
	* Método que insere objeto no banco e retorna o ID gerado
	*/
	private int insereEndereco (EnderecoDTO enderecoDTO) throws PersistenciaException{
		int chave = 0;
	
		try {
			// Algum código para abrir conexão com o banco
			Connection connection =ConexaoUtil.getInstance(). getConnection();
			String sql ="INSERT INTO TB_ENDERECO( LOGRADOURO,BAIRRO,CIDADE,NUMERO,CEP,COD_UF) VALUES(?,?,?,?,?,?)";		
			PreparedStatement statement= connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);	
			statement.setString(1, enderecoDTO.getLogradouro());
			statement.setString(2,enderecoDTO.getBairro());
			statement.setString(3,enderecoDTO.getCidade());
			statement.setLong(4,enderecoDTO.getNumero() );
			statement.setInt(5, enderecoDTO.getCep());
			statement.setInt(6, enderecoDTO.getUfDTO().getIdUF());
			
			statement.executeUpdate();
			
			// recupera chave do objeto
			ResultSet resultSet = statement.getGeneratedKeys();
			
			if(resultSet.next()){
				chave = resultSet.getInt(1);	
			}
			connection.close();	
		} catch (Exception e) {
			e.printStackTrace();	
			throw new PersistenciaException(e.getMessage(),e);
		}
		 // retorna o id do objeto recém inserido
	      return chave;	
	
	}

	@Override
	public void atualizar(PessoaDTO pessoaDTO) throws PersistenciaException {
		// TODO Auto-generated method stub
		 
		try {
		Connection connection = ConexaoUtil.getInstance().getConnection();
		String sql ="UPDATE TB_PESSOA SET NOME=?, CPF = ?,SEXO = ?,"
				+ "DT_NASC=? WHERE ID_PESSOA= ?";
		PreparedStatement statement= connection.prepareStatement(sql);	
		statement.setString(1,pessoaDTO.getNome());
		statement.setLong(2,pessoaDTO.getCpf());
		statement.setString(3,String.valueOf(pessoaDTO.getSexo()));
		statement.setDate(4,new Date(pessoaDTO.getDtNascimento().getTime()));
		statement.setInt(5, pessoaDTO.getIdPessoa());
		
		statement.execute();
		connection.close();
		//atualiza agora o relacionamento da pssoa
		atualizaEndereco(pessoaDTO.getEnderecoDTO());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),e);
		}

	}
	private void atualizaEndereco(EnderecoDTO enderecoDTO) throws PersistenciaException{

		try {

       Connection connection  = ConexaoUtil.getInstance().getConnection();
       String sql  = "UPDATE TB_ENDERECO SET LOGRADOURO  = ? , BAIRRO = ? , CIDADE = ? , NUMERO = ? "
       		+ ", CEP = ?, COD_UF = ? WHERE ID_ENDERECO = ? ";
       PreparedStatement preparedStatement  = connection.prepareStatement(sql);
       preparedStatement.setString(1, enderecoDTO.getLogradouro());
       preparedStatement.setString(2, enderecoDTO.getBairro());
       preparedStatement.setString(3,enderecoDTO.getCidade());
       preparedStatement.setLong(4,enderecoDTO.getNumero());
       preparedStatement.setInt(5,enderecoDTO.getCep());
       preparedStatement.setInt(6, enderecoDTO.getUfDTO().getIdUF());
       preparedStatement.setInt(7,enderecoDTO.getId_endereco());
       
       preparedStatement.execute();
       connection.close();
       
       
		} catch (Exception e) {

			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),e);
		}
	}

	@Override
	public void deletar (Integer idPessoa ) throws PersistenciaException {
		// TODO Auto-generated method stub
		
		try {
	    Connection connection = ConexaoUtil.getInstance().getConnection();
		String sql = "DELETE FROM TB_PESSOA WHERE ID_PESSOA = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1,idPessoa);
		statement.execute();
		connection.close();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),e);
			
		}
		
	}
	public void deletarEndereco( Integer idEndreco ) throws PersistenciaException{
		try {
		    Connection connection = ConexaoUtil.getInstance().getConnection();
			String sql = "DELETE FROM TB_ENDERECO WHERE ID_ENDERECO = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1,idEndreco);
			statement.execute();
			connection.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new PersistenciaException(e.getMessage(),e);
				
			}
	 }
	
	public void deletarTudo() throws PersistenciaException{
		try {
			 Connection connection = ConexaoUtil.getInstance().getConnection();
			 String sql  = "DELETE FROM TB_PESSOA";
			 PreparedStatement Statement = connection.prepareStatement(sql);
			 Statement.execute();
			 connection.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage());
		}
	}

	@Override
	public List<PessoaDTO> listarTodos() throws PersistenciaException {
		// TODO Auto-generated method stub
		List<PessoaDTO> listaPessoas  = new ArrayList<>();
		try{
		Connection connection = ConexaoUtil.getInstance().getConnection();
		
		 String sql ="SELECT * FROM TB_PESSOA"; 
		 
		PreparedStatement statement= connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()){
			PessoaDTO pessoaDTO = new PessoaDTO();
			pessoaDTO.setIdPessoa(resultSet.getInt("ID_PESSOA"));
			pessoaDTO.setNome(resultSet.getString("NOME"));
			pessoaDTO.setCpf(resultSet.getLong("CPF"));
			pessoaDTO.setSexo(resultSet.getString("SEXO").charAt(0));
			pessoaDTO.setDtNascimento(resultSet.getDate("DT_NASC"));
			pessoaDTO.setEnderecoDTO(buscaEnderecoPorId(resultSet.getInt("COD_ENDERECO")));
			
			listaPessoas.add(pessoaDTO);
		}
		}catch(Exception e){
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),e);
				
		  }
		return listaPessoas;	
	}
	
	
	@Override
	public PessoaDTO buscarPorId(Integer id) throws PersistenciaException {
		// TODO Auto-generated method stub
		PessoaDTO pessoaDTO = null;
		try {
			Connection connection = ConexaoUtil.getInstance().getConnection();
			String sql ="SELECT * FROM TB_PESSOA where ID_PESSOA=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				pessoaDTO = new PessoaDTO();
				pessoaDTO.setIdPessoa(resultSet.getInt("ID_PESSOA"));
				pessoaDTO.setNome(resultSet.getString("NOME"));
				pessoaDTO.setCpf(resultSet.getLong("CPF"));
				pessoaDTO.setSexo(resultSet.getString("SEXO").charAt(0));
				pessoaDTO.setDtNascimento(resultSet.getDate("DT_NASC"));
				pessoaDTO.setEnderecoDTO(buscaEnderecoPorId(resultSet.getInt("COD_ENDERECO")));
				
			}
			connection.close();
		} catch (Exception e) {
		
		}
		return pessoaDTO;
	}

	private  EnderecoDTO buscaEnderecoPorId(Integer idEndereco) throws PersistenciaException{
		 EnderecoDTO enderecoDTO = null;
		try {
			Connection connection =  ConexaoUtil.getInstance().getConnection();
			String sql  = "select * from TB_ENDERECO where ID_ENDERECO = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, idEndereco);
			ResultSet resultado = statement.executeQuery();
			if(resultado.next()) {
				enderecoDTO = new EnderecoDTO();
				enderecoDTO.setId_endereco(resultado.getInt(1));
				enderecoDTO.setLogradouro(resultado.getString(2));
				enderecoDTO.setBairro(resultado.getString(3));
				enderecoDTO.setCidade(resultado.getString(4));
				enderecoDTO.setNumero(resultado.getLong(5));
				enderecoDTO.setCep(resultado.getInt(6));
				enderecoDTO.setUfDTO(buscaUfPorId(resultado.getInt(7)));
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),e);
		}
		return enderecoDTO;
	}
	
	private  UfDTO buscaUfPorId(Integer idUf) throws PersistenciaException{
		UfDTO ufDTO = null;
		try {
			Connection connection =  ConexaoUtil.getInstance().getConnection();
			String sql  = "select * from TB_UF where ID_UF = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, idUf);
			ResultSet resultado = statement.executeQuery();
			
			if(resultado.next()) {
				ufDTO= new UfDTO();
				ufDTO.setIdUF(resultado.getInt(1));
				ufDTO.setSiglaUf(resultado.getString(2));
				ufDTO.setDescricao(resultado.getString(3));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),e);
		}
		return ufDTO;
	}
	
	
	
	
	
	
	public List<PessoaDTO> filtraPessoa(String nome , Long cpf , String sexo, String order ) throws PersistenciaException{
		List<PessoaDTO> lista = new  ArrayList<PessoaDTO>();
		
		try{
			Connection connection = ConexaoUtil.getInstance().getConnection();
			String sql ="SELECT * FROM  TB_PESSOA ";
			boolean ultimo = false;
			if (nome != null && !nome.equals("")){
				sql +=" WHERE NOME LIKE ? ";
				ultimo = true;
			}
	     	if(cpf != null && !cpf.equals("")){
	     		if(ultimo){
				sql +=" AND ";
	     		}else{
	     		sql +=" WHERE ";	
	     		 ultimo = true;
	     		}
	     		sql +=" CPF LIKE ? ";
			} 
	     	if(sexo != null && !sexo.equals("")){
	     		if(ultimo){
				sql +=" AND ";
	     		}else{
	     		sql +=" WHERE ";	
	     		}
	     		sql +=" SEXO = ?";
			} 
	     	sql+= "ORDER  BY "+order;
			PreparedStatement statement = connection.prepareStatement(sql);
			int cont = 0;
			if(nome != null && !nome.equals("")){
				statement.setString(++cont,"%"+ nome+"%");
			}
			if(cpf != null && !cpf.equals("")){
				statement.setString(++cont, "%"+cpf+"%");
			} 
			if(sexo != null && !sexo.equals("")){
				statement.setString(++cont, sexo);
			} 
			 
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				PessoaDTO pessoaDTO = new PessoaDTO();
				pessoaDTO.setIdPessoa(resultSet.getInt("ID_PESSOA"));
				pessoaDTO.setNome(resultSet.getString("NOME"));
				pessoaDTO.setCpf(resultSet.getLong("CPF"));
				pessoaDTO.setSexo(resultSet.getString("SEXO").charAt(0));
				pessoaDTO.setDtNascimento(resultSet.getDate("DT_NASC"));
				lista.add(pessoaDTO);

			}

		}catch(Exception e){
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),e);
		}
		return lista;
	}
	

	
}
