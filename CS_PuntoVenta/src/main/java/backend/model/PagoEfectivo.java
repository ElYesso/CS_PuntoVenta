package backend.model;

public class PagoEfectivo extends MetodoPago {

    @Override
    public boolean procesarPago(double monto) {
        if (monto <= 0) {
            System.out.println("El monto debe ser mayor a cero.");
            return false;
        }
        System.out.println("Pago en efectivo registrado.");
        return true;
    }
}
