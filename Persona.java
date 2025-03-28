
import java.util.Random;

public class Persona {
    private int id;
    private int pianoPartenza;
    private int pianoDestinazione;
    public boolean direzioneAlto;
    
    public Persona(int id, Ascensore a) {
        this.id = id;
        Random r = new Random();
        direzioneAlto = r.nextBoolean();
        pianoPartenza = r.nextInt(a.piani.size());
        if (pianoPartenza == 0) {
            direzioneAlto = true;
        }
        if (pianoPartenza == a.piani.size() - 1) {
            direzioneAlto = false;
        }
        if (direzioneAlto) {
            pianoDestinazione = r.nextInt(a.piani.size() - 1 - pianoPartenza) + pianoPartenza + 1;
        }
        else {
            pianoDestinazione = r.nextInt(pianoPartenza);
        }
    }

    public void saliSuAscensore(Ascensore a) {
        if (a.getPianoCorrente().contienePersona(this) && a.isPorteAperte()) {
            if(a.aggiungiPersona(this)) {
                a.getPianoCorrente().rimuoviPersonaCoda(this);
                System.out.println("Persona " + id + " è salita sull'ascensore.");
                a.aggiungiPersona(this);
            }
        }        
    }
    
    public Persona scendiDaAscensore(Ascensore a) {
        if (a.getPianoCorrente().getNumeroPiano() == pianoDestinazione && a.isPorteAperte()) {
            a.rimuoviPersona(this);
            System.out.println("Persona " + id + " è scesa dall'ascensore al piano " + pianoDestinazione + ".");
            if (pianoDestinazione == 0) {
                System.out.println("Persona " + id + " è stata cancellata.");
                return null;
            }
        }
        return this;
    }

    public int getPianoDestinazione() {
        return pianoDestinazione;
    }

    public int getPianoPartenza() {
        return pianoPartenza;
    }

    public int getId() {
        return id;
    }

    public boolean getDirezione() {
        return direzioneAlto;
    }

    @Override
    public String toString() {
        return "Persona {ID=" + id + ", Partenza=" + pianoPartenza + ", Destinazione=" + pianoDestinazione + "}";
    }
}
