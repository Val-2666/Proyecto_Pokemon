import java.awt.*;
import javax.swing.*;

public class InterfazGrafica extends JFrame {
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
        setTitle("¡Bienvenido al Mundo Pokémon!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());
    
        ImageIcon logo = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.NORTH);
    
        JLabel welcomeLabel = new JLabel(
            "<html><center>🎮✨ ¡Prepárate para la Aventura! ✨🎮<br>Bienvenido al Simulador de Batallas Pokémon</center></html>",
            SwingConstants.CENTER
            );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(255, 100, 100));
        add(welcomeLabel, BorderLayout.CENTER);
    
        JButton startButton = new JButton("¡Comenzar!");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBackground(new Color(100, 200, 255));
        startButton.setForeground(Color.BLACK);
        add(startButton, BorderLayout.SOUTH);
    
        startButton.addActionListener(e -> {
            dispose(); // cierra esta ventana
            mostrarVentanaEntrenadores();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

   public void mostrarVentanaEntrenadores() {
    JFrame ventanaEntrenadores = new JFrame("Ingreso de Nombres de Entrenadores");
    ventanaEntrenadores.setSize(700, 500);
    ventanaEntrenadores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ventanaEntrenadores.setLayout(new BorderLayout());

    // Panel de fondo con imagen
    JPanel panelFondo = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon fondo = new ImageIcon("fondo_entrenador.png");
            g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };
    panelFondo.setLayout(new BoxLayout(panelFondo, BoxLayout.Y_AXIS));
    ventanaEntrenadores.setContentPane(panelFondo);

    // Panel del formulario
    JPanel panelFormulario = new JPanel();
    panelFormulario.setOpaque(false);
    panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
    panelFormulario.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100)); // Márgenes amplios

    // Campo Entrenador 1
    JLabel labelEntrenador1 = new JLabel("Nombre del Entrenador 1:");
    labelEntrenador1.setFont(new Font("Arial", Font.BOLD, 16));
    labelEntrenador1.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelFormulario.add(labelEntrenador1);

    tfEntrenador1 = new JTextField();
    tfEntrenador1.setMaximumSize(new Dimension(300, 30));
    tfEntrenador1.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelFormulario.add(tfEntrenador1);

    panelFormulario.add(Box.createVerticalStrut(20)); // Espacio vertical

    // Campo Entrenador 2
    JLabel labelEntrenador2 = new JLabel("Nombre del Entrenador 2:");
    labelEntrenador2.setFont(new Font("Arial", Font.BOLD, 16));
    labelEntrenador2.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelFormulario.add(labelEntrenador2);

    tfEntrenador2 = new JTextField();
    tfEntrenador2.setMaximumSize(new Dimension(300, 30));
    tfEntrenador2.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelFormulario.add(tfEntrenador2);

    panelFondo.add(Box.createVerticalGlue()); // Empuja el formulario hacia el centro
    panelFondo.add(panelFormulario);
    panelFondo.add(Box.createVerticalStrut(30)); // Espacio entre formulario y botón

    // Panel del botón
    JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelBoton.setOpaque(false);
    JButton generarEquiposButton = new JButton("Presiona para formar los equipos");
    
    generarEquiposButton.addActionListener(e -> {
        String nombreEntrenador1 = tfEntrenador1.getText().trim();
        String nombreEntrenador2 = tfEntrenador2.getText().trim();

        if (nombreEntrenador1.isEmpty() || nombreEntrenador2.isEmpty()) {
            JOptionPane.showMessageDialog(ventanaEntrenadores, 
                "Por favor, ingresa los nombres de ambos entrenadores.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            entrenador1 = new Entrenador(nombreEntrenador1);
            entrenador2 = new Entrenador(nombreEntrenador2);
            mostrarEquiposGenerados();
            ventanaEntrenadores.dispose(); // Cierra y libera recursos
        }
    });

    panelBoton.add(generarEquiposButton);
    panelFondo.add(panelBoton);
    panelFondo.add(Box.createVerticalGlue()); // Empuja el botón hacia abajo

    ventanaEntrenadores.setLocationRelativeTo(null);
    ventanaEntrenadores.setVisible(true);
}


    public void mostrarEquiposGenerados() {
        JFrame ventanaEquipos = new JFrame("Equipos Generados");
        ventanaEquipos.setSize(700, 500);
        ventanaEquipos.setLayout(new BorderLayout());

        JPanel panelContenido = new JPanel(new GridLayout(1, 2, 20, 0)); // Dos columnas para los entrenadores

        // Panel para el Entrenador 1
        JPanel panelEntrenador1 = new JPanel();
        panelEntrenador1.setLayout(new BoxLayout(panelEntrenador1, BoxLayout.Y_AXIS));
        JLabel label1 = new JLabel(entrenador1.getNombre() + ":");
        label1.setFont(new Font("Arial", Font.BOLD, 16));
        panelEntrenador1.add(label1);
        for (Pokemon p : entrenador1.getEquipo()) {
            panelEntrenador1.add(crearPanelPokemon(p));
        }
        panelContenido.add(panelEntrenador1);

        // Panel para el Entrenador 2
        JPanel panelEntrenador2 = new JPanel();
        panelEntrenador2.setLayout(new BoxLayout(panelEntrenador2, BoxLayout.Y_AXIS));
        JLabel label2 = new JLabel(entrenador2.getNombre() + ":");
        label2.setFont(new Font("Arial", Font.BOLD, 16));
        panelEntrenador2.add(label2);
        for (Pokemon p : entrenador2.getEquipo()) {
            panelEntrenador2.add(crearPanelPokemon(p));
        }
        panelContenido.add(panelEntrenador2);

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        ventanaEquipos.add(scrollPane, BorderLayout.CENTER);

        // Panel para centrar el botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton iniciarBatallaButton = new JButton("Iniciar Batalla");
        iniciarBatallaButton.addActionListener(e -> {
            poke1 = entrenador1.obtenerPokemonActivo();
            poke2 = entrenador2.obtenerPokemonActivo();
            mostrarVentanaBatalla();
            ventanaEquipos.setVisible(false);
        });
        panelBoton.add(iniciarBatallaButton);

        ventanaEquipos.add(panelBoton, BorderLayout.SOUTH);
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

        panelSuperior.add(new JLabel(entrenador1.getNombre() + ", elige el ataque de tu Pokemón"));
        panelSuperior.add(comboAtaques1);
        panelSuperior.add(new JLabel(entrenador2.getNombre() + ", elige el ataque de tu Pokemón"));
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
                comboAtaques1.addItem(ataque.getdamagename() + " (Daño: " + ataque.getdamagepotency() + ")");
            }
        }
        if (poke2 != null) {
            for (Ataque ataque : poke2.getAttacks()) {
                comboAtaques2.addItem(ataque.getdamagename() + " (Daño: " + ataque.getdamagepotency() + ")");
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
            ataquesTexto.append(ataque.getdamagename())
                        .append(" (Daño: ")
                        .append(ataque.getdamagepotency())
                        .append(", Tipo: ")
                        .append(ataque.getdamagetype());
            if (ataque.advantage(pokemon.getType())) {
                ataquesTexto.append(", Ventaja de tipo");
            }
        }
        if (ataquesTexto.length() > 0) {
            ataquesTexto.setLength(ataquesTexto.length() - 2); // Elimina la última coma y espacio
        }
        panel.add(new JLabel(ataquesTexto.toString()));

        return panel;
    }
}