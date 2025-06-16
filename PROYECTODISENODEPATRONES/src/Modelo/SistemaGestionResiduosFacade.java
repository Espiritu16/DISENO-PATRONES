package Modelo;

import Modelo.Registro.PrototypeClasificacionResiduo;
import Modelo.Registro.PrototypeDireccion;
import Modelo.Registro.PrototypeResiduo;
import Modelo.Registro.PrototypeUsuario;
import Modelo.*;

public interface SistemaGestionResiduosFacade {
    boolean registrarFlujoCompleto(
        PrototypeUsuario usuario,
        PrototypeDireccion direccion,
        PrototypeClasificacionResiduo clasificacion,
        PrototypeResiduo residuo
    );
}