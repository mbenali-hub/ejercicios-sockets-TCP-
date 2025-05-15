package Ejercicio7.Clientes;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Clientes {

	public static void main(String[] args) {
		DatagramSocket socket=null;
		try {
			 socket= new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread[] hilos = new Thread[5];
		Datos datos = new Datos(socket);
		for (int i = 0; i < hilos.length; i++) {
			hilos[i]= new Thread(new HiloJugador(socket,datos,i));
			hilos[i].start();
		}

	}

}
