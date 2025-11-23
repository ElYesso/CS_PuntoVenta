package backend.model;

public class ProductoVenta {

    private Producto producto;
    private int cantidad;

    // Constructor
    public ProductoVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

}
