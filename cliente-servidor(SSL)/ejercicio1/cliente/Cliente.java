package ejercicio1.cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class Cliente {
	private static BufferedReader br;
	private static BufferedWriter bw;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SSLSocket socket = crearSSLSocket();
		inicializarVariables(socket);
		mandarMensaje("Hola");
		System.out.println(leerMensaje());
	}
	private static void inicializarVariables(SSLSocket socket) {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static String leerMensaje() {
		String mensaje = null;
		try {
			mensaje = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensaje;
	}
	private static void mandarMensaje(String mensaje) {
		try {
			System.out.println(mensaje);
			bw.write(mensaje);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static SSLSocket crearSSLSocket() {
		SSLSocket socket = null;
		try {
			KeyStore almacen = KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream("almacenEjemplo4.pk12"), "123qwe".toCharArray());
			
			TrustManagerFactory tm = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tm.init(almacen);
			
			SSLContext contexto = SSLContext.getInstance("TLS");
			contexto.init(null, tm.getTrustManagers(), null);
			
			socket = (SSLSocket) contexto.getSocketFactory().createSocket(InetAddress.getLocalHost(), 1234);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return socket;
	}
}
