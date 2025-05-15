package Ejercicio9.Cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HiloAmigo implements Runnable {

	//String[] nombres = {"M","J","D","Q","I","W"};
	
	private int index;
	private Socket s;
	private Thread hiloT;
	private Datos datos;
	public HiloAmigo(Datos datos,Socket s) {
		this.datos=datos;
		this.s=s;

	}
	@Override
	public void run() {
		Random r = new Random(); 
		boolean enviado=false;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			int tiempo=r.nextInt(5, 10);
			String amigo=datos.getNombre();
			while(!enviado) {
				try {
					Thread.sleep(tiempo*10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bw.write(amigo);
				bw.newLine();
				bw.flush();	
				enviado=true;								
			}
			
			String amigoRecibido=br.readLine();
			
			System.out.println(amigo+"/"+amigoRecibido);
			s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
