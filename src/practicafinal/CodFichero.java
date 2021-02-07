/**
 * Practica Final - Programación I
 *
 * Creación: Noviembre 2020
 * Finalización: Febrero 2021
 *
 */
package practicafinal;

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Jorge González y Luis Barca
 */
public class CodFichero {

    private int semilla;
    private static final char[] caracteres = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
        'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '.', ',', ':', '@', '?', '!', '"', '(', ')', '<', '>'};
    private LecturaFichero lecturaFichero;
    private EscrituraFichero escrituraFichero;

    private String nombreFichero;

    // MÉTODOS PÚBLICOS

    /**
     * Método constructor de la clase
     *
     * @param nombreFichero
     * @param numCod
     * @throws IOException
     */
    public CodFichero(String nombreFichero, int numCod) throws IOException {
        this.nombreFichero = nombreFichero;
        Random random = new Random(numCod);
        this.semilla = random.nextInt(caracteres.length);
    }

    /**
     * Método para codificar caracter a caracter del fichero leído. La
     * codifición se hace de la siguiente manera: Asigamos al caracter, el
     * caracter correspondiente a la suma de la semilla y su posición si este se
     * pasa vuelve al principio.
     *
     * @throws IOException
     */
    public void codificarFichero() throws IOException {
        lecturaFichero = new LecturaFichero(nombreFichero);
        escrituraFichero = new EscrituraFichero(nombreFichero + ".cod.txt");

        // Leemos la linea del fichero correspondiente
        String linea = lecturaFichero.leerLinea();

        while (linea != null) {
            char[] caracteresLinea = linea.toCharArray();
            // Variable para almacenar la linea codificada
            String salida = "";

            // Codificamos toda la linea
            for (int i = 0; i < caracteresLinea.length; i++) {
                if (caracteresLinea[i] != ' ') {
                    //Codificacion caracter a caracter
                    int index = buscarIndCaracter(caracteresLinea[i]) + semilla;
                    while (index >= caracteres.length) {
                        index -= caracteres.length;
                    }
                    caracteresLinea[i] = caracteres[index];
                }
                salida += caracteresLinea[i];
            }

            // Escribimos la linea codificada y volvemos ha leer
            escrituraFichero.escribirLinea(salida + "\n");
            linea = lecturaFichero.leerLinea();
        }

        lecturaFichero.cerrarFichero();
        escrituraFichero.cerrarFichero();
    }

    /**
     * Método para decodificar el fichero codificado, caracter a caracter. La
     * descodifiación se hace de la siguiente manera: Asigamos al caracter, el
     * caracter correspondiente a la resta de la semilla y su posición si este se
     * pasa vuelve al principio. (Todo respecto a la codificación)
     *
     * @throws IOException
     */
    public void descodificarFichero() throws IOException {
        lecturaFichero = new LecturaFichero(nombreFichero + ".cod.txt");
        //escrituraFichero = new EscrituraFichero(nombreFichero + ".cod.txt");

        // Leemos la linea del fichero correspondiente
        String linea = lecturaFichero.leerLinea();

        while (linea != null) {
            char[] caracteresLinea = linea.toCharArray();
            // Variable para almacenar la linea codificada
            String salida = "";

            // Descodificación toda la linea
            for (int i = 0; i < caracteresLinea.length; i++) {
                if (caracteresLinea[i] != ' ') {
                    // Descodificación caracter a cadacters
                    int index = buscarIndCaracter(caracteresLinea[i]) - semilla;
                    while (index < 0 && index != caracteres.length - 1) {
                        index += caracteres.length;
                    }
                    caracteresLinea[i] = caracteres[index];
                }

                salida += caracteresLinea[i];
            }

            // Mostramos por pantalla la linea descodificada
            System.out.println(salida);
            // Volvemos a leer
            linea = lecturaFichero.leerLinea();
        }
        lecturaFichero.cerrarFichero();
    }

    // MÉTODOS PRIVADOS

    /**
     * Método que dado un caracterer mirar a que poscion pertenece en el array
     * de caracteres.
     *
     * @param c
     * @return
     */
    private int buscarIndCaracter(char c) {
        boolean encontrado = false;
        int i = 0;

        // Si se un caracter en mayuscula lo pasamos a miniscula
        if ((c >= 'A') && (c <= 'Z')) {
            int change = ('a' - 'A'); // Sumar la diferencia
            c = (char) (c + change);
        }

        // Buscamos el indice de donde se encuentra en nustro repertorio
        while (i < caracteres.length && !encontrado) {
            if (caracteres[i] == c) {
                encontrado = true;
            }
            
            i++;
        }

        // Control de posicion erronea (por el i++ anterior)
        i--;

        return i;
    }
}
