package controlador;

import modelo.Entrenador;
import modelo.PokemonesPro;
import modelo.Pokemon;
import modelo.Ataque;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controlador {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private int turno; // 0 = jugador1, 1 = jugador2
    private boolean juegoTerminado;

    //  Gestión de rondas y progreso
    private int rondaActual = 1;
    private int rondasGanadasJugador1 = 0;
    private int rondasGanadasJugador2 = 0;
    private int indicePokemon = 0;

    public Controlador() {
        this.turno = 0;
        this.juegoTerminado = false;
    }

    public void crearEntrenadores(String nombre1, String nombre2) {
        List<Pokemon> pool = new ArrayList<>(PokemonesPro.obtenerPokemonesDisponibles());
        Collections.shuffle(pool);

        List<Pokemon> equipo1 = new ArrayList<>(pool.subList(0, 3));
        List<Pokemon> equipo2 = new ArrayList<>(pool.subList(3, 6));

        this.entrenador1 = new Entrenador(nombre1, equipo1);
        this.entrenador2 = new Entrenador(nombre2, equipo2);

        // Asignar primer Pokémon
        entrenador1.setPokemonActual(equipo1.get(0));
        entrenador2.setPokemonActual(equipo2.get(0));
    }

    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    public Entrenador getEntrenador2() {
        return entrenador2;
    }

    public Pokemon getPokemonActualJugador1() {
        return entrenador1.getPokemonActual();
    }

    public Pokemon getPokemonActualJugador2() {
        return entrenador2.getPokemonActual();
    }

    public Ataque[] getAtaquesJugador1() {
        return entrenador1.getPokemonActual().getAttacks().toArray(new Ataque[0]);
    }

    public Ataque[] getAtaquesJugador2() {
        return entrenador2.getPokemonActual().getAttacks().toArray(new Ataque[0]);
    }

    public String realizarAtaque(int indiceAtaque) {
        if (juegoTerminado) return "El juego ya terminó.";

        Entrenador atacante = (turno == 0) ? entrenador1 : entrenador2;
        Entrenador defensor = (turno == 0) ? entrenador2 : entrenador1;

        Pokemon atacantePokemon = atacante.getPokemonActual();
        Pokemon defensorPokemon = defensor.getPokemonActual();

        Ataque ataque = atacantePokemon.getAttacks().get(indiceAtaque);
        int danio = ataque.getDamagePotency();
        defensorPokemon.subtractHp(danio);

        String resumen = "Turno de " + atacante.getNombre() + ":\n";
        resumen += atacante.getNombre() + " usó " + ataque.getDamageName() +
                " e hizo " + danio + " de daño a " + defensorPokemon.getName() + ".";

        if (defensorPokemon.estaDerrotado()) {
            resumen += "\n☠️ ¡" + defensorPokemon.getName() + " se ha debilitado!";

            // Registrar ronda ganada
            if (turno == 0) {
                rondasGanadasJugador1++;
            } else {
                rondasGanadasJugador2++;
            }

            resumen += "\n🏁 " + atacante.getNombre() + " ganó la Ronda " + rondaActual + ".";

            rondaActual++;
            indicePokemon++;

            if (rondaActual > 3) {
                juegoTerminado = true;
                String ganadorFinal;
                if (rondasGanadasJugador1 > rondasGanadasJugador2) {
                    ganadorFinal = entrenador1.getNombre();
                } else if (rondasGanadasJugador2 > rondasGanadasJugador1) {
                    ganadorFinal = entrenador2.getNombre();
                } else {
                    ganadorFinal = "Empate";
                }

                resumen += "\n\n🏆 ¡Ganador final: " + ganadorFinal + "!";
            } else {
                // Preparar siguiente ronda
                entrenador1.setPokemonActual(entrenador1.getEquipo().get(indicePokemon));
                entrenador2.setPokemonActual(entrenador2.getEquipo().get(indicePokemon));
                resumen += "\n\n🎮 Comienza la Ronda " + rondaActual + ".";
            }
        } else {
            turno = 1 - turno; // Cambia el turno si no ha terminado
        }

        return resumen;
    }

    public boolean juegoTerminado() {
        return juegoTerminado;
    }

    public String obtenerGanador() {
        if (!juegoTerminado) return null;

        if (rondasGanadasJugador1 > rondasGanadasJugador2) {
            return entrenador1.getNombre();
        } else if (rondasGanadasJugador2 > rondasGanadasJugador1) {
            return entrenador2.getNombre();
        } else {
            return "Empate";
        }
    }

    public String obtenerNombreTurnoActual() {
        return (turno == 0) ? entrenador1.getNombre() : entrenador2.getNombre();
    }

    public void elegirPokemonJugador1(String nombrePokemon) {
        for (Pokemon p : entrenador1.getEquipo()) {
            if (p.getName().equals(nombrePokemon)) {
                entrenador1.setPokemonActual(p);
                break;
            }
        }
    }

    public void elegirPokemonJugador2(String nombrePokemon) {
        for (Pokemon p : entrenador2.getEquipo()) {
            if (p.getName().equals(nombrePokemon)) {
                entrenador2.setPokemonActual(p);
                break;
            }
        }
    }

    public int getRondaActual() {
        return rondaActual;
    }

    public int getRondasGanadasJugador1() {
        return rondasGanadasJugador1;
    }

    public int getRondasGanadasJugador2() {
        return rondasGanadasJugador2;
    }
}
