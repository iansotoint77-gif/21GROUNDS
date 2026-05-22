package com.ejemplo.model;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String username;
    private String correo;
    private String password;
    private int comunidadId;
    private String rol;
    private double puntosTotales;
    private int partidosJugados;

    public Usuario() {}

    public Usuario(String nombre, String apellido, String username, String correo, String password, int comunidadId) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.correo = correo;
        this.password = password;
        this.comunidadId = comunidadId;
        this.rol = "usuario";
        this.puntosTotales = 0.0;
        this.partidosJugados = 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getComunidadId() { return comunidadId; }
    public void setComunidadId(int comunidadId) { this.comunidadId = comunidadId; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public double getPuntosTotales() { return puntosTotales; }
    public void setPuntosTotales(double puntosTotales) { this.puntosTotales = puntosTotales; }

    public int getPartidosJugados() { return partidosJugados; }
    public void setPartidosJugados(int partidosJugados) { this.partidosJugados = partidosJugados; }
}
