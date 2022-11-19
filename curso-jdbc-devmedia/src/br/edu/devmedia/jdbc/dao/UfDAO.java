package br.edu.devmedia.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;







import br.edu.devmedia.jdbc.ConexaoUtil;
import br.edu.devmedia.jdbc.dto.UfDTO;
import br.edu.devmedia.jdbc.exeption.PersistenciaException;

public class UfDAO {
  
	public List  <UfDTO> listaEstados() throws PersistenciaException{
		List  <UfDTO> lista = new ArrayList<UfDTO>();
		try {
			Connection connection = ConexaoUtil.getInstance().getConnection();
			String sql ="select * from TB_UF";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultado = preparedStatement.executeQuery();
			while (resultado.next()) {
			 UfDTO ufDTO = new UfDTO();
			 ufDTO.setIdUF(resultado.getInt(1));
			 ufDTO.setSiglaUf(resultado.getString(2));
			 ufDTO.setDescricao(resultado.getString(3));
			 lista.add(ufDTO);
			 
			}
		 connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),e);
		}
		
		return lista;
	}
	
}
