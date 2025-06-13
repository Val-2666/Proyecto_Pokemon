package modelo;

public class ResultadoTurno {
    private String resumen;
    private boolean finDelJuego;
    private boolean finDelTurno;
    private String ganador;

    public ResultadoTurno(String resumen, boolean finDelJuego, boolean finDelTurno, String ganador) {
        this.resumen = resumen;
        this.finDelJuego = finDelJuego;
        this.finDelTurno = finDelTurno;
        this.ganador = ganador;
    }

    public String getResumen() {
        return resumen;
    }

    public boolean isFinDelJuego() {
        return finDelJuego;
    }

    public boolean isFinDelTurno() {
        return finDelTurno;
    }
    public String getGanador() {
    return this.ganador;
}

}