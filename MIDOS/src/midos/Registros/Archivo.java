package midos.Registros;

public class Archivo extends Registro {

    private final String tipo = "arch";
    private String texto;

    public Archivo() {
    }

    public Archivo(String nombre, String idNum,  String tipo, String idPadre, String texto) {
        super();
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nombre + "\t" + idNum + "\t" +  tipo + "\t" + idPadre + "\t" + texto;
    }

} // Fin clase
