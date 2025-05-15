package Ejercicio10.Clientes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;

public class HiloCliente implements Runnable {
	
	private Socket s;
	public HiloCliente(Socket s) {
		this.s=s;
	}
	@Override
	public void run() {
		Random r = new Random();
		int numero=0;
		boolean noHaTerminado= false;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			numero=r.nextInt(0,101);
			String numeroS = String.valueOf(numero);
			bw.write(numeroS);
			bw.newLine();
			bw.flush();
			System.out.println("Enviado numero al servidor");

			while(!noHaTerminado) {
				String mediaRecibida=br.readLine();
				System.out.println("La media recibida es: "+mediaRecibida);
				
				if(!mediaRecibida.startsWith("FIN")) {
					int media= Integer.parseInt(mediaRecibida);
					int numeroNuevo=0;
					if(numero<media) {
						numeroNuevo= r.nextInt(numero, 101);
					}
					else if(numero>media) {
						numeroNuevo= r.nextInt(0, numero);
					}
					
					numeroS = String.valueOf(numeroNuevo);
					bw.write(numeroS);
					bw.newLine();
					bw.flush();					
				}
				else {
					noHaTerminado=true;
					System.out.println(mediaRecibida);
				}
			}

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
