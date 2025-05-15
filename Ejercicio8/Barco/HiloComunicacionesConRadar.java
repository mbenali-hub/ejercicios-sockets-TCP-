package Ejercicio8.Barco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javax.crypto.spec.SecretKeySpec;

public class HiloComunicacionesConRadar implements Runnable {
	
	
	private Socket s;
	private BufferedReader br;
	private BufferedWriter bw;
	private String idRadar;
	private float x;
	private float y;
	private float posicionXRadar;
	private float posicionYRadar;
	private PosiconesBarco posiconesBarco;
	private SecretKey claveSecreta;
	private Cipher cifrador;
	private Cipher desCifrador;
	public HiloComunicacionesConRadar(Socket s,PosiconesBarco posiconesBarco) {
		this.s=s;
		claveSecreta=getClave();
		cifrador=crearCifrador(1);
		desCifrador=crearCifrador(2);
		this.posiconesBarco=posiconesBarco;
		
		
	}
	@Override
	public void run() {
		iniciarCanales();
		try {
			String x =br.readLine();
			String y = br.readLine();
			String id= br.readLine();
			posicionXRadar= Float.parseFloat(new String(descifrarMensaje(x)));
			posicionYRadar = Float.parseFloat(new String(descifrarMensaje(y)));
			idRadar= new String(descifrarMensaje(id));
			System.out.println(posicionXRadar+"/"+posicionYRadar);
			String mensaje;
			System.out.println("Coordenadas del radar "+idRadar+": "+posicionXRadar+"-"+posicionYRadar+". Poscion actual del barco "+posiconesBarco.getX()+"-"+posiconesBarco.getY());
			
				while(true) {
					mensaje=br.readLine();
					if(mensaje.equals("MARCO")) {
						double distancia=calcularDistanciaARadar();
						bw.write(String.valueOf(distancia));
						bw.newLine();
						bw.flush();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void iniciarCanales() {
		try {
			br= new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw= new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private double calcularDistanciaARadar() {
		double distancia=0;
		x=posiconesBarco.getX();
		y=posiconesBarco.getY();
		
		distancia= Math.pow(x-posicionXRadar,2)+Math.pow(y-posicionYRadar,2);
		
		return distancia;
	}
	
	public SecretKey getClave() {
		SecretKeySpec sps=null;
		try {
			FileReader fr = new FileReader("SC.txt");
			BufferedReader br = new BufferedReader(fr);
			String claveEnBase64 = br.readLine();
			
			byte[] claveEnBytes = Base64.getDecoder().decode(claveEnBase64);
			sps = new SecretKeySpec(claveEnBytes, "AES");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sps;
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
	
	private byte[] cifrarMensaje(String mensaje) {
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
		return mensajeCifrado;
	}
	
	private byte[] descifrarMensaje(String mensaje) {
	    byte[] mensajeDescifrado = null;
	    try {
	        byte[] mensajeDecodificado = Base64.getDecoder().decode(mensaje);
	        mensajeDescifrado = desCifrador.doFinal(mensajeDecodificado);
	    } catch (IllegalBlockSizeException | BadPaddingException e) {
	        e.printStackTrace();
	    }
	    return mensajeDescifrado;
	}
	
}
