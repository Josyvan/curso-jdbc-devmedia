package br.edu.devmedia.jdbc.dto;
import java.util.Date;

public class PessoaDTO {
	private Integer idPessoa;
	private String nome ;
	private Long cpf ;
	private Character sexo;
    private Date dtNascimento;
     private EnderecoDTO enderecoDTO;
     
    
	public EnderecoDTO getEnderecoDTO() {
		return enderecoDTO;
	}
	public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
		this.enderecoDTO = enderecoDTO;
	}
	public Integer getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	
	public Character getSexo() {
		return sexo;
	}
	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}
	public Date getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	
	
}
