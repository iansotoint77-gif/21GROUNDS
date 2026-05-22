package com.ejemplo.controller;

import com.ejemplo.model.Partido;
import com.ejemplo.model.PartidoDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * GET /PartidosApiServlet  →  Devuelve JSON con la lista de partidos abiertos.
 * Lo consume partidos.html con fetch().
 */
@WebServlet("/PartidosApiServlet")
public class PartidosApiServlet extends HttpServlet {

    private PartidoDAO partidoDAO;
    private Gson gson;

    @Override
    public void init() {
        partidoDAO = new PartidoDAO();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Partido> partidos = partidoDAO.listarPartidosAbiertos();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(partidos));
    }
}
