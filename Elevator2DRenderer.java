import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class Elevator2DRenderer extends JPanel {

    //Immagini e font
    Image personImage = new ImageIcon("./Assets/person.png").getImage();
    Image elevatorImage = new ImageIcon("./Assets/elevator.png").getImage();
    Font font;

    {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("./Assets/GothamBold.ttf"));
        } catch (FontFormatException | IOException e) {
            font = new Font("SansSerif", Font.BOLD, 12); // Fallback font
            System.err.println("Errore nel caricamento del font: " + e.getMessage());
        }
    }

    private static final int FLOOR_HEIGHT = 90; // Altezza grafica di ogni piano
    private static final int ELEVATOR_WIDTH = 75; // Larghezza dell'ascensore
    private static final int ELEVATOR_HEIGHT = 90; // Altezza dell'ascensore
    private static final int BUILDING_WIDTH = 400; // Larghezza dell'edificio

    private final Ascensore ascensore;
    private Timer timer;
    private int delay = 1000; // Velocità iniziale (1000 ms per ciclo)
    private boolean isPaused = false;

    public Elevator2DRenderer(Ascensore ascensore) {
        this.ascensore = ascensore;

        // Configura la finestra
        JFrame frame = new JFrame("Simulazione Ascensore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 1080); // Dimensione della finestra
        frame.setLocationRelativeTo(null);

        // Pannello principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(this, BorderLayout.CENTER);

        // Slider per la velocità
        JSlider speedSlider = new JSlider(0, 5000, delay);
        speedSlider.setMajorTickSpacing(1000);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> {
            delay = speedSlider.getValue();
            timer.setDelay(delay);
        });

        // Pulsante pausa/ripresa
        JButton pauseButton = new JButton("Pausa");
        pauseButton.addActionListener(e -> {
            isPaused = !isPaused;
            pauseButton.setText(isPaused ? "Riprendi" : "Pausa");
            if (isPaused) {
                timer.stop();
            } else {
                timer.start();
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            resetTimer();
            repaint();
        });

        // Pannello di controllo
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Velocità:"));
        controlPanel.add(speedSlider);
        controlPanel.add(pauseButton);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);

        frame.setVisible(true);

        // Timer per il ciclo della simulazione
        startTimer();
    }

    private void startTimer() {
        timer = new Timer(delay, e -> {
            if (!isPaused) {
                ascensore.ciclo(); // Passa il numero di secondi trascorsi
                repaint();
            }
        });
        timer.start();
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        startTimer();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font.deriveFont(14f));

        // Disegna i piani
        List<Piano> piani = ascensore.piani;
        for (int i = 0; i < piani.size(); i++) {
            int y = getHeight() - i * FLOOR_HEIGHT - 170;
            g.setColor(Color.BLACK);
            g.drawLine(0, y, BUILDING_WIDTH, y); // Linea separatrice del piano
            g.drawString("Piano " + i, 10, y + 20);
            g.drawString("Tempo di attesa " + piani.get(i).getTempoAttesa(), 10, y + 40);

            // Mostra visivamente le persone in attesa accanto ai piani
            List<Persona> coda = piani.get(i).getCodaPersone();
            for (int j = 0; j < coda.size(); j++) {
                int personX = BUILDING_WIDTH + 20 + j * 25;
                int personY = y + 10;
                g.drawImage(personImage, personX, personY, 25, 25, this); // Disegna un cerchio per ogni persona
            }
        }

        // Disegna l'ascensore
        int pianoCorrente = ascensore.getPianoCorrente().getNumeroPiano();
        int elevatorY = (piani.size() - 1 - pianoCorrente) * FLOOR_HEIGHT;
        int elevatorX = BUILDING_WIDTH / 2 - ELEVATOR_WIDTH / 2;
        g.drawImage(elevatorImage, elevatorX, elevatorY, ELEVATOR_WIDTH, ELEVATOR_HEIGHT, this);

        // Disegna le persone dentro l'ascensore
        g.setColor(Color.BLACK);
        g.drawString(ascensore.personeDentro.size() + "/" + ascensore.getCapienzaMassima(), elevatorX + ELEVATOR_WIDTH + 10, elevatorY + 25);

        // Disegna lo stato delle porte
        g.setColor(ascensore.isHaApertoPorte() ? Color.GREEN : Color.RED);
        g.drawString(ascensore.isHaApertoPorte() ? "Porte Aperte" : "Porte Chiuse", elevatorX + ELEVATOR_WIDTH + 10, elevatorY + 40);

        // Scritte di debug
        g.setColor(Color.BLACK);
        g.drawString("Stato ascensore: " + ascensore, 10, getHeight() - 40);
        g.drawString("Velocità corrente: " + ((float)delay / 1000) + " s/ciclo", 10, getHeight() - 20);
    }
}
