package backend.model;

public class Administrador extends Usuario {

    private String idAministrador;

    public Administrador(String nombre, String correoElectronico, String contraseña, int edad, String idAministrador) {
        super(nombre, correoElectronico, contraseña, edad);
        this.idAministrador = idAministrador;
    }

    public String getIdAministrador() {
        return idAministrador;
    }

    public void setIdAministrador(String idAministrador) {
        this.idAministrador = idAministrador;
    }
}
