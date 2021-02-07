/**
 * Practica Final - Programación I
 *
 * Creación: Noviembre 2020
 * Finalización: Febrero 2021
 *
 */
package practicafinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Jorge González y Luis Barca
 */
public class LecturaFichero {

    private static final String ruta = "./Ficheros";

    private File file;
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    // MÉTODOS PÚBLICOS

    /**
     * Método constructor vacío
     * 
     * @throws FileNotFoundException
     */
    public LecturaFichero() throws FileNotFoundException {
        this.file = new File(ruta);
    }

    /**
     * Método constructor de la clase
     * 
     * @param nombreFichero
     * @throws FileNotFoundException
     */
    public LecturaFichero(String nombreFichero) throws FileNotFoundException {
        this.file = new File(nombreFichero);
        this.fileReader = new FileReader(file);
        this.bufferedReader = new BufferedReader(fileReader);
    }

    /**
     * Método que lee una linea del fichero
     * 
     * @return
     * @throws IOException
     */
    public String leerLinea() throws IOException {
        return bufferedReader.readLine();
    }
    
    /**
     * Método que devuelve la longitud del fichero
     * 
     * @return
     */
    public long longitud() {
        return file.length();
    }

    /**
     * Método que cierra el enlace con los ficheros
     * 
     */
    public void cerrarFichero() throws IOException {
        fileReader.close();
        bufferedReader.close();
    }

    /**
     * Método que devuelve el listado de ficheros disponibles
     * 
     * @return
     */
    public String[] fileList() {
        return file.list();
    }
}
