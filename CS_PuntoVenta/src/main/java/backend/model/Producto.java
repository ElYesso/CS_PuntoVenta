package backend.model;

public class Producto {

    private String idProducto;
    private String nombreProducto;
    private double precio;
    private int cantidad;      // stock
    private String descripcion;
    private String imagen;     // ruta como /assets/img/collar.jpg

    public Producto(String idProducto,
                    String nombreProducto,
                    double precio,
                    int cantidad,
                    String descripcion,
                    String imagen) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Lo agregamos para evitar errores si en algún punto lo usas
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
