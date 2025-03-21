public class Persona {
    private int id;
    private int pianoDestinazione;
    
    public Persona(int id, int pianoDestinazione) {
        this.id = id;
        this.pianoDestinazione = pianoDestinazione;
    }
    
    public void saliSuAscensore(Ascensore a) {
        if (a.getPianoCorrente().contienePersona(this) && a.isPorteAperte()) {
            if(a.aggiungiPersona(this)) {
                a.getPianoCorrente().rimuoviPersonaCoda();
                System.out.println("Persona " + id + " è salita sull'ascensore.");
                a.aggiungiPersona(this);
            }
        }
    }
    
    public void scendiDaAscensore(Ascensore a) {
        if (a.getPianoCorrente().getNumeroPiano() == this.pianoDestinazione && a.isPorteAperte()) {
            System.out.println("Persona " + id + " è scesa dall'ascensore al piano " + this.pianoDestinazione);
            
            a.personeDentro.remove(this);

            if (a.getPianoCorrente().getNumeroPiano() == 0) {
                System.out.println("Persona " + id + " è uscita definitivamente dal sistema.");
            } else {
                a.getPianoCorrente().aggiungiPersonaCoda(this);
                System.out.println("Persona " + id + " è ora in attesa al piano " + a.getPianoCorrente().getNumeroPiano());
            }
            this.pianoDestinazione = 0;
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
