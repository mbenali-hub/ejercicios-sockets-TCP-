package Ejercicio8.Barco;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;



public class Barco {

	public static void main(String[] args) {
		int posicionesIniciales=0;
		Socket s=null;
		BufferedReader br;
		PosiconesBarco pB= new PosiconesBarco(4f,8f);
		Thread hiloControladorBarco = new Thread(new HiloMoverBarco(pB));
		hiloControladorBarco.start();
		try {
			ServerSocket server= new ServerSocket(2500);
			while(posicionesIniciales<3) {
				s=server.accept();

				Thread hilo = new Thread(new HiloComunicacionesConRadar(s,pB));
				hilo.start();
				posicionesIniciales++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
