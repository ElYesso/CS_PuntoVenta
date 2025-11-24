package backend.controller;

import java.util.ArrayList;

import backend.model.Producto;
import backend.model.bd.CSVReader;

public class GestorInventario {

    private static ArrayList<Producto> inventario = new ArrayList<>();

    static {
        inventario = CSVReader.cargarInventario("bd/Inventario.csv");
    }

    public static ArrayList<Producto> obtenerProductos() {
        return new ArrayList<>(inventario);
    }

    public static Producto buscarPorId(String id) {
        for (Producto p : inventario) {
            if (p.getIdProducto().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public static void agregarProducto(Producto p) {
        inventario.add(p);
    }

    public static boolean eliminarProducto(String id) {
        return inventario.removeIf(p -> p.getIdProducto().equalsIgnoreCase(id));
    }

    public static boolean editarProducto(Producto editado) {
        for (int i = 0; i < inventario.size(); i++) {
            if (inventario.get(i).getIdProducto().equalsIgnoreCase(editado.getIdProducto())) {
                inventario.set(i, editado);
                return true;
            }
        }
        return false;
    }
}
