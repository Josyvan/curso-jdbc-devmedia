package br.edu.devmedia.jdbc;

import java.sql.*;
import java.util.ResourceBundle;


public class ConexaoUtil {
	
	private static  ResourceBundle config;
	
	private static ConexaoUtil conexaoUtil;
	private ConexaoUtil(){
		config = ResourceBundle.getBundle("config");
	}
	
	public static ConexaoUtil getInstance(){
		if(conexaoUtil ==null){
			conexaoUtil = new ConexaoUtil();	
		}
		return conexaoUtil;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName(config.getString("br.edu.devmedia.bd.driver.mysql")) ;
		return DriverManager.getConnection(config.getString("br.edu.devmedia.bd.url.conexao"),config.getString("br.edu.devmedia.bd.usuario") ,config.getString("br.edu.devmedia.bd.senha"));
		
	}
	public static void main(String [] args){
		try {
		System.out.println(getInstance().getConnection());	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
