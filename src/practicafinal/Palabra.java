/*
/**
 * Practica Final - Programación I
 *
 * Creación: Noviembre 2020
 * Finalización: Febrero 2021
 *
 * CLASE Palabra
 *
 * NOTA: en esta clase Palabra se utiliza un atributo de objeto entero para la 
 * identificación del final de una palabra a través de su número de caracteres.
 */
package practicafinal;

/**
 *
 * @author Jorge González y Luis Barca
 */
public class Palabra {

    private static final char ESPACIO = ' ';
    private static final int CARACTERES_MAX = 20;
    private static final char FINAL_SECUENCIA = '\n';

    private static char caracter = ESPACIO;

    private char[] caracteres;
    private int numCaracteres;

    // MÉTODOS PÚBLICOS

    /**
     * Constructor vacío
     */
    public Palabra() {
        this.caracteres = new char[CARACTERES_MAX];
        this.numCaracteres = 0;
    }

    /**
     * Método constructor que construye una palabra a partir de un array
     *
     * @param caracteres
     */
    public Palabra(char[] caracteres) {
        this.caracteres = caracteres;
        this.numCaracteres = caracteres.length;
    }

    /**
     * Constructor con las variables pasadas por parámetro
     *
     * @param caracteres
     * @param numCaracteres
     */
    public Palabra(char[] caracteres, int numCaracteres) {
        this.caracteres = caracteres;
        this.numCaracteres = numCaracteres;
    }

    /**
     * Verifica si un carácter dado es un carácter separador
     *
     * @param caracter
     * @return
     */
    public static boolean isSeparador(char caracter) {
        return (((caracter == ESPACIO) || (caracter == ',') || (caracter == ';') || (caracter == ':') || (caracter == '.')
                || (caracter == '(') || (caracter == ')') || (caracter == '-') || (caracter == '[') || (caracter == ']')
                || (caracter == '{') || (caracter == '}')) && (caracter != FINAL_SECUENCIA));
    }

    /**
     * Verifica si un caracter dado es un caracter alfabético
     *
     * @param caracter
     * @return
     */
    public static boolean isAlfabetico(char caracter) {
        return (((caracter >= 'a') && (caracter <= 'z')) || ((caracter >= 'A') && (caracter <= 'Z')));
    }

    /**
     * Verifica si es caracter vacío
     *
     * @param c
     * @return
     */
    public static boolean isCharEmpty(char c) {
        boolean empty = false;
        // 10 = null line
        // 13 = retorno
        int vacios[] = {10, 13};

        for (int i = 0; i < vacios.length; i++) {
            if (c == (char) vacios[i]) {
                empty = true;
            }
        }

        return empty;
    }

    /**
     * Lectura de un objeto Palabra desde una array que posiblemente sea una
     * frase
     *
     * @param frase
     */
    public void lecturaArray(char frase[]) {
        numCaracteres = 0;
        caracter = frase[numCaracteres];

        while (!isSeparador(caracter) && (frase.length >= numCaracteres)) {
            // Almacenamiento del carácter leído 
            caracteres[numCaracteres] = caracter;
            numCaracteres++;
            caracter = frase[numCaracteres];
        }
    }

    /**
     * Método que verifica si dos palabras son iguales o no
     *
     * @param palabra
     * @return
     */
    public boolean isIgual(Palabra palabra) {
        if (numCaracteres == palabra.numCaracteres) {
            for (int i = 0; i < numCaracteres; i++) {
                if (caracteres[i] != palabra.caracteres[i]) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Método que mira si una palabra esta vacia
     *
     * @return
     */
    public boolean isEmpty() {
        return numCaracteres == 0;
    }

    /**
     * Método que limpia la palabra y se queda con un tamaño n
     *
     * @param n
     */
    public void purge(int n) {
        char palabraLimpia[] = new char[n];

        for (int i = 0; i < palabraLimpia.length; i++) {
            palabraLimpia[i] = this.caracteres[i];
        }

        this.caracteres = palabraLimpia;
        this.numCaracteres = n;
    }

    /**
     * Convierte a String un objeto Palabra
     *
     * @return
     */
    @Override
    public String toString() {
        String resultado = "";

        for (int indice = 0; indice < numCaracteres; indice++) {
            resultado += caracteres[indice];
        }

        return resultado;
    }

    /**
     * Convierte un objeto Palabra en un array de caracteres
     *
     * @return
     */
    public char[] toArray() {
        return this.caracteres;
    }

    // GETS & SETS

    /**
     * Devuelve el caracter del objeto Palabra cuya posición en el array
     * caracteres es dada por parámetro
     *
     * @param i
     * @return
     */
    public char getCaracter(int i) {
        return this.caracteres[i];
    }

    /**
     * Devuelve el atributo array caracteres
     *
     * @return
     */
    public char[] getCaracteres() {
        return this.caracteres;
    }

    /**
     * Método que devuelve el máximo de caracteres posibles en una Palabra
     *
     * @return
     */
    public static int getCaracteresMAX() {
        return CARACTERES_MAX;
    }
}
