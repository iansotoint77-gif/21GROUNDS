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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        Usuario user = usuarioDAO.autenticarUsuario(usuario, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", user);
            
            if ("admin".equals(user.getRol())) {
                response.sendRedirect("admin.html");
            } else {
                response.sendRedirect("perfil-usuario.html");
            }
        } else {
            response.sendRedirect("login.html?error=true");
        }
    }
}
