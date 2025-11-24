package backend.model;

public class Usuario {

    private String idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String direccion;
    private String tipoDeUsuario; // Administrador, Comprador, etc.

    public Usuario(String idUsuario,
                   String nombre,
                   String correo,
                   String contrasena,
                   String direccion,
                   String tipoDeUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.direccion = direccion;
        this.tipoDeUsuario = tipoDeUsuario;
    }

    // ========= GETTERS =========
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipoDeUsuario() {
        return tipoDeUsuario;
    }

    // ========= SETTERS =========
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipoDeUsuario(String tipoDeUsuario) {
        this.tipoDeUsuario = tipoDeUsuario;
    }
}
