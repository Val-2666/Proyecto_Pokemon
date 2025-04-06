public class Ataque {
    private String damagename;
    private String damagetype;
    private int damagepotency;

    public Ataque(String damagename, String damagetype, int damagepotency) {
        this.damagename = damagename;
        this.damagetype = damagetype;
        this.damagepotency = damagepotency;
    }
    
    public String getdamagename() {
        return damagename;
    }
    public String getdamagetype() {
        return damagetype;
    }
    public int getdamagepotency() {
        return damagepotency;
    }
    public void setdamagename(String damagename) {
        this.damagename = damagename;
    }

    public void setdamagetype(String damagetype) {
        this.damagetype = damagetype;
    }

    public void setdamagepotency(int damagepotency) {
        this.damagepotency = damagepotency;
    }

    //metodo para calcular el daño
    public int calculatedamage(String enemytype, int enemydenfence){
        double base = 1.0;

        if(advantage(enemytype)){
            base += 0.3;
    }

    int initialdamage = (int) ((damagepotency * base));
    int finaldamage = initialdamage - enemydenfence;
    if(finaldamage < 0){
       finaldamage = 0;
    }
return finaldamage;
}
//metodo para verificar si el ataque tiene advantage sobre el enemigo
    public boolean advantage(String enemytype){
        if(this.damagetype.equals("agua") && enemytype.equals("fuego")){
            return true;
        }else return this.damagetype.equals("fuego") && enemytype.equals("planta") || this.damagetype.equals("planta") && enemytype.equals("agua");
    }

    public void applyattack(Pokemon enemy){
        int damage = calculatedamage(enemy.gettype(), enemy.getdefence());
        enemy.subtracthp(damage);
        System.out.println("El ataque " + damagename + " ha hecho " + damage + " de daño a " + enemy.getname());
    }

}