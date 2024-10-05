package br.ucsal.docshare.util.exception;

public class RecordNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RecordNotFoundException(String message) {
        super(message);
    }
	
	// Construtor que aceita uma mensagem e uma causa (outra exceção que causou esta)
    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
