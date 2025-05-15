import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class VerificadorDeHash {
	public static void main(String[] args) {
		try {
			byte[] contenidoSospechoso = cargarArchivoEnBytes("Original.txt");
			byte[] hashSospechoso = generarHash(contenidoSospechoso);
			String hashSospechosoString = codificarEnBase64(hashSospechoso);
			
			String hashOriginal = cargarArchivo("Hash.txt");
			
			if(hashOriginal.equals(hashSospechosoString)) {
				System.out.println("Okey");
			}
			else {
				System.out.println("Not okey");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static String cargarArchivo(String ruta) {
		String resultado="";
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(ruta));
			resultado=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;		
	}
	private static byte[] cargarArchivoEnBytes(String ruta) throws IOException {
		return Files.readAllBytes(Paths.get(ruta));
	}
	private static byte[] generarHash(byte[] origen) {
		byte[] resultado=null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultado = md.digest(origen);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	private static String codificarEnBase64(byte[] cotenido) {
		Encoder codificador = Base64.getEncoder();
		return codificador.encodeToString(cotenido);
	}
}
