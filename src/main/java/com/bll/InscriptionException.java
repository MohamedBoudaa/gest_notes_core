package com.bll;

public class InscriptionException  extends  Exception {
	
	
	public static final int CNE_EXISTS = 0;
	
	private int type;
	
	public InscriptionException(String message, int pType) {
		super(message);
		type = pType;
	}


	public InscriptionException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InscriptionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InscriptionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InscriptionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InscriptionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
