package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ResiduoDAO {
    
    public int ejecutarCRUD(String operacion, Residuos residuo) {
        int resultado = -1;
        
        try (Connection con = ConexionBD.getInstancia().getConexion();
             CallableStatement stmt = con.prepareCall("{call sp_Residuos_CRUD(?, ?, ?, ?, ?, ?, ?, ?)}")) {
            
            // Configurar parámetros de entrada
            stmt.setString(1, operacion);
            stmt.setObject(2, (operacion.equals("CREATE")) ? null : residuo.getIdResiduos());
            stmt.setString(3, residuo.getNombre());
            stmt.setInt(4, residuo.getIdClasificacion());
            stmt.setString(5, residuo.getDescripcion());
            stmt.setString(6, residuo.getUnidadMedida());
            stmt.setString(7, residuo.getPeligrosidad());
            
            // Registrar parámetro de salida para filas afectadas o ID generado
            stmt.registerOutParameter(8, java.sql.Types.INTEGER);
            
            boolean tieneResultado = stmt.execute();
            
            // Procesar resultados para operación READ
            if (operacion.equals("READ") && tieneResultado) {
                ResultSet rs = stmt.getResultSet();
                List<Residuos> residuos = new ArrayList<>();
                
                while (rs.next()) {
                    Residuos r = new Residuos(
                        rs.getInt("idResiduos"),
                        rs.getString("nombre"),
                        rs.getInt("idClasificacion"),
                        rs.getString("descripcion"),
                        rs.getString("unidad_medida"),
                        rs.getString("peligrosidad"),
                        rs.getDate("fecha_registro")
                    );
                    residuos.add(r);
                }
                
                // Si es READ de un solo elemento, devolver el primero
                if (residuos.size() == 1 && residuo.getIdResiduos() != 0) {
                    return residuos.get(0).getIdResiduos();
                }
                // Para READ_ALL, podrías almacenar la lista en algún lugar
            }
            
            resultado = stmt.getInt(8);
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar procedimiento almacenado: " + e.getMessage());
        }
        
        return resultado;
    }
    
    // Métodos específicos para cada operación CRUD
    public int crear(Residuos residuo) {
        return ejecutarCRUD("CREATE", residuo);
    }
    
    public Residuos leer(int idResiduos) {
        Residuos residuo = new Residuos();
        residuo.setIdResiduos(idResiduos);
        ejecutarCRUD("READ", residuo);
        return residuo; // Nota: Necesitarías ajustar esto para obtener el objeto real
    }
    
    public List<Residuos> leerTodos() {
        Residuos residuo = new Residuos();
        ejecutarCRUD("READ", residuo);
        // Nota: Necesitarías un mecanismo para recuperar la lista generada
        return new ArrayList<>();
    }
    
    public int actualizar(Residuos residuo) {
        return ejecutarCRUD("UPDATE", residuo);
    }
    
    public int eliminar(int idResiduos) {
        Residuos residuo = new Residuos();
        residuo.setIdResiduos(idResiduos);
        return ejecutarCRUD("DELETE", residuo);
    }
    
}

