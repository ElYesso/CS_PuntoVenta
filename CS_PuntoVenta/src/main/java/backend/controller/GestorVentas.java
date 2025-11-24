package backend.controller;

import backend.model.Carrito;
import backend.model.MetodoPago;
import backend.model.ProductoVenta;

public class GestorVentas {

    public boolean realizarPago(Carrito carrito, MetodoPago metodoPago) {
        if (carrito == null || metodoPago == null) return false;

        double total = carrito.calcularTotal();

        if (total <= 0) {
            System.out.println("El carrito está vacío.");
            return false;
        }

        boolean pagoExitoso = metodoPago.procesarPago(total);

        if (pagoExitoso) {
            System.out.println("Pago realizado con éxito. Total: " + total);
            carrito.vaciar();
        }

        return pagoExitoso;
    }

    public void eliminarProducto(Carrito carrito, ProductoVenta productoAEliminar) {
        if (carrito == null || productoAEliminar == null) return;
        carrito.eliminarProducto(productoAEliminar);
    }

    public double calcularPrecioTotal(Carrito carrito) {
        if (carrito == null) return 0;
        return carrito.calcularTotal();
    }

    public double calcularPrecioTotal(int cantidad, double precio) {
        return cantidad * precio;
    }
}
