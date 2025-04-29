import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    private static Entrenador entrenador1;
    private static Entrenador entrenador2;
    private static Pokemon poke1;
    private static Pokemon poke2;
    private static int ronda = 1;
    private static JFrame ventana;
    private static JTextArea textoBatalla;
    private static JButton btnAtacar;
    private static JComboBox<String> comboAtaques;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                iniciarBatalla();  // Llamamos directamente a iniciarBatalla en lugar de InterfazGrafica
            }
        });
    }

    public static void iniciarBatalla() {
        // Crear los entrenadores con nombres aleatorios
        entrenador1 = new Entrenador("Entrenador 1");
        entrenador2 = new Entrenador("Entrenador 2");

        // Crear la ventana de la interfaz gráfica
        ventana = new JFrame("Batalla Pokémon");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(600, 400);
        ventana.setLayout(new BorderLayout());

        // Crear área de texto para mostrar el estado de la batalla
        textoBatalla = new JTextArea();
        textoBatalla.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textoBatalla);
        ventana.add(scrollPane, BorderLayout.CENTER);

        // Crear el panel de botones y ataques
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        // Crear un JComboBox para seleccionar ataque
        comboAtaques = new JComboBox<>();
        panelBotones.add(comboAtaques);

        // Crear el botón de ataque
        btnAtacar = new JButton("Atacar");
        btnAtacar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarTurno();
            }
        });
        panelBotones.add(btnAtacar);

        ventana.add(panelBotones, BorderLayout.SOUTH);
        ventana.setVisible(true);

        // Mostrar la información inicial
        mostrarEstadoInicial();
    }

    private static void mostrarEstadoInicial() {
        // Los entrenadores ya tienen sus equipos generados
        poke1 = entrenador1.obtenerPokemonActivo();
        poke2 = entrenador2.obtenerPokemonActivo();

        // Mostrar los nombres de los entrenadores y los Pokémon
        textoBatalla.setText("¡Los equipos están listos!\n\n"
                + "Entrenador 1: " + entrenador1.getNombre() + " - Pokémon: " + poke1.getName() + "\n"
                + "Entrenador 2: " + entrenador2.getNombre() + " - Pokémon: " + poke2.getName() + "\n\n");

        // Llenar el JComboBox con los ataques del primer Pokémon
        actualizarAtaques();
    }

    private static void actualizarAtaques() {
        comboAtaques.removeAllItems();
        for (Ataque ataque : poke1.getAttacks()) {
            comboAtaques.addItem(ataque.getdamagename() + " (Potencia: " + ataque.getPower() + ")");
        }
    }

    private static void realizarTurno() {
        if (poke1.getHealthPoints() > 0 && poke2.getHealthPoints() > 0) {
            // Seleccionar ataque del primer Pokémon
            String ataqueSeleccionado = (String) comboAtaques.getSelectedItem();
            Ataque ataque = obtenerAtaquePorNombre(ataqueSeleccionado);

            // Realizar el ataque
            if (ataque != null) {
                poke1.useAttack(ataque, poke2);
            }

            // Actualizar la interfaz gráfica
            actualizarEstadoBatalla();

            // Verificar si el Pokémon defensor está vencido
            if (poke2.getHealthPoints() <= 0) {
                // El primer entrenador gana si su Pokémon ha derrotado al segundo
                mostrarResultadoBatalla();
            } else {
                // Cambio de turno: El segundo entrenador ataca
                ataqueSeleccionado = (String) comboAtaques.getSelectedItem();
                ataque = obtenerAtaquePorNombre(ataqueSeleccionado);

                if (ataque != null) {
                    poke2.useAttack(ataque, poke1);
                }

                actualizarEstadoBatalla();

                if (poke1.getHealthPoints() <= 0) {
                    // El segundo entrenador gana si su Pokémon ha derrotado al primero
                    mostrarResultadoBatalla();
                }
            }
        }
    }

    private static Ataque obtenerAtaquePorNombre(String ataqueSeleccionado) {
        for (Ataque ataque : poke1.getAttacks()) {
            if (ataque.getdamagename().equals(ataqueSeleccionado.split(" ")[0])) {
                return ataque;
            }
        }
        return null;
    }

    private static void actualizarEstadoBatalla() {
        String estado = "Ronda " + ronda + "\n\n"
                + poke1.getName() + " (" + entrenador1.getNombre() + "): " + poke1.getHealthPoints() + " HP\n"
                + poke2.getName() + " (" + entrenador2.getNombre() + "): " + poke2.getHealthPoints() + " HP\n\n";

        // Actualizar el área de texto con el nuevo estado
        textoBatalla.setText(estado);

        // Incrementar la ronda
        ronda++;
    }

    private static void mostrarResultadoBatalla() {
        String resultado;
        if (poke1.getHealthPoints() <= 0 && poke2.getHealthPoints() <= 0) {
            resultado = "¡Empate! Ambos Pokémon han caído.";
        } else if (poke1.getHealthPoints() <= 0) {
            resultado = poke2.getName() + " ha ganado. ¡Entrenador " + entrenador2.getNombre() + " es el vencedor!";
        } else {
            resultado = poke1.getName() + " ha ganado. ¡Entrenador " + entrenador1.getNombre() + " es el vencedor!";
        }

        textoBatalla.append("\n\n" + resultado);
        JOptionPane.showMessageDialog(ventana, resultado, "Fin de la Batalla", JOptionPane.INFORMATION_MESSAGE);
    }
}
