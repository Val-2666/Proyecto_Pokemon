package controlador;

import modelo.Entrenador;
import modelo.PokemonesPro;
import modelo.Pokemon;
import modelo.Ataque;
import modelo.ResultadoTurno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controlador {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private int turno; // 0 = jugador1, 1 = jugador2
    private boolean juegoTerminado;

    // Gestión de rondas y progreso
    private int rondaActual = 1;
    private int rondasGanadasJugador1 = 0;
    private int rondasGanadasJugador2 = 0;
    private int indicePokemon = 0;
    private boolean rondaTerminada;

    // Estado del turno
    private boolean pokemonDebilitadoEsteTurno = false;
    private String nombrePokemonDebilitado = null;
    private String ganadorRondaActual = null;

    public Controlador() {
        this.turno = 0;
        this.juegoTerminado = false;
        this.rondaTerminada = false;
    }

    public void crearEntrenadores(String nombre1, String nombre2) {
        List<Pokemon> pool = new ArrayList<>(PokemonesPro.obtenerPokemonesDisponibles());
        Collections.shuffle(pool);

        List<Pokemon> equipo1 = new ArrayList<>(pool.subList(0, 3));
        List<Pokemon> equipo2 = new ArrayList<>(pool.subList(3, 6));

        this.entrenador1 = new Entrenador(nombre1, equipo1);
        this.entrenador2 = new Entrenador(nombre2, equipo2);

        entrenador1.setPokemonActual(equipo1.get(0));
        entrenador2.setPokemonActual(equipo2.get(0));

        this.indicePokemon = 0;
        this.rondaActual = 1;
        this.rondasGanadasJugador1 = 0;
        this.rondasGanadasJugador2 = 0;
        this.juegoTerminado = false;
        this.turno = 0;
        this.rondaTerminada = false;
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

    public ResultadoTurno realizarTurno(int jugador, int indiceAtaque) {
        if (juegoTerminado) {
            return new ResultadoTurno("El juego ya terminó.", true, true);
        }

        if (jugador != turno) {
            return new ResultadoTurno("No es el turno del jugador " + (jugador + 1), false, false);
        }

        Entrenador atacante = (jugador == 0) ? entrenador1 : entrenador2;
        Entrenador defensor = (jugador == 0) ? entrenador2 : entrenador1;

        Pokemon atacantePokemon = atacante.getPokemonActual();
        Pokemon defensorPokemon = defensor.getPokemonActual();

        Ataque ataque = atacantePokemon.getAttacks().get(indiceAtaque);
        int danio = ataque.getDamagePotency();
        defensorPokemon.subtractHp(danio);

        // Reiniciar estado del turno
        rondaTerminada = false;
        pokemonDebilitadoEsteTurno = false;
        nombrePokemonDebilitado = null;
        ganadorRondaActual = null;

        String resumen = "Turno de " + atacante.getNombre() + ":\n";
        resumen += atacante.getNombre() + " usó " + ataque.getDamageName() +
                " e hizo " + danio + " de daño a " + defensorPokemon.getName() + ".";

        if (defensorPokemon.estaDerrotado()) {
            pokemonDebilitadoEsteTurno = true;
            nombrePokemonDebilitado = defensorPokemon.getName();
            ganadorRondaActual = atacante.getNombre();

            if (jugador == 0) {
                rondasGanadasJugador1++;
            } else {
                rondasGanadasJugador2++;
            }

            rondaActual++;
            indicePokemon++;
            rondaTerminada = true;

            if (indicePokemon >= entrenador1.getEquipo().size() ||
                indicePokemon >= entrenador2.getEquipo().size()) {
                juegoTerminado = true;
            } else if (rondaActual > 3) {
                juegoTerminado = true;
            } else {
                entrenador1.setPokemonActual(entrenador1.getEquipo().get(indicePokemon));
                entrenador2.setPokemonActual(entrenador2.getEquipo().get(indicePokemon));
            }
        } else {
            turno = 1 - turno;
        }

        return new ResultadoTurno(resumen, juegoTerminado, rondaTerminada);
    }

    public boolean terminoRonda() {
        return rondaTerminada;
    }

    public boolean terminoJuego() {
        return juegoTerminado;
    }

    public String getGanador() {
        if (!juegoTerminado) return null;

        if (rondasGanadasJugador1 > rondasGanadasJugador2) {
            return entrenador1.getNombre();
        } else if (rondasGanadasJugador2 > rondasGanadasJugador1) {
            return entrenador2.getNombre();
        } else {
            return "Empate";
        }
    }

    public String getNombreTurnoActual() {
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

    public boolean huboDebilitado() {
        return pokemonDebilitadoEsteTurno;
    }

    public String getNombrePokemonDebilitado() {
        return nombrePokemonDebilitado;
    }

    public String getGanadorRondaActual() {
        return ganadorRondaActual;
    }
}
