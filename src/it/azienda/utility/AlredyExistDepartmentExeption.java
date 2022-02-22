package it.azienda.utility;

public class AlredyExistDepartmentExeption extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//metodo costruttore 
	public AlredyExistDepartmentExeption(String department) { //Quando viene istanziato lancia l'ecezione
		super("Dipartimento con nome "+department+" già esistende");
	}
	


}
