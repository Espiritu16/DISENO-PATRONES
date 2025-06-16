package Vista.PatronObserver;
import java.util.Observable;

public class Formulario extends Observable {
    private boolean registrarSeleccionado = false;  // Estado del formulario (si se seleccionó "Registrar")

    public void setRegistrarSeleccionado(boolean seleccionado) {
        this.registrarSeleccionado = seleccionado;
        setChanged();  // Marca que ha habido un cambio
        notifyObservers();  // Notifica a los observadores registrados
    }

    public boolean isRegistrarSeleccionado() {
        return registrarSeleccionado;
    }
}