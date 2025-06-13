package persistencia;

import modelo.Ataque;
import modelo.Entrenador;
import modelo.Pokemon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorDeArchivos {
    private static final String ARCHIVO_ENTRENADORES = "entrenadores_guardados.txt";

    public static void guardarHistorial(List<String> historial, String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(nombreArchivo + ".txt")) {
            for (String linea : historial) {
                writer.println(linea);
            }
            System.out.println("Historial guardado exitosamente en " + nombreArchivo + ".txt");
        } catch (IOException e) {
            System.err.println("Error al guardar historial: " + e.getMessage());
        }
    }

    public static void guardarEntrenador(Entrenador entrenador) {
        if (entrenador == null) {
            System.err.println("Error: Entrenador nulo.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_ENTRENADORES, true))) {
            writer.write("ENTRENADOR:" + entrenador.getNombre() + "\n");

            for (Pokemon p : entrenador.getEquipo()) {
                writer.write("POKEMON:" + p.getName() + "," + p.getType() + "," + p.getMaxHealthPoints() + "," +
                        p.getAttack() + "," + p.getDefense() + "," + p.getSpeed() + "\n");

                for (Ataque a : p.getAttacks()) {
                    writer.write("ATAQUE:" + a.getDamageName() + "," + a.getDamageType() + "," + a.getDamagePotency() + "\n");
                }
            }

            writer.write("FIN_ENTRENADOR\n");
            writer.flush();
            System.out.println("Entrenador guardado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar entrenador: " + e.getMessage());
        }
    }

    public static List<Entrenador> cargarEntrenadores() {
        List<Entrenador> entrenadores = new ArrayList<>();
        File archivo = new File(ARCHIVO_ENTRENADORES);

        if (!archivo.exists()) {
            System.err.println("El archivo de entrenadores no existe aún.");
            return entrenadores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            String nombre = null;
            List<Pokemon> equipo = new ArrayList<>();
            Pokemon actual = null;

            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("ENTRENADOR:")) {
                    nombre = linea.substring("ENTRENADOR:".length()).trim();
                    equipo = new ArrayList<>();
                } else if (linea.startsWith("POKEMON:")) {
                    String[] partes = linea.substring("POKEMON:".length()).split(",");
                    actual = new Pokemon(partes[0], partes[1], Integer.parseInt(partes[2]),
                            Integer.parseInt(partes[3]), Integer.parseInt(partes[4]), Integer.parseInt(partes[5]));
                    equipo.add(actual);
                } else if (linea.startsWith("ATAQUE:") && actual != null) {
                    String[] partes = linea.substring("ATAQUE:".length()).split(",");
                    Ataque ataque = new Ataque(partes[0], partes[1], Integer.parseInt(partes[2]));
                    actual.addAttack(ataque);
                } else if (linea.equals("FIN_ENTRENADOR") && nombre != null) {
                    entrenadores.add(new Entrenador(nombre, equipo));
                    nombre = null;
                    equipo = new ArrayList<>();
                    actual = null;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar entrenadores: " + e.getMessage());
        }

        return entrenadores;
    }
}
