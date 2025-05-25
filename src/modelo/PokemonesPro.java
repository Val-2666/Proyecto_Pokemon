package modelo;
import java.util.ArrayList;
import java.util.Collections;

public class PokemonesPro {

    // Método para obtener la lista completa de Pokémon disponibles
    public static ArrayList<Pokemon> obtenerPokemonesDisponibles() {
        ArrayList<Pokemon> pool = new ArrayList<>();

        pool.add(new Pokemon("Charmander", "fuego", 100, 52, 43, 60));
        pool.add(new Pokemon("Squirtle", "agua", 110, 48, 65, 43));
        pool.add(new Pokemon("Bulbasaur", "planta", 105, 49, 49, 45));
        pool.add(new Pokemon("Pikachu", "electrico", 90, 55, 40, 90));
        pool.add(new Pokemon("Growlithe", "fuego", 110, 70, 45, 60));
        pool.add(new Pokemon("Poliwag", "agua", 95, 50, 40, 90));
        pool.add(new Pokemon("Oddish", "planta", 100, 50, 55, 30));
        pool.add(new Pokemon("Magnemite", "electrico", 85, 35, 70, 45));
        pool.add(new Pokemon("Torchic", "fuego", 95, 60, 40, 45));
        pool.add(new Pokemon("Lotad", "agua", 90, 40, 50, 30)); // Tipo Agua simplificado

        return pool;
    }

    // Método para obtener un equipo de 3 Pokémon aleatorios diferentes
    public static ArrayList<Pokemon> getRandomTeam() {
        ArrayList<Pokemon> pool = obtenerPokemonesDisponibles();
        Collections.shuffle(pool); // Mezcla la lista de Pokémon

        ArrayList<Pokemon> team = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            team.add(pool.get(i));
        }

        return team;
    }
}
