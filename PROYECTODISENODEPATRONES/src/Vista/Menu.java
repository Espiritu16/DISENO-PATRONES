package Vista;

import javax.swing.JOptionPane;

public class Menu {

    private SistemaVistaFacade sistema;

    public Menu() {
        // Crear una instancia de la fachada
        this.sistema = new SistemaVistaFacade();
    }

    // Método para mostrar el menú
    public void mostrarMenu() {
        String menu = "Seleccione una opción:\n"
                    + "1. Ejecutar M1\n"
                    + "2. Ejecutar M2\n"
                    + "3. Ejecutar M3\n"
                    + "4. Mostrar Formulario de Registro\n";
        int opcion = 0;

        do {
            // Mostrar el menú y obtener la opción seleccionada
            String input = JOptionPane.showInputDialog(menu);
            try {
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1:
                        sistema.ejecutarM1();
                        break;
                    case 2:
                        sistema.ejecutarM2();
                        break;
                    case 3:
                        sistema.ejecutarM3();
                        break;
                    case 4:
                        sistema.mostrarRegistro(); // Muestra el formulario cuando el usuario selecciona la opción 4
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
            }
        } while (opcion != 4); // El bucle se repite hasta que el usuario seleccione "Mostrar Formulario de Registro"
    }

    // Método main para ejecutar el programa
    public static void main(String[] args) {
        // Crear una instancia del menú y mostrarlo
        Menu menu = new Menu();
        menu.mostrarMenu();
    }
}