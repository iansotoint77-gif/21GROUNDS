package com.ejemplo.model;

import com.ejemplo.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, username, correo, password, comunidad_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getPassword());
            stmt.setInt(6, usuario.getComunidadId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario autenticarUsuario(String loginInput, String password) {
        // Permitir login con correo o username
        String sql = "SELECT * FROM usuarios WHERE (correo = ? OR username = ?) AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, loginInput);
            stmt.setString(2, loginInput);
            stmt.setString(3, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setUsername(rs.getString("username"));
                    u.setCorreo(rs.getString("correo"));
                    u.setPassword(rs.getString("password"));
                    u.setComunidadId(rs.getInt("comunidad_id"));
                    u.setRol(rs.getString("rol"));
                    u.setPuntosTotales(rs.getDouble("puntos_totales"));
                    u.setPartidosJugados(rs.getInt("partidos_jugados"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarUsuario(int id, String username, int comunidadId, String newPassword) {
        StringBuilder sql = new StringBuilder("UPDATE usuarios SET username = ?, comunidad_id = ?");
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            sql.append(", password = ?");
        }
        sql.append(" WHERE id = ?");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
             
            stmt.setString(1, username);
            stmt.setInt(2, comunidadId);
            
            int paramIndex = 3;
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                stmt.setString(paramIndex++, newPassword);
            }
            stmt.setInt(paramIndex, id);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public java.util.List<Usuario> listarUsuarios() {
        java.util.List<Usuario> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol != 'admin'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setUsername(rs.getString("username"));
                u.setCorreo(rs.getString("correo"));
                u.setRol(rs.getString("rol"));
                u.setPuntosTotales(rs.getDouble("puntos_totales"));
                u.setPartidosJugados(rs.getInt("partidos_jugados"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
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
