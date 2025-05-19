create database GestionResiduo;
use GestionResiduo;

-- Tabla Direccion
CREATE TABLE Direccion (
    idDireccion INT IDENTITY(1,1) PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    distrito VARCHAR(100),
    codigo_postal VARCHAR(20),
    pais VARCHAR(100) DEFAULT 'Peru',
    referencia VARCHAR(255),
    fecha_creacion DATETIME DEFAULT GETDATE(),
    fecha_actualizacion DATETIME DEFAULT GETDATE()
);
GO

-- Tabla Usuario
CREATE TABLE Usuario (
    idUsuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    contrasena_hash VARCHAR(255) NOT NULL,
    idDireccion INT,
    fecha_registro DATETIME DEFAULT GETDATE(),
    ultimo_acceso DATETIME,
    activo BIT DEFAULT 1,
    rol VARCHAR(50) DEFAULT 'Usuario',
    FOREIGN KEY (idDireccion) REFERENCES Direccion(idDireccion)
);
GO

-- Tabla Clasificacion_Residuos
CREATE TABLE Clasificacion_Residuos (
    idClasificacion INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    color_codigo VARCHAR(20),
    icono VARCHAR(50),
    fecha_creacion DATETIME DEFAULT GETDATE()
);
GO

-- Tabla Residuos
CREATE TABLE Residuos (
    idResiduos INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    idClasificacion INT NOT NULL,
    descripcion VARCHAR(255),
    unidad_medida VARCHAR(50) DEFAULT 'Kilogramos',
    peligrosidad VARCHAR(50),
    fecha_registro DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (idClasificacion) REFERENCES Clasificacion_Residuos(idClasificacion)
);
GO

-- Tabla Ingreso_Residuos
CREATE TABLE Ingreso_Residuos (
    idIngreso INT IDENTITY(1,1) PRIMARY KEY,
    idUsuario INT NOT NULL,
    idResiduos INT NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    fecha_ingreso DATETIME DEFAULT GETDATE(),
    fecha_recoleccion DATETIME,
    estado VARCHAR(50) DEFAULT 'Pendiente',
    observaciones TEXT,
    ubicacion_almacenamiento VARCHAR(255),
    foto_url VARCHAR(255),
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario),
    FOREIGN KEY (idResiduos) REFERENCES Residuos(idResiduos)
);
GO

-- Tabla MedioDeEnvio
CREATE TABLE MedioDeEnvio (
    idMedio INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    activo BIT DEFAULT 1,
    configuracion TEXT
);
GO

-- Tabla Mensaje
CREATE TABLE Mensaje (
    idMensaje INT IDENTITY(1,1) PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    contenido TEXT NOT NULL,
    plantilla VARCHAR(50),
    fecha_creacion DATETIME DEFAULT GETDATE(),
    creador_id INT,
    FOREIGN KEY (creador_id) REFERENCES Usuario(idUsuario)
);
GO

-- Tabla Notificacion
CREATE TABLE Notificacion (
    idNotification INT IDENTITY(1,1) PRIMARY KEY,
    idMensaje INT NOT NULL,
    fecha_Envio DATETIME NOT NULL DEFAULT GETDATE(),
    idMedio INT NOT NULL,
    estado VARCHAR(50) DEFAULT 'Programada',
    intentos INT DEFAULT 0,
    FOREIGN KEY (idMedio) REFERENCES MedioDeEnvio(idMedio),
    FOREIGN KEY (idMensaje) REFERENCES Mensaje(idMensaje)
);
GO

-- Tabla Usuario_Destino
CREATE TABLE Usuario_Destino (
    idNotification INT NOT NULL,
    idUsuario INT NOT NULL,
    leido BIT DEFAULT 0,
    fecha_leido DATETIME,
    respuesta VARCHAR(255),
    PRIMARY KEY (idNotification, idUsuario),
    FOREIGN KEY (idNotification) REFERENCES Notificacion(idNotification),
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);
GO

-- Tabla Sugerencia
CREATE TABLE Sugerencia (
    idSugerencia INT IDENTITY(1,1) PRIMARY KEY,
    idUsuario INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    estado VARCHAR(50) DEFAULT 'Pendiente',
    prioridad INT DEFAULT 3,
    categoria VARCHAR(100),
    respuesta TEXT,
    fecha_respuesta DATETIME,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);
GO

-- Tabla Auditoria (nueva)
CREATE TABLE Auditoria (
    idAuditoria INT IDENTITY(1,1) PRIMARY KEY,
    tabla_afectada VARCHAR(100) NOT NULL,
    id_registro INT,
    accion VARCHAR(50) NOT NULL,
    datos_anteriores TEXT,
    datos_nuevos TEXT,
    idUsuario INT,
    fecha_evento DATETIME DEFAULT GETDATE(),
    ip_origen VARCHAR(50),
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);
GO

-- Tabla Configuracion (nueva)
CREATE TABLE Configuracion (
    idConfiguracion INT IDENTITY(1,1) PRIMARY KEY,
    clave VARCHAR(100) NOT NULL UNIQUE,
    valor TEXT NOT NULL,
    descripcion VARCHAR(255),
    editable BIT DEFAULT 1,
    seccion VARCHAR(100)
);
GO

-- �ndices para mejorar el rendimiento
CREATE INDEX IX_Usuario_Correo ON Usuario(correo);
CREATE INDEX IX_Residuos_Clasificacion ON Residuos(idClasificacion);
CREATE INDEX IX_IngresoResiduos_Fecha ON Ingreso_Residuos(fecha_ingreso);
CREATE INDEX IX_Notificacion_Estado ON Notificacion(estado);
CREATE INDEX IX_Sugerencia_Estado ON Sugerencia(estado);
GO











--Procedimineto Almacenado CRUD Usuario--
CREATE PROCEDURE sp_crud_usuario_simple
    @opcion VARCHAR(10), -- 'CREATE', 'READ', 'UPDATE', 'DELETE'
    @idUsuario INT = NULL,
    @nombre VARCHAR(100) = NULL,
    @apellido VARCHAR(100) = NULL,
    @correo VARCHAR(255) = NULL,
    @telefono VARCHAR(20) = NULL,
    @contrasena_hash VARCHAR(255) = NULL,
    @idDireccion INT = NULL,
    @resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    IF @opcion = 'CREATE'
    BEGIN
        -- Validación básica de campos requeridos
        IF @nombre IS NULL OR @apellido IS NULL OR @correo IS NULL OR @contrasena_hash IS NULL
        BEGIN
            SET @resultado = -1;
            RETURN;
        END
        
        -- Verificar si el correo ya existe
        IF EXISTS (SELECT 1 FROM Usuario WHERE correo = @correo)
        BEGIN
            SET @resultado = -2; -- Correo ya registrado
            RETURN;
        END
        
        INSERT INTO Usuario (
            nombre,
            apellido,
            correo,
            telefono,
            contrasena_hash,
            idDireccion,
            fecha_registro,
            activo,
            rol
        )
        VALUES (
            @nombre,
            @apellido,
            @correo,
            @telefono,
            @contrasena_hash,
            @idDireccion,
            GETDATE(), -- fecha_registro automática
            1,        -- activo por defecto
            'Usuario' -- rol por defecto
        );
        
        SET @resultado = SCOPE_IDENTITY();
    END
    ELSE IF @opcion = 'READ'
    BEGIN
        IF @idUsuario IS NULL
            SELECT * FROM Usuario;
        ELSE
            SELECT * FROM Usuario WHERE idUsuario = @idUsuario;
    END
    ELSE IF @opcion = 'UPDATE'
    BEGIN
        UPDATE Usuario SET 
            nombre = ISNULL(@nombre, nombre),
            apellido = ISNULL(@apellido, apellido),
            correo = ISNULL(@correo, correo),
            telefono = ISNULL(@telefono, telefono),
            contrasena_hash = ISNULL(@contrasena_hash, contrasena_hash),
            idDireccion = ISNULL(@idDireccion, idDireccion)
        WHERE idUsuario = @idUsuario;
        
        SET @resultado = @idUsuario;
    END
    ELSE IF @opcion = 'DELETE'
    BEGIN
        -- Verificación básica de relaciones antes de eliminar
        IF NOT EXISTS (
            SELECT 1 FROM Ingreso_Residuos WHERE idUsuario = @idUsuario
            UNION
            SELECT 1 FROM Sugerencia WHERE idUsuario = @idUsuario
            UNION
            SELECT 1 FROM Usuario_Destino WHERE idUsuario = @idUsuario
        )
        BEGIN
            DELETE FROM Usuario WHERE idUsuario = @idUsuario;
            SET @resultado = @idUsuario;
        END
        ELSE
        BEGIN
            SET @resultado = -1; -- No se puede eliminar por relaciones existentes
        END
    END
END
GO










CREATE PROCEDURE sp_crud_direccion
    @opcion VARCHAR(10), -- 'CREATE', 'READ', 'UPDATE', 'DELETE'
    @idDireccion INT = NULL,
    @direccion VARCHAR(255) = NULL,
    @ciudad VARCHAR(100) = NULL,
    @distrito VARCHAR(100) = NULL,
    @codigo_postal VARCHAR(20) = NULL,
    @pais VARCHAR(100) = NULL,
    @referencia VARCHAR(255) = NULL,
    @resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    IF @opcion = 'CREATE'
    BEGIN
        IF @direccion IS NULL OR @ciudad IS NULL
        BEGIN
            SET @resultado = -1; -- Campos obligatorios faltantes
            RETURN;
        END

        INSERT INTO Direccion (
            direccion, ciudad, distrito, codigo_postal, pais, referencia, fecha_creacion, fecha_actualizacion
        )
        VALUES (
            @direccion, @ciudad, @distrito, @codigo_postal, ISNULL(@pais, 'Perú'), @referencia, GETDATE(), GETDATE()
        );

        SET @resultado = SCOPE_IDENTITY();
    END
    ELSE IF @opcion = 'READ'
    BEGIN
        IF @idDireccion IS NULL
            SELECT * FROM Direccion;
        ELSE
            SELECT * FROM Direccion WHERE idDireccion = @idDireccion;
    END
    ELSE IF @opcion = 'UPDATE'
    BEGIN
        UPDATE Direccion SET
            direccion = ISNULL(@direccion, direccion),
            ciudad = ISNULL(@ciudad, ciudad),
            distrito = ISNULL(@distrito, distrito),
            codigo_postal = ISNULL(@codigo_postal, codigo_postal),
            pais = ISNULL(@pais, pais),
            referencia = ISNULL(@referencia, referencia),
            fecha_actualizacion = GETDATE()
        WHERE idDireccion = @idDireccion;

        SET @resultado = @idDireccion;
    END
    ELSE IF @opcion = 'DELETE'
    BEGIN
        IF EXISTS (SELECT 1 FROM Usuario WHERE idDireccion = @idDireccion)
        BEGIN
            SET @resultado = -1; -- Relación existente con Usuario
            RETURN;
        END

        DELETE FROM Direccion WHERE idDireccion = @idDireccion;
        SET @resultado = @idDireccion;
    END
END
GO





CREATE PROCEDURE sp_crud_residuos
    @opcion VARCHAR(10), -- 'CREATE', 'READ', 'UPDATE', 'DELETE'
    @idResiduos INT = NULL,
    @nombre VARCHAR(100) = NULL,
    @idClasificacion INT = NULL,
    @descripcion VARCHAR(255) = NULL,
    @unidad_medida VARCHAR(50) = NULL,
    @peligrosidad VARCHAR(50) = NULL,
    @resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    IF @opcion = 'CREATE'
    BEGIN
        IF @nombre IS NULL OR @idClasificacion IS NULL
        BEGIN
            SET @resultado = -1; -- Campos obligatorios faltantes
            RETURN;
        END

        -- Validar que la clasificación exista
        IF NOT EXISTS (SELECT 1 FROM Clasificacion_Residuos WHERE idClasificacion = @idClasificacion)
        BEGIN
            SET @resultado = -2; -- Clasificación no existe
            RETURN;
        END

        INSERT INTO Residuos (
            nombre, idClasificacion, descripcion, unidad_medida, peligrosidad, fecha_registro
        )
        VALUES (
            @nombre, @idClasificacion, @descripcion, ISNULL(@unidad_medida, 'Kilogramos'), @peligrosidad, GETDATE()
        );

        SET @resultado = SCOPE_IDENTITY();
    END
    ELSE IF @opcion = 'READ'
    BEGIN
        IF @idResiduos IS NULL
            SELECT * FROM Residuos;
        ELSE
            SELECT * FROM Residuos WHERE idResiduos = @idResiduos;
    END
    ELSE IF @opcion = 'UPDATE'
    BEGIN
        -- Validar si se actualiza la clasificación que exista
        IF @idClasificacion IS NOT NULL AND NOT EXISTS (SELECT 1 FROM Clasificacion_Residuos WHERE idClasificacion = @idClasificacion)
        BEGIN
            SET @resultado = -2; -- Clasificación no existe
            RETURN;
        END

        UPDATE Residuos SET
            nombre = ISNULL(@nombre, nombre),
            idClasificacion = ISNULL(@idClasificacion, idClasificacion),
            descripcion = ISNULL(@descripcion, descripcion),
            unidad_medida = ISNULL(@unidad_medida, unidad_medida),
            peligrosidad = ISNULL(@peligrosidad, peligrosidad)
        WHERE idResiduos = @idResiduos;

        SET @resultado = @idResiduos;
    END
    ELSE IF @opcion = 'DELETE'
    BEGIN
        DELETE FROM Residuos WHERE idResiduos = @idResiduos;
        SET @resultado = @idResiduos;
    END
END
GO











CREATE PROCEDURE sp_crud_clasificacion_residuos
    @opcion VARCHAR(10), -- 'CREATE', 'READ', 'UPDATE', 'DELETE'
    @idClasificacion INT = NULL,
    @nombre VARCHAR(100) = NULL,
    @descripcion VARCHAR(255) = NULL,
    @color_codigo VARCHAR(20) = NULL,
    @icono VARCHAR(50) = NULL,
    @resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    IF @opcion = 'CREATE'
    BEGIN
        IF @nombre IS NULL OR @descripcion IS NULL
        BEGIN
            SET @resultado = -1; -- Campos obligatorios faltantes
            RETURN;
        END

        INSERT INTO Clasificacion_Residuos (
            nombre, descripcion, color_codigo, icono, fecha_creacion
        )
        VALUES (
            @nombre, @descripcion, @color_codigo, @icono, GETDATE()
        );

        SET @resultado = SCOPE_IDENTITY();
        RETURN;
    END
    ELSE IF @opcion = 'READ'
    BEGIN
        SET @resultado = 0; -- Asignar siempre resultado

        -- Opción 1: puedes devolver datos aquí pero Java debe manejar resultset
        IF @idClasificacion IS NULL
            SELECT * FROM Clasificacion_Residuos;
        ELSE
            SELECT * FROM Clasificacion_Residuos WHERE idClasificacion = @idClasificacion;

        RETURN;
    END
    ELSE IF @opcion = 'UPDATE'
    BEGIN
        UPDATE Clasificacion_Residuos SET
            nombre = ISNULL(@nombre, nombre),
            descripcion = ISNULL(@descripcion, descripcion),
            color_codigo = ISNULL(@color_codigo, color_codigo),
            icono = ISNULL(@icono, icono)
        WHERE idClasificacion = @idClasificacion;

        SET @resultado = @idClasificacion;
        RETURN;
    END
    ELSE IF @opcion = 'DELETE'
    BEGIN
        IF EXISTS (SELECT 1 FROM Residuos WHERE idClasificacion = @idClasificacion)
        BEGIN
            SET @resultado = -1; -- No se puede eliminar por residuos vinculados
            RETURN;
        END

        DELETE FROM Clasificacion_Residuos WHERE idClasificacion = @idClasificacion;
        SET @resultado = @idClasificacion;
        RETURN;
    END
    ELSE
    BEGIN
        SET @resultado = -99; -- Opción inválida
        RETURN;
    END
END
GO















-- Insertar 10 registros en la tabla Direccion
INSERT INTO Direccion (direccion, ciudad, distrito, codigo_postal, referencia)
VALUES
    ('Av. Los Pr�ceres 123', 'Lima', 'San Juan de Lurigancho', '15425', 'Frente al parque central'),
    ('Av. Los Pr�ceres 124', 'Lima', 'Miraflores', '15073', 'Cerca al parque Kennedy'),
    ('Calle Falsa 10', 'Lima', 'San Isidro', '15073', 'Cerca al centro comercial'),
    ('Av. Pardo y Aliaga 252', 'Lima', 'San Borja', '15073', 'Frente a la comisaria'),
    ('Jr. Amazonas 145', 'Arequipa', 'Cayma', '04000', 'Cerca a la Plaza de Armas'),
    ('Calle Larga 14', 'Trujillo', 'El Porvenir', '13000', 'Cerca del centro histórico'),
    ('Av. Brasil 778', 'Cusco', 'Cusco', '08000', 'Cerca a la Plaza Mayor'),
    ('Jr. Puno 25', 'Puno', 'Puno', '10000', 'Frente a la Universidad Nacional'),
    ('Calle Lima 151', 'Piura', 'Piura', '15000', 'Cerca al supermercado'),
    ('Jr. Tacna 54', 'Chiclayo', 'Chiclayo', '14000', 'Frente al Mercado Central');
GO

-- Insertar 10 registros en la tabla Clasificacion_Residuos
INSERT INTO Clasificacion_Residuos (nombre, descripcion, color_codigo, icono)
VALUES
    ('Plástico', 'Residuos plásticos reciclables', 'blue', 'plastic-icon.png'),
    ('Vidrio', 'Residuos de vidrio reciclables', 'green', 'glass-icon.png'),
    ('Papel', 'Residuos de papel reciclables', 'yellow', 'paper-icon.png'),
    ('Metal', 'Residuos metálicos reciclables', 'gray', 'metal-icon.png'),
    ('Orgánico', 'Residuos orgánicos biodegradables', 'brown', 'organic-icon.png'),
    ('Ropa', 'Residuos textiles reciclables', 'purple', 'clothes-icon.png'),
    ('Pilas', 'Residuos de pilas y baterías', 'red', 'battery-icon.png'),
    ('Electrónicos', 'Residuos electrónicos', 'black', 'electronic-icon.png'),
    ('Tetra Pak', 'Residuos de envases Tetra Pak', 'blue', 'tetra-pak-icon.png'),
    ('Cartón', 'Residuos de cartón reciclables', 'orange', 'cardboard-icon.png');
GO



--consultas--
select * from Usuario;
GO

select * from Direccion;
GO

SELECT * from Clasificacion_Residuos;
GO

select * from Residuos;

--Borrar los datos de las tablas--
DELETE FROM Usuario_Destino;
DELETE FROM Ingreso_Residuos;
DELETE FROM Sugerencia;
DELETE FROM Notificacion;
DELETE FROM Mensaje;
DELETE FROM Auditoria;
DELETE FROM Residuos;
DELETE FROM Clasificacion_Residuos;
DELETE FROM Usuario;
DELETE FROM Direccion;
GO

IF OBJECT_ID('sp_registro_completo_usuario_residuo', 'P') IS NOT NULL
    DROP PROCEDURE sp_registro_completo_usuario_residuo;
GO


SELECT 
    p.name AS ProcedureName,
    prm.name AS ParameterName,
    prm.is_output
FROM sys.procedures p
JOIN sys.parameters prm ON p.object_id = prm.object_id
WHERE p.name = 'sp_crud_clasificacion_residuos';