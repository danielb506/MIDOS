/*
Clase para manejar el comando PROMPT y las 4 formas distintas que tiene la linea de comandos
 */
package midos.Utilidades;

import java.io.IOException;

public class LineaComandos {

    private static String lineaComandos;

//  Getter y setter  
    public static String getLineaComandos() throws IOException {
        return lineaComandos;
    }

    protected static void setLineaComandos(String lineaComandos) {
        LineaComandos.lineaComandos = lineaComandos;
    }

// Establece linea de comandos para PROMPT sin argumentos: M:\>
    public static void comandoPROMPT() {
        setLineaComandos(configLineaComandos(1));
        Analizador.recibeInput();
    }

// Establece linea de comandos (un argumento) M:\ y >
    public static void comandoPROMPT(String arg) {
        switch (arg) {
            case "$P":
                setLineaComandos(configLineaComandos(3));
                Analizador.recibeInput();
                break;
            case "$G":
                setLineaComandos(configLineaComandos(4));
                Analizador.recibeInput();
                break;
            default:
                Excepciones.MsjError(8, "PROMPT"); // Error: argumento inválido
                Analizador.recibeInput();
                break;
        }
    }

// Establece linea de comandos (dos argumentos)  M:\> y  >M:\
    public static void comandoPROMPT(String arg1, String arg2) {
        switch (arg1) {
            case "$P":
                if (arg2.equals("$G")) {
                    setLineaComandos(configLineaComandos(1));
                    Analizador.recibeInput();
                } else {
                    Excepciones.MsjError(8, "PROMPT"); // Error: argumento inválido
                    Analizador.recibeInput();
                }
                break;
            case "$G":
                if (arg2.equals("$P")) {
                    setLineaComandos(configLineaComandos(2));
                    Analizador.recibeInput();
                } else {
                    Excepciones.MsjError(8, "PROMPT"); // Error: argumento inválido
                    Analizador.recibeInput();
                }
                break;
            default:
                Excepciones.MsjError(8, "PROMPT"); // Error: argumento inválido
                Analizador.recibeInput();
                break;
        }
    }

// Switch para configurar el formato de la linea de comandos
    protected static String configLineaComandos(int tipo) {
        switch (tipo) {
            case 1:
                DirectorioActual.setPrompt(1);
                lineaComandos = DirectorioActual.getRuta() + ">";
                break;
            case 2:
                DirectorioActual.setPrompt(2);
                lineaComandos = ">" + DirectorioActual.getRuta();
                break;
            case 3:
                DirectorioActual.setPrompt(3);
                lineaComandos = DirectorioActual.getRuta();
                break;
            default:
                DirectorioActual.setPrompt(4);
                lineaComandos = ">";
                break;
        }
        return lineaComandos;
    }

} // Fin de la clase

