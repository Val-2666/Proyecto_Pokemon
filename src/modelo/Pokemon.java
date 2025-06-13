package modelo;

import modelo.PokemonDebilitadoException;
import modelo.AtaqueNoDisponibleException;
import java.util.ArrayList;
import java.util.List;


/** Representa un Pokémon con sus estadísticas y ataques. */
public class Pokemon {
    private String name;
    private String type;
    private int healthPoints;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private int maxHealthPoints;
    private List<Ataque> attacks;

    public Pokemon(String name, String type, int healthPoints, int attack, int defense, int speed) {
        this.name = name;
        this.type = type;
        this.healthPoints = healthPoints;
        this.maxHealthPoints = healthPoints;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.specialAttack = 0;
        this.specialDefense = 0;
        this.attacks = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getHealthPoints() { return healthPoints; }
    public int getMaxHealthPoints() { return maxHealthPoints; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpecialAttack() { return specialAttack; }
    public int getSpecialDefense() { return specialDefense; }
    public int getSpeed() { return speed; }
    public List<Ataque> getAttacks() { return attacks; }

    public void addAttack(Ataque ataque) {
        if (attacks.size() < 4) {
            attacks.add(ataque);
        } else {
            System.out.println("Este Pokémon ya tiene 4 ataques.");
        }
    }

    public void subtractHp(int damage) {
        this.healthPoints = Math.max(0, this.healthPoints - damage);
    }

    /** 
     * Intenta usar un ataque sobre otro Pokémon.
     * @throws PokemonDebilitadoException si este Pokémon ya está a 0 HP.
     * @throws AtaqueNoDisponibleException si el ataque no está en su lista.
     */
    public void useAttack(Ataque ataque, Pokemon enemy)
        throws PokemonDebilitadoException, AtaqueNoDisponibleException {
        if (estaDerrotado()) {
            throw new PokemonDebilitadoException(
                "El Pokémon " + name + " está debilitado y no puede atacar."
            );
        }
        if (!attacks.contains(ataque)) {
            throw new AtaqueNoDisponibleException(
                "Ataque " + ataque.getDamageName() + " no disponible para " + name
            );
        }
        ataque.applyAttack(enemy);
    }

    public boolean estaDerrotado() {
        return this.healthPoints <= 0;
    }
}
