package Ejercicio6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Datos {
	
	private final char PREMIO='$';
	private final int XPREMIO=8;
	private final int YPREMIO=3;
	private char[][] tablero;
	private Map<String, Integer> clientesConectados = new HashMap<String, Integer>();
	private Map<String, Integer> idClientes= new HashMap<String, Integer>();
	public Datos(int lado) {
		tablero=new char[lado][lado];
		rellenarTablero(tablero);
	}
	
	public synchronized void moverJugador(int x,int y,String id) {
		char[] idC= id.toCharArray();
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if((i==x&&j==y)) {
					tablero[i][j]=idC[0];
				}
				
			}
		}
	}
	
	public synchronized char[][] getTablero() {
		return tablero;
	}
	
	public synchronized int getDistancia(int x,int y) {
		int distancia=0;
		
		distancia= (int) Math.sqrt(Math.pow((x-XPREMIO),2)+Math.pow((y-YPREMIO),2));
		return distancia;
	}
	public synchronized Map<String, Integer> getClientesConectado(){
		return clientesConectados;
	}
	private void rellenarTablero(char[][] tablero) {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				tablero[i][j]='-';									
			}
		}
	}
	
	public synchronized void actualizarClientes(String dir,int puerto) {
		if(!clientesConectados.containsValue(puerto)) {
			clientesConectados.put(dir, puerto);
		}
	}
	
	public synchronized void actaulizarIdClientes(String id, int puerto) {
		if(!idClientes.containsValue(puerto)) {
			idClientes.put(id, puerto);
		}
	}
	
	public synchronized String getiD(int puerto) {
		String id="";
		for (Map.Entry<String, Integer> entry : idClientes.entrySet()) {
			String idCliente = entry.getKey();
			int p = entry.getValue();
			
			if(p==puerto) {
				id=idCliente;
			}
			
		}
		System.out.println("Se ha conseguido este id "+id);
		return id;
	}
	
	
	public synchronized void enviarTableroATodos(DatagramSocket socket) {
		for (Map.Entry<String, Integer> entry : clientesConectados.entrySet()) {
			String dir = entry.getKey();
			int puerto = entry.getValue();
			enviarTablero(dir,puerto,socket);
		}
	}
	private void enviarTablero(String dir,int puerto, DatagramSocket socket) {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j <tablero[i].length; j++) {
				try {
					String s = String.valueOf(tablero[i][j]);
					//System.out.println(s);
					byte[] mensajeRespuesta= s.getBytes();
					
					DatagramPacket p = new DatagramPacket(mensajeRespuesta, mensajeRespuesta.length,InetAddress.getByName(dir), puerto);
					socket.send(p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
