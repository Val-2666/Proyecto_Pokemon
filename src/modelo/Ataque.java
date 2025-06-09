package modelo;

public class Ataque {
    private String damageName;
    private String damageType;
    private int damagePotency;

    // Constructor principal
    public Ataque(String damageName, String damageType, int damagePotency) {
        this.damageName = damageName;
        this.damageType = damageType.toLowerCase(); // para evitar problemas con mayúsculas
        this.damagePotency = damagePotency;
    }

    // Constructor alternativo (para ataques sin tipo)
    public Ataque(String damageName, int damagePotency) {
        this.damageName = damageName;
        this.damageType = "normal";  // Tipo por defecto
        this.damagePotency = damagePotency;
    }

    // Getters
    public String getDamageName() {
        return damageName;
    }

    public String getDamageType() {
        return damageType;
    }

    public int getDamagePotency() {
        return damagePotency;
    }

    // Setters
    public void setDamageName(String damageName) {
        this.damageName = damageName;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType.toLowerCase();
    }

    public void setDamagePotency(int damagePotency) {
        this.damagePotency = damagePotency;
    }

    // Aplica el ataque al Pokémon enemigo
    public void applyAttack(Pokemon enemy) {
        int damage = calculateDamage(enemy.getType(), enemy.getDefense());
        enemy.subtractHp(damage);
        System.out.println("El ataque " + damageName + " ha hecho " + damage + " de daño a " + enemy.getName());
    }

    // Calcula el daño con o sin ventaja de tipo
    public int calculateDamage(String enemyType, int enemyDefense) {
        double baseMultiplier = 1.0;

        if (hasAdvantage(enemyType.toLowerCase())) {
            baseMultiplier += 0.3;  // ventaja de tipo
        }

        int rawDamage = (int) (damagePotency * baseMultiplier);
        int finalDamage = rawDamage - enemyDefense;
        return Math.max(finalDamage, 0);  // nunca daño negativo
    }

    // Verifica ventaja de tipo
    public boolean hasAdvantage(String enemyType) {
        return (damageType.equals("agua") && enemyType.equals("fuego")) ||
               (damageType.equals("fuego") && enemyType.equals("planta")) ||
               (damageType.equals("planta") && enemyType.equals("agua"));
    }
}
