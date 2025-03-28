import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Elevator2DRenderer extends JPanel {
    private static final int FLOOR_HEIGHT = 80; // Altezza grafica di ogni piano
    private static final int ELEVATOR_WIDTH = 70; // Larghezza dell'ascensore
    private static final int ELEVATOR_HEIGHT = 70; // Altezza dell'ascensore
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
        frame.setSize(1000, 1200); // Dimensione della finestra
        frame.setLocationRelativeTo(null);

        // Pannello principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(this, BorderLayout.CENTER);

        // Slider per la velocità
        JSlider speedSlider = new JSlider(100, 2000, delay);
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> {
            delay = speedSlider.getValue();
            resetTimer();
        });

        // Pulsante pausa/ripresa
        JButton pauseButton = new JButton("Pausa");
        pauseButton.addActionListener(e -> {
            isPaused = !isPaused;
            pauseButton.setText(isPaused ? "Riprendi" : "Pausa");
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
                ascensore.ciclo(0); // Simula un ciclo
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

        // Sfondo
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Disegna i piani
        List<Piano> piani = ascensore.piani;
        for (int i = 0; i < piani.size(); i++) {
            int y = i * FLOOR_HEIGHT;
            g.setColor(Color.BLACK);
            g.drawLine(0, y, BUILDING_WIDTH, y); // Linea separatrice del piano
            g.drawString("Piano " + i, 10, y + 20);

            // Mostra visivamente le persone in attesa accanto ai piani
            List<Persona> coda = piani.get(i).getCodaPersone();
            g.setColor(Color.BLUE);
            for (int j = 0; j < coda.size(); j++) {
                int personX = BUILDING_WIDTH + 20 + j * 10;
                int personY = y + 10;
                g.fillOval(personX, personY, 10, 10); // Disegna un cerchio per ogni persona
            }
        }

        // Disegna l'ascensore
        int pianoCorrente = ascensore.getPianoCorrente().getNumeroPiano();
        int elevatorY = (piani.size() - 1 - pianoCorrente) * FLOOR_HEIGHT;

        g.setColor(Color.RED);
        int elevatorX = BUILDING_WIDTH / 2 - ELEVATOR_WIDTH / 2;
        g.fillRect(elevatorX, elevatorY, ELEVATOR_WIDTH, ELEVATOR_HEIGHT);

        // Disegna le persone dentro l'ascensore
        g.setColor(Color.WHITE);
        g.drawString(ascensore.personeDentro.size() + " persone", elevatorX + 5, elevatorY + 25);

        // Disegna lo stato delle porte
        g.setColor(ascensore.isPorteAperte() ? Color.GREEN : Color.RED);
        g.drawString(ascensore.isPorteAperte() ? "Porte Aperte" : "Porte Chiuse", elevatorX, elevatorY + ELEVATOR_HEIGHT + 15);

        // Scritte di debug
        g.setColor(Color.BLACK);
        g.drawString("Stato ascensore: " + ascensore, 10, getHeight() - 40);
        g.drawString("Velocità corrente: " + delay + " ms/ciclo", 10, getHeight() - 20);
    }
}
