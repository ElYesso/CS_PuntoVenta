package backend.model;

import java.util.ArrayList;

public class Carrito {

    private ArrayList<ProductoVenta> productos;

    public Carrito() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(ProductoVenta productoVenta) {
        if (productoVenta == null) return;
        productos.add(productoVenta);
    }

    public void eliminarProducto(ProductoVenta productoVenta) {
        productos.remove(productoVenta);
    }

    public void eliminarProductoPorId(String idProducto) {
        if (idProducto == null) return;
        productos.removeIf(pv ->
                pv.getProducto().getIdProducto().equalsIgnoreCase(idProducto.trim()));
    }

    public ArrayList<ProductoVenta> getProductos() {
        return productos;
    }

    public double calcularTotal() {
        double total = 0;
        for (ProductoVenta pv : productos) {
            total += pv.getSubtotal();
        }
        return total;
    }

    public void vaciar() {
        productos.clear();
    }
}
