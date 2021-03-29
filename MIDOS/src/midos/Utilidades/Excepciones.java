/*
 * Descripción: Esta clase define los mensajes a mostrar al usuario según en base al error que se presente
 * durante la ejecucion del programa.
 */
package midos.Utilidades;

public class Excepciones {

    public static void MsjError(int numError, String valor) {

        switch (numError) {
            case 1:
                System.out.println("     ERROR 001 >> Comando inválido: " + valor + "\n");
                Analizador.recibeInput();
                break;
            case 2:
                System.out.println("     ERROR 002 >> Ya existe un directorio con el nombre  \"" + valor + "\" \n");
                break;
            case 3:
                System.out.println("     ERROR 003 >> Ya existe un archivo con el nombre  \"" + valor + "\" \n");
                break;
            case 4:
                System.out.println("     ERROR 004 >> El nombre  \"" + valor + "\" excede "
                        + "los 8 \n     caracteres permitidos.\n");
                break;
            case 5:
                System.out.println("     ERROR 005 >> El nombre  \"" + valor + "\" contiene "
                        + "caracteres no válidos."
                        + "\n     Solo se permiten letras y numeros.\n");
                break;
            case 6:
                System.out.println("     ERROR 006 >> El nombre ingresado \"" + valor + "\" no "
                        + "empieza con una letra.\n");
                break;
            case 7:
                System.out.println("     ERROR 007 >> Ingresó un número incorrecto de argumentos "
                        + "\n     para el comando: " + valor + "\n");
                break;
            case 8:
                System.out.println("     ERROR 008 >> Ingresó un argumento inválido "
                        + "para el comando: " + valor + "\n");
                break;
            case 9:
                System.out.println("     ERROR 009 >> El número máximo de elementos que cada directorio "
                        + "\n     puede contener es: 8 \n");
                break;

            case 10:
                System.out.println("     ERROR 010 >> Memoria insufiente. El registro no fue agregado. "
                        + "\n     Libere espacio e inténtelo de nuevo. \n");
                break;
            case 11:
                System.out.println("     ERROR 011 >> El directorio \"" + valor + "\" no existe "
                        + "bajo el directorio actual. \n");
                break;
            case 12:
                System.out.println("     ERROR 012 >> El nombre ingresado \"" + valor + "\" corresponde "
                        + "a un archivo. \n");
                break;
            case 13:
                System.out.println("     ERROR 013 >>  M:\\ es el directorio raiz de MIDOS.  \n");
                break;
            case 14:
                System.out.println("     ERROR 014 >> El directorio \"" + valor + "\"  no se puede borrar "
                        + "\n     porque contiene directorios o archivos. \n");
                break;
            case 15:
                System.out.println("     ERROR 015 >> El nombre del directorio ingresado \"" + valor + "\"  no "
                        + "existe \n     bajo el directorio actual. \n");
                break;
            case 16:
                System.out.println("     ERROR 016 >> El directorio ingresado \"" + valor + "\"   no se puede borrar. \n");
                break;
            case 17:
                System.out.println("     ERROR 017 >> El archivo \"" + valor + "\"  no existe  "
                        + "dentro del directorio actual. \n");
                break;
            case 18:
                System.out.println("     ERROR 018 >> El nombre \"" + valor + "\"  debe existir "
                        + "\n     bajo el directorio actual. \n");
                break;
            case 19:
                System.out.println("     ERROR 019 >> El nombre \"" + valor + "\"  no debe existir "
                        + "\n     bajo el directorio actual. \n");
                break;
            case 20:
                System.out.println("     ERROR 020 >> Ambos nombres deben corresponder al mismo "
                        + "\n     tipo de objeto (<DIR> o arch). \n");
                break;
            case 21:
                System.out.println("     ERROR 021 >> El nombre ingresado \"" + valor + "\" corresponde "
                        + "a un directorio. \n");
                break;
            case 100:
                System.out.println("     ERROR 100 >> Hubo un error al agregar un registro nuevo.\n");
                break;
            case 101:
                System.out.println("     ERROR 101 >> Hubo un error al actualizar la memoria.\n");
                break;
            case 102:
                System.out.println("     ERROR 102 >> Hubo un error al leer el archivo MIDOSTRE.\n");
                break;
            case 103:
                System.out.println("     ERROR 103 >> Hubo un error al leer el archivo MIDOSFRE.\n");
                break;
            default:
                System.out.println("     ERROR 000 >> COMANDO EN CONSTRUCCION: " + valor + "\n");
                break;
        }

    }

}
