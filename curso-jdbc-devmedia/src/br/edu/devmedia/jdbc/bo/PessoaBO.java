package br.edu.devmedia.jdbc.bo;

import br.edu.devmedia.jdbc.dao.PessoaDAO;
import br.edu.devmedia.jdbc.dto.EnderecoDTO;
import br.edu.devmedia.jdbc.dto.PessoaDTO;
import br.edu.devmedia.jdbc.exeption.NegocioExeption;
import br.edu.devmedia.jdbc.exeption.ValidaException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PessoaBO {
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY"); 
	
	public void cadastrar(PessoaDTO pessoaDTO) throws NegocioExeption{
		try{
		PessoaDAO pessoaDAO = new PessoaDAO();	
		pessoaDAO.inserirPesssoa(pessoaDTO);
		
		
		}catch(Exception ex){
			throw new NegocioExeption(ex.getMessage());
		}
		
	}
	
	public String[][] listagem(List<Integer> idsPessaoas) throws NegocioExeption {
		int numCol  =  10;
		String [][] listaRetorno  = null;
		try{
		PessoaDAO pessoaDAO = new PessoaDAO();
		
		List <PessoaDTO> lista = pessoaDAO.listarTodos();
		listaRetorno = new String[lista.size()][numCol];
		for(int i  = 0; i < lista.size();i++){
			PessoaDTO pessoaDTO =lista.get(i);
			EnderecoDTO enderecoDTO = pessoaDTO.getEnderecoDTO();
			
			listaRetorno[i][0] = pessoaDTO.getIdPessoa().toString();
			
			idsPessaoas.add(pessoaDTO.getIdPessoa());
			
			listaRetorno[i][1]= pessoaDTO.getNome();
			listaRetorno[i][2] = pessoaDTO.getCpf().toString();
			listaRetorno[i][3] = pessoaDTO.getSexo() == 'M' ? "Masculino":"Femenino";
			listaRetorno[i][4] = dateFormat.format(pessoaDTO.getDtNascimento());
			listaRetorno[i][5] = enderecoDTO.getLogradouro();
			listaRetorno[i][6] = enderecoDTO.getCep().toString();
			listaRetorno[i][7] = enderecoDTO.getUfDTO().getDescricao();
			
			listaRetorno[i][8] ="Editar";
			listaRetorno[i][9] = "Deletar";
			
					
		}
		
		}catch(Exception e){
		 throw new NegocioExeption(e.getMessage());
		}
		return listaRetorno;
	}
	
	public boolean  validaNome(String nome)throws ValidaException{
		boolean ehValido = true;
		if(nome==null || nome.equals("")){
			ehValido = false;
			throw new  ValidaException("Campo nome eh obrigatorio");
		}else if(nome.length() > 30){
			throw new  ValidaException("Campo nome suporta no maximo 30 chars!");
		}
		return ehValido;
		
	}
	public boolean  validaCpf(String cpf)throws ValidaException{
		boolean ehValido = true;
		if(cpf==null || cpf.equals("")){
			ehValido = false;
			throw new  ValidaException("Campo CPF eh obrigatorio");
	/**	}else if(cpf.length() != 11){
			ehValido = false;
			throw new  ValidaException("Campo CPF deve ter  11 numeros!");
		}else{
			char[] digitos = cpf.toCharArray();
			for(char digito : digitos ){
				if(! Character.isDigit(digito)){
					throw new  ValidaException("Campo CPF é somente numerico");
				}
			}
			*/
		}
		return ehValido;
	}
	public boolean  validaEndereco(EnderecoDTO endereco)throws ValidaException{
		boolean ehValido = true;
		if(endereco.getLogradouro()==null || endereco.getLogradouro().equals("")){
			ehValido = false;
			throw new  ValidaException("Campo Logradouro eh obrigatorio");
		 }else if(endereco.getBairro()==null || endereco.getBairro().equals("")){
			 ehValido = false;
			 throw new  ValidaException("Campo bairro é obrigatorio! ");
		 }else if(endereco.getNumero()==null|| endereco.getNumero().equals("") ){
			 ehValido = false;
			 throw new  ValidaException("Campo Numero é obrigatorio!!"); 
		 }else if(endereco.getCep()==null|| endereco.getCep().equals("") ){
			 ehValido = false;
			 throw new  ValidaException("Campo CEP é obrigatorio!!"); 
		 }
		
		
		return ehValido;
		
	}
	public boolean  validaDtaNasc(String dataNasc)throws ValidaException{
		boolean ehValido = true;
		if(dataNasc==null || dataNasc.equals("")){
			ehValido = false;
			throw new  ValidaException("Campo dtNasc eh obrigatorio");
		}else {
			try {
				dateFormat.parse(dataNasc);
			} catch (ParseException e) {
				throw new  ValidaException("formato data invalido");
			}
			ehValido = false;
		}
		return ehValido;
		
	}
	public String[][] listaConsulta(String nome , Long cpf  ,char sexo, String order ) throws NegocioExeption {
		int numCol = 6;
		String [][] listaRetorno  = null;
		try{
		PessoaDAO pessoaDAO = new PessoaDAO();
		List <PessoaDTO> lista = pessoaDAO.filtraPessoa(nome, cpf,String.valueOf(sexo),order);
		listaRetorno = new String[lista.size()][numCol];
		for(int i  = 0; i < lista.size();i++){
			PessoaDTO pessoaDTO =lista.get(i);
			listaRetorno[i][0]= pessoaDTO.getIdPessoa().toString();
			listaRetorno[i][1]= pessoaDTO.getNome();
			listaRetorno[i][2] = pessoaDTO.getCpf().toString();
			listaRetorno[i][3] = pessoaDTO.getSexo() == 'M' ? "Masculino":"Femenino";
			listaRetorno[i][4] = dateFormat.format(pessoaDTO.getDtNascimento());
					
		}
		
		}catch(Exception e){
		 throw new NegocioExeption(e.getMessage());
		}
		return listaRetorno;
	}
	public void removerPessoas(Integer idsPessoa ,Integer idEndereco) throws NegocioExeption{
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.deletar(idsPessoa);
			pessoaDAO.deletarEndereco(idEndereco);
		} catch (Exception e) {
	
			 throw new NegocioExeption(e.getMessage());
		}
	}
	public void removerTudo() throws NegocioExeption{
		try {
			PessoaDAO pessoaDAO  = new PessoaDAO();
			pessoaDAO.deletarTudo();
		} catch (Exception e) {
			 throw new NegocioExeption(e.getMessage());
		}
	}
	public PessoaDTO buscaPorId (Integer idPesoa)throws NegocioExeption{
		PessoaDTO pessoaDTO = null;
		try {
		PessoaDAO pessoaDAO = new PessoaDAO();
		pessoaDTO = pessoaDAO.buscarPorId(idPesoa);
		
		} catch (Exception e) {
			// TODO: handle exception
			 throw new NegocioExeption(e.getMessage());
		}
		return pessoaDTO;
	}
	public void atualizarpessoa(PessoaDTO pessoaDTO)throws NegocioExeption{
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.atualizar(pessoaDTO);
		} catch (Exception e) {
			 throw new NegocioExeption(e.getMessage());
		}
	}

}
