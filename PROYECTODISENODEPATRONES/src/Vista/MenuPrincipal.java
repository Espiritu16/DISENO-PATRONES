package Vista;
import Controlador.CRUD;
import Controlador.CRUDFactory;
import Controlador.EntidadDAOFactory;
import Modelo.PrototypeResiduo;
import Modelo.PrototypeUsuario;
import java.util.Scanner;
import java.sql.Date;

public class MenuPrincipal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Mostrar el menú
        System.out.println("Bienvenido al sistema");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Crear un nuevo Usuario");
        System.out.println("2. Crear un nuevo Residuo");
        System.out.println("3. Salir");
        
        // Leer la opción del usuario
        int opcion = scanner.nextInt();

        // Crear una instancia de la fábrica
        CRUDFactory factory = new EntidadDAOFactory();

        // Según la opción seleccionada, ejecutar la acción correspondiente
        switch (opcion) {
            case 1:
                // Crear un nuevo Usuario
                crearUsuario(factory);
                break;

            case 2:
                // Crear un nuevo Residuo
                crearResiduo(factory);
                break;

            case 3:
                System.out.println("Saliendo del sistema...");
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }

        scanner.close();
    }

    // Método para crear un Usuario
    private static void crearUsuario(CRUDFactory factory) {
        Scanner scanner = new Scanner(System.in);

        // Usar la fábrica para obtener una instancia de CRUD (UsuarioDAO)
        CRUD usuarioDAO = factory.crearUsuarioCRUD();

        // Capturar los datos del nuevo usuario
        System.out.println("Crear un nuevo Usuario:");

        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Ingrese el correo: ");
        String correo = scanner.nextLine();

        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();

        System.out.print("Ingrese el ID de la dirección: ");
        int idDireccion = scanner.nextInt();

        // Crear el objeto PrototypeUsuario
        PrototypeUsuario nuevoUsuario = new PrototypeUsuario(
                0, // idUsuario (auto-generado)
                nombre, 
                apellido, 
                correo, 
                telefono, 
                contrasena, 
                idDireccion, 
                new Date(System.currentTimeMillis()), // fecha_registro
                new Date(System.currentTimeMillis()), // ultimo_acceso
                true, // activo
                "Usuario" // rol
        );

        // Usar el método crear de UsuarioDAO
        int resultado = usuarioDAO.crear(nuevoUsuario);

        // Mostrar el resultado
        if (resultado > 0) {
            System.out.println("Usuario creado con éxito. ID del nuevo usuario: " + resultado);
        } else {
            System.out.println("Error al crear el usuario.");
        }
    }

    // Método para crear un Residuo
    private static void crearResiduo(CRUDFactory factory) {
        Scanner scanner = new Scanner(System.in);

        // Usar la fábrica para obtener una instancia de CRUD (ResiduosDAO)
        CRUD residuosDAO = factory.crearResiduoCRUD();

        // Capturar los datos del nuevo residuo
        System.out.println("Crear un nuevo Residuo:");

        System.out.print("Ingrese el nombre del residuo: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el ID de clasificación: ");
        int idClasificacion = scanner.nextInt();

        scanner.nextLine();  // Limpiar el buffer de entrada

        System.out.print("Ingrese la descripción del residuo: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese la unidad de medida: ");
        String unidadMedida = scanner.nextLine();

        System.out.print("Ingrese la peligrosidad: ");
        String peligrosidad = scanner.nextLine();

        // Crear el objeto PrototypeResiduo
        PrototypeResiduo nuevoResiduo = new PrototypeResiduo(
                0, // idResiduos (auto-generado)
                nombre, 
                idClasificacion, 
                descripcion, 
                unidadMedida, 
                peligrosidad, 
                new Date(System.currentTimeMillis()) // fecha_registro
        );

        // Usar el método crear de ResiduosDAO
        int resultadoResiduo = residuosDAO.crear(nuevoResiduo);

        // Mostrar el resultado
        if (resultadoResiduo > 0) {
            System.out.println("Residuo creado con éxito. ID del nuevo residuo: " + resultadoResiduo);
        } else {
            System.out.println("Error al crear el residuo.");
        }
    }
}