/*
 * Descripción: Clase auxiliar de la aplicación MIDOS.
 */
package midos.Utilidades;

import java.io.IOException;
import midos.Registros.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CoreMIDOS {

// Metodo que procesa comandos sin parametros y ejecuta el método apropiado    
    public static void inicioMIDOS(String comando) throws IOException {
        switch (comando) {
            case "CLS":
                comandoCLS();
                break;
            case "VER":
                comandoVER();
                break;
            case "DATE":
                comandoDATE();
                break;
            case "TIME":
                comandoTIME();
                break;
            case "EXIT":
                comandoEXIT();
                break;
            case "DIR":
                comandoDIR();
                break;
            case "PROMPT":
                DirectorioActual.setPrompt(1);
                LineaComandos.comandoPROMPT();
                break;
            case "TREE":
                comandoTREE();
                break;
            case "CD..":
                comandoCD("..");
                break;
            case "CD\\":
                comandoCD("\\");
                break;
            default:
                Excepciones.MsjError(1, comando);
                break;
        }
    }

// "Limpia la pantalla" imprimiento 13 lineas en blanco.    
    private static void comandoCLS() {

        String Dato = "                                 \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n"
                + "                                     \n";
        System.out.println(Dato);
        Analizador.recibeInput();
    }

// Muestra en pantalla la version actual de MIDOS.
    private static void comandoVER() {

        String Dato = "MINGOSOFT MIDOS \n"
                + "Copyright MINGOSOFT CORPORATION 2018 \n"
                + "MIDOS Versión 4.0    Memoria libre: " + Memoria.getMemoriaDisponible() + " K\n"
                + "Autor: Daniel Bermúdez Vílchez \n";

        System.out.println(Dato);
        Analizador.recibeInput();
    }

// Muestra en pantalla la fecha actual.      
    private static void comandoDATE() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = new Date();
        System.out.println("La fecha actual es: " + formatoFecha.format(fecha) + "\n");
        Analizador.recibeInput();
    }

// Muestra en pantalla la hora actual.   
    private static void comandoTIME() {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        Date hora = new Date();
        System.out.println("La hora actual es: " + formatoHora.format(hora) + "\n");
        Analizador.recibeInput();
    }

// Contiene la lógica de la salida del programa con el comando EXIT.
    private static void comandoEXIT() {
        System.out.println("¿Está seguro de salir de MIDOS? S = si / N = no ");
        Scanner input = new Scanner(System.in);
        String respuesta = input.next();
        if (respuesta.equals("S") || respuesta.equals("s")) {
            despedida();
            ManejoArchivos fileF = new ManejoArchivos();
            fileF.validaArchivoFRE();
            System.exit(0);
        }
        if (respuesta.equals("N") || (respuesta.equals("n"))) {
            Analizador.recibeInput();
        } else {
            System.out.println("Favor ingrese una respuesta válida");
            comandoEXIT();
        }
    }

// Método para crear un directorio         
    protected static void comandoMD(String nombre) {
        try {
            if (DirectorioActual.existeDuplicado(nombre)) {
                Analizador.recibeInput();
            } else if (DirectorioActual.cuentaRegistros() >= 8) {
                Excepciones.MsjError(9, null); // Cada directorio puede contener maximo 8 hijos
                Analizador.recibeInput();
            } else if (!Memoria.existeMemoriaNuevoDir()) {
                Excepciones.MsjError(10, null); // Memoria insuficiente
                Analizador.recibeInput();
            } else {
                Directorio nuevoDir = new Directorio();
                nuevoDir.setNombre(nombre);
                nuevoDir.setIdNum(SistemaArchivos.getID());
                nuevoDir.setIdPadre(DirectorioActual.getDirActual());
                ManejoArchivos.escribeRegistro(nuevoDir.toString());
                Memoria.actualizaMemoria();
                System.out.println();
                Analizador.recibeInput();
            }
        } catch (IOException ex) {
            Excepciones.MsjError(100, nombre); // Error al agregar un registro nuevo
            Analizador.recibeInput();
        }
    }

// Método para crear un archivo de texto      //**** DEPURAR ***** ///
    protected static void comandoCOPY(String nombre) {
        try {
            if (DirectorioActual.existeDuplicado(nombre)) {
                Analizador.recibeInput();
            } else if (DirectorioActual.cuentaRegistros() >= 8) {
                Excepciones.MsjError(9, null); // Cada directorio puede contener maximo 8 hijos
                Analizador.recibeInput();
            } else if (!Memoria.existeMemoriaNuevoArch()) {
                Excepciones.MsjError(10, null); // Memoria insuficiente
                Analizador.recibeInput();
            } else {

                boolean completo = false;

                do {
                    System.out.println("Digite el texto terminando con ^Z: ");
                    Scanner input = new Scanner(System.in);
                    String cadena = input.nextLine();
                    String value = cadena;
                    String lastTwo = null;
                    if (value != null && value.length() >= 2) {
                        lastTwo = value.substring(value.length() - 2);
                    }
                    if (lastTwo.equalsIgnoreCase("^Z")) {
                        String texto = cadena.substring(0, cadena.length() - 2);
                        Archivo arch = new Archivo();
                        arch.setNombre(nombre);
                        arch.setIdNum(SistemaArchivos.getID());
                        arch.setIdPadre(DirectorioActual.getDirActual());
                        arch.setTexto(texto);
                        ManejoArchivos.escribeRegistro(arch.toString());
                        Memoria.actualizaMemoria();
                        completo = true;
                    } else {
                        System.out.println("El texto ingresado no se guardó: No termina con ^Z ");
                        completo = false;
                    }

                } while (completo = false);

                Analizador.recibeInput();

            }
        } catch (IOException ex) {
            Excepciones.MsjError(100, nombre); // Error al agregar un registro nuevo
            Analizador.recibeInput();
        }
    }

// Método para mostrar listado de directorios bajo el DIR actual     
    private static void comandoDIR() {
        int numDirectorios;
        int numArchivos;
        int memoriaLibre;
        if (DirectorioActual.getDirActual().equals("M:\\")) {
            System.out.println("  Directorio de " + DirectorioActual.getDirActual());
        } else {
            System.out.println("  Directorio de " + SistemaArchivos.getNombreRegistro(DirectorioActual.getDirActual()));
        }

        try {
            DirectorioActual.muestraRegistros();
            System.out.println();
            numDirectorios = DirectorioActual.cuentaDirectorios();
            numArchivos = DirectorioActual.cuentaArchivos();
            System.out.println("  " + numDirectorios + " directorios");
            System.out.println("  " + numArchivos + " archivos");
            memoriaLibre = Memoria.getMemoriaDisponible();
            System.out.println(memoriaLibre + " K libres\n");
            Analizador.recibeInput();
        } catch (Exception e) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
            Analizador.recibeInput();
        }
    }

// Método para navegar por el sistema de directorios     
    protected static void comandoCD(String dirDestino) throws IOException {

        String idDirPadre;
        String idDirActual = DirectorioActual.getDirActual();
        String idDestino = "";

        switch (dirDestino) {
            case "..":
                if (idDirActual.equals("M:\\")) {
                    Excepciones.MsjError(13, null); // Error: El directorio M:\\ es la raiz de MIDOS.
                    System.out.println();
                    Analizador.recibeInput();
                } else {
                    idDirPadre = DirectorioActual.getPadreID(idDirActual);
                    DirectorioActual.setDirActual(idDirPadre);
                    DirectorioActual.creaRuta();
                    Analizador.recibeInput();
                }
                break;
            case "\\":
                DirectorioActual.resetDirMIDOS();
                DirectorioActual.creaRuta();
                Analizador.recibeInput();
                break;
            case "/":
                Excepciones.MsjError(8, "CD"); // Error. Argumento inválido
                Analizador.recibeInput();
                break;
            default:
                if (DirectorioActual.encuentraDirHijo(idDirActual, dirDestino)) {
                    idDestino = SistemaArchivos.getDirHijoID(idDirActual, dirDestino);
                    DirectorioActual.setDirActual(idDestino);
                    DirectorioActual.creaRuta();
                    Analizador.recibeInput();
                } else {
                    Excepciones.MsjError(11, dirDestino); // Error. El "dir" hijo no existe bajo el DIR actual
                    Analizador.recibeInput();
                }
                break;
        }
    }

// Método para mostrar contenido de archivo
    protected static void comandoTYPE(String archivo) {
        String dirActual = DirectorioActual.getDirActual();
        DirectorioActual.leeArchivo(dirActual, archivo);
        Analizador.recibeInput();
    }

// Método para eliminar un directorio  
    protected static void comandoRD(String dirNombre) {
        String dirActual = DirectorioActual.getDirActual();
        String idHijo = SistemaArchivos.getDirHijoID(dirActual, dirNombre);
        if (!DirectorioActual.encuentraDirHijo(dirActual, dirNombre)) {
            Excepciones.MsjError(11, dirNombre); // Error: "dir" no existe bajo el directorio actual
            Analizador.recibeInput();
        } else if (SistemaArchivos.tieneHijos(idHijo)) {
            Excepciones.MsjError(14, dirNombre); // Error: "dir" contiene directorios o archivos.
            Analizador.recibeInput();
        } else {
            try {
                DirectorioActual.eliminaDirectorio(dirNombre);
                Memoria.actualizaMemoria();
                System.out.println();
                Analizador.recibeInput();
            } catch (Exception ex) {
                Excepciones.MsjError(16, dirNombre); // Error: "dir" no se borró
            }
        }
    }

// Método para eliminar archivo 
    protected static void comandoDEL(String arch) {
        String dirActual = DirectorioActual.getDirActual();
        if (!DirectorioActual.encuentraArchivoHijo(dirActual, arch)) {
            Excepciones.MsjError(17, arch); // Error: "archivo" no existe bajo el directorio actual
            Analizador.recibeInput();
        } else {
            DirectorioActual.eliminaArchivo(arch);
            Memoria.actualizaMemoria();
            Analizador.recibeInput();
        }
    }

// Método para cambiar nombre a directorios y archivos 
    protected static void comandoREN(String nombreViejo, String nombreNuevo) {
        String tipoReg = DirectorioActual.verificaTipoRegistro(nombreViejo);
        String tipoNuevo = DirectorioActual.verificaTipoRegistro(nombreNuevo);
        if (tipoReg.equals("<DIR>") || tipoReg.equals("arch")) {
            switch (tipoNuevo) {
                case "<DIR>":
                    Excepciones.MsjError(2, nombreNuevo); // Error: Ya existe un DIR con nombreNuevo XXX
                    Analizador.recibeInput();
                case "arch":
                    Excepciones.MsjError(3, nombreNuevo); // Error: Ya existe un ARCH con nombreNuevo XXX
                    Analizador.recibeInput();
                case "noExiste":
                    DirectorioActual.modificaRegistro(tipoReg, nombreViejo, nombreNuevo);
                    System.out.println();
                    Analizador.recibeInput();
            }
        } else {
            Excepciones.MsjError(18, nombreViejo); // Error: nombreViejo no existe bajo Dir Actual
            Analizador.recibeInput();
        }
    }

// Método para imprimir jerarquia del sistema de archivos   
    protected static void comandoTREE() {
        String dirRaiz = "M:\\";
        int nivel = 0;

        List<String> directorios = new ArrayList<String>();
        List<String> hijos = new ArrayList<String>();
        List<String> hijos2 = new ArrayList<String>();
        List<String> hijos3 = new ArrayList<String>();
        directorios = SistemaArchivos.getSubDirectorios(dirRaiz);
        for (String elemento : directorios) {
            System.out.println(SistemaArchivos.getNombreRegistro(elemento));
            if (SistemaArchivos.tieneHijos(elemento)) {
                nivel = 1;
                hijos = SistemaArchivos.getSubDirectorios(elemento);
                for (String hijo : hijos) {
                    System.out.println("|...    " + SistemaArchivos.getNombreRegistro(hijo));
                    if (SistemaArchivos.tieneHijos(hijo)) {
                        nivel = 2;
                        hijos2 = SistemaArchivos.getSubDirectorios(hijo);
                        for (String child : hijos2) {
                            System.out.println("|...    |...    " + SistemaArchivos.getNombreRegistro(child));
                            if (SistemaArchivos.tieneHijos(child)) {
                                nivel = 3;
                                hijos3 = SistemaArchivos.getSubDirectorios(child);
                                for (String dirs : hijos3) {
                                    System.out.println("|...    |...    |...    " + SistemaArchivos.getNombreRegistro(dirs));
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println();
        Analizador.recibeInput();
    }
    
    // *** METODOS COMPLEMENTARIOS  ***

    public static void bienvenida() throws IOException {

        String Dato = "*****************************************\n"
                + "MINGOSOFT MIDOS \n"
                + "Copyright MINGOSOFT CORPORATION 2018 \n"
                + "MIDOS Versión 4.0    Memoria libre: " + Memoria.getMemoriaDisponible() + " K\n"
                + "Autor: Daniel Bermúdez\n";
        System.out.println(Dato);
        DirectorioActual.creaRuta(); // Configura el prompt por defecto (Raiz de MIDOS)

    }

    protected static void despedida() {
        System.out.println("Gracias por usar MIDOS");
    }

} // Fin de la clase CoreMIDOS

