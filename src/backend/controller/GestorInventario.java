package backend.controller;

import backend.model.*;
import backend.model.bd.CSVReader;
import java.util.ArrayList;

public class GestorInventario {

    private Inventario inventarioActual = new Inventario();
    private final String rutaArchivoInventario;

    public GestorInventario(String rutaArchivoInventario) {
        this.rutaArchivoInventario = rutaArchivoInventario;
        cargarInventario();
    }

    //verifica la existencia de un inventario
    public void cargarInventario() {

        ArrayList<String[]> filasArchivo = CSVReader.leerCSV(rutaArchivoInventario);

        for (String[] datos : filasArchivo) {

            String nombreProducto = datos[0];
            String idProducto = datos[1];
            double precio = Double.parseDouble(datos[2]);
            int cantidad = Integer.parseInt(datos[3]);

            Producto productoNuevo = new Producto(
                    nombreProducto, idProducto, nombreProducto, precio);

            for (int i = 0; i < cantidad; i++) {
                inventarioActual.agregarProducto(productoNuevo);
            }
        }
    }

    public Inventario getInventario() {
        return inventarioActual;
    }
}
