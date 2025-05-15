package Ejercicio7.Clientes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
	

public class Datos {
	private DatagramSocket socket;
	private InetAddress dir;
	private int puerto;
	
	public Datos(DatagramSocket socket) {
		this.socket=socket;
		try {
			dir= InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		puerto=12345;
	}
	
	
	public synchronized void enviarMensajeAServidor(String mensaje) {
		byte[] buffer = mensaje.getBytes();
		
		DatagramPacket p = new DatagramPacket(buffer, buffer.length,dir,puerto);
		try {
			socket.send(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized byte[] recibirPaquete() {
		byte[] mensaje = new byte[2048];
		DatagramPacket paquete = new DatagramPacket(mensaje, mensaje.length);
		try {
			socket.receive(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dir= paquete.getAddress();
		puerto= paquete.getPort();
		return limpiarBuffer(mensaje, paquete.getLength());
	}
	
	private static byte[] limpiarBuffer(byte[] buffer,int longitud) {
		return Arrays.copyOf(buffer, longitud);
	}
}
