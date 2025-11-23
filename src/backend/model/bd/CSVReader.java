package backend.model.bd;

import java.io.*;
import java.util.ArrayList;

public class CSVReader {

    public static ArrayList<String[]> leerCSV(String ruta) {
        ArrayList<String[]> filas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {

                // Saltar encabezado
                if (primera) {
                    primera = false;
                    continue;
                }

                // Cortar por comas
                String[] datos = linea.split(",");
                filas.add(datos);
            }

        } catch (Exception e) {
            System.out.println("Error leyendo CSV: " + ruta);
            e.printStackTrace();
        }

        return filas;
    }
}
