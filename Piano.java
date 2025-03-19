import java.util.ArrayList;

public class Piano {
    private int numeroPiano;
    private ArrayList<Persona> codaPersone;

    public Piano(int numeroPiano) {
        this.numeroPiano = numeroPiano;
        this.codaPersone = new ArrayList<>();
    }

    public void aggiungiPersonaCoda(Persona p) {
        codaPersone.add(p);
    }

    public Persona rimuoviPersonaCoda() {
        if (!codaPersone.isEmpty()) {
            return codaPersone.remove(0);
        }
        return null;
    }

    public ArrayList<Persona> getCodaPersone() {
        return codaPersone;
    }

    public int getNumeroPiano() {
        return numeroPiano;
    }

    @Override
    public String toString() {
        return "Piano " + numeroPiano + ", Persone in coda: " + codaPersone.size();
    }
}
