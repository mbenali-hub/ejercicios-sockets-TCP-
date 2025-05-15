package Ejercicio11;

import java.util.ArrayList;
import java.util.List;

public class HiloSuma implements Runnable {

	private byte[] p1;
	private byte[] p2;
	private List<Thread> hilos = new ArrayList<Thread>();
	public HiloSuma(byte[] p1,byte[] p2) {
		this.p1=p1;
		this.p2=p2;
	}
	@Override
	public void run() {
		
		Datos datos = new Datos(new String(p1),new String(p2));
		int id=0;
		while(!datos.terminado()) {			
			String cifras=datos.getCifra();
			if(!cifras.equals("")) {
				Thread hilo = new Thread(new HiloSumaCifras(cifras,datos,id));
				hilo.start();
				id++;
				hilos.add(hilo);				
			}
		}
		
		for (Thread h : hilos) {
			try {
				h.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(datos.getSumaTotal());
	}

}
