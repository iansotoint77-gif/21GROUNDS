package com.ejemplo.controller;

import com.ejemplo.model.Usuario;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GET /SessionServlet → Devuelve JSON con el estado de la sesión actual.
 * Lo usan las páginas HTML para saber si el usuario está logueado y con qué
 * rol.
 *
 * Respuesta ejemplo (logueado):
 * { "loggedIn": true, "rol": "usuario", "username": "@TheGoat", "nombre": "Ian"
 * }
 *
 * Respuesta ejemplo (no logueado):
 * { "loggedIn": false }
 */
@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Evitar que el navegador cachee la respuesta de sesión
        response.setHeader("Cache-Control", "no-store");

        HttpSession session = request.getSession(false);
        Map<String, Object> data = new HashMap<>();

        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
            data.put("loggedIn", true);
            data.put("rol", user.getRol());
            data.put("id", user.getId());
            data.put("username", user.getUsername());
            data.put("nombre", user.getNombre());
            data.put("apellido", user.getApellido());
            data.put("correo", user.getCorreo());
            data.put("comunidadId", user.getComunidadId());
            data.put("puntosTotales", user.getPuntosTotales());
            data.put("partidosJugados", user.getPartidosJugados());
            // Color único por usuario: par = azul, impar = naranja
            data.put("colorPerfil", user.getId() % 2 == 0 ? "#3B82F6" : "#FF6B00");
        } else {
            data.put("loggedIn", false);
        }

        response.getWriter().write(gson.toJson(data));
    }
}
