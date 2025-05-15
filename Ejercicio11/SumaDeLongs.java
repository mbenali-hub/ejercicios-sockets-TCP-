package Ejercicio11;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class SumaDeLongs {

	public static void main(String[] args) {
		//RECIBIR DOS MENSAJES
		//CREAR UN HILO PARA SUMAR
		//SEUIR ESCUCHANDO CLIENTES
		try {
			DatagramSocket socket = new DatagramSocket(12345);
			
			while(true) {
				byte[] paquete1=recibirPaquete(socket);
				byte[] paquete2=recibirPaquete(socket);
				
				
				Thread hilo = new Thread(new HiloSuma(paquete1,paquete2));
				hilo.start();				
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static byte[] recibirPaquete(DatagramSocket s) {
		byte[] b = new byte[8];
		
		DatagramPacket p = new DatagramPacket(b, b.length);
		
		try {
			s.receive(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Arrays.copyOf(b,p.getLength());
	}
	
}
