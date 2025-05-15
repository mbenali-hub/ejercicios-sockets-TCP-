package Ejercicio6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FrioCaliente {

	public static void main(String[] args) {

		Random r = new Random();
		byte[] movimientos= new byte[10];
		Datos datos = new Datos(10);
		DatagramSocket socket=null;
		try {
			socket = new DatagramSocket(12345);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			
			try {
								
				DatagramPacket paquete = new DatagramPacket(movimientos, movimientos.length);
				socket.receive(paquete);
				String mensaje= new String(movimientos);
				int puerto = paquete.getPort();	
				if(mensaje.startsWith("PERMISO")) {	
					String id= extraerId(mensaje);
					String dir = paquete.getAddress().getHostAddress();				
					datos.actualizarClientes(dir, puerto);
					datos.actaulizarIdClientes(id, puerto);
					enviarMensaje(InetAddress.getByName(dir), puerto, "OK", socket);
					System.out.println("Un cliente conectado: "+dir+"/"+puerto);
					movimientos= new byte[10];
				}
				Thread hilo = new Thread(new HiloJugador(paquete,datos,socket,datos.getiD(puerto)));
				hilo.start();
			} catch (SocketException | UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static String extraerId(String mensaje) {
		String[] ids = mensaje.split("-");
		String id="";
		if(ids.length>=2) {
			id=ids[1];
		}
		return id;
	}
	
	private static void enviarMensaje(InetAddress dir, int puerto,String mensaje,DatagramSocket socket) {
		byte[] mensajeBytes = mensaje.getBytes();
		DatagramPacket packet = new DatagramPacket(mensajeBytes, mensajeBytes.length, dir, puerto);

		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
