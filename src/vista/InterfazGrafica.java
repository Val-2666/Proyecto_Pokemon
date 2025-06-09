package vista;

import java.awt.*;
import javax.swing.*;
import controlador.Controlador;
import modelo.Ataque;
import modelo.Entrenador;
import modelo.Pokemon;
import modelo.ResultadoTurno;

public class InterfazGrafica extends JFrame {
    private JTextField tfEntrenador1, tfEntrenador2;
    private JComboBox<String> comboAtaques1, comboAtaques2;
    private JTextArea textoBatalla;
    private JButton btnRealizarTurno;
    private JProgressBar barraHP1, barraHP2;
    private JLabel labelPokemon1, labelPokemon2;
    private int ronda = 1;
    private Controlador controlador;

    private boolean turnoEntrenador1 = true;

    public InterfazGrafica() {
        controlador = new Controlador();
        configurarVentanaInicial();
    }

    private void configurarVentanaInicial() {
        setTitle("¡Bienvenido al Mundo Pokémon!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon(getClass().getResource("/vista/recursos/logo.png"));
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.NORTH);

        JLabel welcomeLabel = crearLabelHTML(
            "<html><center>🎮✨ ¡Prepárate para la Aventura! ✨🎮<br>Bienvenido al Simulador de Batallas Pokémon</center></html>",
            new Font("Arial", Font.BOLD, 24),
            new Color(255, 100, 100),
            SwingConstants.CENTER
        );
        add(welcomeLabel, BorderLayout.CENTER);

        JButton startButton = crearBoton("¡Comenzar!", new Font("Arial", Font.BOLD, 18),
                new Color(100, 200, 255), Color.BLACK);
        add(startButton, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            dispose();
            mostrarVentanaEntrenadores();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel crearLabelHTML(String texto, Font fuente, Color color, int alineacion) {
        JLabel label = new JLabel(texto, alineacion);
        label.setFont(fuente);
        label.setForeground(color);
        return label;
    }

    private JButton crearBoton(String texto, Font fuente, Color fondo, Color fg) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setBackground(fondo);
        boton.setForeground(fg);
        return boton;
    }

    public void mostrarVentanaEntrenadores() {
        JFrame ventana = new JFrame("Ingreso de Nombres de Entrenadores");
        ventana.setSize(700, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon(getClass().getResource("/vista/recursos/fondo_pokemon.png"));
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new BoxLayout(panelFondo, BoxLayout.Y_AXIS));
        panelFondo.setOpaque(false);
        ventana.setContentPane(panelFondo);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setOpaque(false);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));

        JLabel labelEntrenador1 = crearLabelSimple("Nombre del Entrenador 1:");
        JLabel labelEntrenador2 = crearLabelSimple("Nombre del Entrenador 2:");

        tfEntrenador1 = crearTextField();
        tfEntrenador2 = crearTextField();

        JButton btnGenerarEquipos = crearBoton("Presiona para formar los equipos",
                new Font("Arial", Font.PLAIN, 14), null, null);
        btnGenerarEquipos.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnGenerarEquipos.addActionListener(e -> {
            String nombre1 = tfEntrenador1.getText().trim();
            String nombre2 = tfEntrenador2.getText().trim();

            if (nombre1.isEmpty() || nombre2.isEmpty()) {
                JOptionPane.showMessageDialog(ventana,
                        "Por favor, ingresa los nombres de ambos entrenadores.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                controlador.crearEntrenadores(nombre1, nombre2);
                mostrarEquiposGenerados(controlador.getEntrenador1(), controlador.getEntrenador2());
                ventana.dispose();
            }
        });

        panelFormulario.add(labelEntrenador1);
        panelFormulario.add(tfEntrenador1);
        panelFormulario.add(Box.createVerticalStrut(20));
        panelFormulario.add(labelEntrenador2);
        panelFormulario.add(tfEntrenador2);
        panelFormulario.add(Box.createVerticalStrut(30));
        panelFormulario.add(btnGenerarEquipos);

        panelFondo.add(Box.createVerticalGlue());
        panelFondo.add(panelFormulario);
        panelFondo.add(Box.createVerticalGlue());

        ventana.setVisible(true);
    }

    private JLabel crearLabelSimple(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField crearTextField() {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(300, 30));
        tf.setAlignmentX(Component.CENTER_ALIGNMENT);
        return tf;
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

        JButton botonIniciar = crearBoton("Iniciar Batalla", new Font("Arial", Font.BOLD, 14),
                new Color(100, 200, 100), Color.BLACK);
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
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));
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

    private void actualizarAtaques() {
        comboAtaques1.removeAllItems();
        comboAtaques2.removeAllItems();

        for (Ataque a : controlador.getPokemonActualJugador1().getAttacks()) {
            comboAtaques1.addItem("💥 " + a.getDamageName() + " (" + a.getDamagePotency() + "⚡)");
        }

        for (Ataque a : controlador.getPokemonActualJugador2().getAttacks()) {
            comboAtaques2.addItem("💥 " + a.getDamageName() + " (" + a.getDamagePotency() + "⚡)");
        }
    }

        public void mostrarVentanaBatalla() {
        JFrame ventana = new JFrame("Batalla Pokémon");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1000, 650);
        ventana.setLocationRelativeTo(null);

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon(getClass().getResource("/vista/recursos/fondo_pokemon.png"));
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(null);
        ventana.setContentPane(panelFondo);

        // Labels de entrenadores
        JLabel labelJugador1 = new JLabel("Entrenador 1: " + controlador.getEntrenador1().getNombre());
        labelJugador1.setFont(new Font("Arial", Font.BOLD, 20));
        labelJugador1.setBounds(50, 20, 400, 30);
        panelFondo.add(labelJugador1);

        JLabel labelJugador2 = new JLabel("Entrenador 2: " + controlador.getEntrenador2().getNombre());
        labelJugador2.setFont(new Font("Arial", Font.BOLD, 20));
        labelJugador2.setBounds(550, 20, 400, 30);
        panelFondo.add(labelJugador2);

        // Nombres de Pokémon
        labelPokemon1 = new JLabel(controlador.getPokemonActualJugador1().getName());
        labelPokemon1.setFont(new Font("Arial", Font.BOLD, 16));
        labelPokemon1.setBounds(50, 60, 200, 25);
        panelFondo.add(labelPokemon1);

        labelPokemon2 = new JLabel(controlador.getPokemonActualJugador2().getName());
        labelPokemon2.setFont(new Font("Arial", Font.BOLD, 16));
        labelPokemon2.setBounds(550, 60, 200, 25);
        panelFondo.add(labelPokemon2);

        // Barras de HP
        barraHP1 = new JProgressBar(0, controlador.getPokemonActualJugador1().getMaxHealthPoints());
        barraHP1.setValue(controlador.getPokemonActualJugador1().getHealthPoints());
        barraHP1.setStringPainted(true);
        barraHP1.setBounds(50, 90, 200, 25);
        panelFondo.add(barraHP1);

        barraHP2 = new JProgressBar(0, controlador.getPokemonActualJugador2().getMaxHealthPoints());
        barraHP2.setValue(controlador.getPokemonActualJugador2().getHealthPoints());
        barraHP2.setStringPainted(true);
        barraHP2.setBounds(550, 90, 200, 25);
        panelFondo.add(barraHP2);

        // Combo de ataques
        comboAtaques1 = new JComboBox<>();
        comboAtaques1.setBounds(50, 130, 200, 30);
        panelFondo.add(comboAtaques1);

        comboAtaques2 = new JComboBox<>();
        comboAtaques2.setBounds(550, 130, 200, 30);
        panelFondo.add(comboAtaques2);

        actualizarAtaques();

        // Botón para realizar turno
        btnRealizarTurno = new JButton("Realizar turno");
        btnRealizarTurno.setBounds(400, 180, 180, 40);
        panelFondo.add(btnRealizarTurno);

        // Área de texto con scroll para mostrar la batalla
        textoBatalla = new JTextArea();
        textoBatalla.setEditable(false);
        textoBatalla.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textoBatalla.setLineWrap(true);
        textoBatalla.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textoBatalla);
        scrollPane.setBounds(50, 240, 700, 350);
        panelFondo.add(scrollPane);

        btnRealizarTurno.addActionListener(e -> realizarTurno());

        ventana.setVisible(true);
    }
        private void realizarTurno() {
        int indiceAtaque;
        ResultadoTurno resultado;

        if (turnoEntrenador1) {
            indiceAtaque = comboAtaques1.getSelectedIndex();
            resultado = controlador.realizarTurno(0, indiceAtaque);
        } else {
            indiceAtaque = comboAtaques2.getSelectedIndex();
            resultado = controlador.realizarTurno(1, indiceAtaque);
        }

        textoBatalla.append(resultado.getResumen() + "\n\n");

        actualizarEstadoPokemons();

        if (resultado.isFinDelJuego()) {
            textoBatalla.append("🏆 ¡El juego ha terminado! Felicitaciones a "
                    + controlador.getGanador() + " 🏆\n");
            btnRealizarTurno.setEnabled(false);
            comboAtaques1.setEnabled(false);
            comboAtaques2.setEnabled(false);
            return;
        }

        if (resultado.isFinDelTurno()) {
            textoBatalla.append("☠️ ¡Un Pokémon se ha debilitado!\n");
            textoBatalla.append("🏁 " + (turnoEntrenador1 ? "Jugador 1" : "Jugador 2") + " ganó la Ronda " + ronda + ".\n\n");
            ronda++;

            actualizarAtaques();
            actualizarEstadoPokemons();
        }

        turnoEntrenador1 = !turnoEntrenador1;
        }

        private void actualizarEstadoPokemons() {
        Pokemon p1 = controlador.getPokemonActualJugador1();
        Pokemon p2 = controlador.getPokemonActualJugador2();

        labelPokemon1.setText(p1.getName());
        labelPokemon2.setText(p2.getName());

        barraHP1.setMaximum(p1.getMaxHealthPoints());
        barraHP1.setValue(p1.getHealthPoints());

        barraHP2.setMaximum(p2.getMaxHealthPoints());
        barraHP2.setValue(p2.getHealthPoints());

        actualizarAtaques();
    }

}