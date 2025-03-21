import java.util.*;

public class Ascensore {
    public ArrayList<Piano> piani;
    private int pianoCorrente;
    private int capienzaMassima;
    public ArrayList<Persona> personeDentro;
    private boolean porteAperte = false;
    
    public Ascensore(int capienzaMassima, int numeroPiani) {
        this.piani = new ArrayList<>();
        for (int i = 0; i < numeroPiani; i++) {
            piani.add(new Piano(i));
        }
        this.pianoCorrente = 0;
        this.capienzaMassima = capienzaMassima;
        this.personeDentro = new ArrayList<>();
    }

    public void apriPorte() {
        porteAperte = true;
    }

    public void chiudiPorte() {
        porteAperte = false;
    }

    public boolean aggiungiPersona(Persona p) {
        if (personeDentro.size() < capienzaMassima) {
            personeDentro.add(p);
            return true;
        }
        return false;
    }
    public void rimuoviPersoneArrivate() {
        Iterator<Persona> iterator = personeDentro.iterator();
        while (iterator.hasNext()) {
            Persona p = iterator.next();
            if (p.getPianoDestinazione() == pianoCorrente) {
                getPianoCorrente().aggiungiPersonaCoda(p);
                iterator.remove(); // Safely remove the element using the iterator
            }
        }
    }

    public void Salita() {
        this.pianoCorrente++;
    }
    
    public void Discesa() {
        this.pianoCorrente--;
    }

    public void decidiDirezione() {
        // Se l'ascensore ha persone dentro, gestisci prima loro
        if (!personeDentro.isEmpty()) {
            // Trova il piano destinazione più vicino nella direzione corrente
            Persona personaDaServire = trovaProssimaPersona();
            if (personaDaServire.getPianoDestinazione() > pianoCorrente) {
                Salita();
            } else if (personaDaServire.getPianoDestinazione() < pianoCorrente) {
                Discesa();
            }
            return;
        }
    
        // Se l'ascensore è vuoto, cerca il piano con persone in attesa
        Piano pianoDaServire = trovaProssimoPianoInAttesa();
    
        if (pianoDaServire != null) {
            if (pianoDaServire.getNumeroPiano() > pianoCorrente) {
                Salita();
            } else if (pianoDaServire.getNumeroPiano() < pianoCorrente) {
                Discesa();
            }
        }
    }
    

    private Persona trovaProssimaPersona() {
        Persona personaDaServire = null;
        int distanzaMinima = Integer.MAX_VALUE;
    
        for (Persona persona : personeDentro) {
            int distanza = Math.abs(persona.getPianoDestinazione() - pianoCorrente);
            if (distanza < distanzaMinima) {
                distanzaMinima = distanza;
                personaDaServire = persona;
            }
        }
        return personaDaServire;
    }    

    private Piano trovaProssimoPianoInAttesa() {
        Piano pianoDaServire = null;
        int prioritaMassima = -1; // La priorità più alta è un valore maggiore
    
        for (Piano piano : piani) {
            if (!piano.getCodaPersone().isEmpty()) {
                // Calcola la priorità: più alto è il tempo di attesa, più alta è la priorità
                int priorita = piano.getTempoAttesa();
    
                if (priorita > prioritaMassima) {
                    prioritaMassima = priorita;
                    pianoDaServire = piano;
                }
            }
        }
    
        return pianoDaServire;
    }    

    public Piano getPianoCorrente() {
        return piani.get(pianoCorrente);
    }

    public boolean isPorteAperte() {
        return porteAperte;
    }

    @Override
    public String toString() {
        return "piano: " + pianoCorrente + ", " + personeDentro.size() + "/" + capienzaMassima + " persone";
    }
}