import javax.swing.SwingUtilities;
import vista.InterfazGrafica;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazGrafica ventana = new InterfazGrafica();
            ventana.setVisible(true); 
        });
    }
}