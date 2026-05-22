package com.ejemplo.controller;

import com.ejemplo.model.Usuario;
import com.ejemplo.model.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/AjustesServlet")
public class AjustesServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.html");
            return;
        }
        
        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
        
        String username = request.getParameter("username");
        String comunidad = request.getParameter("comunidad");
        String newPassword = request.getParameter("newPassword");
        
        if (username != null && !username.trim().isEmpty() && comunidad != null) {
            int comunidadId;
            try {
                comunidadId = Integer.parseInt(comunidad);
            } catch (NumberFormatException e) {
                response.sendRedirect("ajustes.html?error=comunidad_invalida");
                return;
            }
            
            boolean actualizado = usuarioDAO.actualizarUsuario(user.getId(), username, comunidadId, newPassword);
            if (actualizado) {
                // Actualizar sesión local
                user.setUsername(username);
                user.setComunidadId(comunidadId);
                if (newPassword != null && !newPassword.trim().isEmpty()) {
                    user.setPassword(newPassword);
                }
                session.setAttribute("usuarioLogueado", user);
                
                // Redirigir a perfil-usuario con un parámetro de éxito
                response.sendRedirect("perfil-usuario.html?status=actualizado");
            } else {
                response.sendRedirect("ajustes.html?error=error_db");
            }
        } else {
            response.sendRedirect("ajustes.html?error=campos_incompletos");
        }
    }
}
