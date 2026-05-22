package com.ejemplo.model;

public class Partido {
    private int id;
    private int creadorId;
    private String creadorUsername;
    private String tipoPartido;
    private int maxJugadores;
    private int jugadoresActuales;
    private String lugar;
    private int comunidadId;
    private String comunidadNombre;
    private String fechaHora;
    private String estado;
    // Jugadores por equipo (sus usernames, para mostrar avatares)
    private java.util.List<String> equipo1Jugadores = new java.util.ArrayList<>();
    private java.util.List<String> equipo2Jugadores = new java.util.ArrayList<>();


    public Partido() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCreadorId() { return creadorId; }
    public void setCreadorId(int creadorId) { this.creadorId = creadorId; }

    public String getCreadorUsername() { return creadorUsername; }
    public void setCreadorUsername(String creadorUsername) { this.creadorUsername = creadorUsername; }

    public String getTipoPartido() { return tipoPartido; }
    public void setTipoPartido(String tipoPartido) { this.tipoPartido = tipoPartido; }

    public int getMaxJugadores() { return maxJugadores; }
    public void setMaxJugadores(int maxJugadores) { this.maxJugadores = maxJugadores; }

    public int getJugadoresActuales() { return jugadoresActuales; }
    public void setJugadoresActuales(int jugadoresActuales) { this.jugadoresActuales = jugadoresActuales; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public int getComunidadId() { return comunidadId; }
    public void setComunidadId(int comunidadId) { this.comunidadId = comunidadId; }

    public String getComunidadNombre() { return comunidadNombre; }
    public void setComunidadNombre(String comunidadNombre) { this.comunidadNombre = comunidadNombre; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public java.util.List<String> getEquipo1Jugadores() { return equipo1Jugadores; }
    public void setEquipo1Jugadores(java.util.List<String> equipo1Jugadores) { this.equipo1Jugadores = equipo1Jugadores; }

    public java.util.List<String> getEquipo2Jugadores() { return equipo2Jugadores; }
    public void setEquipo2Jugadores(java.util.List<String> equipo2Jugadores) { this.equipo2Jugadores = equipo2Jugadores; }
}
