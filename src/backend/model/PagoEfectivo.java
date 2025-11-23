package backend.model;
public class PagoEfectivo extends MetodoPago {

    @Override
    public boolean procesarPago(double monto) {
        System.out.println("Pago en efectivo recibido.");
        return true;
    }
}
