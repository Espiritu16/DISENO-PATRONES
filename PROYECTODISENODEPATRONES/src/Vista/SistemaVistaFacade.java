package Vista;

import javax.swing.SwingUtilities;
import Vista.RegistroConTabsSeguro;

public class SistemaVistaFacade {

    public void lanzarInterfaz() {
        SwingUtilities.invokeLater(() -> new RegistroConTabsSeguro());
    }

    public static void main(String[] args) {
        SistemaVistaFacade fachada = new SistemaVistaFacade();
        fachada.lanzarInterfaz();
    }
}