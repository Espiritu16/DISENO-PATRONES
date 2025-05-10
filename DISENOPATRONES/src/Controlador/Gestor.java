package Controlador;
import Modelo.Residuos;
import Modelo.Usuario;
import java.util.Scanner;

public class Gestor {

    private UsuarioControlador usuarioControlador;
    private ResiduoControlador residuoControlador;

    public Gestor() {
        // Inicializar los controladores
        this.usuarioControlador = new UsuarioControlador();
        this.residuoControlador = new ResiduoControlador();
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Seleccione una opción:");
        System.out.println("1. Gestionar Usuario");
        System.out.println("2. Gestionar Residuo");
        System.out.print("Ingrese el número de la opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner
        
        switch (opcion) {
            case 1:
                gestionarUsuario(scanner);
                break;
            case 2:
                gestionarResiduo(scanner);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void gestionarUsuario(Scanner scanner) {
        System.out.println("Gestionando Usuario...");
        
        System.out.println("Seleccione una acción para el Usuario:");
        System.out.println("1. Crear Usuario");
        System.out.println("2. Obtener Usuario");
        System.out.println("3. Actualizar Usuario");
        System.out.println("4. Eliminar Usuario");
        System.out.print("Ingrese el número de la acción: ");
        
        int accion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner

        switch (accion) {
            case 1:
                // Crear Usuario
                Usuario nuevoUsuario = new Usuario();
                System.out.print("Ingrese nombre: ");
                nuevoUsuario.setNombre(scanner.nextLine());
                System.out.print("Ingrese apellido: ");
                nuevoUsuario.setApellido(scanner.nextLine());
                System.out.print("Ingrese correo: ");
                nuevoUsuario.setCorreo(scanner.nextLine());
                // Asumimos que ya tienes un teléfono y contraseña predeterminados
                int resultadoCrear = usuarioControlador.crearUsuario(nuevoUsuario);
                System.out.println("Resultado de la creación: " + resultadoCrear);
                break;
            case 2:
                // Obtener Usuario
                System.out.print("Ingrese el ID del Usuario: ");
                int idUsuario = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                Usuario usuarioObtenido = usuarioControlador.obtenerUsuario(idUsuario);
                if (usuarioObtenido != null) {
                    System.out.println("Usuario encontrado: " + usuarioObtenido);
                } else {
                    System.out.println("Usuario no encontrado.");
                }
                break;
            case 3:
                // Actualizar Usuario
                System.out.print("Ingrese el ID del Usuario a actualizar: ");
                int idActualizar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                Usuario usuarioActualizar = new Usuario();
                usuarioActualizar.setIdUsuario(idActualizar);
                System.out.print("Ingrese nuevo nombre: ");
                usuarioActualizar.setNombre(scanner.nextLine());
                System.out.print("Ingrese nuevo apellido: ");
                usuarioActualizar.setApellido(scanner.nextLine());
                // Asumiendo que el correo también cambiará
                System.out.print("Ingrese nuevo correo: ");
                usuarioActualizar.setCorreo(scanner.nextLine());
                int resultadoActualizar = usuarioControlador.actualizarUsuario(usuarioActualizar);
                System.out.println("Resultado de la actualización: " + resultadoActualizar);
                break;
            case 4:
                // Eliminar Usuario
                System.out.print("Ingrese el ID del Usuario a eliminar: ");
                int idEliminar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                int resultadoEliminar = usuarioControlador.eliminarUsuario(idEliminar);
                System.out.println("Resultado de la eliminación: " + resultadoEliminar);
                break;
            default:
                System.out.println("Acción no válida.");
        }
    }

    private void gestionarResiduo(Scanner scanner) {
        System.out.println("Gestionando Residuo...");
        
        System.out.println("Seleccione una acción para el Residuo:");
        System.out.println("1. Crear Residuo");
        System.out.println("2. Obtener Residuo");
        System.out.println("3. Actualizar Residuo");
        System.out.println("4. Eliminar Residuo");
        System.out.print("Ingrese el número de la acción: ");
        
        int accion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner

        switch (accion) {
            case 1:
                // Crear Residuo
                Residuos nuevoResiduo = new Residuos();
                System.out.print("Ingrese nombre del residuo: ");
                nuevoResiduo.setNombre(scanner.nextLine());
                System.out.print("Ingrese descripción del residuo: ");
                nuevoResiduo.setDescripcion(scanner.nextLine());
                System.out.print("Ingrese unidad de medida: ");
                nuevoResiduo.setUnidadMedida(scanner.nextLine());
                int resultadoCrearResiduo = residuoControlador.crearResiduo(nuevoResiduo);
                System.out.println("Resultado de la creación: " + resultadoCrearResiduo);
                break;
            case 2:
                // Obtener Residuo
                System.out.print("Ingrese el ID del Residuo: ");
                int idResiduo = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                Residuos residuoObtenido = residuoControlador.obtenerResiduo(idResiduo);
                if (residuoObtenido != null) {
                    System.out.println("Residuo encontrado: " + residuoObtenido);
                } else {
                    System.out.println("Residuo no encontrado.");
                }
                break;
            case 3:
                // Actualizar Residuo
                System.out.print("Ingrese el ID del Residuo a actualizar: ");
                int idActualizarResiduo = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                Residuos residuoActualizar = new Residuos();
                residuoActualizar.setIdResiduos(idActualizarResiduo);
                System.out.print("Ingrese nuevo nombre: ");
                residuoActualizar.setNombre(scanner.nextLine());
                System.out.print("Ingrese nueva descripción: ");
                residuoActualizar.setDescripcion(scanner.nextLine());
                int resultadoActualizarResiduo = residuoControlador.actualizarResiduo(residuoActualizar);
                System.out.println("Resultado de la actualización: " + resultadoActualizarResiduo);
                break;
            case 4:
                // Eliminar Residuo
                System.out.print("Ingrese el ID del Residuo a eliminar: ");
                int idEliminarResiduo = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                int resultadoEliminarResiduo = residuoControlador.eliminarResiduo(idEliminarResiduo);
                System.out.println("Resultado de la eliminación: " + resultadoEliminarResiduo);
                break;
            default:
                System.out.println("Acción no válida.");
        }
    }
}