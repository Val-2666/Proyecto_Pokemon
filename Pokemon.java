import java.util.ArrayList;

public class Pokemon {
    private String nombre;
    private String tipo;
    private int puntosSalud;

    // private ArrayList<Ataque> ataques;

    public Pokemon(String nombre, String tipo, int puntosSalud) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.puntosSalud = puntosSalud;
        // this.ataques = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPuntosSalud() {
        return puntosSalud;
    }

    public void setPuntosSalud(int puntosSalud) {
        this.puntosSalud = puntosSalud;
    }

    public void recibirDanio(int danio) {
        puntosSalud -= danio;
        if (puntosSalud < 0) {
            puntosSalud = 0;
        }
    }

    // Unico punto de interacción con Ataque
    public void usarAtaque(Ataque ataque, Pokemon enemigo) {
        ataque.atacar(this, enemigo); // 'this' es el atacante
    }
}
