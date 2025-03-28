import java.awt.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Simulazione {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci la capienza dell'ascensore: ");
        int capienza = 0;
        while (capienza <= 0 ) {
            capienza = scanner.nextInt();
            if (capienza <= 0) {
                System.out.println("Inserisci un valore maggiore di 0.");
            }
        }
        System.out.println("Inserisci il numero di simulazioni simultanee: ");
        int n = 0;
        while (n <= 0 ) {
            n = scanner.nextInt();
            if (n <= 0) {
                System.out.println("Inserisci un valore maggiore di 0.");
            }
        }
        
        Ascensore[] ascensori = new Ascensore[n];
        for (int i = 0; i < n; i++) {
            ascensori[i] = new Ascensore(capienza, 10);
        } // Ascensore con 4 posti e 10 piani


        // Crea il renderer
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < ascensori.length; i++) {
                new Elevator2DRenderer(ascensori[i]);
            }
        });
    }
}
