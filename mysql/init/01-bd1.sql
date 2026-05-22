CREATE DATABASE IF NOT EXISTS 21grounds_db;
USE 21grounds_db;

CREATE TABLE comunidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    correo VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, 
    comunidad_id INT NOT NULL, 
    rol VARCHAR(20) DEFAULT 'usuario', 
    puntos_totales DECIMAL(10,2) DEFAULT 0.00,
    partidos_jugados INT DEFAULT 0,
    FOREIGN KEY (comunidad_id) REFERENCES comunidades(id)
);

CREATE TABLE partidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    creador_id INT NOT NULL,
    tipo_partido VARCHAR(10) NOT NULL, 
    max_jugadores INT NOT NULL,
    lugar VARCHAR(255) NOT NULL,
    comunidad_id INT NOT NULL, 
    fecha_hora DATETIME NOT NULL,
    estado VARCHAR(20) DEFAULT 'abierto', 
    FOREIGN KEY (creador_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (comunidad_id) REFERENCES comunidades(id)
);

CREATE TABLE partido_usuarios (
    partido_id INT NOT NULL,
    usuario_id INT NOT NULL,
    equipo TINYINT NOT NULL DEFAULT 1,         -- 1 = Equipo 1, 2 = Equipo 2
    puntos_anotados INT DEFAULT 0,
    PRIMARY KEY (partido_id, usuario_id),
    FOREIGN KEY (partido_id) REFERENCES partidos(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE noticias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    imagen_url VARCHAR(255) NOT NULL, 
    contenido TEXT NOT NULL,
    es_destacada BOOLEAN DEFAULT FALSE
);

INSERT INTO comunidades (nombre) VALUES 
('Andalucía'), ('Aragón'), ('Asturias'), ('Baleares'), 
('Canarias'), ('Cantabria'), ('Castilla y León'), 
('Castilla-La Mancha'), ('Cataluña'), ('Comunidad Valenciana'), 
('Extremadura'), ('Galicia'), ('Madrid'), ('Murcia'), 
('Navarra'), ('País Vasco'), ('La Rioja'), ('Ceuta'), ('Melilla');

INSERT INTO usuarios (nombre, apellido, username, correo, password, comunidad_id, rol) VALUES 
('Admin', 'General', '@Admin', 'admin@admin.com', 'admin', 13, 'admin'),
('Usuario', 'Normal', '@Usuario1', 'user@user.com', 'user', 13, 'usuario');