package br.edu.devmedia.jdbc.dao;

import java.util.List;

import br.edu.devmedia.jdbc.exeption.PersistenciaException;

public interface GenericDAO <T>{
	void inserirPesssoa(T obj) throws PersistenciaException;
	void atualizar(T obj)throws PersistenciaException;
	void deletar(Integer id)throws PersistenciaException;
	List<T> listarTodos()throws PersistenciaException;
	T buscarPorId(Integer id)throws PersistenciaException;
	

}
