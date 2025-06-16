package Modelo.RegistroDAO;

import Modelo.RegistroPrototype.PrototypeDireccion;
import Modelo.ConexionSingleton.ConexionSingleton;
import Controlador.CRUD;
import java.sql.*;

public class DireccionDAO implements CRUD {

    @Override
   public int crear(Object objeto) {
    if (objeto instanceof PrototypeDireccion) {
        PrototypeDireccion direccion = (PrototypeDireccion) objeto;
        String sql = "{CALL sp_crud_direccion(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        int resultado = -1;

        try {
            Connection connection = ConexionSingleton.getConexion();

            // Validamos que la conexión esté viva
            if (connection == null || connection.isClosed()) {
                System.out.println("La conexión se ");
                
                return -1;
            }

            // Ahora sí preparamos el statement
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setString(1, "CREATE");
                stmt.setNull(2, Types.INTEGER);
                stmt.setString(3, direccion.getDireccion());
                stmt.setString(4, direccion.getCiudad());
                stmt.setString(5, direccion.getDistrito());
                stmt.setString(6, direccion.getCodigo_postal());
                stmt.setString(7, direccion.getPais());
                stmt.setString(8, direccion.getReferencia());
                stmt.registerOutParameter(9, Types.INTEGER);

                stmt.execute();
                resultado = stmt.getInt(9);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al insertar dirección.");
        }

        return resultado;
    } else {
        System.out.println("El objeto no es del tipo esperado (PrototypeDireccion)");
        return -1;
    }
}

    @Override
    public void leer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}