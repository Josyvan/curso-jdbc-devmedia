package br.edu.devmedia.jdbc.dto;

public class EnderecoDTO {
	
 private Integer Id_endereco;
 private String logradouro;
 private String cidade;
 private String bairro;
 private Long numero;
 private Integer cep;
 private UfDTO ufDTO;
 

public Integer getId_endereco() {
	return Id_endereco;
}
public void setId_endereco(Integer id_endereco) {
	Id_endereco = id_endereco;
}
public String getLogradouro() {
	return logradouro;
}
public void setLogradouro(String logradouro) {
	this.logradouro = logradouro;
}
public String getCidade() {
	return cidade;
}
public void setCidade(String cidade) {
	this.cidade = cidade;
}
public String getBairro() {
	return bairro;
}
public void setBairro(String bairro) {
	this.bairro = bairro;
}
public Long getNumero() {
	return numero;
}
public void setNumero(Long numero) {
	this.numero = numero;
}
public Integer getCep() {
	return cep;
}
public void setCep(Integer cep) {
	this.cep = cep;
}
public UfDTO getUfDTO() {
	return ufDTO;
}
public void setUfDTO(UfDTO ufDTO) {
	this.ufDTO = ufDTO;
}

}
