package Vista;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SistemaVistaFacade {

    private M1 m1;
    private M2 m2;
    private M3 m3;
    private RegistroConTabsSeguro registro;

    public SistemaVistaFacade() {
        this.m1 = new M1();
        this.m2 = new M2();
        this.m3 = new M3();
    }

    // Método para ejecutar la acción de M1
    public void ejecutarM1() {
        JOptionPane.showMessageDialog(null, "M1 aún no implementado.");
    }

    // Método para ejecutar la acción de M2
    public void ejecutarM2() {
        JOptionPane.showMessageDialog(null, "M2 aún no implementado.");
    }

    // Método para ejecutar la acción de M3
    public void ejecutarM3() {
        JOptionPane.showMessageDialog(null, "M3 aún no implementado.");
    }

    // Método para mostrar el formulario de Registro
    public void mostrarRegistro() {
        // Aplicamos Look and Feel Nimbus para asegurar la correcta visualización de los colores
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Muestra el formulario de registro solo cuando el usuario elija la opción
        SwingUtilities.invokeLater(() -> {
            // Crear la instancia de la ventana de registro y mostrarla
            RegistroConTabsSeguro registroForm = new RegistroConTabsSeguro(); 
            registroForm.setVisible(true); 
        });
    }
}