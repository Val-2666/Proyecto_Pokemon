package modelo;

import java.util.ArrayList;

public class AtaquesDisponibles {

    public static ArrayList<Ataque> obtenerAtaquesDisponibles() {
        ArrayList<Ataque> ataques = new ArrayList<>();

        ataques.add(new Ataque("Lanzallamas", "fuego", 90));
        ataques.add(new Ataque("Hidrobomba", "agua", 110));
        ataques.add(new Ataque("Rayo Solar", "planta", 120));
        ataques.add(new Ataque("Impactrueno", "electrico", 40));
        ataques.add(new Ataque("Ascuas", "fuego", 40));
        ataques.add(new Ataque("Surf", "agua", 90));
        ataques.add(new Ataque("Latigo Cepa", "planta", 45));
        ataques.add(new Ataque("Trueno", "electrico", 110));
        ataques.add(new Ataque("Placaje", "normal", 40));
        ataques.add(new Ataque("Golpe Cuerpo", "normal", 85));

        return ataques;
    }
}