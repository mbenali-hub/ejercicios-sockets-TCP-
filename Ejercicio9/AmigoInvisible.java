package Ejercicio9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AmigoInvisible {

	public static void main(String[] args) throws InterruptedException, ServidorCerradoException {
		
		Socket socket=null;
		ServerSocket sserver=null;
		List<Thread> hilos = new ArrayList<Thread>();

		try {
			sserver = new ServerSocket(12345);
			Datos datos= new Datos(sserver);
			Thread hiloT = new Thread(new HiloTemporizador(datos));
			

	        socket=sserver.accept();
	        System.out.println("Conexion recibida");
	        Thread hiloCliente = new Thread(new HiloCliente(socket,datos));
	        hiloCliente.start();
			hilos.add(hiloCliente);
	        hiloT.start();
			while(!datos.getTerninado()) {
				
				try {
					socket=sserver.accept();						
					System.out.println("Conexion recibida");
					hiloCliente = new Thread(new HiloCliente(socket,datos));
					hiloCliente.start();
					hilos.add(hiloCliente);
	
				} catch (IOException e) {

				}
			}
			for (Thread thread : hilos) {
				thread.join();				
			}
			datos.enviarAmigos();
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
