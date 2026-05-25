package com.ejemplo.model;

import com.ejemplo.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO {

    public String crearPartido(Partido partido) {
        String sqlPartido = "INSERT INTO partidos (creador_id, tipo_partido, max_jugadores, lugar, comunidad_id, fecha_hora) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlUnirse = "INSERT INTO partido_usuarios (partido_id, usuario_id, equipo) VALUES (?, ?, 1)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPartido = conn.prepareStatement(sqlPartido, Statement.RETURN_GENERATED_KEYS)) {
                stmtPartido.setInt(1, partido.getCreadorId());
                stmtPartido.setString(2, partido.getTipoPartido());
                stmtPartido.setInt(3, partido.getMaxJugadores());
                stmtPartido.setString(4, partido.getLugar());
                stmtPartido.setInt(5, partido.getComunidadId());
                stmtPartido.setString(6, partido.getFechaHora());
                stmtPartido.executeUpdate();

                try (ResultSet keys = stmtPartido.getGeneratedKeys()) {
                    if (keys.next()) {
                        int partidoId = keys.getInt(1);
                        try (PreparedStatement stmtUnirse = conn.prepareStatement(sqlUnirse)) {
                            stmtUnirse.setInt(1, partidoId);
                            stmtUnirse.setInt(2, partido.getCreadorId());
                            stmtUnirse.executeUpdate();
                        }
                    }
                }

                conn.commit();
                return "ok";

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return "Error SQL al insertar: " + e.getMessage();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error SQL conexion: " + e.getMessage();
        } catch (Exception e) {
            return "Error general: " + e.getMessage();
        }
    }

    /**
     * Lógica de unirse a un partido:
     * 1. Comprueba si el usuario ya está en el partido → error "ya_unido"
     * 2. Cuenta jugadores en Equipo 1 y Equipo 2
     * 3. Asigna al primer equipo con hueco (mitad del maxJugadores por equipo)
     * 4. Si ambos llenos → error "lleno"
     * 5. Si el partido ya no está abierto → error "cerrado"
     *
     * Devuelve: "ok_equipo1", "ok_equipo2", "ya_unido", "lleno", "cerrado", "error"
     */
    public String unirseAPartido(int partidoId, int usuarioId) {
        // 1. Obtener datos del partido
        String sqlPartido = "SELECT max_jugadores, estado FROM partidos WHERE id = ?";
        String sqlYaUnido = "SELECT COUNT(*) FROM partido_usuarios WHERE partido_id = ? AND usuario_id = ?";
        String sqlContarEquipo = "SELECT COUNT(*) FROM partido_usuarios WHERE partido_id = ? AND equipo = ?";
        String sqlInsertar = "INSERT INTO partido_usuarios (partido_id, usuario_id, equipo) VALUES (?, ?, ?)";
        String sqlCerrar = "UPDATE partidos SET estado = 'cerrado' WHERE id = ? AND (SELECT COUNT(*) FROM partido_usuarios WHERE partido_id = ?) >= max_jugadores";

        try (Connection conn = DBConnection.getConnection()) {

            // Comprobar estado del partido
            int maxJugadores;
            String estado;
            try (PreparedStatement stmt = conn.prepareStatement(sqlPartido)) {
                stmt.setInt(1, partidoId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next())
                        return "error";
                    maxJugadores = rs.getInt("max_jugadores");
                    estado = rs.getString("estado");
                }
            }

            if (!"abierto".equals(estado))
                return "cerrado";

            // Comprobar si ya está unido
            try (PreparedStatement stmt = conn.prepareStatement(sqlYaUnido)) {
                stmt.setInt(1, partidoId);
                stmt.setInt(2, usuarioId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0)
                        return "ya_unido";
                }
            }

            // Cuántos jugadores caben por equipo
            int porEquipo = maxJugadores / 2;

            // Contar jugadores actuales en cada equipo
            int eq1 = contarEquipo(conn, sqlContarEquipo, partidoId, 1);
            int eq2 = contarEquipo(conn, sqlContarEquipo, partidoId, 2);

            // Determinar a qué equipo asignar
            int equipoAsignado;
            if (eq1 < porEquipo) {
                equipoAsignado = 1;
            } else if (eq2 < porEquipo) {
                equipoAsignado = 2;
            } else {
                return "lleno";
            }

            // Insertar jugador
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsertar)) {
                stmt.setInt(1, partidoId);
                stmt.setInt(2, usuarioId);
                stmt.setInt(3, equipoAsignado);
                stmt.executeUpdate();
            }

            // Si el partido está lleno tras unirse, cerrarlo
            int totalAhora = eq1 + eq2 + 1;
            if (totalAhora >= maxJugadores) {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE partidos SET estado = 'cerrado' WHERE id = ?")) {
                    stmt.setInt(1, partidoId);
                    stmt.executeUpdate();
                }
            }

            return "ok_equipo" + equipoAsignado;

        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }

    private int contarEquipo(Connection conn, String sql, int partidoId, int equipo) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, partidoId);
            stmt.setInt(2, equipo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    /**
     * Devuelve todos los partidos en estado 'abierto', incluyendo los jugadores
     * de cada equipo (sus usernames) para mostrar avatares en el frontend.
     */
    public List<Partido> listarPartidosAbiertos() {
        List<Partido> lista = new ArrayList<>();

        // GROUP_CONCAT trae los usernames de cada equipo separados por coma
        String sql = """
                SELECT p.id, p.tipo_partido, p.max_jugadores, p.lugar, p.fecha_hora, p.estado,
                       c.nombre AS comunidad_nombre,
                       u.username AS creador_username,
                       COUNT(pu.usuario_id) AS jugadores_actuales,
                       GROUP_CONCAT(CASE WHEN pu.equipo = 1 THEN CONCAT(u2.id, ':', u2.username) END
                                    ORDER BY pu.usuario_id SEPARATOR ',') AS eq1_users,
                       GROUP_CONCAT(CASE WHEN pu.equipo = 2 THEN CONCAT(u2.id, ':', u2.username) END
                                    ORDER BY pu.usuario_id SEPARATOR ',') AS eq2_users
                FROM partidos p
                JOIN comunidades c ON p.comunidad_id = c.id
                JOIN usuarios u ON p.creador_id = u.id
                LEFT JOIN partido_usuarios pu ON p.id = pu.partido_id
                LEFT JOIN usuarios u2 ON pu.usuario_id = u2.id
                WHERE p.estado = 'abierto'
                GROUP BY p.id
                ORDER BY p.fecha_hora ASC
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Partido p = new Partido();
                p.setId(rs.getInt("id"));
                p.setTipoPartido(rs.getString("tipo_partido"));
                p.setMaxJugadores(rs.getInt("max_jugadores"));
                p.setJugadoresActuales(rs.getInt("jugadores_actuales"));
                p.setLugar(rs.getString("lugar"));
                p.setFechaHora(rs.getString("fecha_hora"));
                p.setEstado(rs.getString("estado"));
                p.setComunidadNombre(rs.getString("comunidad_nombre"));
                p.setCreadorUsername(rs.getString("creador_username"));

                // Convertir CSV de usernames a listas
                String eq1Csv = rs.getString("eq1_users");
                String eq2Csv = rs.getString("eq2_users");
                if (eq1Csv != null && !eq1Csv.isBlank()) {
                    p.setEquipo1Jugadores(List.of(eq1Csv.split(",")));
                }
                if (eq2Csv != null && !eq2Csv.isBlank()) {
                    p.setEquipo2Jugadores(List.of(eq2Csv.split(",")));
                }

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Elimina un partido por ID (para el admin).
     */
    public boolean eliminarPartido(int id) {
        String sql = "DELETE FROM partidos WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
