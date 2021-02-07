/**
 * Practica Final - Programación I
 *
 * Creación: Noviembre 2020
 * Finalización: Febrero 2021
 *
 */
package practicafinal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Jorge González y Luis Barca
 */
public class EscrituraFichero {

    private BufferedWriter bufferedWriter;

    // MÉTODOS PÚBLICOS

    /**
     * Método constructor de la clase
     * 
     * @param nombreFichero
     * @throws IOException
     */
    public EscrituraFichero(String nombreFichero) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(nombreFichero));
    }

    /**
     * Método que escribe una linea en el fichero
     * 
     * @param linea
     * @throws IOException
     */
    public void escribirLinea(String linea) throws IOException {
        bufferedWriter.write(linea);
    }

    /**
     * Método que cierra el enlace con los ficheros
     * 
     * @throws IOException
     */
    public void cerrarFichero() throws IOException {
        bufferedWriter.close();
    }
}
