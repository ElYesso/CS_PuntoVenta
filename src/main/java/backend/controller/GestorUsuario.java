package backend.controller;

import java.util.ArrayList;

import backend.model.Usuario;
import backend.model.bd.CSVReader;

public class GestorUsuario {

    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios = CSVReader.cargarUsuarios("bd/ListaUsuarios.csv");
        System.out.println("Usuarios cargados: " + usuarios.size());
    }

    public static Usuario autenticar(String correo, String password) {
        if (correo == null || password == null) return null;

        String c = correo.trim();
        String p = password.trim();

        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(c)
                    && u.getContrasena().equals(p)) {
                return u;
            }
        }
        return null;
    }

    public static ArrayList<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
}
