package Ejercicio9.Cliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Clientes {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Thread[] hilos = new Thread[49];
		
		Socket s=null;
		
		Datos datos = new Datos();
		for (int i = 0; i < hilos.length; i++) {
			s = new Socket("localhost", 12345);
			hilos[i] = new Thread(new HiloAmigo(datos,s));
			hilos[i].start();
		}

	}

}
