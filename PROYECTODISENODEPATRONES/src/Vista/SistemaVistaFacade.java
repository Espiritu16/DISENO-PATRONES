package Vista;

import javax.swing.SwingUtilities;
import Vista.RegistroConTabsSeguro;
import javax.swing.UIManager;

public class SistemaVistaFacade {

    public void lanzarInterfaz() {
        SwingUtilities.invokeLater(() -> new RegistroConTabsSeguro());
    }

    public static void main(String[] args) {
         // Aplicamos Look and Feel Nimbus para asegurar la correcta visualización de los colores
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SistemaVistaFacade fachada = new SistemaVistaFacade();
        fachada.lanzarInterfaz();
    }
}
