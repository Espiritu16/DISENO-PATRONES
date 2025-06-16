package Vista.PatronObserver;
import java.util.ArrayList;
import java.util.List;

public class Formulario {
    private boolean formularioCompleto = false;  // Estado del formulario
    private final List<Observador> observadores = new ArrayList<>();  // Lista de observadores (botones)

    // Agregar un observador (botón)
    public void agregarObservador(Observador obs) {
        observadores.add(obs);
    }

    // Cambiar el estado del formulario (cuando un campo cambia)
    public void setFormularioCompleto(boolean completo) {
        this.formularioCompleto = completo;
        notificarObservadores();  // Notificar a los observadores (botones)
    }

    // Notificar a todos los observadores (botones)
    private void notificarObservadores() {
        for (Observador o : observadores) {
            o.actualizar(formularioCompleto);  // Notificar el estado del formulario
        }
    }

    // Obtener el estado del formulario
    public boolean isFormularioCompleto() {
        return formularioCompleto;
    }
}