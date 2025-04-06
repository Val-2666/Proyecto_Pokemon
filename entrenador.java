public class entrenador {
        import java.util.ArrayList;
    import java.util.List;
    import pokemones.Pokemon; // Importamos la clase Pokemon
    
    public class entrenador {
        private String nombre; // Nombre del entrenador
        private List<Pokemon> equipo; // Equipo de Pokémon (máximo 3)
    
        // Constructor
        public entrenador(String nombre) {
            this.nombre = nombre;
            this.equipo = new ArrayList<>();
        }
    
        // Método para agregar un Pokémon al equipo (máximo 3)
        public boolean agregarPokemon(Pokemon pokemon) {
            if (equipo.size() < 3) {
                equipo.add(pokemon);
                return true;
            } else {
                System.out.println("No se pueden agregar más de 3 Pokémon al equipo.");
                return false;
            }
        }
    
        // Método para elegir un Pokémon para la batalla
        public Pokemon elegirPokemonParaBatalla() {
            if (equipo.isEmpty()) {
                System.out.println("El equipo está vacío.");
                return null;
            }
    
            // Selecciona el Pokémon con menos salud
            Pokemon pokemonConMenosSalud = equipo.get(0);
            for (Pokemon pokemon : equipo) {
                if (pokemon.getSalud() < pokemonConMenosSalud.getSalud()) {
                    pokemonConMenosSalud = pokemon;
                }
            }
            return pokemonConMenosSalud;
        }
    
        // Método para mostrar el equipo
        public void mostrarEquipo() {
            System.out.println("Equipo de " + nombre + ":");
            for (Pokemon pokemon : equipo) {
                System.out.println("- " + pokemon.getNombre() + " (Salud: " + pokemon.getSalud() + ")");
            }
        }
    
        // Getter para el nombre
        public String getNombre() {
            return nombre;
        }
    }
}
