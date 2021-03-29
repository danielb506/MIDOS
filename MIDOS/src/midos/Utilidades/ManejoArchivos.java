/*
 * Descripción: Contiene metodos para manipular los archivos de texto
 */
package midos.Utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class ManejoArchivos {

    private static final String ARCH_DIR = "MIDOSTRE.txt";
    private static final String ARCH_MEM = "MIDOSFRE.txt";
    private static final String NUEVA_LINEA = System.getProperty("line.separator");

//***** METODOS PARA MANEJAR MIDOSFRE.txt ******  
// Método para almacenar en el archivo de texto "MIDOSFRE.txt" la memoria utilizada. 
    public void escribeArchivoFRE(int valor) {
        try {
            validaArchivoFRE();
            PrintWriter writer = new PrintWriter(ARCH_MEM);
            writer.println(valor);
            writer.close();
        } catch (IOException ioex) {
            Excepciones.MsjError(101, null); // Error al escribir en MIDOSFRE
        }
    }

// Método para validar que el archivo MIDOSFRE exista, sino, lo crea automaticamente.  
    protected void validaArchivoFRE() {
        try {
            File archivo = new File(ARCH_MEM);
            archivo.createNewFile();
        } catch (IOException e) {
            Excepciones.MsjError(101, null); // Error al escribir en MIDOSFRE
        }
    }

// Método para leer el numero almacenado en MIDOSFRE.txt.
    public int leeArchivoFRE() {
        String linea;
        int valor = 0;
        try {
            validaArchivoFRE();
            FileReader fileReader = new FileReader(ARCH_MEM);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((linea = bufferedReader.readLine()) != null) {
                valor = Integer.parseInt(linea);
            }
            bufferedReader.close();
        } catch (IOException ioex) {
            Excepciones.MsjError(103, null); // Error al leer  MIDOSFRE
        }
        return valor;
    }

//***** METODOS PARA MANEJAR MIDOSTRE.txt ******   
// Metodo para agregar en el archivo MIDOSTRE un nuevo registro    
    protected static void escribeRegistro(String registro) {
        PrintWriter printWriter = null;
        File file = new File(ARCH_DIR);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            printWriter = new PrintWriter(new FileOutputStream(ARCH_DIR, true));
            printWriter.write(registro + NUEVA_LINEA);
        } catch (IOException ioex) {
            Excepciones.MsjError(100, null); // Error al escribir en MIDOSTRE
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

// Método para validar que el archivo MIDOSTRE exista, sino, lo crea automaticamente.
    protected static void validaArchivoTRE() throws IOException {
        try {
            File archivo = new File(ARCH_DIR);
            archivo.createNewFile();
        } catch (IOException e) {
            Excepciones.MsjError(102, null); // Error al leer MIDOSTRE
        }
    }

// Método que genera List con el contenido total del archivo  MIDOSTRE.txt  
    public static List<String> leeArchivoTRE() throws IOException {
        validaArchivoTRE();
        List<String> lineas = new ArrayList<>();
        String linea;
        try {
            FileReader fileReader = new FileReader(ARCH_DIR);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((linea = bufferedReader.readLine()) != null) {
                lineas.add(linea);
            }
            bufferedReader.close();
        } catch (IOException ioex) {
            Excepciones.MsjError(102, null);  // Error al leer MIDOSTRE
        }
        return lineas;
    }

// Agrega en el archivo MIDOSTRE todos los registros masivamente     
    public static void escribeRegistros(ArrayList registros) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(ARCH_DIR);
            for (Object linea : registros) {
                writer.println(linea);
            }
                writer.flush();
                writer.close();
        } catch (Exception ex) {
            Excepciones.MsjError(100, null); // Error al escribir en MIDOSTRE
        }
    }


} // Fin de la clase

