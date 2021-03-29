/*
Clase que procesa el input del usuario y realiza el analisis léxico y sintáctico  
para luego redireccionar los tokens al método apropiado en la clase CoreMIDOS.
 */
package midos.Utilidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Analizador {

// Metodo principal para recibir input del usuario    
    public static void recibeInput() {
        try {
            LineaComandos.getLineaComandos();
            System.out.print(LineaComandos.getLineaComandos());
            Scanner input = new Scanner(System.in);
            String cadena = input.nextLine();
            if (cadena.isEmpty()) {
                recibeInput();
            } else {
                procesaInput(cadena);
            }

        } catch (IOException e) {
            Excepciones.MsjError(1, null); // Comando inválido
        }
    }

// Procesa input separando el comando de sus argumentos cuando estos existen
    private static void procesaInput(String cadena) throws IOException {
// Copia cadena en arraylist para poder validar numero de argumentos (eliminando espacios en blanco)
        String[] entrada = cadena.trim().split("\\s+");
        int args = entrada.length;
        switch (args) {
            case 1:
                procesaNoArgs(procesaComando(cadena));
                break;
            case 2:
                procesaUnArg(entrada[0].toUpperCase(), entrada[1]);
                break;
            case 3:
                procesaDosArgs(entrada[0].toUpperCase(), entrada[1], entrada[2]);
                break;
            default:
                procesaMultiArgs(entrada[0].toUpperCase());
                break;
        }
    }

// Limpia comandos eliminando espacios en blanco, tabs, y pasándolo a Mayuscula
    private static String procesaComando(String cadena) {
        String a = cadena;
        a = a.replaceAll("\\s+", "");
        String token = a.toUpperCase();
        return token;
    }

// Procesa comandos que no tienen argumento (CLS, VER, DATE, TIME, EXIT, DIR, y TREE)  
    private static void procesaNoArgs(String cadena) throws IOException {
        if (validaComandosMIDOS(cadena) && validaNoArgs(cadena)) {
            CoreMIDOS.inicioMIDOS(cadena);
        } else if (!validaComandosMIDOS(cadena)) {
            Excepciones.MsjError(1, cadena);    // Comando inválido
            recibeInput();
        } else {
            Excepciones.MsjError(7, cadena); // Número incorrecto de argumentos
            recibeInput();
        }
    }

// Procesa comandos que tienen un argumento (MD, PROMPT, CD, RD, TYPE, y DEL)    
    private static void procesaUnArg(String comando, String arg) throws IOException {
        if (validaComandosMIDOS(comando)) {
            if (validaUnArg(comando)) {
                switch (comando) {
                    case "MD":
                        if (analizadorSintactico(arg)) {
                            CoreMIDOS.comandoMD(arg); // Si nombre es válido se ejecuta MD
                        } else {
                            recibeInput();
                        }
                        break;
                    case "PROMPT":
                        DirectorioActual.setPrompt(1);
                        LineaComandos.comandoPROMPT(arg.toUpperCase());
                        break;
                    case "CD":
                        CoreMIDOS.comandoCD(arg);
                        break;
                    case "RD":
                        CoreMIDOS.comandoRD(arg);
                        break;
                    case "TYPE":
                        CoreMIDOS.comandoTYPE(arg);
                        break;
                    case "DEL":
                        CoreMIDOS.comandoDEL(arg);
                        break;
                    default:
                        Excepciones.MsjError(1, comando); // Comando invalido
                        break;
                }
            } else {
                Excepciones.MsjError(7, comando); // Número incorrecto de argumentos
                recibeInput();
            }
        } else {
            Excepciones.MsjError(1, comando); // Comando inválido 
        }
    }

// Procesa comandos que tienen dos argumentos (PROMPT, COPY CON, y REN)
    private static void procesaDosArgs(String comando, String arg1, String arg2) throws IOException {
        if (validaComandosMIDOS(comando)) {
            if (validaDosArgs(comando)) {
                switch (comando) {
                    case "PROMPT":
                        LineaComandos.comandoPROMPT(arg1.toUpperCase(), arg2.toUpperCase());
                        recibeInput();
                        break;
                    case "COPY":
                        if (arg1.toUpperCase().equals("CON") && analizadorSintactico(arg2)) {
                            CoreMIDOS.comandoCOPY(arg2);
                        } else {
                            Excepciones.MsjError(8, comando); // Argumento inválido
                            recibeInput();
                        }
                        break;
                    case "REN":
                        if (analizadorSintactico(arg2)) {
                            CoreMIDOS.comandoREN(arg1, arg2);
                        } else {
                            Excepciones.MsjError(8, comando); // Argumento inválido
                            recibeInput();
                        }

                        CoreMIDOS.comandoREN(arg1, arg2);
                        recibeInput();
                        break;
                    default:
                        Excepciones.MsjError(0, comando);
                        recibeInput();
                        break;
                }
            } else {
                Excepciones.MsjError(7, comando); // Número incorrecto de argumentos
                recibeInput();
            }
        } else {
            Excepciones.MsjError(1, comando); // Comando invalido 
        }
    }

// Procesa comandos que erroneamente son ingresados con 3 o argumentos 
    private static void procesaMultiArgs(String comando) {
        if (validaComandosMIDOS(comando)) {
            Excepciones.MsjError(7, comando); // Numero incorrecto de argumentos
            recibeInput();
        } else {
            Excepciones.MsjError(1, comando); // Comando invalido
            recibeInput();
        }
    }

// Metodo para validar que nombres de directorios y archivos cumplan con especificaciones
    private static boolean analizadorSintactico(String token) throws IOException {
        boolean valor = true;
        if (token.length() > 8) {
            Excepciones.MsjError(4, token); // Excede los 8 caracteres permitidos
            valor = false;
        }
        if (!(token.charAt(0) >= 97 && token.charAt(0) <= 122)
                && !(token.charAt(0) >= 65 && token.charAt(0) <= 90)) {
            Excepciones.MsjError(6, token); // No empieza con una letra
            valor = false;
        }
        for (int i = 0; i < token.length(); i++) {
            if (!(token.charAt(i) >= 48 && token.charAt(i) <= 57) && !(token.charAt(i) >= 97
                    && token.charAt(i) <= 122) && !(token.charAt(i) >= 65 && token.charAt(i) <= 90)) {
                Excepciones.MsjError(5, token); // Contiene caracteres no validos.
                valor = false;
                break;
            }
        }
        return valor;
    }

// Valida si el input corresponde a un comando MIDOS.
    private static boolean validaComandosMIDOS(String cadena) {
        List<String> comandosMIDOS = new ArrayList<>();
        comandosMIDOS.addAll(Arrays.asList("CLS", "VER", "DATE", "TIME", "EXIT", "MD",
                "DIR", "PROMPT", "CD", "RD", "DIR", "COPY", "TYPE", "DEL", "REN", "TREE", "CD..", "CD\\"));
        for (String s : comandosMIDOS) {
            if (cadena.equals(s)) {
                return true;
            }
        }
        return false;
    }

// Valida si el input corresponde a comando sin argumentos (CLS, VER, DATE, TIME, EXIT, DIR, y TREE)  
    private static boolean validaNoArgs(String cadena) {
        List<String> comandosNoArgs = new ArrayList<>();
        comandosNoArgs.addAll(Arrays.asList("CLS", "VER", "DATE", "TIME", "EXIT", "DIR", "PROMPT", "TREE", "CD..", "CD\\"));
        for (String s : comandosNoArgs) {
            if (cadena.equals(s)) {
                return true;
            }
        }
        return false;
    }

// Valida si el input corresponde a comando con un argumento (MD, PROMPT, CD, RD, TYPE, y DEL)
    private static boolean validaUnArg(String cadena) {
        List<String> comandosUnArg = new ArrayList<>();
        comandosUnArg.addAll(Arrays.asList("MD", "PROMPT", "CD", "RD", "TYPE", "DEL"));
        for (String s : comandosUnArg) {
            if (cadena.equals(s)) {
                return true;
            }
        }
        return false;
    }

// Valida si el input corresponde a comando con dos argumentos (PROMPT, COPY CON, y REN)
    private static boolean validaDosArgs(String cadena) {
        List<String> comandosDosArs = new ArrayList<>();
        comandosDosArs.addAll(Arrays.asList("PROMPT", "COPY", "REN"));
        for (String s : comandosDosArs) {
            if (cadena.equals(s)) {
                return true;
            }
        }
        return false;
    }

} // FIN
