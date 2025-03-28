
import java.util.ArrayList;

public class Piano {
    
    private int numeroPiano;
    public ArrayList<Persona> codaPersone;
    private int tempoAttesa;

    public Piano(int numeroPiano) {
        this.numeroPiano = numeroPiano;
        this.codaPersone = new ArrayList<>();
    }

    public void aggiungiPersonaCoda(Persona p) {
        codaPersone.add(p);
    }


    public Persona rimuoviPersonaCoda(Persona p) {
        if (codaPersone.contains(p)) {
            codaPersone.remove(p);
            return p;
        } else {
            return null;
        }
    }

    public int getNumeroPiano() {
        return numeroPiano;
    }

    public ArrayList<Persona> getCodaPersone() {
        return codaPersone;
    }

    public void incrementaTempoAttesa() {
        tempoAttesa++;
    }

    public void resettaTempoAttesa() {
        tempoAttesa = 0;
    }

    public int getTempoAttesa() {
        return tempoAttesa;
    }

    public boolean contienePersona(Persona p) {
        return codaPersone.contains(p);
    }

    @Override
    public String toString() {
        return "Piano " + numeroPiano + ", Persone in coda: " + codaPersone.size();
    }
}