package Ejercicio9.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Datos {
	private List<String> nombres = new ArrayList<String>();
	private List<String> nombresUsados = new ArrayList<String>();
	private String[] listaNombres = {"Juan", "María", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Sofía", "Diego", "Elena",
	                            "Andrea", "Alejandro", "Marta", "David", "Paula", "Javier", "Lucía", "Miguel", "Rosa", "Francisco",
	                            "Carmen", "Daniel", "Isabel", "José", "Raquel", "Jorge", "Natalia", "Roberto", "Clara", "Antonio",
	                            "Patricia", "Fernando", "Eva", "Pablo", "Beatriz", "Manuel", "Victoria", "Adrián", "Silvia", "Ricardo",
	                            "Teresa", "Alberto", "Olga", "Gabriel", "Monica", "Emilio", "Cristina", "Mario", "Julia"};


	
	public Datos() {
		for (int i = 0; i < listaNombres.length; i++) {
			nombres.add(listaNombres[i]);
		}
		
	}
	public synchronized String getNombre() {
		Random r = new Random();
		String nombre=nombres.get(0);
		nombresUsados.add(nombre);
		nombres.remove(nombre);
		return nombre;
	}
	
	
}
