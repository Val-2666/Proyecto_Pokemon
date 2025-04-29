public class Ataque {
    private String damagename;
    private String damagetype;
    private int damagepotency;

    public Ataque(String damagename, String damagetype, int damagepotency) {
        this.damagename = damagename;
        this.damagetype = damagetype;
        this.damagepotency = damagepotency;
    }

    // Getters
    public String getdamagename() {
        return damagename;
    }

    public String getdamagetype() {
        return damagetype;
    }

    public int getdamagepotency() {
        return damagepotency;
    }

    public int getPower() {
        return damagepotency;  // 👈 Ahora sí devuelve la potencia real
    }

    // Setters
    public void setdamagename(String damagename) {
        this.damagename = damagename;
    }

    public void setdamagetype(String damagetype) {
        this.damagetype = damagetype;
    }

    public void setdamagepotency(int damagepotency) {
        this.damagepotency = damagepotency;
    }

    // Aplica el ataque al Pokémon enemigo
    public void applyAttack(Pokemon enemy) {
        int damage = calculateDamage(enemy.getType(), enemy.getDefense());
        enemy.subtractHp(damage);
        System.out.println("El ataque " + damagename + " ha hecho " + damage + " de daño a " + enemy.getName());
    }

    // Calcula el daño con o sin ventaja de tipo
    public int calculateDamage(String enemytype, int enemydefense) {
        double base = 1.0;

        if (advantage(enemytype)) {
            base += 0.3;
        }

        int rawDamage = (int) (damagepotency * base);
        int finalDamage = rawDamage - enemydefense;
        return Math.max(finalDamage, 0);
    }

    // Verifica ventaja de tipo
    public boolean advantage(String enemytype) {
        return (damagetype.equals("agua") && enemytype.equals("fuego")) ||
               (damagetype.equals("fuego") && enemytype.equals("planta")) ||
               (damagetype.equals("planta") && enemytype.equals("agua"));
    }
}
