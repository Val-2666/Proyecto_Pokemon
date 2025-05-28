package vista;

import java.awt.*;
import javax.swing.*;
import controlador.Controlador;
import modelo.Ataque;
import modelo.Entrenador;
import modelo.Pokemon;

public class InterfazGrafica extends JFrame {
    private JTextField tfEntrenador1, tfEntrenador2;
    private JComboBox<String> comboAtaques1, comboAtaques2;
    private JTextArea textoBatalla;
    private JButton btnRealizarTurno;
    private JProgressBar barraHP1, barraHP2;
    private int ronda = 1;
    private Controlador controlador;

    public InterfazGrafica() {
        controlador = new Controlador();
        setTitle("¡Bienvenido al Mundo Pokémon!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon(getClass().getResource("/vista/recursos/logo.png"));
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
            dispose();
            mostrarVentanaEntrenadores();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void mostrarVentanaEntrenadores() {
        JFrame ventanaEntrenadores = new JFrame("Ingreso de Nombres de Entrenadores");
        ventanaEntrenadores.setSize(700, 500);
        ventanaEntrenadores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaEntrenadores.setLocationRelativeTo(null);

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon(getClass().getResource("/vista/recursos/fondo_entrenador.png"));
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new BoxLayout(panelFondo, BoxLayout.Y_AXIS));
        panelFondo.setOpaque(false);
        ventanaEntrenadores.setContentPane(panelFondo);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setOpaque(false);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));

        JLabel labelEntrenador1 = new JLabel("Nombre del Entrenador 1:");
        labelEntrenador1.setFont(new Font("Arial", Font.BOLD, 16));
        labelEntrenador1.setAlignmentX(Component.CENTER_ALIGNMENT);

        tfEntrenador1 = new JTextField();
        tfEntrenador1.setMaximumSize(new Dimension(300, 30));
        tfEntrenador1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelEntrenador2 = new JLabel("Nombre del Entrenador 2:");
        labelEntrenador2.setFont(new Font("Arial", Font.BOLD, 16));
        labelEntrenador2.setAlignmentX(Component.CENTER_ALIGNMENT);

        tfEntrenador2 = new JTextField();
        tfEntrenador2.setMaximumSize(new Dimension(300, 30));
        tfEntrenador2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton generarEquiposButton = new JButton("Presiona para formar los equipos");
        generarEquiposButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generarEquiposButton.addActionListener(e -> {
            String nombre1 = tfEntrenador1.getText().trim();
            String nombre2 = tfEntrenador2.getText().trim();

            if (nombre1.isEmpty() || nombre2.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaEntrenadores,
                        "Por favor, ingresa los nombres de ambos entrenadores.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                controlador.crearEntrenadores(nombre1, nombre2);
                mostrarEquiposGenerados(controlador.getEntrenador1(), controlador.getEntrenador2());
                ventanaEntrenadores.dispose();
            }
        });

        panelFormulario.add(labelEntrenador1);
        panelFormulario.add(tfEntrenador1);
        panelFormulario.add(Box.createVerticalStrut(20));
        panelFormulario.add(labelEntrenador2);
        panelFormulario.add(tfEntrenador2);
        panelFormulario.add(Box.createVerticalStrut(30));
        panelFormulario.add(generarEquiposButton);

        panelFondo.add(Box.createVerticalGlue());
        panelFondo.add(panelFormulario);
        panelFondo.add(Box.createVerticalGlue());

        ventanaEntrenadores.setVisible(true);
    }

    private void mostrarEquiposGenerados(Entrenador e1, Entrenador e2) {
        JFrame frame = new JFrame("Equipos Generados");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.add(crearPanelEntrenador(e1));
        panelPrincipal.add(crearPanelEntrenador(e2));

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JButton botonIniciar = new JButton("Iniciar Batalla");
        botonIniciar.addActionListener(e -> {
            frame.dispose();
            mostrarVentanaBatalla();
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(botonIniciar);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panelInferior, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JPanel crearPanelEntrenador(Entrenador entrenador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(entrenador.getNombre());
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));

        for (Pokemon p : entrenador.getEquipo()) {
            JPanel pokePanel = new JPanel();
            pokePanel.setLayout(new BoxLayout(pokePanel, BoxLayout.Y_AXIS));
            pokePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            pokePanel.setBackground(new Color(245, 245, 245));

            pokePanel.add(new JLabel("Nombre: " + p.getName()));
            pokePanel.add(new JLabel("Tipo: " + p.getType()));
            pokePanel.add(new JLabel("HP: " + p.getHealthPoints() + " / " + p.getMaxHealthPoints()));
            pokePanel.add(new JLabel("Ataques:"));
            for (Ataque a : p.getAttacks()) {
                pokePanel.add(new JLabel(" - " + a.getDamageName() + " (" + a.getDamagePotency() + " daño)"));
            }

            pokePanel.add(Box.createVerticalStrut(5));
            panel.add(pokePanel);
            panel.add(Box.createVerticalStrut(10));
        }

        return panel;
    }

    public void mostrarVentanaBatalla() {
        JFrame ventanaBatalla = new JFrame("🔥 Batalla Pokémon 🔥");
        ventanaBatalla.setSize(800, 600);
        ventanaBatalla.setLayout(new BorderLayout());
        ventanaBatalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelSuperior = new JPanel(new GridLayout(3, 2, 15, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.setBackground(new Color(240, 248, 255));

        JLabel labelEntrenador1 = new JLabel(controlador.getEntrenador1().getNombre(), SwingConstants.CENTER);
        JLabel labelEntrenador2 = new JLabel(controlador.getEntrenador2().getNombre(), SwingConstants.CENTER);
        labelEntrenador1.setFont(new Font("Arial", Font.BOLD, 16));
        labelEntrenador2.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel labelPokemon1 = new JLabel(controlador.getPokemonActualJugador1().getName(), SwingConstants.CENTER);
        JLabel labelPokemon2 = new JLabel(controlador.getPokemonActualJugador2().getName(), SwingConstants.CENTER);

        barraHP1 = new JProgressBar(0, controlador.getPokemonActualJugador1().getMaxHealthPoints());
        barraHP2 = new JProgressBar(0, controlador.getPokemonActualJugador2().getMaxHealthPoints());
        barraHP1.setForeground(new Color(144, 238, 144));
        barraHP2.setForeground(new Color(255, 99, 71));
        barraHP1.setStringPainted(true);
        barraHP2.setStringPainted(true);

        panelSuperior.add(labelEntrenador1);
        panelSuperior.add(labelEntrenador2);
        panelSuperior.add(labelPokemon1);
        panelSuperior.add(labelPokemon2);
        panelSuperior.add(barraHP1);
        panelSuperior.add(barraHP2);

        ventanaBatalla.add(panelSuperior, BorderLayout.NORTH);

        textoBatalla = new JTextArea();
        textoBatalla.setEditable(false);
        textoBatalla.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textoBatalla.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollTexto = new JScrollPane(textoBatalla);
        ventanaBatalla.add(scrollTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelAtaques = new JPanel(new GridLayout(2, 2, 10, 10));
        comboAtaques1 = new JComboBox<>();
        comboAtaques2 = new JComboBox<>();
        actualizarAtaques();

        panelAtaques.add(new JLabel("Ataques de " + labelEntrenador1.getText() + ":"));
        panelAtaques.add(new JLabel("Ataques de " + labelEntrenador2.getText() + ":"));
        panelAtaques.add(comboAtaques1);
        panelAtaques.add(comboAtaques2);

        btnRealizarTurno = new JButton("⚔️ Realizar Turno");
        btnRealizarTurno.setFont(new Font("Arial", Font.BOLD, 16));
        btnRealizarTurno.setBackground(new Color(255, 215, 0));
        btnRealizarTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRealizarTurno.addActionListener(e -> realizarTurno());

        panelInferior.add(panelAtaques);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(btnRealizarTurno);

        ventanaBatalla.add(panelInferior, BorderLayout.SOUTH);

        actualizarBarrasHP();
        ventanaBatalla.setLocationRelativeTo(null);
        ventanaBatalla.setVisible(true);
    }

    private void actualizarAtaques() {
        comboAtaques1.removeAllItems();
        comboAtaques2.removeAllItems();

        Pokemon p1 = controlador.getPokemonActualJugador1();
        Pokemon p2 = controlador.getPokemonActualJugador2();

        for (Ataque a : p1.getAttacks()) {
            comboAtaques1.addItem("💥 " + a.getDamageName() + " (" + a.getDamagePotency() + "⚡)");
        }

        for (Ataque a : p2.getAttacks()) {
            comboAtaques2.addItem("💥 " + a.getDamageName() + " (" + a.getDamagePotency() + "⚡)");
        }
    }

    private void actualizarBarrasHP() {
        Pokemon p1 = controlador.getPokemonActualJugador1();
        Pokemon p2 = controlador.getPokemonActualJugador2();

        barraHP1.setMaximum(p1.getMaxHealthPoints());
        barraHP1.setValue(Math.max(p1.getHealthPoints(), 0));
        barraHP1.setString(p1.getName() + " HP: " + p1.getHealthPoints());

        barraHP2.setMaximum(p2.getMaxHealthPoints());
        barraHP2.setValue(Math.max(p2.getHealthPoints(), 0));
        barraHP2.setString(p2.getName() + " HP: " + p2.getHealthPoints());
    }

    private void realizarTurno() {
        int index1 = comboAtaques1.getSelectedIndex();
        int index2 = comboAtaques2.getSelectedIndex();

        if (index1 == -1 || index2 == -1) {
            JOptionPane.showMessageDialog(this, "Ambos jugadores deben seleccionar un ataque.");
            return;
        }

        String resumen = controlador.realizarAtaque(index1);
        textoBatalla.append("🔁 Ronda " + ronda + " 🔁\n" + resumen + "\n\n");
        ronda++;

        actualizarBarrasHP();
        actualizarAtaques();

        if (controlador.juegoTerminado()) {
            String ganador = controlador.obtenerGanador();
            JOptionPane.showMessageDialog(this, "🎉 ¡" + ganador + " ha ganado la batalla! 🎉");
            btnRealizarTurno.setEnabled(false);
        }
    }
}
