package Morse.Cliente;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

final class Cliente {

	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost",25000);
			Scanner sc = new Scanner(System.in);
			
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			String texto = "El rápido desarrollo tecnológico ha transformado la manera en que interactuamos con el mundo. "
		            + "Desde la invención del internet, la comunicación ha alcanzado niveles inimaginables, permitiendo la conexión "
		            + "instantánea entre personas de diferentes partes del globo. Las redes sociales se han convertido en un "
		            + "componente esencial de la vida diaria, facilitando la difusión de información y la interacción social.\n\n"
		            + "A medida que avanzamos hacia un futuro más digital, es crucial considerar las implicaciones éticas y sociales "
		            + "de estos avances. La inteligencia artificial, por ejemplo, ofrece numerosas ventajas, pero también plantea "
		            + "desafíos significativos en términos de privacidad y seguridad. Las empresas y los gobiernos deben trabajar "
		            + "juntos para establecer regulaciones que protejan a los usuarios sin obstaculizar la innovación.\n\n"
		            + "La educación también ha experimentado una transformación significativa. Las plataformas de aprendizaje en línea "
		            + "han democratizado el acceso al conocimiento, permitiendo que personas de todas las edades y antecedentes puedan "
		            + "adquirir nuevas habilidades y competencias. Sin embargo, es importante abordar la brecha digital para asegurar que "
		            + "todos tengan acceso a estas oportunidades.\n\n"
		            + "El cambio climático es otro desafío crucial que enfrenta la humanidad. La tecnología puede jugar un papel vital en "
		            + "la mitigación de sus efectos, desde el desarrollo de energías renovables hasta la creación de soluciones para la "
		            + "reducción de residuos. Es esencial que adoptemos un enfoque sostenible en todas las áreas de la tecnología y la "
		            + "industria.\n\n"
		            + "La salud es otro campo que ha sido revolucionado por la tecnología. Los avances en telemedicina y dispositivos médicos "
		            + "han mejorado significativamente la capacidad de los profesionales de la salud para diagnosticar y tratar enfermedades. "
		            + "Sin embargo, es necesario garantizar que estos avances sean accesibles para todas las personas, independientemente de su "
		            + "ubicación o situación económica.\n\n"
		            + "En conclusión, el progreso tecnológico ofrece innumerables beneficios, pero también conlleva responsabilidades. Debemos "
		            + "trabajar colectivamente para asegurar que estos avances se utilicen de manera ética y sostenible, promoviendo el bienestar "
		            + "y la equidad para todos.";

			bw.write(texto);
			bw.newLine();
			bw.flush();	
			bw.close();
			
			//recibirArray
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
