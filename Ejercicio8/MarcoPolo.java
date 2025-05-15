package Ejercicio8;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MarcoPolo {

	private static DatagramSocket socket;
	public static void main(String[] args) {
		try {
			socket= new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//arrancarProceso();
		Datos datos = new Datos(socket);
		Thread hilo1= new Thread(new HiloRadar("123","43","0",datos));
		Thread hilo2= new Thread(new HiloRadar("54","78","1",datos));
		Thread hilo3= new Thread(new HiloRadar("178","3","2",datos));
		hilo1.start();
		hilo2.start();
		hilo3.start();
		
		while(true) {
			byte[] buffer = new byte[8];
			DatagramPacket paquete;
			
			paquete= new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(paquete);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ByteBuffer bb = ByteBuffer.wrap(paquete.getData());
            float posicionX = bb.getFloat();
            float posicionY = bb.getFloat();

            System.out.println("Posición recibida: " + posicionX + "/" + posicionY);
		}

	}
	
	
	private static void arrancarProceso()  {
		ProcessBuilder pb = new ProcessBuilder("java","-cp","bin","Ejercicio8.CalculadoraPosicion.CalculadoraDePosicion");
		Process p;
		
		try {
			p=pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


