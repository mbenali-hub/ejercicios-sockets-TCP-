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
