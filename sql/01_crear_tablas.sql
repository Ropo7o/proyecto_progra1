-- =============================================
-- SCRIPT: Creacion de tablas
-- Base de datos: SQL Server
-- Proyecto: Sistema de Gestion Mundial 2026
-- =============================================

CREATE TABLE EQUIPOS_MUNDIAL (
    ID          INT          PRIMARY KEY IDENTITY(1,1),
    PAIS_ORIGEN VARCHAR(200) NOT NULL,
    NOMBRE_DT   VARCHAR(200) NOT NULL,
    GRUPO       CHAR(1)      NOT NULL,
    PJ          INT          NOT NULL DEFAULT 0,
    PG          INT          NOT NULL DEFAULT 0,
    PE          INT          NOT NULL DEFAULT 0,
    PP          INT          NOT NULL DEFAULT 0,
    GF          INT          NOT NULL DEFAULT 0,
    GC          INT          NOT NULL DEFAULT 0,
    PUNTOS      INT          NOT NULL DEFAULT 0
);

CREATE TABLE JUGADORES_MUNDIAL (
    ID                  INT          PRIMARY KEY IDENTITY(1,1),
    NOMBRE_JUGADOR      VARCHAR(200) NOT NULL,
    NUMERO_JUGADOR      INT          NOT NULL,
    POSICION_JUGADOR    VARCHAR(200) NOT NULL,
    CODIGO_PAIS_JUGADOR INT          NULL,
    ESTATURA_JUGADOR    FLOAT        NOT NULL,
    EDAD_JUGADOR        INT          NOT NULL,
    GOLES_JUGADOR       INT          NOT NULL,
    CONSTRAINT FK_JUGADORES_PAIS
        FOREIGN KEY (CODIGO_PAIS_JUGADOR) REFERENCES EQUIPOS_MUNDIAL(ID)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);

CREATE TABLE PARTIDOS_MUNDIAL (
    ID              INT          PRIMARY KEY IDENTITY(1,1),
    ID_LOCAL        INT          NOT NULL,
    ID_VISITANTE    INT          NOT NULL,
    GOLES_LOCAL     INT          NULL,
    GOLES_VISITANTE INT          NULL,
    FECHA           VARCHAR(20)  NOT NULL,
    FASE            VARCHAR(100) NOT NULL,
    CONSTRAINT FK_PARTIDO_LOCAL
        FOREIGN KEY (ID_LOCAL)     REFERENCES EQUIPOS_MUNDIAL(ID),
    CONSTRAINT FK_PARTIDO_VISITANTE
        FOREIGN KEY (ID_VISITANTE) REFERENCES EQUIPOS_MUNDIAL(ID)
);
