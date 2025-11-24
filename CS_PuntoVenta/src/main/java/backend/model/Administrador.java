package backend.model;

public class Administrador extends Usuario {

    public Administrador(String idUsuario,
                         String nombre,
                         String correo,
                         String contrasena,
                         String direccion) {
        super(idUsuario, nombre, correo, contrasena, direccion, "Administrador");
    }

    
}
