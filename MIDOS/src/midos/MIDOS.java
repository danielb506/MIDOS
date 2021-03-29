/*
 * Descripción: Clase principal de la aplicación MIDOS.
 */
package midos;

import java.io.IOException;
import midos.Utilidades.*;


/**
 * @author Daniel Bermudez
 */
public class MIDOS {

    public static void main(String[] args) throws IOException {

        CoreMIDOS.bienvenida();
        Analizador.recibeInput();

    }
}
