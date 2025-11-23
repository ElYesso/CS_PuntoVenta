package backend.model;
import java.util.Scanner;
public class Credito extends PagoTarjeta {

    private int numeroDeMesesMax = 12;
    private double limiteDisponible;
    private int numeroMesesSeleccionados;

    @Override
    public boolean procesarPago(double monto) {

        // 1. Validar que puede autorizar el monto
        if (!puedeAutorizarMonto(monto)) {
            System.out.println("El pago no puede ser autorizado.");
            return false;
        }

        // 2. Seleccionar meses sin intereses
        numeroMesesSeleccionados = seleccionarMesesSinIntereses();

        // 3. Procesar: descontar del límite disponible
        limiteDisponible -= monto;

        System.out.println("Pago autorizado.");
        System.out.println("Meses seleccionados: " + numeroMesesSeleccionados);
        System.out.println("Pago total: $" + monto);
        System.out.println("Nuevo límite disponible: $" + limiteDisponible);

        return true;
    }


    public int seleccionarMesesSinIntereses(){
        Scanner scanner = new Scanner(System.in);

        // Lista de meses disponibles
        int[] mesesDisponibles = {1, 3, 6, 9, 12};

        System.out.println("Seleccione los meses para su pago:");

        // Mostrar la lista de meses disponibles
        for (int i = 0; i < mesesDisponibles.length; i++) {
            System.out.println((i + 1) + ". " + mesesDisponibles[i] + " meses");
        }

        System.out.print("Ingrese el número de opción: ");
        int opcion = scanner.nextInt();

        if (opcion < 1 || opcion > mesesDisponibles.length) {
            System.out.println("Opción no válida, seleccionando 1 mes por defecto.");
            return 1;
        }

        // devuelve los meses elegidos
        return mesesDisponibles[opcion - 1];

    }

    public boolean puedeAutorizarMonto(double monto){
        //valida que que el monto y limite sean validos
        //valida que el limite de credito sea mayor que el monto
        double limiteCredito = limiteDisponible;

        if (monto <= 0) {
            System.out.println("El monto debe ser mayor a cero.");
            return false;
        }

        if (limiteCredito <= 0) {
            System.out.println("El limite disponible no puede ser menor o iguala cero");
            return false;
        }

        return limiteCredito >= monto; 
        }

}