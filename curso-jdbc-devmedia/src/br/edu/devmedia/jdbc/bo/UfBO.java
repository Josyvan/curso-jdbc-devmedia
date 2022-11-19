package br.edu.devmedia.jdbc.bo;

import java.util.List;

import br.edu.devmedia.jdbc.dao.UfDAO;
import br.edu.devmedia.jdbc.dto.UfDTO;
import br.edu.devmedia.jdbc.exeption.NegocioExeption;

public class UfBO {
	
	public List <UfDTO> listaUfs() throws NegocioExeption{
		List<UfDTO> lista  = null;
		try {
			UfDAO ufDAO =  new UfDAO();
			lista = ufDAO.listaEstados();
			
		} catch (Exception e) {
			throw new NegocioExeption(e.getMessage());
		}
		return lista;
	}

}
