import java.util.ArrayList;

public class Pokemon {
    private String nombre;
    private String tipo;
    private int puntosSalud;

    // Comentamos esto porque la clase Ataque aún no existe
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

    // public ArrayList<Ataque> getAtaques() {
    //     return ataques;
    // }

    // public void agregarAtaque(Ataque ataque) {
    //     if (ataques.size() < 4) {
    //         ataques.add(ataque);
    //     } else {
    //         System.out.println("Este Pokémon ya tiene 4 ataques.");
    //     }
    // }

    public void recibirDanio(int danio) {
        puntosSalud -= danio;
        if (puntosSalud < 0) {
            puntosSalud = 0;
        }
    }

    // public void atacar(Pokemon enemigo, Ataque ataque) {
    //     int danio = ataque.getPotencia();
    //     if (tieneVentaja(this.tipo, enemigo.getTipo())) {
    //         danio += danio * 0.3;
    //         System.out.println("¡Es súper efectivo!");
    //     }
    //     System.out.println(this.nombre + " usa " + ataque.getNombre() + " contra " + enemigo.getNombre());
    //     enemigo.recibirDanio(danio);
    // }

    private boolean tieneVentaja(String tipoAtacante, String tipoDefensor) {
        return (tipoAtacante.equals("Fuego") && tipoDefensor.equals("Planta")) ||
               (tipoAtacante.equals("Agua") && tipoDefensor.equals("Fuego")) ||
               (tipoAtacante.equals("Planta") && tipoDefensor.equals("Agua"));
    }
}
