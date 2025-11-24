package backend.model;

public class Credito extends PagoTarjeta {

    private double limiteDisponible;

    public Credito(double limiteDisponible) {
        this.limiteDisponible = limiteDisponible;
    }

    public double getLimiteDisponible() {
        return limiteDisponible;
    }

    public void setLimiteDisponible(double limiteDisponible) {
        this.limiteDisponible = limiteDisponible;
    }

    @Override
    public boolean procesarPago(double monto) {
        if (monto <= 0) {
            System.out.println("El monto debe ser mayor a cero.");
            return false;
        }
        if (limiteDisponible < monto) {
            System.out.println("Crédito insuficiente.");
            return false;
        }
        limiteDisponible -= monto;
        System.out.println("Pago con tarjeta de crédito aprobado.");
        return true;
    }
}
