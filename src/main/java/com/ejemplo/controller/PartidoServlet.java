package com.ejemplo.controller;

import com.ejemplo.model.Partido;
import com.ejemplo.model.PartidoDAO;
import com.ejemplo.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * POST /PartidoServlet  →  Crear partido nuevo y redirigir a partidos.html
 */
@WebServlet("/PartidoServlet")
public class PartidoServlet extends HttpServlet {

    private PartidoDAO partidoDAO;

    @Override
    public void init() {
        partidoDAO = new PartidoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar que el usuario está logueado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.html");
            return;
        }

        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");

        // Recoger datos del formulario
        String formato   = request.getParameter("formato");   // ej: "3vs3"
        String lugar     = request.getParameter("lugar");
        String comunidad = request.getParameter("comunidad");  // nombre de comunidad
        String fecha     = request.getParameter("fecha");      // "2026-06-01"
        String hora      = request.getParameter("hora");       // "18:00"

        // Validar que no vengan vacíos
        if (formato == null || lugar == null || comunidad == null || fecha == null || hora == null
                || formato.isBlank() || lugar.isBlank() || comunidad.isBlank() || fecha.isBlank() || hora.isBlank()) {
            response.sendRedirect("crearPartido.html?error=campos");
            return;
        }

        // Calcular maxJugadores según formato
        int maxJugadores = calcularMaxJugadores(formato);

        // Resolver comunidad_id a partir del nombre
        int comunidadId = resolverComunidadId(comunidad);
        if (comunidadId == -1) {
            response.sendRedirect("crearPartido.html?error=comunidad");
            return;
        }

        // Construir objeto Partido
        Partido partido = new Partido();
        partido.setCreadorId(user.getId());
        partido.setTipoPartido(formato);
        partido.setMaxJugadores(maxJugadores);
        partido.setLugar(lugar);
        partido.setComunidadId(comunidadId);
        partido.setFechaHora(fecha + " " + hora + ":00");
        partido.setEstado("abierto");

        boolean creado = partidoDAO.crearPartido(partido);

        if (creado) {
            response.sendRedirect("partidos.html");
        } else {
            response.sendRedirect("crearPartido.html?error=true");
        }
    }

    /**
     * Convierte "3vs3" → 6, "5vs5" → 10, etc.
     */
    private int calcularMaxJugadores(String formato) {
        try {
            String[] partes = formato.split("vs");
            int porEquipo = Integer.parseInt(partes[0].trim());
            return porEquipo * 2;
        } catch (Exception e) {
            return 2; // por defecto 1vs1
        }
    }

    /**
     * Devuelve el comunidad_id buscando por nombre.
     * Usamos un mapa sencillo que coincide con el INSERT del SQL.
     */
    private int resolverComunidadId(String nombre) {
        String[] comunidades = {
            "Andalucía", "Aragón", "Asturias", "Baleares", "Canarias",
            "Cantabria", "Castilla y León", "Castilla-La Mancha", "Cataluña",
            "Comunidad Valenciana", "Extremadura", "Galicia", "Madrid",
            "Murcia", "Navarra", "País Vasco", "La Rioja", "Ceuta", "Melilla"
        };
        for (int i = 0; i < comunidades.length; i++) {
            if (comunidades[i].equalsIgnoreCase(nombre.trim())) {
                return i + 1; // IDs empiezan en 1
            }
        }
        return -1;
    }
}
