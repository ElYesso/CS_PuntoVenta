package backend.model;

import java.util.ArrayList;

public class Inventario {

    private ArrayList<Producto> productos;

    public Inventario() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        }
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public Producto buscarProducto(String idProducto) {
        if (idProducto == null) return null;
        for (Producto p : productos) {
            if (p.getIdProducto().equalsIgnoreCase(idProducto.trim())) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}
