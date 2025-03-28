import java.util.Random;

public class prova {
    public static void main(String[] args) {
        for (int i = 100; i > 0; i--) {
            int pianoPartenza;
            int pianoDestinazione;
            Random r = new Random();
            pianoPartenza = 1;
            pianoDestinazione = r.nextInt(pianoPartenza);
            if( pianoDestinazione == pianoPartenza){
                break;
               }
        System.out.println(i + " Piano partenza: " + pianoPartenza + " Piano destinazione: " + pianoDestinazione);
    
        }
            }
}
