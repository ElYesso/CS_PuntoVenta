package backend;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import backend.controller.GestorInventario;
import backend.controller.GestorUsuario;
import backend.model.Producto;
import backend.model.Usuario;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Server {

    public static void main(String[] args) {

        port(4567);
        Gson gson = new Gson();

        staticFiles.location("/public");

        // LOGIN
        post("/api/login", (req, res) -> {
            res.type("application/json");

            String id = req.queryParams("identificador");
            String pass = req.queryParams("password");

            if (id == null || id.isBlank() || pass == null || pass.isBlank()) {
                return gson.toJson(Map.of(
                        "status", "error",
                        "msg", "Datos incompletos"
                ));
            }

            Usuario u = GestorUsuario.autenticar(id, pass);

            if (u != null) {
                return gson.toJson(Map.of(
                        "status", "ok",
                        "nombre", u.getNombre(),
                        "correo", u.getCorreo(),
                        "tipoUsuario", u.getTipoDeUsuario()
                ));
            } else {
                return gson.toJson(Map.of(
                        "status", "error",
                        "msg", "Credenciales incorrectas"
                ));
            }
        });

        // LISTAR PRODUCTOS
        get("/api/productos", (req, res) -> {
            res.type("application/json");

            ArrayList<Producto> productos = GestorInventario.obtenerProductos();

            return gson.toJson(productos);
        });

        // DETALLE DE PRODUCTO (opcional)
        get("/api/producto/:id", (req, res) -> {
            res.type("application/json");

            String id = req.params("id");
            Producto p = GestorInventario.buscarPorId(id);

            if (p == null) {
                return gson.toJson(Map.of(
                        "status", "error",
                        "msg", "Producto no encontrado"
                ));
            }

            return gson.toJson(p);
        });
    }
}
