/**
 * Practica Final - Programación I
 *
 * Creación: Noviembre 2020
 * Finalización: Enero 2021
 *
 */
package practicafinal;

import java.io.IOException;

/**
 *
 * @author Jorge González y Luis Barca
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            Texto texto = new Texto();
            System.out.println(texto + "\n");

            boolean salir = false;
            while (!salir) {
                menu();
                int opcion = LT.lecturaEntero();

                switch (opcion) {
                    case 0:
                        salir = true;

                        break;
                    case 1:
                        // Letra más repetida y número de apariciones 
                        System.out.println(texto.letraRepetida() + "\n");

                        break;
                    case 2:
                        // Número de apariciones de de cada carácter    
                        System.out.println(texto.registroCaracteres() + "\n");

                        break;
                    case 3:
                        // Palabra más repetida y su número de apariciones.
                        System.out.println(texto.palabraRepetida() + "\n");
                        break;
                    case 4:
                        // Buscar una palabra
                        System.out.println("Escribe la palabra que quieres buscar:");

                        String lectura = LT.lecturaLinea();
                        Palabra palabra = new Palabra(lectura.toCharArray());

                        System.out.println(texto.buscarPalabra(palabra) + "\n");

                        break;
                    case 5:
                        // Buscar un texto
                        System.out.println("Escribe el texto que quieres buscar:");

                        String pal = LT.lecturaLinea();
                        char[] linea = pal.toCharArray();
                        Palabra[] textoFind = Texto.toArrayPalabra(linea);

                        System.out.println(texto.buscarTexto(textoFind));

                        break;
                    case 6:
                        // Busca las palabras repetidas dos veces seguidas
                        System.out.println(texto.buscarPalabraRepetida());

                        break;
                    case 7:
                        // Codifica el fichero
                        System.out.println("Introduce un número entre el 1 y el 100: ");
                        int numero = LT.lecturaEntero();
                        while (numero == 0 || numero > 100) {
                            System.out.println("Número incompatible. Vuelve a escribir otro: ");
                            numero = LT.lecturaEntero();
                        }

                        CodFichero codFichero = new CodFichero(texto.getFichero(), numero);
                        codFichero.codificarFichero();

                        System.out.println("Fichero codificado correctamente!");

                        break;
                    case 8:
                        // Establece semilla de descodificación
                        System.out.println("Introduce la semilla para descodificar el fichero: ");
                        int semilla = LT.lecturaEntero();
                        while (semilla == 0 || semilla > 100) {
                            System.out.println("Número de semilla incompatible. Vuelve a escribir otro: ");
                            semilla = LT.lecturaEntero();
                        }

                        CodFichero codFichero2 = new CodFichero(texto.getFichero(), semilla);
                        codFichero2.descodificarFichero();

                        System.out.println("\nSe ha descodificado correctamente!");

                        break;
                    default:
                        System.out.println("Selecciona una opción válida");

                        break;
                }
            }
            System.out.println("Hasta luego! :)");

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }

    }

    /**
     * Visualización del menú del programa
     *
     */
    private static void menu() {
        System.out.println("\n\t\t *** ANALIZADOR DE FICHEROS ***\n");
        System.out.println("Selecciona una acción:");
        System.out.println("\t1) Letra más repetida y su número de apariciones.");
        System.out.println("\t2) Número de apariciones de cada carácter.");
        System.out.println("\t3) Palabra más repetida y su número de apariciones.");
        System.out.println("\t4) Buscar una palabra.");
        System.out.println("\t5) Buscar un texto.");
        System.out.println("\t6) Buscar palabras repetidas.");
        System.out.println("\t7) Codificar fichero.");
        System.out.println("\t8) Descodificar fichero.");

        System.out.println("\nPulsa 0 para salir");
    }
}
