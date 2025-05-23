package mivalgamer.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Videojuego {
    private final long idVideojuego;
    private final String titulo;
    private final String estudio;
    private final long idGenero;
    private final String descripcion;
    private final double precio;
    private final double precioOriginal;
    private final boolean descuentoAplicado;
    private final EstadoVideojuego estado;
    private final List<String> icono;
    private final List<String>  portada;
    private final List<String>  contenidoVisual;
    private final int stock;

    public Videojuego(long idVideojuego, String titulo, String estudio, long idGenero,
                      String descripcion, double precio, double precioOriginal,
                      boolean descuentoAplicado, EstadoVideojuego estado,
                      List<String>  icono, List<String>  portada, List<String>  contenidoVisual, int stock) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.estudio = estudio;
        this.idGenero = idGenero;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precioOriginal = precioOriginal;
        this.descuentoAplicado = descuentoAplicado;
        this.estado = estado;
        this.icono = icono;
        this.portada = portada;
        this.contenidoVisual = contenidoVisual;
        this.stock = stock;
    }

    // Getters
    public long getIdVideojuego() { return idVideojuego; }
    public String getTitulo() { return titulo; }
    public String getEstudio() { return estudio; }
    public long getIdGenero() { return idGenero; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public double getPrecioOriginal() { return precioOriginal; }
    public boolean isDescuentoAplicado() { return descuentoAplicado; }
    public EstadoVideojuego getEstado() { return estado; }
    public List<String>  getIcono() { return icono; }
    public List<String>  getPortada() { return portada; }
    public List<String>  getContenidoVisual() { return contenidoVisual; }
    public int getStock() { return stock; }


    public List<Plataforma> obtenerPlataformas(Connection conn) throws SQLException {
        return Plataforma.obtenerPorVideojuego(conn, this.idVideojuego);
    }
}