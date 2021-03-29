// Super clase
package midos.Registros;

public class Registro {

    protected String nombre;
    protected String idNum;
    protected String idPadre;

    // Getters y Setters 
    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    @Override
    public String toString() {
        return nombre + "\t" + idNum + "\t" + idPadre;
    }

} // Fin clase
