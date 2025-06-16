package Modelo.Facade;

import Modelo.RegistroPrototype.PrototypeClasificacionResiduo;
import Modelo.RegistroPrototype.PrototypeDireccion;
import Modelo.RegistroPrototype.PrototypeResiduo;
import Modelo.RegistroPrototype.PrototypeUsuario;
import Modelo.*;

public interface SistemaGestionResiduosFacade {
    boolean registrarFlujoCompleto(
        PrototypeUsuario usuario,
        PrototypeDireccion direccion,
        PrototypeClasificacionResiduo clasificacion,
        PrototypeResiduo residuo
    );
}