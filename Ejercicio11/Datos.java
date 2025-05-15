package Ejercicio11;

import java.util.ArrayList;
import java.util.List;

public class Datos {
	
	private String numero1;
	private String numero2;
	private int numerosRepartidos;
	private boolean terminado;
	private List<Integer> sumas = new ArrayList<Integer>();
	private StringBuffer sumaTotal;
	private String resto;
	public Datos(String numero1, String numero2) {
		this.numero1=numero1;
		this.numero2=numero2;
		numerosRepartidos=1;
		terminado=false;
		sumaTotal = new StringBuffer();
		resto="0";
	}
	

	public synchronized String getCifra() {
	    String cifras = "";

	    if (Math.max(numero1.length(), numero2.length()) < numerosRepartidos) {
	        terminado = true;
	    } else {
	        if (numero1.length() >= numerosRepartidos && numero2.length() >= numerosRepartidos) {
	            cifras = numero1.charAt(numero1.length() - numerosRepartidos) + "+" + numero2.charAt(numero2.length() - numerosRepartidos);
	        } else {

	            if (numero1.length() < numerosRepartidos) {
	                cifras += "0+";
	            } else {
	                cifras += numero1.charAt(numero1.length() - numerosRepartidos) + "+";
	            }

	            if (numero2.length() < numerosRepartidos) {
	                cifras += "0";
	            } else {
	                cifras += numero2.charAt(numero2.length() - numerosRepartidos);
	            }
	        }
	    }

	    numerosRepartidos++;
	    System.out.println(cifras);
	   
	    return cifras;
	}
	

	
	public synchronized boolean terminado() {
		return terminado;
	}
	
	public synchronized void anhadirSuma(int suma,int id) {
		sumas.add(suma);
	}
	
	public synchronized String getSumaTotal() {
		
		int llevado=0;

		for (Integer suma : sumas) {
			suma+=llevado;
			String s =String.valueOf(suma);
			if(s.length()>=2) {
				llevado= Integer.parseInt(String.valueOf(s.charAt(0)));
				sumaTotal.append(s.substring(1));
			}
			else if(s.length()<=1) {
				sumaTotal.append(s.substring(0));
				llevado=0;
			}
		}
		return sumaTotal.reverse().toString();
	}
}
