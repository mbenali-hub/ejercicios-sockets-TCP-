package Morse.Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) {
		
		try {
			ServerSocket ss = new ServerSocket(25000);
			Socket s = ss.accept();
			
			Thread hilo = new Thread(new HiloServidor(s));
			hilo.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
