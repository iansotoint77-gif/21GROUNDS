package com.ejemplo.controller;

import com.ejemplo.model.PartidoDAO;
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
 * POST /UnirseServlet?partidoId=X
 * Devuelve JSON: { "resultado": "ok_equipo1" | "ok_equipo2" | "ya_unido" | "lleno" | "cerrado" | "no_sesion" | "error" }
 */
@WebServlet("/UnirseServlet")
public class UnirseServlet extends HttpServlet {

    private PartidoDAO partidoDAO;
    private Gson gson;

    @Override
    public void init() {
        partidoDAO = new PartidoDAO();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> resp = new HashMap<>();

        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.put("resultado", "no_sesion");
            response.getWriter().write(gson.toJson(resp));
            return;
        }

        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");

        // Leer ID del partido
        String partidoIdStr = request.getParameter("partidoId");
        if (partidoIdStr == null || partidoIdStr.isBlank()) {
            resp.put("resultado", "error");
            response.getWriter().write(gson.toJson(resp));
            return;
        }

        int partidoId;
        try {
            partidoId = Integer.parseInt(partidoIdStr);
        } catch (NumberFormatException e) {
            resp.put("resultado", "error");
            response.getWriter().write(gson.toJson(resp));
            return;
        }

        // Intentar unirse
        String resultado = partidoDAO.unirseAPartido(partidoId, user.getId());
        resp.put("resultado", resultado);
        response.getWriter().write(gson.toJson(resp));
    }
}
