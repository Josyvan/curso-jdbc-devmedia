package br.edu.devmedia.jdbc.bo;

import br.edu.devmedia.jdbc.dao.LoginDAO;
import br.edu.devmedia.jdbc.dto.LoginDTO;
import br.edu.devmedia.jdbc.exeption.NegocioExeption;

public class LoginBO {
	
public boolean logar(LoginDTO loginDTO) throws NegocioExeption{
	boolean resultado = false;
	
	try {
		if(loginDTO.getNome() == null || "".equals(loginDTO.getNome() )){
			throw new NegocioExeption("login obrigatorio!");
		} else if(loginDTO.getSenha()== null || "".equals(loginDTO.getSenha())){
			throw new NegocioExeption("senha obrigatoria!");
		}else{
			LoginDAO loginDAO = new LoginDAO();	
			resultado=loginDAO.logar(loginDTO);
		}
	} catch (Exception e) {
		throw new NegocioExeption(e.getMessage());
	}
	return resultado;
}
}
