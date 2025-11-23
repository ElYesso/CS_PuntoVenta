package backend.model;

import java.util.ArrayList;

public class Inventario {
    
    private ArrayList<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        }
    }

    public boolean eliminarProducto(Producto producto) {
        return productos.remove(producto);
    }

    public Producto buscarProducto(String idProducto) {
        for (Producto p : productos) {
            if (p.getIdProducto().equals(idProducto)) {
                return p;
            }
        }
        return null;
    }

    // Obtener lista completa
    public ArrayList<Producto> getProductos() {
        return productos;
    }
}
