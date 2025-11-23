package backend.model;

import java.util.ArrayList;

public class InicioSesion {

    public Usuario iniciarSesion(String correoElectronico, String contraseña, ArrayList<Usuario> listaUsuarios) {

        for (Usuario usuarioActual : listaUsuarios) {

            boolean correoCoincide =
                    usuarioActual.getCorreoElectronico().equals(correoElectronico);

            boolean contraseñaCoincide =
                    usuarioActual.getContraseña().equals(contraseña);

            if (correoCoincide && contraseñaCoincide) {
                return usuarioActual;
            }
        }

        return null;
    }
}
