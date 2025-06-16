package Modelo.RegistroDAO;

import Modelo.RegistroPrototype.PrototypeClasificacionResiduo;
import Modelo.ConexionSingleton.ConexionSingleton;
import Controlador.CRUD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClasificacionResiduosDAO implements CRUD {

    @Override
    public int crear(Object objeto) {
    if (objeto instanceof PrototypeClasificacionResiduo) {
        PrototypeClasificacionResiduo clasificacion = (PrototypeClasificacionResiduo) objeto;

        String sql = "{CALL sp_crud_clasificacion_residuos(?, ?, ?, ?, ?, ?, ?)}";
        int resultado = -1;

        try {
            Connection connection = ConexionSingleton.getConexion();
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.setString(1, "CREATE"); // @opcion
            stmt.setNull(2, Types.INTEGER); // @idClasificacion, NULL para crear
            stmt.setString(3, clasificacion.getNombre()); // @nombre
            stmt.setString(4, clasificacion.getDescripcion()); // @descripcion
            stmt.setString(5, clasificacion.getColor_codigo()); // @color_codigo
            stmt.setString(6, clasificacion.getIcono()); // @icono

            // Registrar parámetro OUTPUT @resultado en posición 7
            stmt.registerOutParameter(7, Types.INTEGER);

            // Ejecutar el procedimiento almacenado
            stmt.execute();

            // Obtener el resultado del parámetro OUTPUT
            resultado = stmt.getInt(7);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al insertar clasificación de residuos.");
        }

        return resultado;
    } else {
        System.out.println("El objeto no es del tipo esperado (PrototypeClasificacionResiduo)");
        return -1;
    }
}
   
    

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("No implementado aún.");
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("No implementado aún.");
    }

    @Override
    public void leer() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}