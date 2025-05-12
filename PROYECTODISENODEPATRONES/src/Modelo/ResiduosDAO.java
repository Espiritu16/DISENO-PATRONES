
package Modelo;
import Controlador.CRUD;
import java.sql.*;

public class ResiduosDAO implements CRUD {

    // Método para crear un nuevo residuo
    @Override
    public int crear(Object objeto) {
        if (objeto instanceof PrototypeResiduo) {
            PrototypeResiduo residuo = (PrototypeResiduo) objeto;
            String sql = "{CALL sp_crud_residuos(?, ?, ?, ?, ?, ?, ?, ?)}";  // Llamada al procedimiento almacenado
            int resultado = -1;

            try (Connection connection = ConexionSingleton.getConexion();
                 CallableStatement stmt = connection.prepareCall(sql)) {

                // Establecer los parámetros para el procedimiento almacenado
                stmt.setString(1, "CREATE");  // Opción 'CREATE' para insertar un nuevo residuo
                stmt.setNull(2, Types.INTEGER); // El parámetro idResiduos se maneja como auto-generado
                stmt.setString(3, residuo.getNombre());
                stmt.setInt(4, residuo.getIdClasificacion());  // idClasificacion (clave foránea)
                stmt.setString(5, residuo.getDescripcion());
                stmt.setString(6, residuo.getUnidad_medida());
                stmt.setString(7, residuo.getPeligrosidad());

                stmt.registerOutParameter(8, Types.INTEGER);  // Parámetro de salida para el resultado

                // Ejecutar el procedimiento almacenado
                stmt.execute();

                // Obtener el resultado de la operación
                resultado = stmt.getInt(8);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al insertar residuo.");
            }

            return resultado;  // Retornar el resultado del procedimiento (código de error o idResiduos)
        } else {
            System.out.println("El objeto no es del tipo esperado (PrototypeResiduo)");
            return -1;  // Retornar un valor de error si el tipo no es el esperado
        }
    }

    // Método para leer los residuos (ejemplo de lectura, se debe ajustar a tu caso)
    @Override
    public void leer() {
       
    }

    // Método para actualizar un residuo
    @Override
    public void actualizar() {
        
    }

    // Método para eliminar un residuo
    @Override
    public void eliminar() {
       
    }
}