package Ejercicio8.Barco;

public class HiloMoverBarco implements Runnable {
	
	private PosiconesBarco pB;
	public HiloMoverBarco(PosiconesBarco pB) {
		this.pB=pB;
	} 
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(10000);
				pB.moverBarco();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
