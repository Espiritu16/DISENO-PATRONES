package Controlador;

import Modelo.Residuos;

public interface ResiduoFactory {
    int crearResiduo(Residuos residuo);
    Residuos obtenerResiduo(int idResiduo);
    int actualizarResiduo(Residuos residuo);
    int eliminarResiduo(int idResiduo);
}