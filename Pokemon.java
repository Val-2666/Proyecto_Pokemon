import java.util.ArrayList;

public class Pokemon {
    private String name;
    private String type;
    private int healthPoints;
    private int defense;
    private ArrayList<Ataque> attacks;

    // Constructor
    public Pokemon(String name, String type, int healthPoints, int defense) {
        this.name = name;
        this.type = type;
        this.healthPoints = healthPoints;
        this.defense = defense;
        this.attacks = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getDefense() {
        return defense;
    }

    public ArrayList<Ataque> getAttacks() {
        return attacks;
    }

    // Métodos
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

    public void useAttack(Ataque ataque, Pokemon enemigo) {
        ataque.applyAttack(enemigo);
    }
}
