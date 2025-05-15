package Ejercicio9;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Datos {

	private List<String> nombresRandom = new ArrayList<String>();
	private List<Socket> socketsPeticiones = new ArrayList<Socket>();
	private List<String> nombresDisponibles = new ArrayList<String>();
	private boolean terminado=false;
	private ServerSocket ss;

	public Datos(ServerSocket ss) {
		this.ss=ss;

	}
	
	public synchronized void anhadirPeticiones(String peticion,Socket s) {
		socketsPeticiones.add(s);
		nombresDisponibles.add(peticion);
	}
	public synchronized void anhadirPeticionDisponible(String peticion) {
		nombresDisponibles.add(peticion);
	}

	public synchronized void enviarAmigos() {

	
		removerLista();
		for (int i = 0; i < nombresRandom.size(); i++) {
			String peticionAmigo= nombresRandom.get(i);
			
			try {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socketsPeticiones.get(i).getOutputStream()));
				bw.write(peticionAmigo);
				bw.newLine();
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	private void removerLista() {
		for (int i = 0; i < nombresDisponibles.size(); i++) {

			
			if(i==nombresDisponibles.size()-1) {
				nombresRandom.add(nombresDisponibles.get(0));		
			}
			else {
				nombresRandom.add(nombresDisponibles.get(i+1));				
			}
		}		
	}
	
	
	public synchronized boolean getTerninado() {
		return terminado;	
	}
	
	public synchronized int getPeticiones() {
		return nombresDisponibles.size();
	}
	public synchronized void setTerminado(boolean fin) throws Exception {
		terminado=fin;
		try {
			ss.close();
		} catch (IOException e) {
			throw new ServidorCerradoException("Servidor cerrado");
		}
	}
}
