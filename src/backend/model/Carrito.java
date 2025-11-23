package backend.model;

import java.util.ArrayList;

public class Carrito {

    private ArrayList<ProductoVenta> productos;

    //inicia la lista del carrito
    public Carrito() {
        productos = new ArrayList<>();
    }

    //metodo que trabaja sobre el carrito
    public void agregarProducto(ProductoVenta productoVenta) {
        if (productoVenta != null) {
            productos.add(productoVenta);
        }
    }

    public boolean eliminarProducto(ProductoVenta productoVenta) {
        return productos.remove(productoVenta);
    }

    //lista los productos disponibles
    public ArrayList<ProductoVenta> getProductos() {
        return productos;
    }

    //calcular total del carrito
    public double calcularTotal() {
        double total = 0;
        for (ProductoVenta pv : productos) {
            total += pv.getSubtotal();
        }
        return total;
    }
}
