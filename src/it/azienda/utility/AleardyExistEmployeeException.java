package it.azienda.utility;

public class AleardyExistEmployeeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//metodo costruttore 
	public AleardyExistEmployeeException(String employee) { //Quando viene istanziato lancia l'ecezione
		super("Dipendente con nome "+employee+" già esistende");
	}
	
}
