package backend;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import backend.controller.GestorCarrito;
import backend.controller.GestorInventario;
import backend.controller.GestorUsuario;
import backend.model.Carrito;
import backend.model.Comprador;
import backend.model.Producto;
import backend.model.Usuario;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class Server {

    public static void main(String[] args) {

        port(4567);
        Gson gson = new Gson();

        GestorInventario gestorInventario = new GestorInventario();
        GestorCarrito gestorCarrito = new GestorCarrito();

        // ============================================================
        //  CARRITO
        // ============================================================

        post("/api/carrito/agregar", (req, res) -> {
            String idProducto = req.queryParams("idProducto");
            int cantidad = Integer.parseInt(req.queryParams("cantidad"));

            Producto p = gestorInventario.buscarPorId(idProducto);
            if (p == null) {
                return gson.toJson(new Respuesta("error", "Producto no encontrado"));
            }

            gestorCarrito.agregarProducto(p, cantidad);

            return gson.toJson(new Respuesta("ok", "Agregado al carrito"));
        });

        get("/api/carrito/obtener", (req, res) -> {
            Carrito c = gestorCarrito.obtenerCarrito();

            JsonObject json = new JsonObject();
            json.add("productos", gson.toJsonTree(c.getProductos()));
            json.addProperty("total", c.calcularTotal());

            return json;
        });

        post("/api/carrito/cambiarCantidad", (req, res) -> {
            String idProducto = req.queryParams("idProducto");
            int cantidad = Integer.parseInt(req.queryParams("cantidad"));

            boolean ok = gestorCarrito.cambiarCantidad(idProducto, cantidad);

            if (!ok)
                return gson.toJson(new Respuesta("error", "No se pudo cambiar la cantidad"));

            return gson.toJson(new Respuesta("ok", "Cantidad actualizada"));
        });

        post("/api/carrito/eliminar", (req, res) -> {
            String idProducto = req.queryParams("idProducto");
            gestorCarrito.eliminarProducto(idProducto);
            return gson.toJson(new Respuesta("ok", "Producto eliminado"));
        });

        post("/api/carrito/vaciar", (req, res) -> {
            gestorCarrito.vaciarCarrito();
            return gson.toJson(new Respuesta("ok", "Carrito vaciado"));
        });

        // ============================================================
        //  REGISTRO DE USUARIO
        // ============================================================

        post("/api/register", (req, res) -> {

            String nombre = req.queryParams("nombre");
            String direccion = req.queryParams("direccion");
            String correo = req.queryParams("correo");
            String password = req.queryParams("password");

            if (nombre == null || direccion == null || correo == null || password == null)
                return gson.toJson(new Respuesta("error", "Datos incompletos"));

            String nuevoId = "USR_" + (GestorUsuario.getUsuarios().size() + 1);

            Usuario nuevo = new Comprador(
                nuevoId,
                nombre,
                correo,
                password,
                direccion
            );

            boolean ok = GestorUsuario.agregarUsuario(nuevo);

            if (!ok)
                return gson.toJson(new Respuesta("error", "El correo ya está registrado"));

            return gson.toJson(new Respuesta("ok", "Usuario registrado correctamente"));
        });

        // ============================================================
        //  LOGIN
        // ============================================================

        post("/api/login", (req, res) -> {

            String correo = req.queryParams("identificador");
            String pass = req.queryParams("password");

            Usuario user = GestorUsuario.autenticar(correo, pass);

            if (user == null)
                return gson.toJson(new Respuesta("error", "Credenciales incorrectas"));

            JsonObject json = new JsonObject();
            json.addProperty("status", "ok");
            json.addProperty("tipoUsuario", user.getTipoDeUsuario());

            return json;
        });

        // ============================================================
        //  PRODUCTOS
        // ============================================================

        get("/api/productos", (req, res) -> {
            return gson.toJson(GestorInventario.obtenerProductos());
        });

        get("/api/producto/:id", (req, res) -> {
            Producto p = gestorInventario.buscarPorId(req.params("id"));

            if (p == null)
                return gson.toJson(new Respuesta("error", "Producto no encontrado"));

            return gson.toJson(p);
        });

        // ============================================================
        //  ADMINISTRACIÓN
        // ============================================================

        post("/api/admin/eliminarProducto", (req, res) -> {
            String id = req.queryParams("id");
            boolean ok = GestorInventario.eliminarProducto(id);
            return gson.toJson(new Respuesta(ok ? "ok" : "error", "Producto eliminado"));
        });

        post("/api/admin/agregarProducto", (req, res) -> {

            String id = req.queryParams("id");
            String nombre = req.queryParams("nombre");
            double precio = Double.parseDouble(req.queryParams("precio"));
            int cantidad = Integer.parseInt(req.queryParams("cantidad"));
            String descripcion = req.queryParams("descripcion");
            String imagen = req.queryParams("imagen");

            Producto p = new Producto(id, nombre, precio, cantidad, descripcion, imagen);

            GestorInventario.agregarProducto(p);

            return gson.toJson(new Respuesta("ok", "Producto agregado"));
        });

        post("/api/admin/editarProducto", (req, res) -> {

            String id = req.queryParams("id");
            String nombre = req.queryParams("nombre");
            double precio = Double.parseDouble(req.queryParams("precio"));
            int cantidad = Integer.parseInt(req.queryParams("cantidad"));
            String descripcion = req.queryParams("descripcion");
            String imagen = req.queryParams("imagen");

            Producto p = new Producto(id, nombre, precio, cantidad, descripcion, imagen);

            boolean ok = GestorInventario.editarProducto(p);

            return gson.toJson(new Respuesta(ok ? "ok" : "error", "Producto actualizado"));
        });
    }

    // ============================================================
    //  CLASE DE RESPUESTA JSON
    // ============================================================
    public static class Respuesta {
        String status;
        String msg;

        public Respuesta(String status, String msg) {
            this.status = status;
            this.msg = msg;
        }
    }
}
