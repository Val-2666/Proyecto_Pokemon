import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazGrafica {
    private JFrame frame;
    private JTextField tfEntrenador1, tfEntrenador2;
    private Entrenador entrenador1, entrenador2;
    private Pokemon poke1, poke2;
    private JComboBox<String> comboAtaques1;
    private JComboBox<String> comboAtaques2;
    private JTextArea textoBatalla;
    private JButton btnConfirmar1, btnConfirmar2, btnRealizarTurno;
    private JProgressBar barraHP1, barraHP2;
    private int ronda = 1;
    private Ataque ataqueSeleccionado1, ataqueSeleccionado2;

    public InterfazGrafica() {
        frame = new JFrame("¡Bienvenido al Mundo Pokémon!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel(
            "<html><center>🎮✨ ¡Prepárate para la Aventura! ✨🎮<br>Bienvenido al Simulador de Batallas Pokémon</center></html>",
            SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(255, 100, 100));
        frame.add(welcomeLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("¡Comenzar!");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBackground(new Color(100, 200, 255));
        startButton.setForeground(Color.BLACK);
        frame.add(startButton, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            frame.setVisible(false);
            mostrarVentanaEntrenadores();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void mostrarVentanaEntrenadores() {
        JFrame ventanaEntrenadores = new JFrame("Ingreso de Nombres de Entrenadores");
        ventanaEntrenadores.setSize(700, 500);
        ventanaEntrenadores.setLayout(new GridLayout(3, 2, 10, 10));

        ventanaEntrenadores.add(new JLabel("Entrenador 1:"));
        tfEntrenador1 = new JTextField();
        ventanaEntrenadores.add(tfEntrenador1);

        ventanaEntrenadores.add(new JLabel("Entrenador 2:"));
        tfEntrenador2 = new JTextField();
        ventanaEntrenadores.add(tfEntrenador2);

        JButton generarEquiposButton = new JButton("Presiona para formar los equipos");
        generarEquiposButton.addActionListener(e -> {
            String nombreEntrenador1 = tfEntrenador1.getText().trim();
            String nombreEntrenador2 = tfEntrenador2.getText().trim();

            if (nombreEntrenador1.isEmpty() || nombreEntrenador2.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaEntrenadores, "Por favor, ingresa los nombres de ambos entrenadores.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                entrenador1 = new Entrenador(nombreEntrenador1);
                entrenador2 = new Entrenador(nombreEntrenador2);
                mostrarEquiposGenerados();
                ventanaEntrenadores.setVisible(false);
            }
        });

        ventanaEntrenadores.add(generarEquiposButton);

        ventanaEntrenadores.setLocationRelativeTo(null);
        ventanaEntrenadores.setVisible(true);
    }

    public void mostrarEquiposGenerados() {
        JFrame ventanaEquipos = new JFrame("Equipos Generados");
        ventanaEquipos.setSize(700, 500);
        ventanaEquipos.setLayout(new BorderLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel(entrenador1.getNombre() + ":");
        label1.setFont(new Font("Arial", Font.BOLD, 16));
        panelContenido.add(label1);
        for (Pokemon p : entrenador1.getEquipo()) {
            panelContenido.add(crearPanelPokemon(p));
        }

        panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel label2 = new JLabel(entrenador2.getNombre() + ":");
        label2.setFont(new Font("Arial", Font.BOLD, 16));
        panelContenido.add(label2);
        for (Pokemon p : entrenador2.getEquipo()) {
            panelContenido.add(crearPanelPokemon(p));
        }

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        ventanaEquipos.add(scrollPane, BorderLayout.CENTER);

        JButton iniciarBatallaButton = new JButton("Iniciar Batalla");
        iniciarBatallaButton.addActionListener(e -> {
            poke1 = entrenador1.obtenerPokemonActivo();
            poke2 = entrenador2.obtenerPokemonActivo();
            mostrarVentanaBatalla();
            ventanaEquipos.setVisible(false);
        });

        ventanaEquipos.add(iniciarBatallaButton, BorderLayout.SOUTH);
        ventanaEquipos.setLocationRelativeTo(null);
        ventanaEquipos.setVisible(true);
    }

    public void mostrarVentanaBatalla() {
        JFrame ventanaBatalla = new JFrame("Batalla Pokémon");
        ventanaBatalla.setSize(700, 500);
        ventanaBatalla.setLayout(new BorderLayout());

        textoBatalla = new JTextArea();
        textoBatalla.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textoBatalla);
        ventanaBatalla.add(scrollPane, BorderLayout.CENTER);

        JPanel panelSuperior = new JPanel(new GridLayout(4, 2, 10, 10));
        comboAtaques1 = new JComboBox<>();
        comboAtaques2 = new JComboBox<>();
        barraHP1 = new JProgressBar(0, 100);
        barraHP2 = new JProgressBar(0, 100);
        barraHP1.setStringPainted(true);
        barraHP2.setStringPainted(true);

        panelSuperior.add(new JLabel(entrenador1.getNombre() + ", elige el ataque de " + poke1.getName()));
        panelSuperior.add(comboAtaques1);
        panelSuperior.add(new JLabel(entrenador2.getNombre() + ", elige el ataque de " + poke2.getName()));
        panelSuperior.add(comboAtaques2);
        panelSuperior.add(barraHP1);
        panelSuperior.add(barraHP2);

        btnRealizarTurno = new JButton("Realizar Turno");
        btnRealizarTurno.addActionListener(e -> realizarTurno());

        ventanaBatalla.add(panelSuperior, BorderLayout.NORTH);
        ventanaBatalla.add(btnRealizarTurno, BorderLayout.SOUTH);

        actualizarAtaques();
        actualizarBarrasHP();
        mostrarEstadoBatalla();

        ventanaBatalla.setLocationRelativeTo(null);
        ventanaBatalla.setVisible(true);
    }

    private void actualizarAtaques() {
        comboAtaques1.removeAllItems();
        comboAtaques2.removeAllItems();
        if (poke1 != null) {
            for (Ataque ataque : poke1.getAttacks()) {
                comboAtaques1.addItem(ataque.getdamagename());
            }
        }
        if (poke2 != null) {
            for (Ataque ataque : poke2.getAttacks()) {
                comboAtaques2.addItem(ataque.getdamagename());
            }
        }
    }

    private void actualizarBarrasHP() {
        if (poke1 != null) {
            barraHP1.setMaximum(poke1.getMaxHP());
            barraHP1.setValue(Math.max(poke1.getHealthPoints(), 0));
            barraHP1.setString(poke1.getName() + " HP: " + poke1.getHealthPoints());
        }
        if (poke2 != null) {
            barraHP2.setMaximum(poke2.getMaxHP());
            barraHP2.setValue(Math.max(poke2.getHealthPoints(), 0));
            barraHP2.setString(poke2.getName() + " HP: " + poke2.getHealthPoints());
        }
    }

    private Ataque buscarAtaque(Pokemon pokemon, String nombreAtaque) {
        for (Ataque ataque : pokemon.getAttacks()) {
            if (ataque.getdamagename().equals(nombreAtaque)) {
                return ataque;
            }
        }
        return null;
    }

    private void realizarTurno() {
        if (poke1 == null || poke2 == null) return;

        String atq1 = (String) comboAtaques1.getSelectedItem();
        String atq2 = (String) comboAtaques2.getSelectedItem();

        if (atq1 == null || atq2 == null) {
            JOptionPane.showMessageDialog(null, "Ambos equipos deben elegir un ataque para continuar.");
            return;
        }

        Ataque ataque1 = buscarAtaque(poke1, atq1);
        Ataque ataque2 = buscarAtaque(poke2, atq2);

        textoBatalla.append("\n────────── RONDA " + ronda + " ──────────\n");

        if (poke1.getSpeed() >= poke2.getSpeed()) {
            ejecutarAtaque(poke1, poke2, ataque1, entrenador2);
            if (poke2 != null && poke2.getHealthPoints() > 0) {
                ejecutarAtaque(poke2, poke1, ataque2, entrenador1);
            }
        } else {
            ejecutarAtaque(poke2, poke1, ataque2, entrenador1);
            if (poke1 != null && poke1.getHealthPoints() > 0) {
                ejecutarAtaque(poke1, poke2, ataque1, entrenador2);
            }
        }

        actualizarBarrasHP();
        textoBatalla.append("────────────────────────────\n\n");
        ronda++;
    }

    private void ejecutarAtaque(Pokemon atacante, Pokemon defensor, Ataque ataque, Entrenador defensorEntrenador) {
        int hpAntes = defensor.getHealthPoints();
        ataque.applyAttack(defensor);

        textoBatalla.append("\n🌟 " + atacante.getName() + " usó " + ataque.getdamagename() + " contra " + defensor.getName() + "\n");
        if (ataque.advantage(defensor.getType())) {
            textoBatalla.append("💥 ¡Ataque con ventaja de tipo!\n");
        }

        int daño = Math.max(hpAntes - defensor.getHealthPoints(), 0);
        textoBatalla.append("🛡️ " + defensor.getName() + " recibió " + daño + " de daño (HP restante: " + defensor.getHealthPoints() + ")\n");

        if (defensor.getHealthPoints() <= 0) {
            textoBatalla.append("☠️ " + defensor.getName() + " ha sido derrotado.\n");
            if (defensorEntrenador == entrenador2) {
                poke2 = entrenador2.obtenerPokemonActivo();
                if (poke2 == null) {
                    textoBatalla.append("\n🏆 ¡" + entrenador1.getNombre() + " gana la batalla!\n");
                    btnRealizarTurno.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, defensorEntrenador.getNombre() + " envía a " + poke2.getName() + " a la batalla.");
                    actualizarAtaques();
                    mostrarEstadoBatalla();
                    actualizarBarrasHP();
                }
            } else {
                poke1 = entrenador1.obtenerPokemonActivo();
                if (poke1 == null) {
                    textoBatalla.append("\n🏆 ¡" + entrenador2.getNombre() + " gana la batalla!\n");
                    btnRealizarTurno.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, defensorEntrenador.getNombre() + " envía a " + poke1.getName() + " a la batalla.");
                    actualizarAtaques();
                    mostrarEstadoBatalla();
                    actualizarBarrasHP();
                }
            }
        }
    }

    private void mostrarEstadoBatalla() {
        textoBatalla.setText("🔥 ¡Comienza la batalla entre " + entrenador1.getNombre() + " y " + entrenador2.getNombre() + "! 🔥\n\n"
                + entrenador1.getNombre() + " - " + poke1.getName() + "\n"
                + entrenador2.getNombre() + " - " + poke2.getName() + "\n\n");
    }

    private JPanel crearPanelPokemon(Pokemon pokemon) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(new GridLayout(0, 2, 5, 5));

        panel.add(new JLabel("Nombre:"));
        panel.add(new JLabel(pokemon.getName()));
        panel.add(new JLabel("Tipo:"));
        panel.add(new JLabel(pokemon.getType()));
        panel.add(new JLabel("Puntos de Salud (HP):"));
        panel.add(new JLabel(String.valueOf(pokemon.getHealthPoints())));
        panel.add(new JLabel("Ataque (At):"));
        panel.add(new JLabel(String.valueOf(pokemon.getAttack())));
        panel.add(new JLabel("Defensa (Df):"));
        panel.add(new JLabel(String.valueOf(pokemon.getDefense())));

        panel.add(new JLabel("Ataques:"));
        StringBuilder ataquesTexto = new StringBuilder();
        for (Ataque ataque : pokemon.getAttacks()) {
            ataquesTexto.append(ataque.getdamagename()).append(", ");
        }
        if (ataquesTexto.length() > 0) {
            ataquesTexto.setLength(ataquesTexto.length() - 2);
        }
        panel.add(new JLabel(ataquesTexto.toString()));

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazGrafica());
    }
}