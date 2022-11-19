package br.edu.devmedia.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import br.edu.devmedia.jdbc.ConexaoUtil;
import br.edu.devmedia.jdbc.dto.LoginDTO;
import br.edu.devmedia.jdbc.dto.PessoaDTO;
import br.edu.devmedia.jdbc.exeption.PersistenciaException;

public class LoginDAO  implements GenericDAO<LoginDTO>{
	
	public boolean logar(LoginDTO loginDTO){
		// TODO Auto-generated method stub
	 boolean resultado = false;
				try {
					Connection connection = ConexaoUtil.getInstance().getConnection();
					String sql ="SELECT * FROM TB_LOGIN WHERE NOME=? AND SENHA = ?";
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, loginDTO.getNome());
					statement.setString(2,loginDTO.getSenha());
					ResultSet resultSet = statement.executeQuery();
					resultado = resultSet.next();
					connection.close();
				} catch (Exception e) {
				
				}
				
		return resultado;
	}

	@Override
	public void inserirPesssoa(LoginDTO obj) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar(LoginDTO obj) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Integer id) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<LoginDTO> listarTodos() throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginDTO buscarPorId(Integer id) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
