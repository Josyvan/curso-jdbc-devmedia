package br.edu.devmedia.jdbc.exeption;

public class PersistenciaException extends Exception{

	private static final long serialVersionUID = 4296622378297578858L;
	
	public PersistenciaException(String msg, Exception exception){
		 super(msg,exception);	
		}
	public PersistenciaException(String msg){
	 super(msg);	
	}

}
