package backend.model;
public abstract class PagoTarjeta extends MetodoPago {

    protected String numeroTarjeta;
    protected String nombreTitular;
    protected String vencimiento;
    protected String cvv;

    @Override
    public abstract boolean procesarPago(double monto);
}


