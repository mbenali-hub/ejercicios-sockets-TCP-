package Ejercicio8.Barco;

public class PosiconesBarco {
	private float x;
	private float y;
	
	
	public PosiconesBarco(Float x,Float y) {
		this.x=x;
		this.y=y;
	}
	
	public synchronized Float getY() {
		return y;
	}
	
	public synchronized Float getX(){
		return x;
	}
	
	public synchronized void moverBarco() {
		x+=10;
		y+=x;
	}
}
