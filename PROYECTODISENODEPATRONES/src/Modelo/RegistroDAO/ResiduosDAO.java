package Modelo.RegistroDAO;

import Modelo.RegistroPrototype.PrototypeResiduo;
import Modelo.ConexionSingleton.ConexionSingleton;
import Controlador.CRUD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResiduosDAO implements CRUD {

    @Override
    public int crear(Object objeto) {
        if (objeto instanceof PrototypeResiduo) {
            PrototypeResiduo residuo = (PrototypeResiduo) objeto;

            String sql = "{CALL sp_crud_residuos(?, ?, ?, ?, ?, ?, ?, ?)}";
            int resultado = -1;

            try {
                Connection connection = ConexionSingleton.getConexion();
                CallableStatement stmt = connection.prepareCall(sql);

                stmt.setString(1, "CREATE");
                stmt.setNull(2, Types.INTEGER); // idResiduos NULL para crear
                stmt.setString(3, residuo.getNombre());
                stmt.setInt(4, residuo.getIdClasificacion());
                stmt.setString(5, residuo.getDescripcion());
                stmt.setString(6, residuo.getUnidad_medida());
                stmt.setString(7, residuo.getPeligrosidad());
                stmt.registerOutParameter(8, Types.INTEGER);

                stmt.execute();
                resultado = stmt.getInt(8);

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al insertar residuo.");
            }

            return resultado;
        } else {
            System.out.println("El objeto no es del tipo esperado (PrototypeResiduo)");
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