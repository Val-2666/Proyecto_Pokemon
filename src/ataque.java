public class Ataque {
    private String nombre_ataque;
    private String tipo_Dano;
    private int potencia_ataque;

    public Ataque(String nombre_ataque, String tipo_Dano, int potencia_ataque) {
        this.nombre_ataque = nombre_ataque;
        this.tipo_Dano = tipo_Dano;
        this.potencia_ataque = potencia_ataque;
    }

    public Ataque() {
    }
    
    public String getNombre_ataque() {
        return nombre_ataque;
    }
    public String getTipo_Dano() {
        return tipo_Dano;
    }
    public int getPotencia_ataque() {
        return potencia_ataque;
    }

    //metodo para calcular el daño
    public int calcular_damage(String tipoEnemigo, int defensaEnemigo){
        double base = 1.0;

        if(ventaja(tipoEnemigo)){
            base += 0.3;
    }

    int damage_inicial = (int) ((potencia_ataque * base));
    int damage_final = damage_inicial - defensaEnemigo;
    if(damage_final < 0){
       damage_final = 0;
    }
return damage_final;
}
//metodo para verificar si el ataque tiene ventaja sobre el enemigo
    public boolean ventaja(String tipoEnemigo){
        if(this.tipo_Dano.equals("agua") && tipoEnemigo.equals("fuego")){
            return true;
        }else if(this.tipo_Dano.equals("fuego") && tipoEnemigo.equals("planta")){
            return true;
        }else if(this.tipo_Dano.equals("planta") && tipoEnemigo.equals("agua")){
            return true;
        }else{
            return false;
        }
    }

    public void aplicar_ataque(Pokemon enemigo){
        int damage = calcular_damage(enemigo.getTipo(), enemigo.getDefensa());
        enemigo.restarHP(damage);
        System.out.println("El ataque " + nombre_ataque + " ha hecho " + damage + " de daño a " + enemigo.getNombre());
    }
}