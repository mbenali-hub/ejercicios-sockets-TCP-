package Morse.Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HiloServidor implements Runnable {
	
	private Socket s;
	private String t;
	private BufferedReader br;

	public HiloServidor(Socket s) {
		this.s=s;	
	}
	
	
	@Override
	public void run() {
		Thread[] hilosTraductores = new Thread[4];
		StringBuilder texto = new StringBuilder();
		try {
			br= new BufferedReader(new InputStreamReader(s.getInputStream()));
			String linea;
			while((linea=br.readLine())!=null) {
				texto.append(linea).append("\n");				
				
			}
			
			
			int trozo = texto.length()/hilosTraductores.length;
			int limiteInferior=0;
			int limiteSuperior=0;
			for (int i = 0; i < hilosTraductores.length; i++) {
				if(i==0) {
					limiteInferior=0;
					limiteSuperior=trozo;
				}
				else {
					if(limiteInferior>=trozo) {
						limiteInferior=limiteSuperior;						
					}
					else {
						limiteInferior=trozo;						
					}
					limiteSuperior=limiteInferior+trozo;	
					
				}
				if(limiteSuperior>=texto.length()) {
					limiteSuperior=texto.length();
				}
				String[] parteTexto = new String[] {texto.substring(limiteInferior, limiteSuperior)};
				System.out.println("Partes del texto :"+i+" longitud: "+parteTexto.length);
				for (int j = 0; j < parteTexto.length; j++) {
					System.out.println(parteTexto[j]);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
