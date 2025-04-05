public class ataque {
    private String nombre_ataque;
    private String tipo_Dano;
    private int potencia_ataque;

    public ataque(String nombre_ataque, String tipo_Dano, int potencia_ataque) {
        this.nombre_ataque = nombre_ataque;
        this.tipo_Dano = tipo_Dano;
        this.potencia_ataque = potencia_ataque;
    }
    
    public String getNombre_ataque() {
        return nombre_ataque;
    }
    public void setNombre_ataque(String nombre_ataque) {
        this.nombre_ataque = nombre_ataque;
    }
    public String getTipo_Dano() {
        return tipo_Dano;
    }
    public void setTipo_Dano(String tipo_Dano) {
        this.tipo_Dano = tipo_Dano;
    }
    public int getPotencia_ataque() {
        return potencia_ataque;
    }
    public void setPotencia_ataque(int potencia_ataque) {
        this.potencia_ataque = potencia_ataque;
    }

    
}
