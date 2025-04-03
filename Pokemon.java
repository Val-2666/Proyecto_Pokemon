public class Pokemon {
    private String nombre;
    private String tipo;
    private int hp;

    public Pokemon(String nombre, String tipo, int hp) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.hp = hp;
    }

    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre + ", Tipo: " + tipo + ", HP: " + hp);
    }
}