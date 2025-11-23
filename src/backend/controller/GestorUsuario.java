package backend.controller;

import backend.model.*;
import backend.model.bd.CSVReader;
import java.util.ArrayList;

public class GestorUsuario {

    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    private InicioSesion servicioInicioSesion = new InicioSesion();
    private final String rutaArchivoUsuarios;

    public GestorUsuario(String rutaArchivoUsuarios) {
        this.rutaArchivoUsuarios = rutaArchivoUsuarios;
        cargarUsuarios();
    }

    //verifica la exitencia de usuarios en bd
    public void cargarUsuarios() {

        var filasArchivo = CSVReader.leerCSV(rutaArchivoUsuarios);

        for (String[] datos : filasArchivo) {

            String nombre = datos[0];
            String correo = datos[1];
            String contraseña = datos[2];
            String textoDireccion = datos[3];
            String idUsuario = datos[4];
            String tipoUsuario = datos[5];

            if (tipoUsuario.equalsIgnoreCase("Administrador")) {

                listaUsuarios.add(
                    new Administrador(nombre, correo, contraseña, 0, idUsuario)
                );

            } else {

                Comprador compradorNuevo = new Comprador(nombre, correo, contraseña, 0);
                compradorNuevo.agregarDirecciones(
                    new Direccion(textoDireccion, "", "", "", "", "")
                );

                listaUsuarios.add(compradorNuevo);
            }
        }
    }

    public Usuario iniciarSesion(String correo, String contraseña) {

        Usuario usuarioEncontrado =
                servicioInicioSesion.iniciarSesion(correo, contraseña, listaUsuarios);

        if (usuarioEncontrado == null) {
            System.out.println("Credenciales incorrectas.");
        } else {
            System.out.println("Inicio de sesión exitoso: " + usuarioEncontrado.getNombre());
        }

        return usuarioEncontrado;
    }

    public void registrarUsuario(Usuario usuario) {

        for (Usuario usuarioExistente : listaUsuarios) {

            if (usuarioExistente.getCorreoElectronico()
                    .equals(usuario.getCorreoElectronico())) {

                System.out.println("Ya existe un usuario con ese correo.");
                return;
            }
        }

        listaUsuarios.add(usuario);
        System.out.println("Usuario registrado correctamente.");
    }


    public void eliminarUsuario(String correoElectronico) {

        Usuario usuarioAEliminar = null;

        for (Usuario usuarioActual : listaUsuarios) {

            if (usuarioActual.getCorreoElectronico().equals(correoElectronico)) {
                usuarioAEliminar = usuarioActual;
                break;
            }
        }

        if (usuarioAEliminar != null) {
            listaUsuarios.remove(usuarioAEliminar);
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("No se encontró un usuario con ese correo.");
        }
    }

    public boolean validarCorreoElectronico(String correoElectronico) {

        if (correoElectronico == null || correoElectronico.isEmpty()) {
            return false;
        }

        return correoElectronico.contains("@") &&
               correoElectronico.contains(".");
    }

    public boolean validarContraseña(String contraseña) {

        if (contraseña == null) return false;

        return contraseña.length() >= 6;
    }

    //lista los usuarios que hay en bd
    public ArrayList<Usuario> getUsuarios() {
        return listaUsuarios;
    }
}
