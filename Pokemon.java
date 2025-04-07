import java.util.ArrayList;

public class Pokemon {
    private String name;
    private String type;
    private int healthPoints;
    private int hp;
    private int defense;
    private ArrayList<Attack> attacks;

    public Pokemon(String name, String type, int healthPoints, int hp, int defense) {
        this.name = name;
        this.type = type;
        this.healthPoints = healthPoints;
        this.hp = hp;
        this.defense = defense;
        this.attacks = new ArrayList<>();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void subtractHp(int damage) {
        healthPoints -= damage;
        if (healthPoints < 0) {
            healthPoints = 0;
        }
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public void addAttack(Attack attack) {
        attacks.add(attack);
    }

    // ✅ Método para usar ataque (para que funcione el Main.java)
    public void useAttack(Attack attack, Pokemon enemy) {
        attack.Applyattack(enemy);
    }
}
