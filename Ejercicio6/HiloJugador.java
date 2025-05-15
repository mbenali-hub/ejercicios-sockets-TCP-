package Ejercicio6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class HiloJugador implements Runnable {

	private DatagramPacket paquete;
	private DatagramSocket socket;
	private String id;
	private int x;
	private int y;
	private Datos datos;
	public HiloJugador(DatagramPacket paquete, Datos datos,DatagramSocket socket,String id) {
		this.paquete=paquete;
		this.datos=datos;
		this.id=id;
		this.socket=socket;
	}
	@Override
	public void run() {
		String posicion = new String(paquete.getData());
		if(!posicion.startsWith("PERMISO")) {
			String[] posiciones= posicion.trim().split("-");
			if(posiciones.length>=2) {
				x=Integer.parseInt(posiciones[0]);
				y=Integer.parseInt(posiciones[1]);				
			}			
			datos.moverJugador(x,y,id);
			int distancia= datos.getDistancia(x, y);
			enviarMensjae(distancia);
			//datos.enviarTableroATodos(socket);
		}
						
	}
	
	private void enviarMensjae(int distancia) {
		String s = String.valueOf(distancia);
		byte[] mensajeRespuesta= s.getBytes();
		
		DatagramPacket p = new DatagramPacket(mensajeRespuesta, mensajeRespuesta.length,paquete.getAddress(), paquete.getPort());
		try {
			socket.send(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
