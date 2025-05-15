import java.util.Base64.Encoder;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class GeneradorHash {
	public static void main(String[] args) {
		try {
			String cadena = pedirCadenaAlUsuario();
			escribirEnArchivo(cadena, "Original.txt");
			byte[] hash = generarHash(Files.readAllBytes(Paths.get("Original.txt")));
			escribirEnArchivo(codificarEnBase64(hash), "Hash.txt");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static String pedirCadenaAlUsuario() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe algo:");
		return sc.nextLine();
	}
	private static void escribirEnArchivo(String contenido, String ruta) throws IOException {
		BufferedWriter bw = Files.newBufferedWriter(Paths.get(ruta));
		bw.write(contenido);
		bw.close();
	}
	private static byte[] generarHash(byte[] origen) throws NoSuchAlgorithmException {
		byte[] hash = null;
		MessageDigest md = MessageDigest.getInstance("MD5");
		hash = md.digest(origen);
		return hash;
	}
	private static String codificarEnBase64(byte[] contenido) {
		Encoder codificador = Base64.getEncoder();
		return codificador.encodeToString(contenido);
	}
}
