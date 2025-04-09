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

    public Pokemon elegirPokemonParaBatalla(Scanner scanner) {
        System.out.println("Elige un Pokémon para la batalla:");
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon p = equipo.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " (" + p.getType() + ") HP: " + p.getHealthPoints());
        }

        int eleccion = -1;
        while (eleccion < 1 || eleccion > equipo.size()) {
            System.out.print("Opción (1-" + equipo.size() + "): ");
            eleccion = scanner.nextInt();
        }

        return equipo.get(eleccion - 1);
    }

    public Pokemon obtenerPokemonConMenosSalud() {
        if (equipo.isEmpty()) return null;

        Pokemon conMenosHP = equipo.get(0);
        for (Pokemon p : equipo) {
            if (p.getHealthPoints() < conMenosHP.getHealthPoints()) {
                conMenosHP = p;
            }
        }
        return conMenosHP;
    }
}