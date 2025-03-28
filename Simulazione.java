import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Simulazione {
    public static void main(String[] args) {
        int secondi = 100;
        Ascensore ascensore = new Ascensore(4, 10); // Ascensore con 4 posti e 10 piani

        // Crea il renderer
        SwingUtilities.invokeLater(() -> {
            Elevator2DRenderer renderer = new Elevator2DRenderer(ascensore);

            // Timer per il ciclo di simulazione
            new Timer().scheduleAtFixedRate(new TimerTask() {
                private int ciclo = 0;

                @Override
                public void run() {
                    if (ciclo < secondi) {
                        ascensore.ciclo(ciclo);
                        renderer.repaint();
                        ciclo++;
                    } else {
                        cancel(); // Termina la simulazione
                        System.out.println("\n=== SIMULAZIONE TERMINATA ===");
                        System.out.println("Stato finale dell'ascensore: " + ascensore);
                    }
                }
            }, 0, 1000); // Aggiornamento ogni secondo
        });
    }
}
