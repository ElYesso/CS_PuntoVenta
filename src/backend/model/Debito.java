package backend.model;
public class Debito extends PagoTarjeta {
    
    private double saldo;

    @Override
    public boolean procesarPago(double monto){

        double saldoRestante;

        if (!validarSaldoDisponible(monto)){
            System.out.println("El pago no puede ser autorizado.");
            return false;
        }
        
        saldoRestante = saldo - monto;

        System.out.println("Pago aprobado por: $" + monto);
        System.err.println("saldo restante:" + saldoRestante);

        return true;
    }

    
    public boolean validarSaldoDisponible(double monto){
        double saldoDisponible = saldo;

        if (monto <= 0) {
            System.out.println("El monto debe ser mayor a cero.");
            return false;
        }

        if (saldo <= 0) {
            System.out.println("El limite disponible no puede ser menor o iguala cero");
            return false;
        }

        return saldoDisponible >= monto; 
        }
}