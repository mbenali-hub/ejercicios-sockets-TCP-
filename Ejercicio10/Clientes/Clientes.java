package Ejercicio10.Clientes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Clientes  {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int nHilos = sc.nextInt();
		
		Thread[] hilos = new Thread[nHilos];
		
		for (int i = 0; i < hilos.length; i++) {
			try {
				Socket s = new Socket("localhost", 12345);
				hilos[i] = new Thread(new HiloCliente(s));
				hilos[i].start();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		for (Thread hilo : hilos) {
//			try {
//				hilo.join();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		

		
	}
	

}
