package com.bll;


public class ManagerFactory {

	/** l'unique instance de cette classe **/
	private static ManagerFactory staticInstance = null;

	
	protected ManagerFactory() {
		/** interdir l'instantiation volentairement**/
	}

	
	public static ManagerFactory getInstance() {

		if (staticInstance == null) {
			staticInstance = new ManagerFactory();
		}
		return staticInstance;
	}

	
	public Manager getManager(String pClasseName) {

		Manager manager = null;
		try {
			System.out.println(pClasseName);
			// On charge la classe par reflexion
			Class<?> c = Class.forName(pClasseName);
			// instantaion du DAO
			manager = (Manager) c.getConstructor().newInstance();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return manager;

	}

}
