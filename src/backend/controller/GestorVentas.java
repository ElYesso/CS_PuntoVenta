package backend.controller;

import backend.model.*;
import java.lang.reflect.Method;

public class GestorVentas {
    public boolean realizarPago(Carrito carrito, MetodoPago metodoPago) {

        double totalVenta = carrito.calcularTotal();

        if (totalVenta <= 0) {
            System.out.println("El carrito está vacío.");
            return false;
        }

        try {
            Method procesarPago = metodoPago.getClass().getDeclaredMethod("procesarPago", double.class);
            procesarPago.setAccessible(true);
            Object resultado = procesarPago.invoke(metodoPago, totalVenta);
            return resultado instanceof Boolean && (Boolean) resultado;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("No se pudo procesar el pago con el método proporcionado.", e);
        }
    }
    

    public void cancelarVentaProductoVentas(Carrito carrito, String idProducto) {

        ProductoVenta productoAEliminar = null;

        for (ProductoVenta productoVenta : carrito.getProductos()) {

            if (productoVenta.getProducto().getIdProducto().equals(idProducto)) {
                productoAEliminar = productoVenta;
                break;
            }
        }

        if (productoAEliminar != null) {
            carrito.eliminarProducto(productoAEliminar);
        }
    }

    //calcula el total final
    public double calcularPrecioTotal(Carrito carrito) {
        return carrito.calcularTotal();
    }

    //calcula el precio dek carrito
    public double calcularPrecioTotal(int cantidad, double precioProducto) {
        return cantidad * precioProducto;
    }
}
