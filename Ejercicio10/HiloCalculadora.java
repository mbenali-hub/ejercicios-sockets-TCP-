package Ejercicico10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HiloCalculadora implements Runnable {

	private Datos datos;
	private Socket s;
	public HiloCalculadora(Socket s,Datos datos) {
		this.datos=datos;
		this.s=s;
		datos.anhadirCLiente(s);
	}
	@Override
	public void run() {
		BufferedReader br;
		BufferedWriter bw;
		boolean haGanado=false;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			
			while(!haGanado) {
				String numeroRecibido=br.readLine();
				System.out.println("Numero recibido :"+ numeroRecibido);
				datos.anhadirNumero(numeroRecibido);
				Thread.sleep(5000);
				int numero= Integer.parseInt(numeroRecibido);
				int media=datos.calcularMedia();
				
				if(media-numero==1) {
					haGanado=true;
					System.out.println("Dierencia era: "+(media-numero));
					datos.enviarFIN("FIN");
					datos.cerrarServidor();
				}
				else {
					System.out.println("Dierencia: "+(media-numero));
					datos.enviarResultado(s,media);				
				}				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
