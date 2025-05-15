package Ejercicio11;

public class HiloSumaCifras implements Runnable {

	private String cifras;
	private Datos datos;
	private int id;
	public HiloSumaCifras(String cifras,Datos datos,int id) {
		this.cifras=cifras;
		this.datos=datos;
		this.id=id;
	}
	@Override
	public void run() {
		 
	    if(!cifras.equals("")) {
	    	String[] cifrasSeparadas = cifras.split("\\+");
	    	
	    	int numero1= Integer.parseInt(cifrasSeparadas[0]);
	    	int numero2= Integer.parseInt(cifrasSeparadas[1]);
	    	int suma = numero1+numero2;
	    	
	    	datos.anhadirSuma(suma, id);
	    }
	}

}
