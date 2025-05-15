package Ejercicio7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FrioCalienteAutomatico {

	private static InetAddress dir;
	private static int puerto;
	private static final int XPREMIO=4;
	private static final int YPREMIO=4;
	private static DatagramSocket socket;
	private static int lado;
	private static char[][] tablero;
	public static void main(String[] args) {
		Map<Integer,InetAddress> clientesConectados = new HashMap<Integer,InetAddress>();
		Scanner sc = new Scanner(System.in);
		lado = sc.nextInt();
		tablero = new char[lado][lado];
		rellenarTablero();
		
		
	
		try {
			socket = new DatagramSocket(12345);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		DatagramPacket paquete=null;
		while (true) {
			byte[] mensajeRecibido = recibirPaquete();
			String mensajeEnPlano = new String(mensajeRecibido);
			System.out.println(mensajeEnPlano);
			System.out.println(puerto+"/"+dir);
			
			
//			if(!clientesConectados.containsKey(puerto)) {
//				clientesConectados.put(puerto, dir);				
//			}
			
			if(!mensajeEnPlano.startsWith("HOLA")) {
				String id = getID(mensajeEnPlano);
				int distancia=comprobarDistancia(mensajeEnPlano,id);
				mostrarTablero();
				String mensajeRespuesta= String.valueOf(distancia);
				enviarRespuesta(mensajeRespuesta);								
			}	
		}
	}
	
	private static void marcarEnTableo(int x, int y,String id) {
		char marca = id.toCharArray()[0];
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if(i==x&&j==y) {
					tablero[i][j]= marca;
				}
				else if(tablero[i][j]==marca) {
					tablero[i][j]='-';
				}
			}
		}
	}
	private static String getID(String mensaje) {
		String id="";
		
		String[] partes = mensaje.split("/");
		if(partes.length>=2) {
			id=partes[1];
		}
		return id;
	}
	private static void enviarRespuesta(String mensajeRespuesta) {
		byte[] respuesta = mensajeRespuesta.getBytes();
		DatagramPacket p = new DatagramPacket(respuesta, respuesta.length, dir, puerto);
		try {
			socket.send(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static int comprobarDistancia(String mensaje,String id) {
		String[] partes = mensaje.trim().split("/");
		int distancia=0;
		if(partes.length>=2) {
			String[] posiciones = partes[0].split("-");
			int x=0;
			int y=0;
			
			if(posiciones.length>=2) {
				x= Integer.parseInt(posiciones[0]);
				y= Integer.parseInt(posiciones[1]);
			}
			
			if(x<=lado||y<=lado) {
				distancia= (int) Math.sqrt(Math.pow((x-XPREMIO),2)+Math.pow((y-YPREMIO),2));			
			}
			marcarEnTableo(x, y, id);			
		}
		return distancia;
		
	}
	
	private static byte[] recibirPaquete() {
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
	private static void rellenarTablero() {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				tablero[i][j]='-';									
			}
		}
	}
	
	private static void mostrarTablero() {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]);
			}
			System.out.println();
		}
	}

}
