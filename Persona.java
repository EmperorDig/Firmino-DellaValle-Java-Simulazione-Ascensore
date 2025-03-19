import java.util.Random;

public class Persona {
    private int id;
    private int pianoDestinazione;
    
    public Persona(int id, int pianoAttuale, int numeroPiani) {
        this.id = id;
        this.pianoDestinazione = generaPianoDestinazione(pianoAttuale, numeroPiani);
    }
    
    private int generaPianoDestinazione(int pianoAttuale, int numeroPiani) {
        Random rand = new Random();
        int destinazione;
        do {
            destinazione = rand.nextInt(numeroPiani) + 1;
        } while (destinazione == pianoAttuale);
        return destinazione;
    }
    
    public void saliSuAscensore(Ascensore a) {
        if (a.getPianoCorrente() == this.pianoDestinazione && a.isPorteAperte() && a.aggiungiPersona(this)) {
            System.out.println("Persona " + id + " è salita sull'ascensore.");
        }
    }
    
    public void scendiDaAscensore(Ascensore a) {
        if (a.getPianoCorrente() == this.pianoDestinazione && a.isPorteAperte()) {
            a.rimuoviPersona(this);
            System.out.println("Persona " + id + " è scesa dall'ascensore al piano " + pianoDestinazione);
        }
    }
    
    public int getId() {
        return id;
    }
    
    public int getPianoDestinazione() {
        return pianoDestinazione;
    }
    
    @Override
    public String toString() {
        return "Persona {ID=" + id + ", Destinazione=" + pianoDestinazione + "}";
    }
}
