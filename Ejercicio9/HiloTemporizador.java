package Ejercicio9;

public class HiloTemporizador implements Runnable {
	
	private Datos datos;
	public HiloTemporizador(Datos datos) {
		this.datos=datos;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Temporizador finalizado");
		try {
			datos.setTerminado(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
