package Ejercicio8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Datos {
	
	private DatagramSocket socket;
	private int coordenadasEnviadas;
	private List<Float> distancias = new ArrayList<Float>();
	private int radaresQueHanEnviado;
	private int dEnviadas;
	public Datos(DatagramSocket socket) {
		this.socket=socket;
		coordenadasEnviadas=0;
		radaresQueHanEnviado=0;
		dEnviadas=0;
		SecretKey clave = crearClaveSimetrica();
		guardarClaveEnArchivo(clave);
	}
	
	public synchronized boolean enviarMensaje(Float x,Float y,String id) {	
		boolean enviado=false;
		int idRadar = Integer.parseInt(id);
		if(radaresQueHanEnviado==idRadar) {			
			try {
				
				byte[] buffer = crearBuffer(x);
				
				enviarPaquete(buffer);
				coordenadasEnviadas++;	
				
				buffer= crearBuffer(y);
				
				enviarPaquete(buffer);
				coordenadasEnviadas++;
				
				radaresQueHanEnviado++;
				enviado=true;
				notifyAll();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(coordenadasEnviadas>=6) {
				notifyAll();
			}
		}
		else {
			esperar();
		}
		return enviado;
	}

	private byte[] crearBuffer(Float x) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putFloat(x);
		byte[] buffer = bb.array();
		return buffer;
	}
	
	public synchronized boolean enviarDistancia(Float distancia,String id) {
		boolean enviado=false;
		int idRadar = Integer.parseInt(id);
		if(coordenadasEnviadas<6) {
			esperar();
		}
		
		if(dEnviadas>=3) {
			dEnviadas=0;
		}
		if(dEnviadas==idRadar) {
			ByteBuffer bb = ByteBuffer.allocate(4);
			bb.putFloat(distancia);
			byte[] buffer = bb.array();
			
			
			try {
				enviarPaquete(buffer);
				dEnviadas++;
				enviado=true;
				notifyAll();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}
		else {
			esperar();
		}
		return enviado;
	}

	private void esperar() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void enviarPaquete(byte[] buffer) throws UnknownHostException, IOException {
		DatagramPacket paquete;
		paquete = new DatagramPacket(buffer, buffer.length,InetAddress.getByName("localhost"),7500);
		socket.send(paquete);
	}
	
	private SecretKey crearClaveSimetrica() {
		KeyGenerator kg;
		SecretKey clave=null;
		try {
			kg = KeyGenerator.getInstance("AES");
			kg.init(256);
			clave= kg.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clave;
	}
	
	private void guardarClaveEnArchivo(SecretKey clave) {
		
		try {
			FileWriter f = new FileWriter("SC.txt");
			BufferedWriter bw = new BufferedWriter(f);
			bw.write(Base64.getEncoder().encodeToString(clave.getEncoded()));
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}
