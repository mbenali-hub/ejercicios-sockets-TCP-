package Ejercicio8;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class HiloRadar implements Runnable {

	private BufferedReader br;
	private BufferedWriter bw;
	private String x;
	private String y;
	private String id;
	private Datos datos;
	private SecretKey claveSecreta;
	private Cipher cifrador;
	private Cipher desCifrador;
	public HiloRadar(String x,String y,String id,Datos datos) {
		this.x=x;
		this.y=y;
		this.id=id;
		this.datos=datos;
		claveSecreta=datos.getClave();
		cifrador=crearCifrador(1);
		desCifrador=crearCifrador(2);
	}
	@Override
	public void run() {
		try {
			
			Socket s = new Socket("localhost", 2500);
			iniciarCanales(s);
			boolean coordenadasEnviadas=false;
			
			while(!coordenadasEnviadas) {
				coordenadasEnviadas=datos.enviarMensaje(Float.parseFloat(x),Float.parseFloat(y),id);				
			}
			System.out.println("Se han enviado dos coordenadas a la calculadora.");
			
			
			enviarMensaje(x);
			enviarMensaje(y);
			enviarMensaje(id);
			
			
			while(true) {
				//enviarMensaje("MARCO");
				bw.write("MARCO");
				bw.newLine();
				bw.flush();
				coordenadasEnviadas=false;
				
				String distancia = br.readLine();	
				while(!coordenadasEnviadas) {
					
					coordenadasEnviadas=datos.enviarDistancia(Float.parseFloat(distancia),id);														
				}
				System.out.println("La distancia al barco desde el radar "+id+" es "+distancia);
				Thread.sleep(2000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void enviarMensaje(String mensaje) {
		try {
			bw.write(cifrarMensaje(mensaje));
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void iniciarCanales(Socket s) {
		try {
			br= new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw= new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private Cipher crearCifrador(int modo) {
		Cipher cifrador=null;
		
		try {
			cifrador=Cipher.getInstance(claveSecreta.getAlgorithm());
			if(modo==1) {
				cifrador.init(Cipher.ENCRYPT_MODE, claveSecreta);				
			}
			else {
				cifrador.init(Cipher.DECRYPT_MODE, claveSecreta);	
			}
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cifrador;
	}
	
	private String cifrarMensaje(String mensaje) {
		byte[] mensajeCifrado=null;
		try {
			mensajeCifrado=cifrador.doFinal(mensaje.getBytes());
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(mensajeCifrado);
	}
}
