create database GestionResiduo;
use GestionResiduo;

-- Tabla Direccion
CREATE TABLE Direccion (
    idDireccion INT IDENTITY(1,1) PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    distrito VARCHAR(100),
    codigo_postal VARCHAR(20),
    pais VARCHAR(100) DEFAULT 'Per�',
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

--crud para Residuos--
CREATE PROCEDURE sp_crud_residuos
    @opcion VARCHAR(10), -- 'CREATE', 'READ', 'UPDATE', 'DELETE'
    @idResiduos INT = NULL, -- Para actualizar o eliminar un residuo específico
    @nombre VARCHAR(100) = NULL, -- Nombre del residuo
    @idClasificacion INT = NULL, -- Clasificación del residuo (referencia a la tabla Clasificacion_Residuos)
    @descripcion VARCHAR(255) = NULL, -- Descripción del residuo
    @unidad_medida VARCHAR(50) = NULL, -- Unidad de medida
    @peligrosidad VARCHAR(50) = NULL, -- Peligrosidad del residuo
    @resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    IF @opcion = 'CREATE'
    BEGIN
        -- Validación básica de campos requeridos
        IF @nombre IS NULL OR @idClasificacion IS NULL
        BEGIN
            SET @resultado = -1; -- Error: Campos requeridos faltantes
            RETURN;
        END

        INSERT INTO Residuos (
            nombre,
            idClasificacion,
            descripcion,
            unidad_medida,
            peligrosidad,
            fecha_registro
        )
        VALUES (
            @nombre,
            @idClasificacion,
            @descripcion,
            @unidad_medida,
            @peligrosidad,
            GETDATE() -- fecha_registro automática
        );

        SET @resultado = SCOPE_IDENTITY(); -- Devuelve el ID del nuevo residuo creado
    END
    ELSE IF @opcion = 'READ'
    BEGIN
        IF @idResiduos IS NULL
            SELECT * FROM Residuos; -- Si no se pasa un ID, retorna todos los residuos
        ELSE
            SELECT * FROM Residuos WHERE idResiduos = @idResiduos; -- Retorna un residuo específico
    END
    ELSE IF @opcion = 'UPDATE'
    BEGIN
        -- Validación básica
        IF @idResiduos IS NULL
        BEGIN
            SET @resultado = -1; -- Error: ID del residuo faltante
            RETURN;
        END

        UPDATE Residuos
        SET
            nombre = ISNULL(@nombre, nombre),
            idClasificacion = ISNULL(@idClasificacion, idClasificacion),
            descripcion = ISNULL(@descripcion, descripcion),
            unidad_medida = ISNULL(@unidad_medida, unidad_medida),
            peligrosidad = ISNULL(@peligrosidad, peligrosidad)
        WHERE idResiduos = @idResiduos;

        SET @resultado = @idResiduos; -- Retorna el ID del residuo actualizado
    END
    ELSE IF @opcion = 'DELETE'
    BEGIN
        -- Verificación para asegurarse de que no existan registros relacionados
        IF EXISTS (SELECT 1 FROM Ingreso_Residuos WHERE idResiduos = @idResiduos)
        BEGIN
            SET @resultado = -1; -- No se puede eliminar porque el residuo está relacionado con otros registros
            RETURN;
        END

        DELETE FROM Residuos WHERE idResiduos = @idResiduos;

        SET @resultado = @idResiduos; -- Retorna el ID del residuo eliminado
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

SELECT * from Direccion;
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