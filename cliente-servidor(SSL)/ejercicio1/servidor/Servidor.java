package ejercicio1.servidor;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class Servidor {
	private static final char[] CONTRASENHA = "123qwe".toCharArray();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KeyStore almacen = cargarAlmacen("almacenEjemplo4.pk12");
		KeyManagerFactory kmf = crearKeyManagerFactory(almacen);
		SSLContext contexto = crearContexto(kmf);
		SSLServerSocket sss = crearSocketServidorSSL(contexto);
		SSLSocket socket;
		
		while(true) {
			try {
				socket = (SSLSocket) sss.accept();
				Thread hilo = new Thread(new Hilo(socket));
				hilo.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	private static KeyStore cargarAlmacen(String nombreAlmacen) {
		KeyStore almacen = null;
		try {
			almacen = KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream(nombreAlmacen), CONTRASENHA);
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return almacen;
	}
	private static KeyManagerFactory crearKeyManagerFactory(KeyStore almacen) {
		KeyManagerFactory kmf = null;
		try {
			kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(almacen, CONTRASENHA);
		} catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kmf;
	}
	private static SSLContext crearContexto(KeyManagerFactory kmf) {
		SSLContext contexto = null;
		try {
			contexto = SSLContext.getInstance("TLS");
			contexto.init(kmf.getKeyManagers(), null, null);
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contexto;
	}
	private static SSLServerSocket crearSocketServidorSSL(SSLContext contexto) {
		SSLServerSocket sss = null;
		try {
			sss = (SSLServerSocket) contexto.getServerSocketFactory().createServerSocket(1234);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sss;
	}
}
