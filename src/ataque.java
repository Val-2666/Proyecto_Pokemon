public class Ataque {
    private String nombreAtaque;
    private String tipoDano;
    private int potenciaAtaque;

    public Ataque(String nombreAtaque, String tipoDano, int potenciaAtaque) {
        this.nombreAtaque = nombreAtaque;
        this.tipoDano = tipoDano;
        this.potenciaAtaque = potenciaAtaque;
    }
    
    public String getnombreAtaque() {
        return nombreAtaque;
    }
    public String gettipoDano() {
        return tipoDano;
    }
    public int getpotenciaAtaque() {
        return potenciaAtaque;
    }
    public void setNombreAtaque(String nombreAtaque) {
        this.nombreAtaque = nombreAtaque;
    }

    public void setTipoDano(String tipoDano) {
        this.tipoDano = tipoDano;
    }

    public void setPotenciaAtaque(int potenciaAtaque) {
        this.potenciaAtaque = potenciaAtaque;
    }

    //metodo para calcular el daño
    public int calcularDamage(String tipoEnemigo, int defensaEnemigo){
        double base = 1.0;

        if(ventaja(tipoEnemigo)){
            base += 0.3;
    }

    int damageInicial = (int) ((potenciaAtaque * base));
    int damageFinal = damageInicial - defensaEnemigo;
    if(damageFinal < 0){
       damageFinal = 0;
    }
return damageFinal;
}
//metodo para verificar si el ataque tiene ventaja sobre el enemigo
    public boolean ventaja(String tipoEnemigo){
        if(this.tipoDano.equals("agua") && tipoEnemigo.equals("fuego")){
            return true;
        }else return this.tipoDano.equals("fuego") && tipoEnemigo.equals("planta") || this.tipoDano.equals("planta") && tipoEnemigo.equals("agua");
    }

    public void aplicar_ataque(Pokemon enemigo){
        int damage = calcularDamage(enemigo.getTipo(), enemigo.getDefensa());
        enemigo.restarHP(damage);
        System.out.println("El ataque " + nombreAtaque + " ha hecho " + damage + " de daño a " + enemigo.getNombre());
    }

}