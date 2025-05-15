package ejercicio5.cliente;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Cliente {
    private static DatagramSocket socket;
    public static void main(String[] args){
        inicializarVariables();
        KeyStore almacen = cargarAlmacen("almacenClavePublica.pk12", "123qwe");
		PublicKey clavePublica = cargarClavePublica(almacen);
		System.out.println(new String(clavePublica.getEncoded()));
		SecretKey clave = crearClave();
		mandarMensaje(encriptarClaveSecreta(clave, clavePublica));
		Cipher cifrador = crearCifrador(clave, 1);
		Cipher descifrador = crearCifrador(clave, 2);
		byte[] mensajeRecibido = leerMensaje();
		System.out.println(new String(desencriptarMensaje(mensajeRecibido, descifrador)));
		mandarMensaje(encriptarMensaje("Hola", cifrador));
    }
    private static void inicializarVariables(){
        try {
            socket = new DatagramSocket(1234);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }
    private static KeyStore cargarAlmacen(String nombre, String password) {
    	KeyStore almacen = null;
    	try {
        	almacen = KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream(nombre), password.toCharArray());
		} catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return almacen;
    }
    private static PublicKey cargarClavePublica(KeyStore almacen) {
		PublicKey clave = null;
		try {
			clave = almacen.getCertificate("Javi").getPublicKey();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clave;
	}
    private static SecretKey crearClave() {
    	SecretKey clave = null;
    	try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			clave = kg.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return clave;
    }
    private static byte[] encriptarClaveSecreta(SecretKey clave, PublicKey clavePublica) {
    	byte[] claveEnBytes = null;
    	try {
        	Cipher cifrador = Cipher.getInstance(clavePublica.getAlgorithm());
			cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
			claveEnBytes = cifrador.doFinal(clave.getEncoded());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return claveEnBytes;
    }
    private static Cipher crearCifrador(SecretKey clave, int opcion) {
		Cipher cifrador = null;
		try {
			cifrador = Cipher.getInstance(clave.getAlgorithm());
			if(opcion == 1) {
				cifrador.init(Cipher.ENCRYPT_MODE, clave);
			}
			else {
				cifrador.init(Cipher.DECRYPT_MODE, clave);
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cifrador;
	}
    private static byte[] encriptarMensaje(String mensaje, Cipher cifrador) {
		byte[] mensajeCifrado = null;
		try {
			mensajeCifrado = cifrador.doFinal(mensaje.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensajeCifrado;
	}
	private static byte[] desencriptarMensaje(byte[] mensajeEncriptado, Cipher descifrador) {
		byte[] mensajeDescifrado = null;
		try {
			mensajeDescifrado = descifrador.doFinal(mensajeEncriptado);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensajeDescifrado;
	}
    private static void mandarMensaje(byte[] mensaje){
        try {
            DatagramPacket paquete = new DatagramPacket(mensaje, mensaje.length, InetAddress.getLocalHost(), 4321);
            socket.send(paquete);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private static byte[] leerMensaje(){
        byte[] buffer = new byte[1024];
        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(paquete);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return limpiarBuffer(paquete.getData());
    }
    public static byte[] limpiarBuffer(byte[] buffer) {
        int newSize = 0;
        for (byte b : buffer) {
            if (b != 0) {
                buffer[newSize++] = b;
            }
        }
        return Arrays.copyOf(buffer, newSize);
    }
}
