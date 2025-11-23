package backend.controller;

import backend.model.*;

public class GestorCarrito {

    public void aplicarDescuento(Carrito carrito, double porcentajeDescuento) {

        if (porcentajeDescuento <= 0) return;

        double factor = (100 - porcentajeDescuento) / 100;

        for (ProductoVenta productoVenta : carrito.getProductos()) {
            //opera el precio original
            double precioOriginal = productoVenta.getProducto().getPrecio();
            double precioConDescuento = precioOriginal * factor;

            productoVenta.getProducto().setPrecio(precioConDescuento);
        }

        System.out.println("Descuento aplicado: " + porcentajeDescuento + "%");
    }
}
