package Ejercicico10;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Datos {
	private List<Integer> numerosRecibidos = new ArrayList<Integer>();
	private List<Socket> clientes = new ArrayList<Socket>();
	private ServerSocket sserver;
	
	public Datos(ServerSocket s) {
		sserver=s;
	}
	public synchronized void anhadirNumero(String n) {
		int numero = Integer.parseInt(n);
		numerosRecibidos.add(numero);
//		int media=datos.calcularMedia();
//		datos.enviarResultado(clientes,media);
	}
	
	public synchronized void cerrarServidor() {
		try {
			sserver.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized int calcularMedia() {
		int media=0;
		int suma=0;
		for (Integer numero : numerosRecibidos) {
			suma+=numero;			
		}
		media=suma/numerosRecibidos.size();
		numerosRecibidos.clear();
		return media;
	}
	
	public synchronized void anhadirCLiente(Socket s) {
		if(!clientes.contains(s)) {
			clientes.add(s);
		}
	}
	
	public synchronized void enviarFIN(String mensaje) {
		for (Socket socket : clientes) {
			try {
				BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				

				bw.write(mensaje);
				bw.newLine();
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void enviarResultado(Socket cliente, int media) {
		try {
			BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
			
			String mediaS = String.valueOf(media);
			bw.write(mediaS);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
