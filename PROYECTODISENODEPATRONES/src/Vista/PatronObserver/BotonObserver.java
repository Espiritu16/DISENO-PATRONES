package Vista.PatronObserver;
import java.util.Observer;
import java.util.Observable;
import javax.swing.JButton;

public class BotonObserver implements Observer {
    private JButton btnServicios;
    private JButton btnEstadisticas;
    private JButton btnSugerencias;
    private JButton btnRegistro;

    public BotonObserver(JButton btnServicios, JButton btnEstadisticas, JButton btnSugerencias, JButton btnRegistro) {
        this.btnServicios = btnServicios;
        this.btnEstadisticas = btnEstadisticas;
        this.btnSugerencias = btnSugerencias;
        this.btnRegistro = btnRegistro;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Formulario) {
            Formulario formulario = (Formulario) o;
            // Verificar el estado del formulario y habilitar/deshabilitar los botones
            if (formulario.isRegistrarSeleccionado()) {
                // Deshabilitar los botones si el formulario está en el estado de "Registrar"
                btnServicios.setEnabled(false);
                btnEstadisticas.setEnabled(false);
                btnSugerencias.setEnabled(false);
                btnRegistro.setEnabled(false); // Si también quieres deshabilitar el botón de "Registrar"
            } else {
                // Habilitar los botones si el formulario no está en el estado de "Registrar"
                btnServicios.setEnabled(true);
                btnEstadisticas.setEnabled(true);
                btnSugerencias.setEnabled(true);
                btnRegistro.setEnabled(true); // Habilitar el botón de "Registrar" si se quiere
            }
        }
    }
}