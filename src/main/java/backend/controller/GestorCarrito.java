package backend.controller;

import backend.model.Carrito;
import backend.model.Producto;
import backend.model.ProductoVenta;

public class GestorCarrito {

    private Carrito carrito = new Carrito();

    public void agregarProducto(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) return;
        ProductoVenta pv = new ProductoVenta(producto, cantidad);
        carrito.agregarProducto(pv);
    }

    public void eliminarProductoPorId(String idProducto) {
        carrito.eliminarProductoPorId(idProducto);
    }

    public double calcularTotal() {
        return carrito.calcularTotal();
    }

    public void aplicarDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento <= 0) return;
        double factor = (100 - porcentajeDescuento) / 100.0;

        for (ProductoVenta pv : carrito.getProductos()) {
            double precioActual = pv.getProducto().getPrecio();
            pv.getProducto().setPrecio(precioActual * factor);
        }
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void vaciarCarrito() {
        carrito.vaciar();
    }
}
