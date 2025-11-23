import backend.controller.*;
import backend.model.*;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String rutaUsuarios = "C:\\Users\\User\\Desktop\\CS_PuntoVenta\\src\\backend\\model\\bd\\ListaUsuarios.csv";
        String rutaInventario = "C:\\Users\\User\\Desktop\\CS_PuntoVenta\\src\\backend\\model\\bd\\Inventario.csv";

        GestorUsuario gestorUsuario = new GestorUsuario(rutaUsuarios);
        GestorInventario gestorInventario = new GestorInventario(rutaInventario);
        GestorCarrito gestorCarrito = new GestorCarrito();
        GestorVentas gestorVentas = new GestorVentas();

        Carrito carrito = new Carrito();
        Usuario usuarioActual = null;

        int opcion;

        do {
            System.out.println("\n===== SISTEMA DE PUNTO DE VENTA =====");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Ver inventario");
            System.out.println("3. Agregar producto al carrito");
            System.out.println("4. Ver carrito");
            System.out.println("5. Aplicar descuento al carrito");
            System.out.println("6. Realizar pago");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {

                case 1: { // INICIO DE SESIÓN
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();

                    System.out.print("Contraseña: ");
                    String contraseña = scanner.nextLine();

                    usuarioActual = gestorUsuario.iniciarSesion(correo, contraseña);
                    break;
                }

                case 2: { // VER INVENTARIO
                    System.out.println("\n--- INVENTARIO DISPONIBLE ---");

                    for (Producto p : gestorInventario.getInventario().getProductos()) {
                        System.out.println("ID: " + p.getIdProducto() +
                                " | Nombre: " + p.getNombreProducto() +
                                " | Precio: $" + p.getPrecio());
                    }

                    break;
                }

                case 3: { // AGREGAR AL CARRITO
                    System.out.print("ID del producto a agregar: ");
                    String idProducto = scanner.nextLine();

                    Producto productoEncontrado =
                            gestorInventario.getInventario().buscarProducto(idProducto);

                    if (productoEncontrado == null) {
                        System.out.println("Producto no encontrado.");
                        break;
                    }

                    System.out.print("Cantidad: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();

                    ProductoVenta venta = new ProductoVenta(productoEncontrado, cantidad);

                    carrito.agregarProducto(venta);

                    System.out.println("Producto agregado al carrito.");
                    break;
                }

                case 4: { // VER CARRITO
                    System.out.println("\n--- CARRITO ACTUAL ---");

                    for (ProductoVenta pv : carrito.getProductos()) {
                        System.out.println("Producto: " + pv.getProducto().getNombreProducto() +
                                " | Cantidad: " + pv.getCantidad() +
                                " | Subtotal: $" + pv.getSubtotal());
                    }

                    System.out.println("TOTAL: $" + carrito.calcularTotal());
                    break;
                }

                case 5: { // APLICAR DESCUENTO
                    System.out.print("Porcentaje de descuento: ");
                    double desc = scanner.nextDouble();
                    scanner.nextLine();

                    gestorCarrito.aplicarDescuento(carrito, desc);
                    break;
                }

                case 6: { // REALIZAR PAGO
                    System.out.println("\nMétodo de pago:");
                    System.out.println("1. Efectivo");
                    System.out.println("2. Débito");
                    System.out.println("3. Crédito");
                    System.out.print("Seleccione uno: ");

                    int metodo = scanner.nextInt();
                    scanner.nextLine();

                    MetodoPago pagoSeleccionado = null;

                    switch (metodo) {
                        case 1:
                            pagoSeleccionado = new PagoEfectivo();
                            break;

                        case 2:
                            pagoSeleccionado = new Debito();
                            break;

                        case 3:
                            pagoSeleccionado = new Credito();
                            break;

                        default:
                            System.out.println("Método inválido.");
                            break;
                    }

                    if (pagoSeleccionado != null) {
                        boolean exito = gestorVentas.realizarPago(carrito, pagoSeleccionado);
                        if (exito) {
                            System.out.println("Pago realizado con éxito.");
                            carrito = new Carrito(); // vaciar carrito
                        }
                    }

                    break;
                }

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}
