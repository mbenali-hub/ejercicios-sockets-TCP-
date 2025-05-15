package Ejercicio8.CalculadoraPosicion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class CalculadoraDePosicion {

	private static float[] posRadar0;
	private static float[] posRadar1;
	private static float[] posRadar2;
	private static float[] posRadar1Tras;
	private static float[] posRadar2Tras;
	//En realidad, recibimos los cuadrados de las distancias
	private static float[] distancias;
	private static InetAddress direccionCliente;
	private static int puertoCliente;
	
	private static DatagramSocket socket;
	
	public static void main(String[] args) {
		try {
			System.out.println("Proceso arrancado");
			socket=new DatagramSocket(7500);
			//Primero el programa recibe seis floats, que son 
			//las posiciones x e y de los tres radares
			RecibirCoordenadasDeRadares();
			while(true) {
				//Ahora recibe tres floats, que son los
				//cuadrados de las distancias a los tres radares
				//(en el orden en el que fueron recibidos en primer lugar)
				RecibirDistancias();
				float[] posicionBarco=CalcularPosicionBarco();
				//Tras calcular la posición, se envía un array de
				//8 bytes, que contiene las coordenadas x e y del
				//barco en forma de dos floats.
				EnviarPosicionBarco(posicionBarco);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void RecibirCoordenadasDeRadares() {
		posRadar0=RecibirArrayDeFloat(2);
		posRadar1=RecibirArrayDeFloat(2);
		posRadar2=RecibirArrayDeFloat(2);
		//Primero hacemos una traslación para asumir que el primer radar está en el origen,
		//lo cual simplifica los cálculos
		posRadar1Tras=new float[] {posRadar1[0]-posRadar0[0],posRadar1[1]-posRadar0[1]};
		posRadar2Tras=new float[] {posRadar2[0]-posRadar0[0],posRadar2[1]-posRadar0[1]};
	}
	
	private static void RecibirDistancias() {
		distancias=RecibirArrayDeFloat(3);
	}
	
	
	private static float[] RecibirArrayDeFloat(int tamanho) {
		float[] array=new float[tamanho];
		byte[] buffer=new byte[4];
		DatagramPacket paquete = new DatagramPacket(buffer,0,buffer.length);
		try {
			for (int i=0;i<array.length;++i) {
				socket.receive(paquete);
				array[i]=ByteBuffer.wrap(buffer).getFloat();
				if (direccionCliente==null) {
					direccionCliente=paquete.getAddress();
					puertoCliente=paquete.getPort();
				}
				System.out.println(i+"/"+array[i]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
	
	private static float[] CalcularPosicionBarco() {
		//Definimos una serie de variables auxiliares que simplificarán los cálculos
		double e1 = (distancias[0]+Math.pow(posRadar1Tras[0],2)+Math.pow(posRadar1Tras[1],2)-distancias[1])/2;
		double e2 = (distancias[0]+Math.pow(posRadar2Tras[0],2)+Math.pow(posRadar2Tras[1],2)-distancias[2])/2;
		float denominador = posRadar1Tras[0]*posRadar2Tras[1]-posRadar2Tras[0]*posRadar1Tras[1];
		float x = (float)(e1*posRadar2Tras[1]-e2*posRadar1Tras[1])/denominador+posRadar0[0];
		float y = (float)(e2*posRadar1Tras[0]-e1*posRadar2Tras[0])/denominador+posRadar0[1];
		return new float[] {x,y};
	}
	
	private static void EnviarPosicionBarco(float[] posicion) {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putFloat(posicion[0]);
		bb.putFloat(posicion[1]);
		DatagramPacket paquete = new DatagramPacket(bb.array(),bb.array().length,direccionCliente,puertoCliente);
		try {
			socket.send(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}

