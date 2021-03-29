// Clase para manejar el sistema de registros 
package midos.Utilidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import midos.Registros.Directorio;
import static midos.Utilidades.DirectorioActual.getDirActual;

public class SistemaArchivos {

    private static final String RAIZ = "M:\\";

    private static int identificador = (int) System.currentTimeMillis();

    public static String getID() {
        int num = getIds();
        String idNum = Integer.toString(num);
        return idNum;
    }

    static public synchronized int getIds() {
        return identificador++;
    }

// Regresa el nombre del registro asociado a un ID especifico
    public static String getNombreRegistro(String id) {
        String nombreRegistro = "";
        ArrayList<String> registros;
        String[] linea;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[1].equals(id)) {
                        nombreRegistro = linea[0];
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return nombreRegistro;
    }

// Regresa el nombre del directorio Padre asociado a un ID especifico
    public static String getNombrePadre(String id) {
        String nombrePadre = "";
        ArrayList<String> registros;
        String[] linea;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[1].equals(id)) {
                        nombrePadre = getNombreRegistro(linea[3]);
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return nombrePadre;
    }

// Regresa el ID del directorio hijo asociado a un nombre especifico
    public static String getDirHijoID(String idPadre, String nombreHijo) {
        String idHijo = "";
        ArrayList<String> registros;
        String[] linea;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idPadre)
                            && linea[0].equalsIgnoreCase(nombreHijo)) {
                        idHijo = linea[1];

                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return idHijo;
    }

// Regresa el nombre del directorio hijo asociado a un ID especifico
    public static String getNombreHijo(String idPadre, String idHijo) {
        ArrayList<String> registros;
        String[] linea;
        String nombreHijo = "";
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idPadre) && linea[1].equalsIgnoreCase(idHijo)) {
                        nombreHijo = linea[0];
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return nombreHijo;
    }

// Valida si un ID aparece como "dirPadre" de algun registro  
    public static boolean tieneHijos(String dirID) {
        boolean respuesta = false;
        ArrayList<String> registros;
        String[] linea;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(dirID)) {
                        respuesta = true; // "DIR" aparece como directorio padre de algun registro
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return respuesta;

    }

// Regresa una Lista de los dirIDs hijos asociados a un ID especifico
    public static List<String> getSubDirectorios(String dirID) {
        ArrayList<String> registros;
        List<String> directorios = new ArrayList<String>();
        String[] entrada;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    entrada = registros.get(i).trim().split("\\s+");
                    if (entrada[3].equals(dirID)) {
                        if (entrada[2].equals("<DIR>")) {
                            directorios.add(entrada[1]);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }

        return directorios;
    }
    
// Imprime nombres de los hijos asociados a un ID especifico
    public static void imprimeSubDirectorios(int nivel, String dirID) {
        List<String> directorios = new ArrayList<String>();
        ArrayList<String> registros;
        String[] entrada;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    entrada = registros.get(i).trim().split("\\s+");
                    if (entrada[3].equals(dirID)) {
                        if (entrada[2].equals("<DIR>")) {
                            directorios.add(getNombreRegistro(entrada[1]));
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        Collections.sort(directorios, String.CASE_INSENSITIVE_ORDER);
        for (String subdir : directorios) {
            if (nivel == 0) {
                System.out.println(subdir);
            }
            if (nivel == 1) {
                System.out.println("|...    " + subdir);
            }
            if (nivel == 2) {
                System.out.println("|...    |...    " + subdir);
            }
            if (nivel == 3) {
                System.out.println("|...    |...    |...    " + subdir);
            }
        }

    }

} // FIN
