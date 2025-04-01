import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public class Ascensore {
    private int nciclo;
    public ArrayList<Piano> piani;
    private int pianoCorrente;
    private int capienzaMassima;
    public ArrayList<Persona> personeDentro;
    private boolean porteAperte = false;
    private boolean haApertoPorte = false;
    private int probabilita = 0;
    private Random random;
    
    public Ascensore(int capienzaMassima, int numeroPiani, int probabilita) {
        this.random = new Random();
        this.piani = new ArrayList<>();
        for (int i = 0; i < numeroPiani; i++) {
            piani.add(new Piano(i));
        }
        this.pianoCorrente = 0;
        this.capienzaMassima = capienzaMassima;
        this.probabilita = probabilita;
        this.personeDentro = new ArrayList<>();
        this.nciclo = 0;
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
    public void rimuoviPersona(Persona p) {
        personeDentro.remove(p);
    }

    public void rimuoviPersoneArrivate() {
        Iterator<Persona> iterator = personeDentro.iterator();
        while (iterator.hasNext()) {
            Persona persona = iterator.next();
            if (persona.getPianoDestinazione() == pianoCorrente) {
                System.out.println("Persona " + persona.getId() + " è scesa al piano: " + pianoCorrente);
                iterator.remove();
            }
        }
    }

    public int getCapienzaMassima() {
        return capienzaMassima;
    }

    public boolean isHaApertoPorte() {
        return haApertoPorte;
    }

    public void Salita() {
        this.pianoCorrente++;
    }
    
    public void Discesa() {
        this.pianoCorrente--;
    }

    public String idCoda() {
        String str = "";
        for (int i = 0; i < personeDentro.size(); i++) {
            str += personeDentro.get(i).getId() + " ";
        }
        return str;
    }

    public void decidiDirezione() {
        // Se l'ascensore ha persone dentro, gestisci prima loro
        if (!personeDentro.isEmpty()) {
            Persona personaDaServire = trovaProssimaPersona();
            if (personaDaServire.getPianoDestinazione() > pianoCorrente) {
                Salita();
            } else if (personaDaServire.getPianoDestinazione() < pianoCorrente) {
                Discesa();
            }
            return;
        }
    
        // Se l'ascensore è vuoto o pieno ma non può far salire nessuno, cerca il piano con persone in attesa
        Piano pianoDaServire = trovaProssimoPianoInAttesa();
    
        if (pianoDaServire != null) {
            if (pianoDaServire.getNumeroPiano() > pianoCorrente) {
                Salita();
            } else if (pianoDaServire.getNumeroPiano() < pianoCorrente) {
                Discesa();
            }
        }
    }
    
    
    private Piano trovaPianoPrioritario(int threshold) {
        Piano pianoPrioritario = null;
        for (Piano piano : piani) {
            if (piano.getTempoAttesa() >= threshold) {
                threshold = piano.getTempoAttesa();
                pianoPrioritario = piano;
            }
        }
        return pianoPrioritario;
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

    public void ciclo() {
        haApertoPorte = false;
        System.out.println("\n=== CICLO " + (nciclo + 1) + " ===");
    
        // 1. Generazione casuale di nuove persone
        if (random.nextInt(101) <= this.probabilita) {
            Persona nuovaPersona = new Persona(nciclo, this);
            piani.get(nuovaPersona.getPianoPartenza()).aggiungiPersonaCoda(nuovaPersona);
            System.out.println("Nuova persona al piano " + nuovaPersona.getPianoPartenza() + " con destinazione " + nuovaPersona.getPianoDestinazione());
        }
    
        // 2. Stato dei piani prima del movimento
        System.out.println(piani);
    
        // 3. Simulazione apertura porte, ingresso e uscita persone
        if (!personeDentro.isEmpty()) {
            // Gestione delle persone che devono scendere
            gestisciPersoneCheScendono();
        }
        if (personeDentro.size() < capienzaMassima && !getPianoCorrente().getCodaPersone().isEmpty()) {
            // Gestione delle persone che devono salire
            apriPorte();
            haApertoPorte = true;
            gestisciPersoneCheSalgono();
        }
    
        // 4. Chiusura delle porte
        aggiornaTempoAttesa();
        System.out.println("L'ascensore sta chiudendo le porte.");
        chiudiPorte();
        System.out.println(this);
    
        // 5. Determinazione direzione e movimento
        decidiDirezione();
        System.out.println("Stato dell'ascensore: " + this);
        System.out.println("Tempi d'attesa ai piani: ");
        for (Piano piano : piani) {
            System.out.println("Piano " + piano.getNumeroPiano() + ": " + piano.getTempoAttesa() + " secondi");
        }
        Piano pianoPrioritario = trovaPianoPrioritario(0);
        if (personeDentro.isEmpty() && pianoPrioritario != null && pianoPrioritario.getTempoAttesa() > 0) {
            System.out.println("Destinazione dell'ascensore: " + pianoPrioritario.getNumeroPiano() + " con tempo di attesa " + pianoPrioritario.getTempoAttesa() + " secondi");
        } else if (!personeDentro.isEmpty()) {
            System.out.println("Destinazione dell'ascensore: " + personeDentro.get(0).getPianoDestinazione());
        } else {
            System.out.println("L'ascensore non ha una destinazione");
        }
        nciclo++;
    }
    
    
    private void gestisciPersoneCheScendono() {    
        Iterator<Persona> iterator = personeDentro.iterator();
        while (iterator.hasNext()) {
            Persona p = iterator.next();
            if (p.getPianoDestinazione() == pianoCorrente) {
                System.out.println("L'ascensore sta aprendo le porte al piano " + pianoCorrente);
                apriPorte();
                haApertoPorte = true;
                rimuoviPersoneArrivate();
                System.out.println(this);
                break;
            }
        }
    }

    private void gestisciPersoneCheSalgono() {
        Iterator<Persona> iterator = getPianoCorrente().getCodaPersone().iterator();
        while (iterator.hasNext()) {
            Persona p = iterator.next();
            if (personeDentro.size() < capienzaMassima &&
                (personeDentro.isEmpty() || stessaDirezione(p))) {
                aggiungiPersona(p);
                System.out.println("Persona " + p.getId() + " è salita nell'ascensore con destinazione " + p.getPianoDestinazione());
                iterator.remove(); // Rimuove la persona in modo sicuro
            }
        }
    }

    private boolean stessaDirezione(Persona p) {
        return (p.getDirezione() && personeDentro.get(0).getDirezione()) ||
               (!p.getDirezione() && !personeDentro.get(0).getDirezione());
    }

    public void aggiornaTempoAttesa() {
        for (Piano piano : piani) {
            if (!piano.getCodaPersone().isEmpty() && !(piano.getNumeroPiano() == pianoCorrente && porteAperte)) {
                piano.incrementaTempoAttesa();
            }
            else if (piano.getNumeroPiano() == pianoCorrente && porteAperte) {
                piano.resettaTempoAttesa();
            }
        }
    }

    @Override
    public String toString() {
        return "piano: " + pianoCorrente + ", " + personeDentro.size() + "/" + capienzaMassima + " persone";
    }
}