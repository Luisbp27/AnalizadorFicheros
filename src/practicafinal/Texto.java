/**
 * Practica Final - Programación I
 *
 * Creación: Noviembre 2020
 * Finalización: Febrero 2021
 *
 */
package practicafinal;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Jorge González y Luis Barca
 */
public class Texto {

    public static final int NMAX_PALABRAS = 500;
    private static final int NMAX_CARACTERES = NMAX_PALABRAS * Palabra.getCaracteresMAX();

    private String fichero;

    private int fila;

    private int numCaracteres;
    private int numPalabras;

    private LecturaFichero lecturaFichero;
    private RegFrecCaracteres registroCaracteres;
    private RegFrecPalabras registroPalabras;

    // MÉTODOS PÚBLICOS

    /**
     * Constructor de la clase
     *
     * @throws FileNotFoundException
     */
    public Texto() throws FileNotFoundException, IOException {
        this.fila = 0;
        this.lecturaFichero = new LecturaFichero();
        selectFile();
        this.lecturaFichero = new LecturaFichero(fichero);
        this.registroCaracteres = new RegFrecCaracteres();
        this.registroPalabras = new RegFrecPalabras();

        lecturaTextoInicio();
        lecturaFichero.cerrarFichero();
    }

    /**
     * Constructor de la clase dado un fichero
     *
     * @param f
     * @throws FileNotFoundException
     */
    public Texto(String f) throws FileNotFoundException {
        this.fila = 0;
        this.fichero = f;
        this.lecturaFichero = new LecturaFichero(fichero);
        this.registroCaracteres = new RegFrecCaracteres();
    }

    /**
     * Método que efectua la lectura del primer texto del fichero
     *
     * @throws IOException
     */
    private void lecturaTextoInicio() throws IOException {
        String linea = lecturaFichero.leerLinea();

        while ((linea != null) && ((numCaracteres <= NMAX_CARACTERES))) {

            char[] frase = linea.toCharArray();

            if (!isEmpty(frase)) {
                int cFrase = 0;
                while (cFrase < frase.length) {
                    int cPalabra = 0;
                    char palabraLeidaChars[] = new char[Palabra.getCaracteresMAX()];

                    // Leemos caracter a caracter de la frase
                    while ((cFrase + cPalabra < frase.length)
                            && Palabra.isAlfabetico(frase[cPalabra + cFrase])
                            && !Palabra.isSeparador(frase[cPalabra + cFrase])
                            && !Palabra.isCharEmpty(frase[cPalabra + cFrase])) {
                        // Añadimos el caracter
                        palabraLeidaChars[cPalabra] = frase[cPalabra + cFrase];
                        cPalabra++;
                    }
                    // Palabra leída
                    Palabra palabraLeida = new Palabra(palabraLeidaChars);
                    palabraLeida.purge(cPalabra);

                    // Si no es palabra vacia
                    if (!palabraLeida.isEmpty()) {
                        cFrase += cPalabra;
                        numPalabras++;
                    } else {
                        cFrase++;
                    }
                }
                // Actualizamos el numero de caracteres
                numCaracteres += cFrase;

                // Actualizamos elos registros de los caracteres
                registroCaracteres(frase);
            }

            //Leemos de nueva linea
            fila++;
            linea = lecturaFichero.leerLinea();
        }
    }

    /**
     * Método que busca el carácter más repetido en un texto
     *
     * @return
     */
    public String letraRepetida() {
        int frecuencia[] = registroCaracteres.letraAparecidaPos();

        return "La letra más repetida es: '" + (char) frecuencia[0] + "'\n"
                + "Con un total de " + frecuencia[1] + " apariciones.";
    }

    /**
     * Método que efectua la lectura para saber cual es la palabra más repetida
     * y su número de apariciones
     *
     * @return
     * @throws IOException
     */
    public String palabraRepetida() throws IOException {
        lecturaFichero = new LecturaFichero(fichero);

        // Lectura del texto
        String linea = lecturaFichero.leerLinea();

        while (linea != null) {
            char[] frase = linea.toCharArray();

            if (!isEmpty(frase)) {
                Palabra palabras[] = toArrayPalabra(frase);
                for (int i = 0; i < palabras.length; i++) {
                    registroPalabras.actualizarFrecPalabra(palabras[i]);
                }
            }

            linea = lecturaFichero.leerLinea();
        }
        // Cerramos el flujo de datos
        lecturaFichero.cerrarFichero();

        return registroPalabras.toString();
    }

    /**
     * Método que dada una palabra retorna el número de aparación y el lugar en
     * el fichero (en columna y fila)
     *
     * @param searchingP
     * @return
     * @throws IOException
     */
    public String buscarPalabra(Palabra searchingP) throws IOException {
        int columna = 0;
        int fila = 0;

        int xPos[] = new int[NMAX_PALABRAS];
        int yPos[] = new int[NMAX_PALABRAS];

        int nApariciones = 0;
        lecturaFichero = new LecturaFichero(fichero);

        // Lectura del texto
        String linea = lecturaFichero.leerLinea();

        while (linea != null) {
            char[] frase = linea.toCharArray();

            if (!isEmpty(frase)) {
                // Contador de los caracteres de la frase
                int cFrase = 0;

                while (cFrase < frase.length) {
                    // Contador de la Palabra
                    int cPalabra = 0;
                    char palabraLeidaChars[] = new char[Palabra.getCaracteresMAX()];

                    while ((cFrase + cPalabra < frase.length)
                            && Palabra.isAlfabetico(frase[cPalabra + cFrase])
                            && !Palabra.isSeparador(frase[cPalabra + cFrase])
                            && !Palabra.isCharEmpty(frase[cPalabra + cFrase])) {
                        // Añadimos el caracter
                        palabraLeidaChars[cPalabra] = frase[cPalabra + cFrase];
                        // Siguiente caracter de la Palabra
                        cPalabra++;
                    }
                    // Limpiamos la palabra con la medida correcta
                    Palabra palabraLeida = new Palabra(palabraLeidaChars);
                    palabraLeida.purge(cPalabra);

                    // Si no es palabra vacia
                    if (!palabraLeida.isEmpty()) {
                        columna = cFrase;
                        cFrase += cPalabra;

                        // Si la palabra es igual actualizamos los registros
                        if (searchingP.isIgual(palabraLeida)) {
                            // El inicio de la palabra será la columna
                            xPos[nApariciones] = columna;
                            yPos[nApariciones] = fila;
                            nApariciones++;
                        }
                    } else {
                        cFrase++;
                    }
                }
            }
            // Leemos de nueva linea
            fila++;
            linea = lecturaFichero.leerLinea();
        }
        // Cerramos el flujo de datos
        lecturaFichero.cerrarFichero();

        // Preparamos el String que se mostrará por pantalla
        String salida = "No se ha podido econtrar la palabra.";
        if (nApariciones != 0) {
            salida = "La palabra " + searchingP + " aparece en:\n";
            for (int i = 0; i < nApariciones; i++) {
                salida += "Aparición nº" + i + "\n";
                salida += "En la columa: " + xPos[i] + "\n";
                salida += "Y la fila: " + yPos[i] + "\n\n";
            }
        }

        return salida;
    }

    /**
     * Método que dado un texto retorna el número de aparación y el lugar en el
     * fichero (en columna y fila)
     *
     * @param textoFind
     * @return
     * @throws java.io.FileNotFoundException
     * @throws IOException
     */
    public String buscarTexto(Palabra[] textoFind) throws FileNotFoundException, IOException {
        boolean palEncontradas[] = new boolean[textoFind.length];
        int nPalEncontradas = 0;

        int columna = 0;
        int fila = 0;
        int xPos[] = new int[NMAX_PALABRAS];
        int yPos[] = new int[NMAX_PALABRAS];
        int nApariciones = 0;

        lecturaFichero = new LecturaFichero(fichero);

        // Lectura del texto
        String linea = lecturaFichero.leerLinea();

        while (linea != null) {
            char[] frase = linea.toCharArray();

            if (!isEmpty(frase)) {
                // Contador de los caracteres de la frase
                int cFrase = 0;

                while (cFrase < frase.length) {
                    // Contador de la Palabra
                    int cPalabra = 0;
                    char palabraLeidaChars[] = new char[Palabra.getCaracteresMAX()];

                    while ((cFrase + cPalabra < frase.length) && Palabra.isAlfabetico(frase[cPalabra + cFrase])
                            && !Palabra.isSeparador(frase[cPalabra + cFrase])
                            && !Palabra.isCharEmpty(frase[cPalabra + cFrase])) {
                        // Añadimos el caracter
                        palabraLeidaChars[cPalabra] = frase[cPalabra + cFrase];
                        // Siguiente caracter de la Palabra
                        cPalabra++;
                    }
                    // Limpiamos la palabra con la medida correcta
                    Palabra palabraLeida = new Palabra(palabraLeidaChars);
                    palabraLeida.purge(cPalabra);

                    // Si no es palabra vacia
                    if (!palabraLeida.isEmpty()) {
                        columna = cFrase;
                        cFrase += cPalabra;

                        // Si la palabra es igual actualizamos los registros
                        if (nPalEncontradas < textoFind.length
                                && textoFind[nPalEncontradas].isIgual(palabraLeida)) {
                            // El inicio de la palabra será la columna
                            if (nPalEncontradas == 0) {
                                xPos[nApariciones] = columna;
                            }
                            palEncontradas[nPalEncontradas] = true;
                            nPalEncontradas++;
                        } else {
                            nPalEncontradas = 0;
                        }
                    } else {
                        cFrase++;
                    }

                }

            }

            // Miramos si se han encontrado todas las palabras en la linea anterior
            int i = 0;
            boolean encontrado = true;

            while (encontrado && i < palEncontradas.length) {
                encontrado = palEncontradas[i];
                i++;
            }

            // Si se ha encontrado lo guardamos en los 'registros'
            if (encontrado) {
                yPos[nApariciones] = fila;
                nApariciones++;
            }

            // Reseteamos
            for (int j = 0; j < palEncontradas.length; j++) {
                palEncontradas[j] = false;
            }
            nPalEncontradas = 0;

            // Leemos de nueva linea
            fila++;
            linea = lecturaFichero.leerLinea();
        }
        // Cerramos el flujo de datos
        lecturaFichero.cerrarFichero();

        // Preparamos el String que se mostrará por pantalla
        String salida = "No se ha podido econtrar la palabra.";
        if (nApariciones != 0) {
            salida = "El texto aparece en:\n";
            for (int i = 0; i < nApariciones; i++) {
                salida += "Aparición nº" + i + "\n";
                salida += "En la columa: " + xPos[i] + "\n";
                salida += "Y la fila: " + yPos[i] + "\n\n";
            }
        }

        return salida;
    }

    /**
     * Método que buscar objetos Palabra repetidos 
     * 
     */
    public String buscarPalabraRepetida() throws FileNotFoundException, IOException {
        // Variable para verificar si se ha encontrado por lo menos alguna palabra repetida
        boolean repe = false; 
        int columna = 0;
        int fila = 0;

        Palabra centinela = new Palabra("$%~||~%$".toCharArray());
        Palabra palabraAnterior = centinela;

        lecturaFichero = new LecturaFichero(fichero);

        String salida = "";
        String linea = lecturaFichero.leerLinea();

        while (linea != null) {
            char[] frase = linea.toCharArray();

            if (!isEmpty(frase)) {
                // Contador de los caracteres de la frase
                int cFrase = 0;

                while (cFrase < frase.length) {
                    // Contador de la Palabra
                    int cPalabra = 0;
                    char palabraLeidaChars[] = new char[Palabra.getCaracteresMAX()];

                    while ((cFrase + cPalabra < frase.length)
                            && Palabra.isAlfabetico(frase[cPalabra + cFrase])
                            && !Palabra.isSeparador(frase[cPalabra + cFrase])
                            && !Palabra.isCharEmpty(frase[cPalabra + cFrase])) {
                        // Añadimos el caracter
                        palabraLeidaChars[cPalabra] = frase[cPalabra + cFrase];
                        // Siguiente caracter de la Palabra
                        cPalabra++;
                    }

                    // Limpiamos la palabra con la medida correcta
                    Palabra palabraLeida = new Palabra(palabraLeidaChars);
                    palabraLeida.purge(cPalabra);

                    // Si no es palabra vacia
                    if (!palabraLeida.isEmpty()) {
                        columna = cFrase;
                        cFrase += cPalabra;

                        // Si la palabra es igual actualizamos los registros
                        if (palabraAnterior.isIgual(palabraLeida)) {
                            repe = true;
                            // El inicio de la palabra repetida será la columna
                            salida += "\nLa palabra '" + palabraAnterior
                                    + "' se repite con la palabra anterior en: "
                                    + "\nColumna: " + columna
                                    + "\nFila: \n" + fila;
                        }
                        // Actualizamos el registro
                        palabraAnterior = palabraLeida;
                    } else {
                        cFrase++;
                    }
                }
            }
            fila++;
            linea = lecturaFichero.leerLinea();
        }
        // Cerramos el flujo de datos
        lecturaFichero.cerrarFichero();

        // Si no hay palabras repetidas lo mostramos
        if (!repe) {
            salida = "No se ha encontrado ninguna palabra repetida.";
        }

        return salida;
    }

    /**
     * Método que dado un char array, retona un array con las palabras que
     * contiene.
     *
     * @param frase
     * @return
     */
    public static Palabra[] toArrayPalabra(char[] frase) {
        Palabra[] palabras = new Palabra[NMAX_PALABRAS];
        // Contador de palabras
        int nPalabras = 0;
        // Contador de los caracteres de la frase
        int cFrase = 0;

        while (cFrase < frase.length) {
            // Contador de la Palabra
            int cPalabra = 0;
            char palabraLeidaChars[] = new char[Palabra.getCaracteresMAX()];

            while ((cFrase + cPalabra < frase.length) && Palabra.isAlfabetico(frase[cPalabra + cFrase])
                    && !Palabra.isSeparador(frase[cPalabra + cFrase])
                    && !Palabra.isCharEmpty(frase[cPalabra + cFrase])) {
                // Añadimos el caracter
                palabraLeidaChars[cPalabra] = frase[cPalabra + cFrase];
                // Siguiente caracter de la Palabra
                cPalabra++;
            }
            Palabra palabraLeida = new Palabra(palabraLeidaChars);
            palabraLeida.purge(cPalabra);

            // Si no es palabra vacia
            if (!palabraLeida.isEmpty()) {
                // Añadimos la palabra leida
                palabras[nPalabras] = palabraLeida;
                nPalabras++;

                cFrase += cPalabra;
            } else {
                cFrase++;
            }
        }

        // Purgamos el array de palabras con el nombre de palabras leidas
        Palabra[] palabrasClean = new Palabra[nPalabras];
        for (int i = 0; i < nPalabras; i++) {
            palabrasClean[i] = palabras[i];
        }

        return palabrasClean;
    }

    /**
     * Retorna el toString del registro de frecuencias
     *
     * @return
     */
    public String registroCaracteres() {
        return registroCaracteres.toString();
    }

    /**
     * Método que convierte a String un objeto Texto
     *
     */
    @Override
    public String toString() {
        return "Número de caracteres: " + numCaracteres
                + "\nNúmero de palabras: " + numPalabras
                + "\nNúmero de líneas: " + fila;
    }

    // MÉTODOS PRIVADOS 

    /**
     * Método que permite seleccionar el fichero que el usuario desee
     * 
     */
    private void selectFile() {
        System.out.println("Escribe un nombre de los siguientes ficheros para leerlo:");
        String[] ficheros = lecturaFichero.fileList();

        // Mostrar todos los nombres de los ficheros
        for (int i = 0; i < ficheros.length; i++) {
            System.out.println(" - " + ficheros[i]);
        }

        fichero = LT.lecturaLinea();

        while (!correctFile(ficheros)) {
            System.out.println("ERROR: Fichero no encontrado. "
                    + "\nVuelve a escribir el nombre del fichero:");

            fichero = LT.lecturaLinea();
        }

        // Ruta del fichero seleccionado
        fichero = "./Ficheros/" + fichero;
    }

    /**
     * Método que verifica si es el fichero correcto
     *
     */
    private boolean correctFile(String s[]) {
        boolean correct = false;

        for (int i = 0; i < s.length; i++) {
            if (s[i].equals(fichero)) {
                correct = true;
            }
        }

        // Miramos si no ha entroducido la extensión
        if (!correct) {
            fichero += ".txt";
            for (int i = 0; i < s.length; i++) {
                if (s[i].equals(fichero)) {
                    correct = true;
                }
            }
        }

        return correct;
    }

    /**
     * Método que actualiza los registros pasado por parametro una frase en
     * forma de array
     *
     * @param frase
     */
    private void registroCaracteres(char[] frase) {
        registroCaracteres.contarCarcteres(frase);
    }

    /**
     * Verifica si una frase esta vacía.
     *
     * @param frase
     * @return
     */
    private boolean isEmpty(char[] frase) {
        boolean empty = false;

        // Si es mayor a 0 miramos que no sea un caracter vacio
        if (frase.length > 0) {
            // 10 = null line
            // 13 = retorno
            int vacios[] = {10, 13};

            for (int i = 0; i < frase.length; i++) {
                for (int j = 0; j < vacios.length; j++) {
                    if (frase[i] == (char) vacios[j]) {
                        empty = true;
                    }
                }
            }
        }

        return empty;
    }

    // GETS & SETS

    /**
     * Método que decuelve el nombre del fichero
     * 
     */
    public String getFichero() {
        return fichero;
    }
}
