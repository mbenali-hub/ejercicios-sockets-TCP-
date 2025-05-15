package Ejercicio9.Cliente;

public class HiloTemporizador implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(10000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Temporizador finalizado");
	}

}
