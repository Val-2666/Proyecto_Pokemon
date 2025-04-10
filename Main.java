import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----- Simulador de Batallas Pokémon -----");

        // 1. Nombres de los entrenadores
        System.out.print("Nombre del Entrenador 1: ");
        String nombre1 = scanner.nextLine();
        Entrenador entrenador1 = new Entrenador(nombre1);

        System.out.print("Nombre del Entrenador 2: ");
        String nombre2 = scanner.nextLine();
        Entrenador entrenador2 = new Entrenador(nombre2);

        // 2. Crear equipos para ambos entrenadores
        System.out.println("\n--- Crear equipo para " + nombre1 + " ---\n");
        crearEquipo(scanner, entrenador1);

        System.out.println("\n--- Crear equipo para " + nombre2 + " ---\n");
        crearEquipo(scanner, entrenador2);

        System.out.println("\n ¡Equipos listos!");
        System.out.println("Presiona ENTER para comenzar la batalla...");
        scanner.nextLine();

        System.out.println("\n--- ¡Comienza la batalla! ---\n");

        Pokemon poke1 = entrenador1.obtenerPokemonConMenosSalud();
        Pokemon poke2 = entrenador2.obtenerPokemonConMenosSalud();

        boolean turnoJugador1 = true;
        int ronda = 1;

        while (poke1.getHealthPoints() > 0 && poke2.getHealthPoints() > 0) {
            limpiarConsola();
            System.out.println("\n=============================\n");
            System.out.println("  RONDA " + ronda);
            System.out.println("=============================\n");

            if (turnoJugador1) {
                System.out.println(" Turno del equipo de " + nombre1);
                System.out.println("Turno de: " + poke1.getName());
                System.out.println();
                Ataque atk1 = elegirAtaque(scanner, poke1);
                poke1.useAttack(atk1, poke2);
            } else {
                System.out.println(" Turno del equipo de " + nombre2);
                System.out.println("Turno de: " + poke2.getName());
                System.out.println();
                Ataque atk2 = elegirAtaque(scanner, poke2);
                poke2.useAttack(atk2, poke1);
                ronda++;
            }

            System.out.println("\n Vida restante:");
            System.out.println(poke1.getName() + " (" + nombre1 + "): " + poke1.getHealthPoints() + " HP");
            System.out.println(poke2.getName() + " (" + nombre2 + "): " + poke2.getHealthPoints() + " HP");

            System.out.println("\nPresiona ENTER para continuar...");
            scanner.nextLine();

            turnoJugador1 = !turnoJugador1;
        }

        System.out.println("\n=============================\n");
        System.out.println("--- Resultado de la Batalla ---");
        if (poke1.getHealthPoints() <= 0) {
            System.out.println(poke2.getName() + " ---- Ha ganado. ¡Entrenador " + nombre2 + " es el vencedor!");
        } else {
            System.out.println(poke1.getName() + " ---- Ha ganado. ¡Entrenador " + nombre1 + " es el vencedor!");
        }
        System.out.println("=============================\n");

        scanner.close();
    }

    public static void crearEquipo(Scanner scanner, Entrenador entrenador) {
        System.out.println("¿Deseas crear tu equipo?");
        System.out.println("1. Manualmente");
        System.out.println("2. Aleatoriamente");

        int opcion = leerEntero(scanner, 1, 2);

        if (opcion == 1) {
            System.out.println("\n--- Tu Pokémon ---\n");

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Tipo (fuego, agua, planta): ");
            String tipo = scanner.nextLine().toLowerCase();

            System.out.print("Puntos de salud (HP): ");
            int hp = leerEntero(scanner, 1, 500);

            System.out.print("Defensa: ");
            int defensa = leerEntero(scanner, 1, 100);

            Pokemon p = new Pokemon(nombre, tipo, hp, defensa);

            for (int j = 0; j < 4; j++) {
                System.out.println("\n--- Ataque #" + (j + 1) + " ---");

                System.out.print("Nombre: ");
                String anombre = scanner.nextLine();

                System.out.print("Tipo de daño (fuego, agua, planta): ");
                String atipo = scanner.nextLine().toLowerCase();

                System.out.print("Potencia: ");
                int potencia = leerEntero(scanner, 1, 200);

                p.addAttack(new Ataque(anombre, atipo, potencia));
            }

            entrenador.agregarPokemon(p);
            System.out.println();

        } else {
            String[] nombresPool = {"Charmander", "Bulbasaur", "Squirtle", "Torchic", "Treecko", "Mudkip"};
            String[] tiposPool = {"fuego", "planta", "agua"};
            String[][] ataquesPorTipo = {
                {"Llama Ardiente", "Ascuas", "Giro Ígneo", "Fuego Fatuo"},
                {"Hoja Afilada", "Drenadoras", "Rayo Solar", "Látigo Cepa"},
                {"Pistola Agua", "Hidrobomba", "Salpicar", "Ola Aplastante"}
            };

            Random rand = new Random();
            int idx = rand.nextInt(nombresPool.length);
            String nombre = nombresPool[idx];
            String tipo = tiposPool[idx % tiposPool.length];

            int hp = 80 + rand.nextInt(41);
            int defensa = 10 + rand.nextInt(11);

            Pokemon p = new Pokemon(nombre, tipo, hp, defensa);

            int tipoIndex = tipo.equals("fuego") ? 0 : tipo.equals("agua") ? 2 : 1;
            for (int j = 0; j < 4; j++) {
                String nombreAtaque = ataquesPorTipo[tipoIndex][j];
                int potencia = 15 + rand.nextInt(31);
                p.addAttack(new Ataque(nombreAtaque, tipo, potencia));
            }

            entrenador.agregarPokemon(p);
            System.out.println("\n>>>> El Pokémon que atrajo tu suerte fue: " + p.getName() + " (" + p.getType() + "), HP: " + p.getHealthPoints() + ", DEF: " + p.getDefense());
            System.out.println("¡ Equipo aleatorio creado !\n");
        }
    }

    public static Ataque elegirAtaque(Scanner scanner, Pokemon pokemon) {
        System.out.println("Ataques de " + pokemon.getName() + ":");
        ArrayList<Ataque> ataques = pokemon.getAttacks();
        for (int i = 0; i < ataques.size(); i++) {
            Ataque a = ataques.get(i);
            System.out.println((i + 1) + ". " + a.getdamagename() + " (" + a.getdamagetype() + ") Potencia: " + a.getdamagepotency());
        }

        int eleccion = leerEntero(scanner, 1, ataques.size());

        return ataques.get(eleccion - 1);
    }

    public static int leerEntero(Scanner scanner, int min, int max) {
        int num = -1;
        boolean valido = false;

        while (!valido) {
            try {
                System.out.print("Ingresa un número (" + min + "-" + max + "): ");
                num = scanner.nextInt();
                scanner.nextLine();

                if (num >= min && num <= max) {
                    valido = true;
                } else {
                    System.out.println("Número fuera de rango. Intenta de nuevo.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Son números, no letras, tonto. Vuelve a intentar.");
                scanner.nextLine();
            }
        }

        return num;
    }

    public static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la consola.");
        }
    }
}