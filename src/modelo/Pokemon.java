package modelo;

import java.util.ArrayList;

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
    private ArrayList<Ataque> attacks;

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

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getMaxHP() {
        return maxHealthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public ArrayList<Ataque> getAttacks() {
        return attacks;
    }

    public void addAttack(Ataque ataque) {
        if (attacks.size() < 4) {
            attacks.add(ataque);
        } else {
            System.out.println("Este Pokémon ya tiene 4 ataques.");
        }
    }

    public void subtractHp(int damage) {
        this.healthPoints -= damage;
        if (this.healthPoints < 0) {
            this.healthPoints = 0;
        }
    }

    public void useAttack(Ataque ataque, Pokemon enemy) {
        ataque.applyAttack(enemy);
    }

    public boolean estaDerrotado() {
        return this.healthPoints <= 0;
    }
}