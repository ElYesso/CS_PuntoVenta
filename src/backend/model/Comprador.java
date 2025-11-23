package backend.model;

import java.util.ArrayList;

public class Comprador extends Usuario {

    private ArrayList<Direccion> direcciones;

    // Constructor correcto llamando al de Usuario
    public Comprador(String nombre, String correoElectronico, String contraseña, int edad) {
        super(nombre, correoElectronico, contraseña, edad);
        this.direcciones = new ArrayList<>();
    }

    //agrega una dirección a la lista
    public void agregarDirecciones(Direccion direccion) {
        if (direccion != null) {
            direcciones.add(direccion);
        }
    }

    //lista las direcciones disponibles
    public ArrayList<Direccion> getDireccion() {
        return direcciones;
    }

    //elimina una direccion
    public boolean eliminarDireccion(Direccion direccion) {
        return direcciones.remove(direccion);
    }
}
