package Ejercicio7.Clientes;

import java.net.DatagramSocket;
import java.util.Random;

public class HiloJugador implements Runnable {

	private DatagramSocket socket;
	private Datos datos;
	private boolean haGanado;
	private int id;
	private Random r = new Random();
	public HiloJugador(DatagramSocket socket, Datos datos,int id) {
		this.socket=socket;
		this.datos=datos;
		haGanado=false;
		this.id=id;
	}
	@Override
	public void run() {
		datos.enviarMensajeAServidor("HOLA SOY");
		
		while(!haGanado) {
			dormirHilo();
			String jugada=crearJugada();
			datos.enviarMensajeAServidor(jugada+"/"+id);
			
			byte[] respuestaRecibida=datos.recibirPaquete();
			String respuesta= new String(respuestaRecibida);
			int distancia = Integer.parseInt(respuesta);
			
			if(distancia==0) {
				System.out.println("La distancia es "+distancia+", por lo el jugador "+id+" ha ganado.");
				haGanado=true;
			}
			else {
				System.out.println("La distancia es "+distancia);
			}
		}
		System.out.println("Cerrando conexion");
	}
	private void dormirHilo() {
		try {
			Thread.sleep(r.nextInt(500, 1001));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String crearJugada() {
		int x = r.nextInt(0, 11);
		int y = r.nextInt(0,11);
		
		return new String(String.valueOf(x)+"-"+String.valueOf(y));
	}
}
