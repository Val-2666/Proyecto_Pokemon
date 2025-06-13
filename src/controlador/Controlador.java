package controlador;

import modelo.PokemonDebilitadoException;
import modelo.AtaqueNoDisponibleException;
import modelo.Entrenador;
import modelo.PokemonesPro;
import modelo.Pokemon;
import modelo.Ataque;
import modelo.ResultadoTurno;
import persistencia.GestorDeArchivos;
import estructuras.ListaEnlazada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Controlador {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private int turno;
    private boolean juegoTerminado;

    private int rondaActual = 1;
    private int rondasGanadasJugador1 = 0;
    private int rondasGanadasJugador2 = 0;
    private int indicePokemon = 0;
    private boolean rondaTerminada;

    private boolean pokemonDebilitadoEsteTurno = false;
    private String nombrePokemonDebilitado = null;
    private String ganadorRondaActual = null;

    private ListaEnlazada<String> historial;

    public Controlador() {
        this.turno = 0;
        this.juegoTerminado = false;
        this.rondaTerminada = false;
        this.historial = new ListaEnlazada<>();
    }

    public void crearEntrenadores(String nombre1, String nombre2) {
        List<Pokemon> pool = new ArrayList<>(PokemonesPro.obtenerPokemonesDisponibles());
        Collections.shuffle(pool);

        List<Pokemon> equipo1 = pool.subList(0, 3);
        List<Pokemon> equipo2 = pool.subList(3, 6);

        this.entrenador1 = new Entrenador(nombre1, equipo1);
        this.entrenador2 = new Entrenador(nombre2, equipo2);
        this.indicePokemon = 0;
        this.rondaActual = 1;
        this.rondasGanadasJugador1 = 0;
        this.rondasGanadasJugador2 = 0;
        this.juegoTerminado = false;
        this.turno = 0;
        this.rondaTerminada = false;
        this.historial = new ListaEnlazada<>();
    }

    public ResultadoTurno realizarTurno(String nombreAtaqueActual, String ignored) {
        List<Ataque> listaAtaques = getAtaquesDisponibles(turno);
        int indice = listaAtaques.stream()
            .map(Ataque::getDamageName)
            .collect(Collectors.toList())
            .indexOf(nombreAtaqueActual);
        if (indice < 0) {
            return new ResultadoTurno("Ataque no disponible: " + nombreAtaqueActual, false, false, ganadorRondaActual);
        }
        return realizarTurno(turno, indice);
    }

    public ResultadoTurno realizarTurno(int jugador, int indiceAtaque) {
        if (juegoTerminado) {
            return new ResultadoTurno("El juego ya terminó.", true, true, ganadorRondaActual);
        }
        if (jugador != turno) {
            return new ResultadoTurno("No es el turno del jugador " + (jugador + 1), false, false, ganadorRondaActual);
        }

        Entrenador atk = (jugador == 0) ? entrenador1 : entrenador2;
        Entrenador def = (jugador == 0) ? entrenador2 : entrenador1;
        Pokemon pokeAtk = atk.getPokemonActual();
        Pokemon pokeDef = def.getPokemonActual();

        rondaTerminada = false;
        pokemonDebilitadoEsteTurno = false;
        nombrePokemonDebilitado = null;
        ganadorRondaActual = null;

        String resumen;
        try {
            if (indiceAtaque < 0 || indiceAtaque >= pokeAtk.getAttacks().size()) {
                return new ResultadoTurno("Índice de ataque inválido.", false, false, ganadorRondaActual);
            }
            Ataque ataque = pokeAtk.getAttacks().get(indiceAtaque);
            int dañoReal = ataque.calculateDamage(pokeDef.getType(), pokeDef.getDefense());
            pokeAtk.useAttack(ataque, pokeDef);

            historial.agregar(ataque.getDamageName());

            resumen = String.format(
                "Turno de %s:\n%s usó %s e hizo %d de daño a %s.",
                atk.getNombre(), atk.getNombre(),
                ataque.getDamageName(), dañoReal,
                pokeDef.getName()
            );

        } catch (PokemonDebilitadoException | AtaqueNoDisponibleException ex) {
            return new ResultadoTurno(ex.getMessage(), false, false, ganadorRondaActual);
        }

        if (pokeDef.estaDerrotado()) {
            pokemonDebilitadoEsteTurno = true;
            nombrePokemonDebilitado = pokeDef.getName();
            ganadorRondaActual = atk.getNombre();

            if (jugador == 0) rondasGanadasJugador1++;
            else rondasGanadasJugador2++;

            historial.agregar("Ganador de la ronda: " + ganadorRondaActual);

            rondaActual++;
            indicePokemon++;
            rondaTerminada = true;

            if (indicePokemon >= entrenador1.getEquipo().size()
                || indicePokemon >= entrenador2.getEquipo().size()
                || rondaActual > 3) {
                juegoTerminado = true;
                String ganadorFinal = getGanadorFinal();
                historial.agregar("GANADOR COMBATE: " + ganadorFinal);
            }

        } else {
            turno = 1 - turno;
        }

        return new ResultadoTurno(resumen, juegoTerminado, rondaTerminada, ganadorRondaActual);
    }

    public String obtenerHistorialComoTexto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < historial.tamanio(); i++) {
            sb.append(historial.obtener(i)).append("\n");
        }
        return sb.toString().trim();
    }

    public List<String> getHistorialList() {
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < historial.tamanio(); i++) {
            lista.add(historial.obtener(i));
        }
        return lista;
    }

    public List<String> getHistorialDetallado() {
        List<String> resultado = new ArrayList<>();
        List<String> ronda = new ArrayList<>();
        int rondaContador = 1;

        for (int i = 0; i < historial.tamanio(); i++) {
            String linea = historial.obtener(i);

            if (linea.startsWith("GANADOR COMBATE:")) {
                resultado.add("Ronda " + rondaContador + " - Ataques:");
                for (int j = 0; j < ronda.size(); j++) {
                    resultado.add("Turno " + (j + 1) + ". " + ronda.get(j));
                }
                resultado.add(linea);
                break;
            }

            if (linea.startsWith("Ganador de la ronda:")) {
                resultado.add("Ronda " + rondaContador + " - Ataques:");
                for (int j = 0; j < ronda.size(); j++) {
                    resultado.add("Turno " + (j + 1) + ". " + ronda.get(j));
                }
                resultado.add(linea);
                resultado.add("");
                ronda.clear();
                rondaContador++;
            } else {
                ronda.add(linea);
            }
        }

        return resultado;
    }

    public void guardarEntrenadores() {
        if (entrenador1 != null && entrenador2 != null) {
            GestorDeArchivos.guardarEntrenador(entrenador1);
            GestorDeArchivos.guardarEntrenador(entrenador2);
        }
    }

    public void cargarEntrenadores() {
        List<Entrenador> entrenadores = GestorDeArchivos.cargarEntrenadores();
        if (entrenadores.size() >= 2) {
            this.entrenador1 = entrenadores.get(0);
            this.entrenador2 = entrenadores.get(1);

            this.indicePokemon = 0;
            this.rondaActual = 1;
            this.rondasGanadasJugador1 = 0;
            this.rondasGanadasJugador2 = 0;
            this.juegoTerminado = false;
            this.turno = 0;
            this.rondaTerminada = false;
            this.historial = new ListaEnlazada<>();
        }
    }

    public void guardarPartida(String nombreArchivo) {
        List<String> historialDetallado = getHistorialDetallado();
        GestorDeArchivos.guardarHistorial(historialDetallado, nombreArchivo);
    }

    public int getTurnoActual() { return turno; }
    public String getNombreTurnoActual() {
        return (turno == 0) ? entrenador1.getNombre() : entrenador2.getNombre();
    }
    public List<Ataque> getAtaquesDisponibles(int jugador) {
        return (jugador == 0 ? entrenador1 : entrenador2).getPokemonActual().getAttacks();
    }
    public String getNombrePokemonActual(int jugador) {
        return (jugador == 0 ? entrenador1 : entrenador2).getPokemonActual().getName();
    }
    public int getHealthPointsActual(int jugador) {
        return (jugador == 0 ? entrenador1 : entrenador2).getPokemonActual().getHealthPoints();
    }
    public int getMaxHealthPointsActual(int jugador) {
        return (jugador == 0 ? entrenador1 : entrenador2).getPokemonActual().getMaxHealthPoints();
    }

    public boolean isRondaTerminada() { return rondaTerminada; }
    public boolean isJuegoTerminado()  { return juegoTerminado; }
    public boolean huboDebilitado()    { return pokemonDebilitadoEsteTurno; }
    public String getNombrePokemonDebilitado() { return nombrePokemonDebilitado; }
    public String getGanadorRondaActual()      { return ganadorRondaActual; }
    public int getRondaActual()                { return rondaActual; }
    public int getRondasGanadasJugador1()      { return rondasGanadasJugador1; }
    public int getRondasGanadasJugador2()      { return rondasGanadasJugador2; }

    public String getGanadorFinal() {
        if (!juegoTerminado) return null;
        if (rondasGanadasJugador1 > rondasGanadasJugador2) return entrenador1.getNombre();
        if (rondasGanadasJugador2 > rondasGanadasJugador1) return entrenador2.getNombre();
        return "Empate";
    }
    public void reiniciar() {
    this.entrenador1 = null;
    this.entrenador2 = null;
    this.turno = 0;
    this.juegoTerminado = false;
}
    public void guardarHistorial(String nombreArchivo) {
        List<String> historialCompleto = getHistorialDetallado();
        GestorDeArchivos.guardarHistorial(historialCompleto, nombreArchivo);
    }
     
    public Entrenador getEntrenador1() {
    return this.entrenador1;
}
    public Entrenador getEntrenador2() {
        return this.entrenador2;
    }
}
