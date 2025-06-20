package modelo;

import java.util.*;

public class PokemonesPro {

    private static final ArrayList<Pokemon> pool = new ArrayList<>();
    private static final Map<String, List<Pokemon>> pokemonesPorTipo = new HashMap<>();
    private static final Map<String, Pokemon> pokemonesPorNombre = new HashMap<>();

    static {
        inicializarPool();
    }

    private static void inicializarPool() {
        Pokemon charmander = new Pokemon("Charmander", "fuego", 100, 52, 43, 60);
        Pokemon squirtle = new Pokemon("Squirtle", "agua", 110, 48, 65, 43);
        Pokemon bulbasaur = new Pokemon("Bulbasaur", "planta", 105, 49, 49, 45);
        Pokemon pikachu = new Pokemon("Pikachu", "electrico", 90, 55, 40, 90);
        Pokemon growlithe = new Pokemon("Growlithe", "fuego", 110, 70, 45, 60);
        Pokemon poliwag = new Pokemon("Poliwag", "agua", 95, 50, 40, 90);
        Pokemon oddish = new Pokemon("Oddish", "planta", 100, 50, 55, 30);
        Pokemon magnemite = new Pokemon("Magnemite", "electrico", 85, 35, 70, 45);
        Pokemon torchic = new Pokemon("Torchic", "fuego", 95, 60, 40, 45);
        Pokemon lotad = new Pokemon("Lotad", "agua", 90, 40, 50, 30);

        Pokemon[] pokemones = {charmander, squirtle, bulbasaur, pikachu, growlithe,
                poliwag, oddish, magnemite, torchic, lotad};

        for (Pokemon p : pokemones) {
            asignarAtaques(p);
            pool.add(p);

            // Indexar por tipo
            pokemonesPorTipo.computeIfAbsent(p.getType(), k -> new ArrayList<>()).add(p);

            // Indexar por nombre
            pokemonesPorNombre.put(p.getName(), p);
        }
    }

    public static ArrayList<Pokemon> obtenerPokemonesDisponibles() {
        return new ArrayList<>(pool);
    }

    public static ArrayList<Pokemon> getRandomTeam() {
        ArrayList<Pokemon> copiaPool = new ArrayList<>(pool);
        Collections.shuffle(copiaPool);

        ArrayList<Pokemon> team = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Pokemon original = copiaPool.get(i);
            Pokemon copia = copiarPokemon(original);
            team.add(copia);
        }

        return team;
    }

    public static Pokemon obtenerPokemonAleatorio() {
        ArrayList<Pokemon> disponibles = new ArrayList<>(pool);
        Collections.shuffle(disponibles);
        return copiarPokemon(disponibles.get(0));
    }

    private static Pokemon copiarPokemon(Pokemon original) {
        Pokemon copia = new Pokemon(
                original.getName(), original.getType(),
                original.getMaxHealthPoints(), original.getAttack(),
                original.getDefense(), original.getSpeed()
        );
        for (Ataque atk : original.getAttacks()) {
            copia.addAttack(new Ataque(atk.getDamageName(), atk.getDamageType(), atk.getDamagePotency()));
        }
        return copia;
    }

    private static void asignarAtaques(Pokemon p) {
        ArrayList<Ataque> compatibles = obtenerAtaquesPorTipo(p.getType());
        int maxAtaques = 4;
        int contador = 0;

        for (Ataque atk : compatibles) {
            if (contador >= maxAtaques) break;
            p.addAttack(new Ataque(atk.getDamageName(), atk.getDamageType(), atk.getDamagePotency()));
            contador++;
        }
    }

    private static ArrayList<Ataque> obtenerAtaquesPorTipo(String tipo) {
        ArrayList<Ataque> ataques = AtaquesDisponibles.obtenerAtaquesDisponibles();
        ArrayList<Ataque> filtrados = new ArrayList<>();

        for (Ataque atk : ataques) {
            if (atk.getDamageType().equalsIgnoreCase(tipo) || atk.getDamageType().equalsIgnoreCase("normal")) {
                filtrados.add(atk);
            }
        }
        return filtrados;
    }

    // ✅ Métodos nuevos para buscar usando HashMap:

    public static List<Pokemon> buscarPorTipo(String tipo) {
        return pokemonesPorTipo.getOrDefault(tipo, new ArrayList<>());
    }

    public static Pokemon buscarPorNombre(String nombre) {
        return pokemonesPorNombre.get(nombre);
    }
}
