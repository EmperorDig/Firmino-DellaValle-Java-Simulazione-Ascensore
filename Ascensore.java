import java.util.*;

public class Ascensore {
    private int pianoCorrente;
    private int capienzaMassima;
    private ArrayList<Persona> personeDentro;
    private boolean porteAperte = false;
    
    public Ascensore(int capienzaMassima) {
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

    public void aggiungiPersona(Persona p) {
        if (personeDentro.size() < capienzaMassima) {
            personeDentro.add(p);
        }
    }
    public void rimuoviPersoneArrivate() {
        
    }

    public void Salita() {
        this.pianoCorrente++;
    }
    
    public void Discesa() {
        this.pianoCorrente--;
    }

    public void decidiDirezione() {

    }

    @Override
    public String toString() {
        return "piano: " + pianoCorrente + ", " + personeDentro.size() + "/" + capienzaMassima + " persone";
    }
}