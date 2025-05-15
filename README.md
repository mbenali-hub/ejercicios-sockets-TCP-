Ejercicio 6:
  - Descripción:
    - Juego multijugador en red donde los participantes buscan un premio oculto en un tablero de 10x10
    - El servidor indica a cada jugador qué tan cerca está del premio (frío/caliente)
    - Implementado usando sockets UDP en Java
  - Servidor 
    - Gestiona todas las conexiones de clientes
    - Crea un hilo independiente para cada jugador
    - Mantiene el estado del juego y las posiciones
  - Cliente
    - Permite a los jugadores conectarse al servidor
    - Envía coordenadas al servidor
    - Recibe información sobre la distancia al premio
  - Datos/Hilo
    - Gestiona el tablero y las posiciones
    - Calcula distancias al premio
    - Controla el flujo del juego
  - Funcionamiento
    - Los jugadores introducen coordenadas en formato X-Y (ej: 3-5)
    - El sistema responde con la distancia al premio
    - Cuando un jugador encuentra el premio, recibe un mensaje de victoria

Ejercicio 7:
  - Descripción:
    - Versión automatizada del juego "Frío/Caliente" donde múltiples clientes buscan simultáneamente un premio en un tablero.
  - Servidor
    - Gestiona el tablero y calcula distancias
  - Clientes
    - 5 jugadores automáticos que generan movimientos aleatorios
  - Datos
    - Maneja comunicación UDP entre clientes y servidor
  - HiloJugador
    - Lógica individual de cada jugador automático
  - Funcionamiento
    - El servidor inicia con un tablero de NxN (N ingresado por consola)
    - 5 clientes automáticos se conectan y envían movimientos aleatorios
    - El servidor calcula la distancia euclidiana al premio (posición fija 4,4)
    - Cuando un jugador acierta (distancia=0), el juego termina para ese cliente
Ejercicio 8:
  - Descripción:
    - Sistema distribuido para rastrear la posición de un barco usando 3 radares y cálculos de trilateración.
    - Las comunicaciones estan cifradas con AES
  - Barco
    - Simula movimiento y comunica posición a radares (puerto 2500)
  - 3 Radares
    - Calculan distancia al barco y envían datos a calculadora 
  - Calculadora
    - Determina posición exacta usando trilateración (puerto 7500)
  - MarcoPolo
    - Coordina el sistema y muestra resultados
  
  - Funcionamiento
    - Cada radar envía sus coordenadas (x,y) a la caluladora mediante UDP, el barco y los radares se comunican mediante TCP
    - La calculadora procesa los datos de los 3 radares
    - Devuelve posición exacta del barco (x,y) mediante UDP
  
Ejercicio 9:
  - Descripción
    - Aplicación cliente-servidor para organizar un amigo invisible entre múltiples participantes mediante TCP.
  
  - Servidor
    - Gestiona conexiones de clientes
    - Asigna aleatoriamente los amigos
    - Envía los resultados a cada participante
  
  - Clientes
    - Los participantes que se conectan al servidor
    - Cada uno envía su nombre y recibe su amigo asignado
  
  - Funcionamiento
    - Los clientes se conectan al servidor (puerto 12345)
    - Cada cliente envía su nombre después de un retardo aleatorio (5-10 seg)
    - Tras 10 segundos, el servidor cierra conexiones y asigna amigos
    - Cada participante recibe el siguiente en la lista como su amigo (cíclico)
    - Cada cliente recibe el nombre de su amigo invisible
    - El servidor se cierra automáticamente tras 10 segundos
      
Ejercicio 10:
  - Descripción:
    - Aplicación cliente-servidor donde múltiples clientes envían números aleatorios a un servidor, que calcula la media y la devuelve. El juego termina cuando la diferencia entre la media y un número enviado es exactamente 1.
    - Comunicaciones TCP
  - Servidor
    - Espera conexiones de clientes (hasta 5)
    - Recibe números, calcula la media y la envía a todos
    - Si la diferencia entre la media y un número es 1, cierra el juego enviando "FIN"
  
  Clientes
    - Se conectan al servidor y envían números aleatorios
    - Reciben la media y ajustan su siguiente número (mayor o menor según la media).
    - Finalizan al recibir "FIN"

Ejercicio 11:
  - Descripción:
    - Aplicación cliente-servidor que suma dos números largos (representados como cadenas) de forma distribuida. El servidor reparte las cifras entre múltiples hilos para calcular la suma parcialmente y luego combina los resultados.
    - Comunicaciones mediante UDP
  - Servidor
    - Recibe dos números largos mediante UDP (puerto 12345)
    - Crea un hilo principal (HiloSuma) para gestionar la suma
    - El hilo principal divide las cifras de los números y las asigna a hilos secundarios (HiloSumaCifras)
    - Cada hilo secundario suma un par de cifras y almacena el resultado
    - Finalmente, el servidor combina todos los resultados parciales y muestra la suma total
  - Clientes
    - Envían dos números largos al servidor mediante UDP
    - Cada número se envía como un paquete independiente
  - Detalles técnicos
    - Los números se procesan cifra a cifra, de derecha a izquierda
    - El servidor maneja acarreos (llevado) para sumas parciales con resultados de más de una cifra
    - La suma total se reconstruye invirtiendo el orden de los resultados parciales
