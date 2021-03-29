// Clase para realizar operaciones sobre el Directorio Actual de MIDOS
package midos.Utilidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import midos.Registros.*;

public class DirectorioActual {

    private static final String RAIZ = "M:\\";
    private static String dirActual = RAIZ;
    private static String ruta;
    private static int prompt;

// ***** Getters y Setters   ***** //
    public static String getRAIZ() {
        return RAIZ;
    }

    public static String getDirActual() {
        return dirActual;
    }

    public static void setDirActual(String dirActual) {
        DirectorioActual.dirActual = dirActual;
    }

    public static String getRuta() {
        return ruta;
    }

    public static void setRuta(String ruta) {
        DirectorioActual.ruta = ruta;
    }

    public static int getPrompt() {
        return prompt;
    }

    public static void setPrompt(int prompt) {
        DirectorioActual.prompt = prompt;
    }

// Cuenta numero total de registros  bajo el directorio actual  ***
    protected static int cuentaRegistros() {
        int registros = cuentaArchivos() + cuentaDirectorios();
        return registros;
    }

// Cuenta numero de archivos bajo el directorio actual     ***
    protected static int cuentaArchivos() {
        int numArch = 0;
        ArrayList<String> registros;
        String[] linea;
        String idDirActual = getDirActual();
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idDirActual)) {
                        if (linea[2].equals("arch")) {
                            numArch++;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return numArch;
    }

// Cuenta numero de directorios bajo el directorio actual      
    protected static int cuentaDirectorios() {
        int numDir = 0;
        String idDirActual = getDirActual();
        ArrayList<String> registros;
        String[] linea;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idDirActual)) {
                        if (linea[2].equals("<DIR>")) {
                            numDir++;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return numDir;
    }

// Muestra la lista de registros bajo el Directorio Actual (DIR)   
    protected static void muestraRegistros() {
        String idDirActual = getDirActual();
        ArrayList<String> registros;
        String[] entrada;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            Collections.sort(registros, String.CASE_INSENSITIVE_ORDER);
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    entrada = registros.get(i).trim().split("\\s+");
                    if (entrada[3].equalsIgnoreCase(idDirActual)) {
                        System.out.println(entrada[0] + "\t" + entrada[2]);
                    }
                }
            }
        } catch (IOException ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
    }

// Elimina un registro bajo el Directorio Actual.  (RD)   
    protected static void eliminaDirectorio(String dir) {
        try {
            boolean valor = false;
            String idDirActual = getDirActual();
            ArrayList<String> registros;
            String[] linea;
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idDirActual)) {
                        if (linea[0].equalsIgnoreCase(dir)) {
                            registros.remove(i); // Elimina el registro
                            valor = true;
                        }
                    }
                }
            }
            if (valor = true) {
                ManejoArchivos.escribeRegistros(registros); // Se reescribe nueva lista de registros
                System.out.println("El directorio \"" + dir + "\" fue eliminado exitosamente.\n");
            } else {
                System.out.println("El directorio \"" + dir + "\" no se pudo eliminar.\n");
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
    }

// Elimina un registro bajo el Directorio Actual.  (DEL)  
    protected static void eliminaArchivo(String arch) {
        try {
            boolean valor = false;
            String idDirActual = getDirActual();
            ArrayList<String> registros;
            String[] linea;
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idDirActual)) {
                        if (linea[2].equals("arch")) {
                            if (linea[0].equalsIgnoreCase(arch)) {
                                registros.remove(i); // Elimina el registro
                                valor = true;
                            }
                        }
                    }
                }
            }
            if (valor = true) {
                ManejoArchivos.escribeRegistros(registros); // Se reescribe nueva lista de registros
                System.out.println("El archivo \"" + arch + "\" fue eliminado exitosamente.\n");
            } else {
                System.out.println("El archivo \"" + arch + "\" no se pudo eliminar.\n");
            }
        } catch (IOException ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
    }

// Lee el contenido de un Archivo bajo el Directorio Actual
    protected static void leeArchivo(String idPadre, String dirHijo) {
        boolean archivoExiste = false;
        ArrayList<String> registros;
        String[] linea;
        String[] texto;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idPadre) && linea[0].equalsIgnoreCase(dirHijo)) {
                        if (linea[2].equals("<DIR>")) {
                            Excepciones.MsjError(21, dirHijo); // Error... Registro es un Directorio
                        } else {
                            archivoExiste = true;
                            texto = new String[linea.length - 4];
                            System.arraycopy(linea, 4, texto, 0, linea.length - 4);
                            System.out.println(java.util.Arrays.toString(texto).
                                    replace("[", "").replace("]", "").replace(",", ""));
                            System.out.println();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        if (!archivoExiste) {
            Excepciones.MsjError(17, dirHijo); // Archivo no Existe bajo DIR Actual
        }
    }

// Modifica un registro bajo el Directorio Actual. (REN)    
    protected static void modificaRegistro(String tipoRegistro, String nombreViejo, String nombreNuevo) {
        try {
            boolean valor = false;
            String idDirActual = getDirActual();
            ArrayList<String> registros;
            String[] linea;
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idDirActual)) {
                        if (linea[0].equalsIgnoreCase(nombreViejo)) {
                            valor = true;
                            if (tipoRegistro.equals("<DIR>")) {
                                Directorio nuevoDir = new Directorio();
                                nuevoDir.setNombre(nombreNuevo);
                                nuevoDir.setIdNum(linea[1]);
                                nuevoDir.setIdPadre(linea[3]);
                                registros.remove(i);
                                ManejoArchivos.escribeRegistros(registros);
                                ManejoArchivos.escribeRegistro(nuevoDir.toString());
                            } else {
                                Archivo arch = new Archivo();
                                String[] cadena = new String[linea.length - 4];
                                System.arraycopy(linea, 4, cadena, 0, linea.length - 4);
                                String texto = java.util.Arrays.toString(cadena).replace("[", "") // *** 
                                        .replace("]", "").replace(",", "");
                                arch.setNombre(nombreNuevo);
                                arch.setIdNum(linea[1]);
                                arch.setIdPadre(linea[3]);
                                arch.setTexto(texto);
                                registros.remove(i);
                                ManejoArchivos.escribeRegistros(registros);
                                ManejoArchivos.escribeRegistro(arch.toString());
                            }
                        }
                    }
                }
            }
            if (valor = true) {
                System.out.println("El nombre  \"" + nombreViejo + "\" fue reemplazado "
                        + "por \"" + nombreNuevo + "\" exitosamente.\n");
            } else {
                System.out.println("El cambio de nombre no se pudo realizar");
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
    }

// Método para validar si existen duplicados bajo el directorio actual 
    protected static boolean existeDuplicado(String dir) throws IOException {
        boolean valor = false;
        String idDirActual = getDirActual();
        ArrayList<String> registros;
        String[] linea;
        registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
        if (!registros.isEmpty()) {
            for (int i = 0; i < registros.size(); i++) {
                linea = registros.get(i).trim().split("\\s+");
                if (linea[3].equals(idDirActual)) {
                    if (linea[0].equalsIgnoreCase(dir)) {
                        valor = true;
                        if (linea[2].equals("<DIR>")) {
                            Excepciones.MsjError(2, dir); // Existe Directorio con mismo nombre
                        } else {
                            Excepciones.MsjError(3, dir); // Existe Archivo con mismo nombre
                        }
                    }
                }
            }
        }
        return valor;
    }

// Método para validar si registro existe bajo Directorio Actual. Devuelve el tipo
    public static String verificaTipoRegistro(String dir) {
        String valor = "noExiste";
        String idDirActual = getDirActual();
        ArrayList<String> registros;
        String[] linea;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idDirActual)) {
                        if (linea[0].equalsIgnoreCase(dir)) {
                            if (linea[2].equals("<DIR>")) {
                                valor = "<DIR>";
                            } else {
                                valor = "arch";
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Excepciones.MsjError(102, null);
        }
        return valor;
    }

// Busca y devuelve en el directorio actual el nombre del directorio Padre. *** VALIDAR SI SIGUE EN USO
    public static String getPadreID(String idHijo) {
        String idPadre = "";
        ArrayList<String> registros;
        String[] linea;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[1].equalsIgnoreCase(idHijo)) {
                        idPadre = linea[3];
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return idPadre;
    }

// Busca en el directorio actual un directorio hijo (Si hay match con un archivo da error). ***
    protected static boolean encuentraDirHijo(String idPadre, String dirHijo) {
        boolean valor = false;
        ArrayList<String> registros;
        String[] linea;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idPadre) && linea[0].equalsIgnoreCase(dirHijo)) {
                        if (linea[2].equals("arch")) {
                            Excepciones.MsjError(12, dirHijo); // Error. El "dir" corresponde a un archivo.
                        } else {
                            valor = true; // Si encuentra Match padre / hijo
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return valor;
    }

// Busca en el directorio actual un archivo hijo (Si hay match con un directorio da error).    
    protected static boolean encuentraArchivoHijo(String idPadre, String archHijo) {
        boolean valor = false;
        ArrayList<String> registros;
        String[] linea;
        try {
            registros = (ArrayList<String>) ManejoArchivos.leeArchivoTRE();
            if (!registros.isEmpty()) {
                for (int i = 0; i < registros.size(); i++) {
                    linea = registros.get(i).trim().split("\\s+");
                    if (linea[3].equals(idPadre) && linea[0].equalsIgnoreCase(archHijo)) {
                        if (linea[2].equals("<DIR>")) {
                            Excepciones.MsjError(12, archHijo); // Error. El "arch" corresponde a un directorio.
                        } else {
                            valor = true; // Si encuentra Match padre / hijo
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Excepciones.MsjError(102, null); // Error al leer el archivo MIDOSTRE
        }
        return valor;
    }

// Configura el directorio Raiz de MIDOS como Directorio Actual.
    protected static void resetDirMIDOS() {
        setDirActual(RAIZ);
    }

// Crea la ruta del Directorio Actual y la envía al Prompt correspondiente
    protected static void creaRuta() throws IOException {
        String directActual = getDirActual();
        String valor = directActual;
        ArrayList<String> dirRuta = new ArrayList<String>();
        ArrayList<String> dirIDList = new ArrayList<String>();
        StringBuilder laRuta = new StringBuilder();
        if (valor.equals(RAIZ)) {;
        } else {
            while (!valor.equals(RAIZ)) {
                dirIDList.add(valor = getPadreID(valor));
                /* Llamada para construir ruta buscando y guardando 
                los IDs de los directorios padre hasta llegar a la Raiz */
            }
        }
        Collections.reverse(dirIDList); // Se invierte orden para tener Raiz de primero
        dirIDList.add(directActual); // Se agrega de ultimo el directorio actual
        
        for (String elemento : dirIDList) {
            if (elemento.equals("M:\\")) {
                dirRuta.add("M:\\");
            } else {
                String nombre = SistemaArchivos.getNombreRegistro(elemento); 
                dirRuta.add(nombre); // Obtiene los nombres  para cada ID
            }
        }
        for (String element : dirRuta) {
            laRuta.append(element).append("\\"); // Se agregan los directorios separandolos con "\"
        }
        laRuta.deleteCharAt(3);
        setRuta(laRuta.toString()); // 
        actualizaPROMPT(); /*  Envia a la clase Linea de Comandos la version del prompt 
            utilizada en un directorio especifico  */
    }

// Configura la Linea de Comandos la version del prompt utilizada en un directorio especifico    
    protected static void actualizaPROMPT() {
        int num = getPrompt(); // Obtiene el int que almacena LineaComandos.configLineaComandos()
        switch (num) {
            case 1:
                LineaComandos.comandoPROMPT("$P", "$G");
            case 2:
                LineaComandos.comandoPROMPT("$G", "$P");
            case 3:
                LineaComandos.comandoPROMPT("$P");
            case 4:
                LineaComandos.comandoPROMPT("$G");
            default:
                LineaComandos.comandoPROMPT();
        }

    }

} // FIN
