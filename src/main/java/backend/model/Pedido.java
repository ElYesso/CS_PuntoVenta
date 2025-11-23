package backend.model;

public class Pedido {

    private String idPedido;
    private Usuario usuario;
    private Carrito carrito;
    private double total;

    public Pedido(String idPedido, Usuario usuario, Carrito carrito) {
        this.idPedido = idPedido;
        this.usuario = usuario;
        this.carrito = carrito;
        this.total = carrito != null ? carrito.calcularTotal() : 0.0;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public double getTotal() {
        return total;
    }
}
