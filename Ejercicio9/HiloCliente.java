package Ejercicio9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HiloCliente implements Runnable {

	
	private Socket s;
	private BufferedReader br;
	private BufferedWriter bw;
	private Datos datos;
	public HiloCliente(Socket s,Datos datos) {
		this.s=s;
		this.datos=datos;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		String peticion;
		try {
			peticion= br.readLine();
			datos.anhadirPeticiones(peticion,s);			
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
	}
	
}


