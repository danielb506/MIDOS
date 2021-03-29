package midos.Registros;



public class Directorio extends Registro {

    private final String tipo = "<DIR>";


    public Directorio() {
    }

    public Directorio(String nombre, String idNum,  String tipo, String idPadre) {
        super();
    }


    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nombre + "\t" + idNum + "\t" +  tipo + "\t" + idPadre;
    }

    
    
    

} // Fin clase 
