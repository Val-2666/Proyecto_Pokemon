package vista;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import controlador.Controlador;
import modelo.Ataque;
import modelo.Entrenador;
import modelo.ResultadoTurno;

public class InterfazGrafica extends JFrame {
    private JTextField tfEntrenador1, tfEntrenador2;
    private JComboBox<String> comboAtaques1, comboAtaques2;
    private JTextArea textoBatalla;
    private JTextPane historialArea;
    private JScrollPane scrollTextoBatalla;
    private JButton btnRealizarTurno;
    private JButton btnGuardarPartida;
    private JProgressBar barraHP1, barraHP2;
    private JLabel labelNombrePokemon1, labelNombrePokemon2;
    private Controlador controlador;
    private JFrame ventanaPrincipal;  // nueva variable de instancia


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
        JLabel logoLabel = new JLabel(logo, SwingConstants.CENTER);
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
        if (fondo != null) boton.setBackground(fondo);
        if (fg != null) boton.setForeground(fg);
        return boton;
    }

    public void mostrarVentanaEntrenadores() {
    JFrame ventana = new JFrame("Ingreso de Entrenadores");
    ventana.setSize(700, 500);
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ventana.setLocationRelativeTo(null);

    JPanel fondo = new JPanel() {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon(getClass().getResource("/vista/recursos/fondo_pokemon.png"));
            g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };
    fondo.setLayout(new BoxLayout(fondo, BoxLayout.Y_AXIS));
    fondo.setOpaque(false);
    ventana.setContentPane(fondo);

    tfEntrenador1 = new JTextField();
    tfEntrenador2 = new JTextField();
    tfEntrenador1.setMaximumSize(new Dimension(250, 35));
    tfEntrenador2.setMaximumSize(new Dimension(250, 35));
    tfEntrenador1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    tfEntrenador2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    tfEntrenador1.setHorizontalAlignment(JTextField.CENTER);
    tfEntrenador2.setHorizontalAlignment(JTextField.CENTER);
    tfEntrenador1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
    tfEntrenador2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));

    JButton generar = new JButton("Formar Equipos");
    generar.setFont(new Font("Segoe UI", Font.BOLD, 14));
    generar.setAlignmentX(Component.CENTER_ALIGNMENT);
    generar.setFocusPainted(false);
    generar.setBackground(new Color(66, 135, 245));
    generar.setForeground(Color.WHITE);
    generar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    generar.addActionListener(e -> {
        String n1 = tfEntrenador1.getText().trim();
        String n2 = tfEntrenador2.getText().trim();
        if (n1.isEmpty() || n2.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Ambos nombres son requeridos.",
                "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            controlador.crearEntrenadores(n1, n2);
            ventana.dispose();
            mostrarVentanaEquipos();
        }
    });

    fondo.add(Box.createVerticalGlue());

    JLabel label1 = new JLabel("Nombre Entrenador 1:");
    label1.setFont(new Font("Segoe UI", Font.BOLD, 18));
    label1.setForeground(Color.BLACK);
    label1.setAlignmentX(Component.CENTER_ALIGNMENT);
    fondo.add(label1);
    fondo.add(Box.createVerticalStrut(5));
    fondo.add(tfEntrenador1);
    fondo.add(Box.createVerticalStrut(20));

    JLabel label2 = new JLabel("Nombre Entrenador 2:");
    label2.setFont(new Font("Segoe UI", Font.BOLD, 18));
    label2.setForeground(Color.BLACK);
    label2.setAlignmentX(Component.CENTER_ALIGNMENT);
    fondo.add(label2);
    fondo.add(Box.createVerticalStrut(5));
    fondo.add(tfEntrenador2);

    fondo.add(Box.createVerticalStrut(30));
    fondo.add(generar);
    fondo.add(Box.createVerticalGlue());

    ventana.setVisible(true);
}

    private void mostrarVentanaEquipos() {
    Entrenador e1 = controlador.getEntrenador1();
    Entrenador e2 = controlador.getEntrenador2();

    JFrame frame = new JFrame("Equipos Pokémon Listos");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(900, 650);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout(10, 10));

    JLabel titulo = new JLabel("🔥 ¡Equipos preparados para la batalla! ⚡", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 22));
    titulo.setForeground(new Color(30, 144, 255));
    frame.add(titulo, BorderLayout.NORTH);

    // Buscador
    JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel lblBuscar = new JLabel("🔍 Buscar Pokémon por nombre o tipo:");
    JTextField campoBusqueda = new JTextField(20);
    panelBusqueda.add(lblBuscar);
    panelBusqueda.add(campoBusqueda);
    frame.add(panelBusqueda, BorderLayout.BEFORE_FIRST_LINE);

    JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
    panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    JScrollPane panelEntrenador1 = crearPanelEntrenadorDecorado(e1, "");
    JScrollPane panelEntrenador2 = crearPanelEntrenadorDecorado(e2, "");

    panelCentral.add(panelEntrenador1);
    panelCentral.add(panelEntrenador2);

    frame.add(panelCentral, BorderLayout.CENTER);

    // Botón iniciar batalla
    JButton inicio = crearBoton("Iniciar Batalla", new Font("Arial", Font.BOLD, 16), new Color(255, 215, 0), Color.BLACK);
    inicio.setPreferredSize(new Dimension(250, 40));
    inicio.addActionListener(e -> {
        frame.dispose();
        mostrarVentanaBatalla();
    });

    JPanel panelSur = new JPanel();
    panelSur.add(inicio);
    frame.add(panelSur, BorderLayout.SOUTH);

    // Lógica del buscador
    campoBusqueda.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
        public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }

        private void actualizar() {
            String texto = campoBusqueda.getText().trim().toLowerCase();
            panelCentral.removeAll();
            panelCentral.add(crearPanelEntrenadorDecorado(e1, texto));
            panelCentral.add(crearPanelEntrenadorDecorado(e2, texto));
            panelCentral.revalidate();
            panelCentral.repaint();
        }
    });

    frame.setVisible(true);
}

private JScrollPane crearPanelEntrenadorDecorado(Entrenador entrenador, String filtro) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(250, 250, 255));
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(100, 100, 255), 2, true),
        BorderFactory.createEmptyBorder(10, 15, 10, 15)
    ));

    JLabel nombre = new JLabel("👤 " + entrenador.getNombre(), SwingConstants.CENTER);
    nombre.setFont(new Font("Arial", Font.BOLD, 18));
    nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
    nombre.setForeground(new Color(50, 50, 200));
    panel.add(nombre);
    panel.add(Box.createVerticalStrut(10));

    for (var p : entrenador.getEquipo()) {
        String nombrePoke = p.getName().toLowerCase();
        String tipoPoke = p.getType().toLowerCase();

        if (!filtro.isEmpty() && !nombrePoke.contains(filtro) && !tipoPoke.contains(filtro)) {
            continue; // Saltar si no coincide
        }

        JLabel lblNombre = new JLabel("🔹 Nombre: " + p.getName());
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel lblTipo = new JLabel("🔸 Tipo: " + p.getType());
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel lblVida = new JLabel("❤️ HP: " + p.getHealthPoints() + " / " + p.getMaxHealthPoints());
        lblVida.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(lblNombre);
        panel.add(lblTipo);
        panel.add(lblVida);
        panel.add(new JLabel("💥 Ataques:"));
        for (Ataque a : p.getAttacks()) {
            panel.add(new JLabel(" - " + a.getDamageName() + " (" + a.getDamagePotency() + " daño)"));
        }
        panel.add(Box.createVerticalStrut(10));
    }

    JScrollPane scroll = new JScrollPane(panel);
    scroll.setBorder(BorderFactory.createEmptyBorder());
    return scroll;
}


    public void mostrarVentanaBatalla() {
        ventanaPrincipal = new JFrame("Batalla Pokémon");
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setSize(1000, 650);
        ventanaPrincipal.setLocationRelativeTo(null);

        JPanel panel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon(getClass().getResource("/vista/recursos/fondo_pokemon.png"));
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        ventanaPrincipal.setContentPane(panel);

        initBattleWindow(panel);
        attachListeners();

        ventanaPrincipal.setVisible(true);
    }

    private void mostrarVentanaFinDeBatalla() {
    int opcion = JOptionPane.showOptionDialog(
        this,
        "🎉 La batalla ha terminado.\n¿Deseas jugar otra vez o salir del programa?",
        "¡Fin de la Batalla!",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        new Object[]{"🔁 Jugar de nuevo", "❌ Salir"},
        "🔁 Jugar de nuevo"
    );

    if (opcion == JOptionPane.YES_OPTION) {
        // Reiniciar juego: mostramos ventana de entrenadores
        ventanaPrincipal.dispose(); // Cierra la ventana actual (batalla)
        controlador.reiniciar();
        mostrarVentanaEntrenadores(); // Empieza desde cero
    } else {
        System.exit(0); // Cierra completamente el programa
    }
}


    private void initBattleWindow(Container parent) {
        createBattleLogArea(parent);
        createHistorialPanel(parent);
        createEntrenadorLabels(parent);
        createPokemonLabelsAndHP(parent);
        createAttackCombos(parent);

        btnGuardarPartida = new JButton("💾 Guardar Historial");
        btnGuardarPartida.setBounds(320, 200, 180, 30);
        btnGuardarPartida.setEnabled(false);
        parent.add(btnGuardarPartida);
    }

    private void createBattleLogArea(Container parent) {
        textoBatalla = new JTextArea();
        textoBatalla.setEditable(false);
        textoBatalla.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textoBatalla.setLineWrap(true);
        textoBatalla.setWrapStyleWord(true);
        
        scrollTextoBatalla = new JScrollPane(textoBatalla);
        scrollTextoBatalla.setBounds(50, 240, 700, 350);
        parent.add(scrollTextoBatalla);
        DefaultCaret caret = (DefaultCaret) textoBatalla.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        textoBatalla.append("🌀 ¡Comienza Batalla! Ronda " + controlador.getRondaActual()
            + ". Turno de " + controlador.getNombreTurnoActual() + "!\n\n");
    }

    private void createHistorialPanel(Container parent) {
        historialArea = new JTextPane();
        historialArea.setContentType("text/html");
        historialArea.setEditable(false);
        historialArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollHist = new JScrollPane(historialArea);
        scrollHist.setBounds(800, 240, 150, 350);
        parent.add(scrollHist);
        updateHistorial();
    }

    private void createEntrenadorLabels(Container parent) {
        nuevaEtiqueta("Entrenador 1: " + controlador.getEntrenador1().getNombre(), parent, 50, 20, 400, 30);
        nuevaEtiqueta("Entrenador 2: " + controlador.getEntrenador2().getNombre(), parent, 550, 20, 400, 30);
    }

    private void createPokemonLabelsAndHP(Container parent) {
        labelNombrePokemon1 = nuevaEtiqueta(controlador.getNombrePokemonActual(0), parent, 50, 60, 200, 25);
        labelNombrePokemon2 = nuevaEtiqueta(controlador.getNombrePokemonActual(1), parent, 550, 60, 200, 25);
        barraHP1 = nuevaBarra(controlador.getHealthPointsActual(0), controlador.getMaxHealthPointsActual(0), parent, 50, 90, 200, 25);
        barraHP2 = nuevaBarra(controlador.getHealthPointsActual(1), controlador.getMaxHealthPointsActual(1), parent, 550, 90, 200, 25);
    }

    private void createAttackCombos(Container parent) {
        comboAtaques1 = nuevoCombo(parent, 50, 130, 200, 30);
        comboAtaques2 = nuevoCombo(parent, 550, 130, 200, 30);
        actualizarAtaques();
        btnRealizarTurno = nuevoBoton(parent, "⚔️ Realizar Turno", 320, 150, 180, 40);
    }

    private void attachListeners() {
        btnRealizarTurno.addActionListener(e -> {
            int j = controlador.getTurnoActual();
            int idx = (j == 0 ? comboAtaques1 : comboAtaques2).getSelectedIndex();
            ResultadoTurno r = controlador.realizarTurno(j, idx);
            textoBatalla.append("🎯 " + r.getResumen() + "\n\n");
            textoBatalla.append("➡️ Ahora: " + controlador.getNombreTurnoActual() + " - Ronda " + controlador.getRondaActual() + "\n\n");
            barraHP1.setValue(controlador.getHealthPointsActual(0));
            barraHP2.setValue(controlador.getHealthPointsActual(1));
            labelNombrePokemon1.setText(controlador.getNombrePokemonActual(0));
            labelNombrePokemon2.setText(controlador.getNombrePokemonActual(1));
            actualizarAtaques();
            updateHistorial();
            if (controlador.isJuegoTerminado()) {
            btnGuardarPartida.setEnabled(true);
}

        });

        btnGuardarPartida.addActionListener(e -> {
            controlador.guardarHistorial("historial_batalla");
            JOptionPane.showMessageDialog(this,
                "Historial guardado en historial_batalla.txt 🎉", "¡Guardado!",
                JOptionPane.INFORMATION_MESSAGE);
                mostrarVentanaFinDeBatalla();
        });
    }

    private JLabel nuevaEtiqueta(String text, Container parent, int x, int y, int w, int h) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 16));
        l.setBounds(x, y, w, h);
        parent.add(l);
        return l;
    }

    private JProgressBar nuevaBarra(int val, int max, Container parent, int x, int y, int w, int h) {
        JProgressBar b = new JProgressBar(0, max);
        b.setValue(val);
        b.setStringPainted(true);
        b.setBounds(x, y, w, h);
        parent.add(b);
        return b;
    }

    private JComboBox<String> nuevoCombo(Container parent, int x, int y, int w, int h) {
        JComboBox<String> c = new JComboBox<>();
        c.setBounds(x, y, w, h);
        parent.add(c);
        return c;
    }

    private JButton nuevoBoton(Container parent, String text, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBounds(x, y, w, h);
        parent.add(b);
        return b;
    }

    private void updateHistorial() {
        List<String> h = controlador.getHistorialDetallado();
        StringBuilder html = new StringBuilder("<html><body style='font-family:sans-serif;font-size:12px;'>");
        for (String ln : h) {
            if (ln.startsWith("Ronda")) html.append("<p style='color:#4287f5;font-weight:bold;'>🌀 " + ln + "</p>");
            else if (ln.startsWith("Turno")) html.append("<p style='margin-left:15px;'>🎯 " + ln + "</p>");
            else if (ln.startsWith("Ganador")) html.append("<p style='color:#28a745;font-weight:bold;'>🏆 " + ln + "</p>");
            else if (ln.startsWith("GANADOR")) html.append("<p style='color:#d6336c;font-weight:bold;'>🎉 " + ln + "</p>");
            else html.append("<p>" + ln + "</p>");
        }
        html.append("</body></html>");
        historialArea.setText(html.toString());
    }

    private void actualizarAtaques() {
        comboAtaques1.removeAllItems();
        comboAtaques2.removeAllItems();
        for (Ataque a : controlador.getAtaquesDisponibles(0)) comboAtaques1.addItem(a.getDamageName());
        for (Ataque a : controlador.getAtaquesDisponibles(1)) comboAtaques2.addItem(a.getDamageName());
    }
}
