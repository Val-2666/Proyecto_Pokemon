package modelo;

import java.util.ArrayList;
import java.util.Collections;

public class PokemonesPro {

    public static ArrayList<Pokemon> obtenerPokemonesDisponibles() {
        ArrayList<Pokemon> pool = new ArrayList<>();

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

        for (Pokemon p : new Pokemon[]{charmander, squirtle, bulbasaur, pikachu, growlithe, poliwag, oddish, magnemite, torchic, lotad}) {
            asignarAtaques(p);
            pool.add(p);
        }

        return pool;
    }

    public static ArrayList<Pokemon> getRandomTeam() {
        ArrayList<Pokemon> pool = obtenerPokemonesDisponibles();
        Collections.shuffle(pool);

        ArrayList<Pokemon> team = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Pokemon original = pool.get(i);
            Pokemon copia = new Pokemon(
                original.getName(), original.getType(),
                original.getMaxHP(), original.getAttack(),
                original.getDefense(), original.getSpeed()
            );
            for (Ataque atk : original.getAttacks()) {
                copia.addAttack(new Ataque(atk.getDamageName(), atk.getDamageType(), atk.getDamagePotency()));
            }
            team.add(copia);
        }

        return team;
    }

    public static Pokemon obtenerPokemonAleatorio() {
        ArrayList<Pokemon> disponibles = obtenerPokemonesDisponibles();
        Collections.shuffle(disponibles);
        Pokemon original = disponibles.get(0);

        Pokemon copia = new Pokemon(
            original.getName(), original.getType(),
            original.getMaxHP(), original.getAttack(),
            original.getDefense(), original.getSpeed()
        );
        for (Ataque atk : original.getAttacks()) {
            copia.addAttack(new Ataque(atk.getDamageName(), atk.getDamageType(), atk.getDamagePotency()));
        }
        return copia;
    }

    private static void asignarAtaques(Pokemon p) {
        switch (p.getType().toLowerCase()) {
            case "fuego":
                p.addAttack(new Ataque("Ascuas", "fuego", 20));
                p.addAttack(new Ataque("Giro Fuego", "fuego", 25));
                break;
            case "agua":
                p.addAttack(new Ataque("Pistola Agua", "agua", 20));
                p.addAttack(new Ataque("Burbuja", "agua", 15));
                break;
            case "planta":
                p.addAttack(new Ataque("Hoja Afilada", "planta", 20));
                p.addAttack(new Ataque("Drenadoras", "planta", 10));
                break;
            case "electrico":
                p.addAttack(new Ataque("Impactrueno", "electrico", 25));
                p.addAttack(new Ataque("Chispa", "electrico", 30));
                break;
        }

        // Ataques comunes a todos
        p.addAttack(new Ataque("Placaje", "normal", 10));
        p.addAttack(new Ataque("Gruñido", "normal", 5));
    }
}
