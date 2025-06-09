package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Entrenador {
    private String nombre;
    private ArrayList<Pokemon> equipo;
    private Pokemon pokemonActual;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = generarEquipoAleatorio();
        this.pokemonActual = equipo.get(0);
    }

    public Entrenador(String nombre, List<Pokemon> equipo) {
    this.nombre = nombre;
    this.equipo = new ArrayList<>(equipo); // Crea una copia como ArrayList
    this.pokemonActual = this.equipo.get(0);
}

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Pokemon> getEquipo() {
        return equipo;
    }

    private ArrayList<Pokemon> generarEquipoAleatorio() {
        Random random = new Random();
        ArrayList<Pokemon> equipoAleatorio = new ArrayList<>();
        ArrayList<Pokemon> pokemonesDisponibles = PokemonesPro.obtenerPokemonesDisponibles();

        if (pokemonesDisponibles.isEmpty()) {
            throw new IllegalStateException("No hay Pokémon disponibles para formar un equipo.");
        }

        while (equipoAleatorio.size() < 3) {
            Pokemon original = pokemonesDisponibles.get(random.nextInt(pokemonesDisponibles.size()));
            if (!equipoAleatorio.contains(original)) {
                Pokemon copia = new Pokemon(original.getName(), original.getType(), original.getMaxHP(), original.getAttack(), original.getDefense(), original.getSpeed());
                asignarAtaques(copia);
                equipoAleatorio.add(copia);
            }
        }

        return equipoAleatorio;
    }

    private void asignarAtaques(Pokemon pokemon) {
        ArrayList<Ataque> ataquesDisponibles = AtaquesDisponibles.obtenerAtaquesDisponibles();
        Collections.shuffle(ataquesDisponibles);

        for (int i = 0; i < 4 && i < ataquesDisponibles.size(); i++) {
            Ataque original = ataquesDisponibles.get(i);
            Ataque copia = new Ataque(original.getDamageName(), original.getDamageType(), original.getDamagePotency());
            pokemon.addAttack(copia);
        }
    }

    public Pokemon getPokemonActual() {
        if (pokemonActual == null || pokemonActual.estaDerrotado()) {
            pokemonActual = obtenerPokemonActivo();
        }
        return pokemonActual;
    }

    public void setPokemonActual(Pokemon nuevo) {
        if (equipo.contains(nuevo)) {
            this.pokemonActual = nuevo;
        } else {
            throw new IllegalArgumentException("El Pokémon no pertenece al equipo del entrenador.");
        }
    }

    public Pokemon obtenerPokemonActivo() {
        for (Pokemon p : equipo) {
            if (p.getHealthPoints() > 0) {
                return p;
            }
        }
        return null;
    }

    public boolean tienePokemonsVivos() {
        for (Pokemon p : equipo) {
            if (p.getHealthPoints() > 0) {
                return true;
            }
        }
        return false;
    }

    public void agregarPokemon(Pokemon pokemon) {
        if (equipo.size() < 3) {
            equipo.add(pokemon);
        }
    }
}