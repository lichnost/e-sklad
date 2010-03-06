package org.stablylab.webui.client.exception;

public class AppException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 945701263189227830L;

	public AppException(){
		super();
	}
	
	public AppException(String message){
		super(message);
	}
}
