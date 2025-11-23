package backend.controller;

import java.util.ArrayList;

import backend.model.Producto;
import backend.model.bd.CSVReader;

public class GestorInventario {

    private static ArrayList<Producto> inventario = new ArrayList<>();

    static {
        inventario = CSVReader.cargarInventario("bd/Inventario.csv");
        System.out.println("Inventario cargado: " + inventario.size() + " productos.");
    }

    public static ArrayList<Producto> obtenerProductos() {
        return new ArrayList<>(inventario);
    }

    public static Producto buscarPorId(String id) {
        if (id == null) return null;
        for (Producto p : inventario) {
            if (p.getIdProducto().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }
}
