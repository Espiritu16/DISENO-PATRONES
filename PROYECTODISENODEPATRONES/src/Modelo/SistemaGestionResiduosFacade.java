package Modelo;

import Modelo.*;

public interface SistemaGestionResiduosFacade {
    boolean registrarFlujoCompleto(
        PrototypeUsuario usuario,
        PrototypeDireccion direccion,
        PrototypeClasificacionResiduo clasificacion,
        PrototypeResiduo residuo
    );
}