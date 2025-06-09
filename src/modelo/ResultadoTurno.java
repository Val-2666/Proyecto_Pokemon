// modelo/ResultadoTurno.java
package modelo;

public class ResultadoTurno {
    private String resumen;
    private boolean finDelJuego;
    private boolean finDelTurno;

    public ResultadoTurno(String resumen, boolean finDelJuego, boolean finDelTurno) {
        this.resumen = resumen;
        this.finDelJuego = finDelJuego;
        this.finDelTurno = finDelTurno;
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
}
