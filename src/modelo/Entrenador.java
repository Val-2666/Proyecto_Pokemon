package modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Entrenador {
    private String nombre;
    private ArrayList<Pokemon> equipo;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = generarEquipoAleatorio();
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
            Pokemon pokemonAleatorio = pokemonesDisponibles.get(random.nextInt(pokemonesDisponibles.size()));
            if (!equipoAleatorio.contains(pokemonAleatorio)) {
                asignarAtaques(pokemonAleatorio);  // 👈 Asignamos ataques
                equipoAleatorio.add(pokemonAleatorio);
            }
        }

        return equipoAleatorio;
    }

    // Método para asignar ataques aleatorios a un pokémon
    private void asignarAtaques(Pokemon pokemon) {
        ArrayList<Ataque> ataquesDisponibles = AtaquesDisponibles.obtenerAtaquesDisponibles();
        Collections.shuffle(ataquesDisponibles);
    
        for (int i = 0; i < 4 && i < ataquesDisponibles.size(); i++) {
            Ataque original = ataquesDisponibles.get(i);
            Ataque copia = new Ataque(original.getdamagename(), original.getdamagetype(), original.getdamagepotency());
            pokemon.addAttack(copia);
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

    public void agregarPokemon(Pokemon pokemon1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarPokemon'");
    }
}
