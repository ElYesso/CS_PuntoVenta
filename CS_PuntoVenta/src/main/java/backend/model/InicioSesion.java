package backend.model;

import java.util.ArrayList;

public class InicioSesion {

    public Usuario iniciarSesion(String correo,
                                 String contrasena,
                                 ArrayList<Usuario> listaUsuarios) {

        if (correo == null || contrasena == null) {
            return null;
        }

        String c = correo.trim();
        String p = contrasena.trim();

        for (Usuario usuarioActual : listaUsuarios) {
            boolean correoCoincide =
                    usuarioActual.getCorreo().equalsIgnoreCase(c);
            boolean contrasenaCoincide =
                    usuarioActual.getContrasena().equals(p);

            if (correoCoincide && contrasenaCoincide) {
                return usuarioActual;
            }
        }

        return null;
    }
}
