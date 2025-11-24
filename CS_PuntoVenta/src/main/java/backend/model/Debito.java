package backend.model;

public class Debito extends PagoTarjeta {

    private double saldo;

    public Debito(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean procesarPago(double monto) {
        if (monto <= 0) {
            System.out.println("El monto debe ser mayor a cero.");
            return false;
        }
        if (saldo < monto) {
            System.out.println("Saldo insuficiente en la tarjeta de débito.");
            return false;
        }
        saldo -= monto;
        System.out.println("Pago con tarjeta de débito aprobado.");
        return true;
    }
}
