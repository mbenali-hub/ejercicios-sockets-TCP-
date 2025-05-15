package Ejercicio6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		boolean haGanado=false;
		DatagramSocket socket=null;
		InetAddress serverAddress=null;
		try {
			socket = new DatagramSocket();
			serverAddress = InetAddress.getByName("localhost");
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		int serverPort = 12345;
		enviarMensaje(serverAddress, serverPort, "PERMISO-1",socket);
		String respuesta = recibirConfirmacion(socket);
		if(respuesta.startsWith("OK")) {			
			while(!haGanado) {								
				try {
					Scanner sc= new Scanner(System.in);
					String mensaje = sc.nextLine();
					enviarMensaje(serverAddress, serverPort, mensaje,socket);
					
					byte[] b = new byte[1];
					DatagramPacket p = new DatagramPacket(b, b.length);
					socket.receive(p);
					String distancia= new String(b);
					System.out.println("El premio se encuentra a "+distancia+" de distancia");
					
					if(distancia.equals("0")) {
						haGanado=true;
						System.out.println("Has ganado el premio. Terminando juego...");
					}
//					for (int i = 0; i <10; i++) {
//						for (int j = 0; j < 10; j++) {
//							socket.receive(p);
//							System.out.print(new String(b));
//						}
//						System.out.println();
//					}
					
					
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
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
	
	private static String recibirConfirmacion(DatagramSocket socket) {
		byte[] b = new byte[2];
		DatagramPacket p = new DatagramPacket(b, b.length);
		try {
			socket.receive(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new String(b);
	}

}
