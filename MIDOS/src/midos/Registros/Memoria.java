package midos.Registros;

import java.util.ArrayList;
import midos.Utilidades.*;

public class Memoria {

    private final static int MEMORIA_TOTAL = 256;

    private static int getMEMORIA_TOTAL() {
        return MEMORIA_TOTAL;
    }

// Metodo que devuelve INT con memoria disponible.   
    public static int getMemoriaDisponible() {
        int memoriaLibre;
        memoriaLibre = MEMORIA_TOTAL - getMemoriaUtilizada();
        return memoriaLibre;
    }

// Actualiza la cantidad de memoria en el archivo MIDOSFRE  
    public static void actualizaMemoria() {
        ManejoArchivos file = new ManejoArchivos();
        int memUtilizada = setMemoriaUsada();
        file.escribeArchivoFRE(memUtilizada);
    }
    
// Lee numero almacenado en el archivo MIDOSFRE.txt
    private static int getMemoriaUtilizada() {
        actualizaMemoria();
        ManejoArchivos file = new ManejoArchivos();
        int valor = file.leeArchivoFRE();
        return valor;
    }

// Cuantifica la cantidad de memoria en uso   
    private static int setMemoriaUsada() {
        int memUsada = cuentaDirectorios() * 8 + cuentaArchivos() * 4;
        return memUsada;
    }

// Cuenta el numero TOTAL de directorios para actualizar la memoria 
    private static int cuentaDirectorios() {
        int numDir = 0;
        ArrayList<String> registros;
        String[] entrada;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    entrada = registros.get(i).trim().split("\\s+");
                    if (entrada[2].equals("<DIR>")) {
                        numDir++;
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }

        return numDir;
    }

// Cuenta el numero TOTAL de archivos  para actualizar la memoria 
    private static int cuentaArchivos() {
        int numArch = 0;
        ArrayList<String> registros;
        String[] entrada;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    entrada = registros.get(i).trim().split("\\s+");
                    if (entrada[2].equals("arch")) {
                        numArch++;
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return numArch;
    }

// Valida si hay memoria disponible para crear un Directorio nuevo
    public static boolean existeMemoriaNuevoDir() {
        boolean respuesta = false;
        int memoriaLibre = getMEMORIA_TOTAL() - Memoria.getMemoriaUtilizada();
        if (memoriaLibre >= 8) {
            respuesta = true;
        }
        return respuesta;
    }

// Valida si hay memoria disponible para crear un Archivo nuevo
    public static boolean existeMemoriaNuevoArch() {
        boolean respuesta = false;
        int memoriaLibre = getMEMORIA_TOTAL() - Memoria.getMemoriaUtilizada();
        if (memoriaLibre >= 4) {
            respuesta = true;
        }
        return respuesta;
    }

    public static int getNumeroRegistros() {
        int registros = cuentaArchivos() + cuentaDirectorios();
        return registros;
     
        
       
    }
    
} // FIN
