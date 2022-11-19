package br.edu.devmedia.jdbc.exeption;

public class NegocioExeption  extends Exception{
	
	private static final long serialVersionUID = 6233860588504289312L;

  public NegocioExeption(String msg,Exception exception){
	  super(msg,exception);
  }
  public NegocioExeption(String msg){
	 super(msg); 
  }
  
		
}
