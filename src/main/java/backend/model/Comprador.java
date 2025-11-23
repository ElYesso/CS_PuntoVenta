package backend.model;

public class Comprador extends Usuario {

    public Comprador(String idUsuario,
                     String nombre,
                     String correo,
                     String contrasena,
                     String direccion) {
        super(idUsuario, nombre, correo, contrasena, direccion, "Comprador");
    }
}
