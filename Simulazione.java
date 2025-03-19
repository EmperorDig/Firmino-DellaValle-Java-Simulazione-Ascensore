import java.util.ArrayList;
import java.util.Random;

public class Simulazione {
    public static void main(String[] args) {
        final int NUMERO_PIANI = 10;
        final int NUMERO_PERSONE = 20;
        final int CAPIENZA_ASCENSORE = 5;
        
        Ascensore ascensore = new Ascensore(CAPIENZA_ASCENSORE);
        ArrayList<Piano> piani = new ArrayList<>();
        
        for (int i = 1; i <= NUMERO_PIANI; i++) {
            piani.add(new Piano(i));
        }
        
        Random rand = new Random();
        for (int i = 1; i <= NUMERO_PERSONE; i++) {
            int pianoPartenza = rand.nextInt(NUMERO_PIANI) + 1;
            Persona persona = new Persona(i, pianoPartenza, NUMERO_PIANI);
            piani.get(pianoPartenza - 1).aggiungiPersonaCoda(persona);
        }
        
        for (int t = 0; t < 100; t++) {
            System.out.println("=== Secondo " + (t + 1) + " ===");
            
            Piano pianoAttuale = piani.get(ascensore.getPianoCorrente() - 1);
            ascensore.apriPorte();
            while (!pianoAttuale.getCodaPersone().isEmpty() && ascensore.getPersoneDentro().size() < CAPIENZA_ASCENSORE) {
                Persona persona = pianoAttuale.rimuoviPersonaCoda();
                ascensore.aggiungiPersona(persona);
            }
            ascensore.rimuoviPersoneArrivate();
            ascensore.chiudiPorte();
            
            ascensore.decidiDirezione();
            
            System.out.println(ascensore);
        }
    }
}
