package Ejercicio11.Clientes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class Clientes {

	public static void main(String[] args) {
		try {
			DatagramSocket s = new DatagramSocket();
			
			enviarPaquete(s, 43242131);
			enviarPaquete(s, 87897875);
			
		
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void enviarPaquete(DatagramSocket s, long numero) {
		String n = String.valueOf(numero);
		byte[] buffer =n.getBytes();
		
		
		try {
			DatagramPacket p = new DatagramPacket(buffer, buffer.length,InetAddress.getByName("localhost"),12345);
			s.send(p);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
