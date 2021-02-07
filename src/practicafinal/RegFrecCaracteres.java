/**
 * Practica Final - Programación I
 *
 * Creación: Noviembre 2020
 * Finalización: Febrero 2021
 *
 */
package practicafinal;

/**
 *
 * @author Jorge González y Luis Barca
 */
public class RegFrecCaracteres {

    private final char[] especialCaracteres = {'.', ',', ':', '@', '?', '!', '"', '(', ')', '<', '>', ' '};
    private static final int nCaracteres = 64;
    private char caracteres[];
    private int[] freqCaracteres;

    // MÉTODOS PÚBLICOS

    /**
     * Método constructor de la clase
     *
     */
    public RegFrecCaracteres() {
        this.caracteres = new char[nCaracteres];
        rellenarCaracteres();
        this.freqCaracteres = new int[caracteres.length];
    }

    /**
     * Método que rellena el array de frecuencias con los posibles caracteres
     * 
     */
    private void rellenarCaracteres() {
        int contador = 0;

        // Caracteres en Mayuscula
        for (int i = 65; i <= 90; i++) {
            caracteres[contador] = (char) i;
            contador++;
        }

        // Caracteres en Minuscula
        for (int i = 97; i <= 122; i++) {
            caracteres[contador] = (char) i;
            contador++;
        }

        // Caracteres Especiales
        for (int i = 0; i < especialCaracteres.length; i++) {
            caracteres[contador] = especialCaracteres[i];
            contador++;
        }
    }

    /**
     * Método que actualiza la frecuencia de los caracteres según la palabara
     * pasada por parámetro
     *
     * @param palabras
     */
    public void contarCarcteres(char[] palabras) {
        for (int i = 0; i < palabras.length; i++) {
            for (int j = 0; j < caracteres.length; j++) {
                if (palabras[i] == caracteres[j]) {
                    freqCaracteres[j]++;
                }
            }
        }
    }

    /**
     * Método que retona un array. Donde en la posición 0 se encuentra la letra
     * y en la 1 el número de apariciones.
     *
     * @return
     */
    public int[] letraAparecidaPos() {
        int frequencia[] = new int[2];

        for (int i = 0; i < caracteres.length; i++) {
            if (freqCaracteres[i] > frequencia[1]) {
                if (!isCaracterEspecial(caracteres[i])) {
                    frequencia[0] = caracteres[i];
                    frequencia[1] = freqCaracteres[i];
                }
            }
        }

        return frequencia;
    }

    /**
     * Método que convierte a String un objeto RegFrecCaracteres
     * 
     */
    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < caracteres.length; i++) {
            s += "Carácter " + caracteres[i] + " aparece " + freqCaracteres[i] + " veces.\n";
        }

        return s;
    }

    // MÉTODOS PRIVADOS 

    /**
     * Método que comprueba si el carácter pasado por parámetro es especial o no y retorna un booleano
     * 
     * @param caracter
     * @return
     */
    private boolean isCaracterEspecial(char caracter) {
        boolean its = false;
        for (int i = 0; i < especialCaracteres.length; i++) {
            if (especialCaracteres[i] == caracter) {
                its = true;
            }
        }
        return its;
    }
}
