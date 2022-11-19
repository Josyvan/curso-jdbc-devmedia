package br.edu.devmedia.jdbc.exeption;

public class ValidaException extends Exception{

	private static final long serialVersionUID = 1L;

	 public ValidaException(String msg,Exception exception){
		  super(msg,exception);
	  }
	  public ValidaException(String msg){
		 super(msg); 
	  }
	
   
}
