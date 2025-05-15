
package Ejercicico10;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraDeMedias {
	public static void main(String[] args) {
		//List<Socket> clientes = new ArrayList<Socket>();
		try {
			int conexiones=0;
			ServerSocket server = new ServerSocket(12345);
			Datos datos = new Datos(server);
			Socket s=null;

			while(conexiones<5){
				s=server.accept();
				Thread hilo = new Thread(new HiloCalculadora(s,datos));
				hilo.start();					
				conexiones++;
			}
			System.out.println("salio");
			
//			datos.enviarFIN(clientes, "FIN");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
