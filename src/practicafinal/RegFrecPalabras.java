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
public class RegFrecPalabras {

    private Palabra[] palabrasRepetidas = new Palabra[Texto.NMAX_PALABRAS + 1];
    private int aparicionesPalabras[] = new int[Texto.NMAX_PALABRAS];
    private static final Palabra CENTINELA = new Palabra("$%~||~%$".toCharArray());

    private int nPalabras;
    private int repeticionMax;

    // MÉTODOS PÚBLICOS

    /**
     * Constructor vacío de la clase
     */
    public RegFrecPalabras() {
        this.palabrasRepetidas[0] = CENTINELA;
        this.nPalabras = 0;
        this.repeticionMax = 0;
    }

    /**
     * Método que actualiza la frecuencia de palabras
     *
     * @param palabra
     */
    public void actualizarFrecPalabra(Palabra palabra) {
        int contador = 0;

        while (!isCentinela(palabrasRepetidas[contador])) {
            if (palabra.isIgual(palabrasRepetidas[contador])) {
                aparicionesPalabras[contador]++;
            }
            contador++;
        }

        // Añadimos palabra y el centinela
        if (contador < Texto.NMAX_PALABRAS) {
            palabrasRepetidas[contador] = palabra;
            palabrasRepetidas[contador + 1] = CENTINELA;
            aparicionesPalabras[contador]++;
        }
        this.nPalabras = contador++;

        // Actualizamos el nombre maximo de las apariciones
        int repMax = 0;
        for (int i = 0; i < nPalabras; i++) {
            if (repMax < aparicionesPalabras[i]) {
                repMax = aparicionesPalabras[i];
            }
        }
        this.repeticionMax = repMax;
    }

    /**
     * Método que convierte a String un objeto RegFrecPalabras
     * 
     */
    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < nPalabras; i++) {
            if (repeticionMax == aparicionesPalabras[i]) {
                s += "La palabra '" + palabrasRepetidas[i].toString() + "'\n";
            }
        }

        s += "Con un número de " + repeticionMax + " apariciones.";

        // Resetamos las apariciones para que no haya errores
        // Lo podemos hacer porque sabemos que en nuestro programa depués 
        // el toString no volvermos a utilizar las apariciones
        palabrasRepetidas[0] = CENTINELA;
        for (int i = 0; i < aparicionesPalabras.length; i++) {
            aparicionesPalabras[i] = 0;
        }
        repeticionMax = 0;

        return s;
    }

    /**
     * Método que mira si una palabra p es igual que el centinela
     *
     * @param p
     * @return
     */
    private boolean isCentinela(Palabra p) {
        return p.isIgual(CENTINELA);
    }

    // GETS & SETS

    public Palabra[] getPalabrasRepetidas() {
        return palabrasRepetidas;
    }

    public void setPalabrasRepetidas(Palabra[] palabrasRepetidas) {
        this.palabrasRepetidas = palabrasRepetidas;
    }

    public int[] getAparicionesPalabras() {
        return aparicionesPalabras;
    }

    public void setAparicionesPalabras(int[] aparicionesPalabras) {
        this.aparicionesPalabras = aparicionesPalabras;
    }

    public int getRepeticionMax() {
        return repeticionMax;
    }

    public void setRepeticionMax(int repeticionMax) {
        this.repeticionMax = repeticionMax;
    }
}
