import java.util.*;

public class Simulazione {
    public static void main(String[] args) {
        int secondi = 100;
        Random random = new Random();
        Scanner check = new Scanner(System.in);

        Ascensore ascensore = new Ascensore(4, 10);

        System.out.println("\n=== STATO INIZIALE ===");
        System.out.println(ascensore);
        System.out.println(ascensore.piani);

        for (int t = 0; t < secondi; t++) {
            if (t == 50) {
                System.out.println("\n=== PAUSA ===");
            }
            System.out.println("\n=== Secondo " + (t + 1) + " ===");

            // Generazione casuale di nuove persone
            if (random.nextBoolean()) { // Circa 50% di probabilità di generare una nuova persona
                int pianoPartenza = random.nextInt(10);
                int pianoDestinazione;
                do {
                    pianoDestinazione = random.nextInt(10);
                } while (pianoDestinazione == pianoPartenza); // Assicura che il piano di destinazione sia diverso

                Persona nuovaPersona = new Persona(t, pianoDestinazione);
                ascensore.piani.get(pianoPartenza).aggiungiPersonaCoda(nuovaPersona);
                System.out.println("📌 Nuova persona al piano " + pianoPartenza + " con destinazione " + pianoDestinazione);
            }

            // Stato dei piani prima del movimento
            System.out.println(ascensore.piani);

            // Simulazione apertura porte, ingresso e uscita persone
            System.out.println("🚪 L'ascensore sta aprendo le porte al piano " + ascensore.getPianoCorrente());
            ascensore.apriPorte();
            System.out.println(ascensore);
            
            for (Persona p : ascensore.getPianoCorrente().getCodaPersone()) {
                ascensore.aggiungiPersona(p);
                System.out.println("👤 Persona " + p.getId() + " è salita nell'ascensore con destinazione " + p.getPianoDestinazione());
            }
            for (Persona p : ascensore.getPianoCorrente().getCodaPersone()) {
                if (p.getPianoDestinazione() == ascensore.getPianoCorrente().getNumeroPiano()) {
                    System.out.println("👤 Persona " + p.getId() + " è scesa dall'ascensore al piano " + ascensore.getPianoCorrente().getNumeroPiano());
                }
            }
            ascensore.getPianoCorrente().getCodaPersone().clear(); // Rimuove dalla coda chi è salito
            
            ascensore.rimuoviPersoneArrivate();
            System.out.println(ascensore);

            System.out.println("🚪 L'ascensore sta chiudendo le porte.");
            ascensore.chiudiPorte();
            System.out.println(ascensore);

            // Determinazione direzione e movimento
            ascensore.decidiDirezione();
            System.out.println("🔄 Stato dell'ascensore: " + ascensore);
            //check.nextLine();
        }

        System.out.println("\n=== SIMULAZIONE TERMINATA ===");
        System.out.println("📊 Stato finale dell'ascensore: " + ascensore);
        System.out.println(ascensore.piani);
        check.close();
    }
}
