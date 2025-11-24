package backend.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import backend.model.Usuario;
import backend.model.bd.CSVReader;

public class GestorUsuario {

    private static final String RUTA_USUARIOS_CSV;

    static {
        // Localizamos el archivo dentro de target/classes/bd/
        try {
            String rawPath = GestorUsuario.class.getClassLoader()
                    .getResource("bd/ListaUsuarios.csv")
                    .getPath();

            // Decodificar espacios (%20 → espacio normal)
            RUTA_USUARIOS_CSV = URLDecoder.decode(rawPath, StandardCharsets.UTF_8);
            System.out.println("CSV de usuarios cargando desde: " + RUTA_USUARIOS_CSV);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo localizar ListaUsuarios.csv", e);
        }
    }

    // Lista en memoria
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios = CSVReader.cargarUsuarios("bd/ListaUsuarios.csv");
        System.out.println("Usuarios cargados: " + usuarios.size());
    }

    // ============================================================
    // AUTENTICACIÓN
    // ============================================================
    public static Usuario autenticar(String identificador, String password) {
        if (identificador == null || password == null) return null;

        String c = identificador.trim();
        String p = password.trim();

        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(c) &&
                u.getContrasena().equals(p)) {
                return u;
            }
        }
        return null;
    }

    // Get de usuarios en memoria
    public static ArrayList<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // ============================================================
    // REGISTRO / GUARDADO
    // ============================================================
    public static boolean agregarUsuario(Usuario nuevo) {
        if (nuevo == null) return false;

        // Verificar correo repetido
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(nuevo.getCorreo())) {
                System.out.println("Correo ya registrado: " + nuevo.getCorreo());
                return false;
            }
        }

        // Agregar a lista en memoria
        usuarios.add(nuevo);

        // Guardar CSV completo
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_USUARIOS_CSV, false))) {

            pw.println("Nombre,Correo,Contraseña,Direccion,IdUsuario,TipoDeUsuario");

            for (Usuario u : usuarios) {
                pw.printf("%s,%s,%s,%s,%s,%s%n",
                        u.getNombre(),
                        u.getCorreo(),
                        u.getContrasena(),
                        u.getDireccion(),
                        u.getIdUsuario(),
                        u.getTipoDeUsuario());
            }

            System.out.println("Usuario guardado en CSV correctamente.");

            return true;

        } catch (IOException e) {
            System.err.println("ERROR GUARDANDO USUARIO EN CSV:");
            e.printStackTrace();
            return false;
        }
    }
}
