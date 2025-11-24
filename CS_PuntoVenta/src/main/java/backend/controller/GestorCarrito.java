package backend.controller;

import backend.model.Carrito;
import backend.model.Producto;
import backend.model.ProductoVenta;

public class GestorCarrito {

    private Carrito carrito = new Carrito();

    public void agregarProducto(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) return;

        for (ProductoVenta pv : carrito.getProductos()) {
            if (pv.getProducto().getIdProducto().equalsIgnoreCase(producto.getIdProducto())) {
                pv.setCantidad(pv.getCantidad() + cantidad);
                return;
            }
        }

        carrito.agregarProducto(new ProductoVenta(producto, cantidad));
    }

    public boolean cambiarCantidad(String idProducto, int cantidad) {
        if (cantidad <= 0) return false;

        for (ProductoVenta pv : carrito.getProductos()) {
            if (pv.getProducto().getIdProducto().equalsIgnoreCase(idProducto)) {
                pv.setCantidad(cantidad);
                return true;
            }
        }
        return false;
    }

    public void eliminarProducto(String idProducto) {
        carrito.eliminarProductoPorId(idProducto);
    }

    public Carrito obtenerCarrito() {
        return carrito;
    }

    public void vaciarCarrito() {
        carrito.vaciar();
    }
}
