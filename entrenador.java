import java.util.ArrayList;
import java.util.Scanner;

public class Entrenador {
    private String nombre;
    private ArrayList<Pokemon> equipo;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Pokemon> getEquipo() {
        return equipo;
    }

    public void agregarPokemon(Pokemon p) {
        if (equipo.size() < 3) {
            equipo.add(p);
        } else {
            System.out.println("Ya tienes 3 Pokémon en tu equipo.");
        }
    }

    public Pokemon obtenerPokemonConMenosSalud() {
        if (equipo.isEmpty()) return null;

        Pokemon conMenosHP = null;
        int minHP = Integer.MAX_VALUE;

        for (Pokemon p : equipo) {
            if (p.getHealthPoints() > 0 && p.getHealthPoints() < minHP) {
                conMenosHP = p;
                minHP = p.getHealthPoints();
            }
        }

        // Si todos están debilitados, retorna el primero 
        if (conMenosHP == null) {
            conMenosHP = equipo.get(0);
        }

        return conMenosHP;
    }

    // Método opcional para saber si el entrenador sigue con vida
    public boolean tienePokemonsVivos() {
        for (Pokemon p : equipo) {
            if (p.getHealthPoints() > 0) {
                return true;
            }
        }
        return false;
    }

    // Elije pokemones de forma manual para futuras batallas múltiples
    public Pokemon elegirPokemonParaBatalla(Scanner scanner) {
        ArrayList<Pokemon> disponibles = new ArrayList<>();
        for (Pokemon p : equipo) {
            if (p.getHealthPoints() > 0) {
                disponibles.add(p);
            }
        }

        if (disponibles.isEmpty()) {
            System.out.println("Todos los Pokémon están debilitados.");
            return null;
        }

        System.out.println("Elige un Pokémon para la batalla:");
        for (int i = 0; i < disponibles.size(); i++) {
            Pokemon p = disponibles.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " (" + p.getType() + ") HP: " + p.getHealthPoints());
        }

        int eleccion = -1;
        while (eleccion < 1 || eleccion > disponibles.size()) {
            System.out.print("Opción (1-" + disponibles.size() + "): ");
            eleccion = scanner.nextInt();
        }

        return disponibles.get(eleccion - 1);
    }
}
