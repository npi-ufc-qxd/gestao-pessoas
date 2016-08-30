package br.ufc.quixada.npi.ge.exception;

public class GestaoEstagioException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public GestaoEstagioException(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}
}
