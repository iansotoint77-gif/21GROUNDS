package com.ejemplo.controller;

import com.ejemplo.model.Partido;
import com.ejemplo.model.PartidoDAO;
import com.ejemplo.model.Usuario;
import com.ejemplo.model.UsuarioDAO;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/AdminApiServlet")
public class AdminApiServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;
    private PartidoDAO partidoDAO;
    private Gson gson;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
        partidoDAO = new PartidoDAO();
        gson = new Gson();
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
            return "admin".equals(user.getRol());
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if ("usuarios".equals(action)) {
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            response.getWriter().write(gson.toJson(usuarios));
        } else if ("partidos".equals(action)) {
            // Reutilizamos listarPartidosAbiertos() para simplificar o podríamos listar todos.
            // Para administrar, mostraremos los abiertos de momento (o podemos devolver todos si existiera el método)
            List<Partido> partidos = partidoDAO.listarPartidosAbiertos();
            response.getWriter().write(gson.toJson(partidos));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        Map<String, Object> res = new HashMap<>();

        if (idStr == null) {
            res.put("success", false);
            res.put("error", "Falta ID");
            response.getWriter().write(gson.toJson(res));
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            boolean success = false;

            if ("usuario".equals(action)) {
                success = usuarioDAO.eliminarUsuario(id);
            } else if ("partido".equals(action)) {
                success = partidoDAO.eliminarPartido(id);
            }

            res.put("success", success);
            response.getWriter().write(gson.toJson(res));

        } catch (NumberFormatException e) {
            res.put("success", false);
            res.put("error", "ID inválido");
            response.getWriter().write(gson.toJson(res));
        }
    }
}
