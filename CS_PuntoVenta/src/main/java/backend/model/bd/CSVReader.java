package backend.model.bd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import backend.model.Producto;
import backend.model.Usuario;

public class CSVReader {

    // Inventario.csv
    // NombreProducto,IdProducto,Precio,Cantidad,Descripcion,Imagen
    public static ArrayList<Producto> cargarInventario(String resourcePath) {
        ArrayList<Producto> productos = new ArrayList<>();

        try (InputStream is = CSVReader.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (is == null) {
                System.err.println("No se encontró el recurso: " + resourcePath);
                return productos;
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))) {

                String line;
                boolean firstLine = true;

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    String[] parts = line.split(",", -1);
                    if (parts.length < 6) {
                        System.err.println("Línea inválida en inventario: " + line);
                        continue;
                    }

                    String nombre = parts[0];
                    String id = parts[1];
                    double precio;
                    int cantidad;

                    try {
                        precio = Double.parseDouble(parts[2]);
                        cantidad = Integer.parseInt(parts[3]);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parseando números en inventario: " + line);
                        continue;
                    }

                    String descripcion = parts[4];
                    String imagen = parts[5];

                    Producto p = new Producto(id, nombre, precio, cantidad, descripcion, imagen);
                    productos.add(p);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return productos;
    }

    // ListaUsuarios.csv
    // Nombre,Correo,Contraseña,Direccion,IdUsuario,TipoDeUsuario
    public static ArrayList<Usuario> cargarUsuarios(String resourcePath) {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try (InputStream is = CSVReader.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (is == null) {
                System.err.println("No se encontró el recurso: " + resourcePath);
                return usuarios;
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))) {

                String line;
                boolean firstLine = true;

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    String[] parts = line.split(",", -1);
                    if (parts.length < 6) {
                        System.err.println("Línea inválida en usuarios: " + line);
                        continue;
                    }

                    String nombre = parts[0];
                    String correo = parts[1];
                    String contrasena = parts[2];
                    String direccion = parts[3];
                    String idUsuario = parts[4];
                    String tipoUsuario = parts[5];

                    Usuario u = new Usuario(idUsuario, nombre, correo, contrasena, direccion, tipoUsuario);
                    usuarios.add(u);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
}
