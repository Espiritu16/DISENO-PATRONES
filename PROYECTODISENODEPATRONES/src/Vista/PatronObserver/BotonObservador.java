package Vista.PatronObserver;


import javax.swing.JButton;

public class BotonObservador implements Observador {
    private JButton boton;

    public BotonObservador(JButton boton) {
        this.boton = boton;
    }

    // Este método se llama cuando el formulario cambia de estado
    @Override
    public void actualizar(boolean formularioCompleto) {
        if (formularioCompleto) {
            boton.setEnabled(true);  // Habilitar el botón si el formulario está completo
        } else {
            boton.setEnabled(false);  // Deshabilitar el botón si el formulario no está completo
        }
    }
}