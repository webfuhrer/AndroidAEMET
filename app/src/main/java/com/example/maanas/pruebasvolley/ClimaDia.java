package com.example.maanas.pruebasvolley;

public class ClimaDia {
    private String fecha;
    private int t_minima, t_maxima;

    public ClimaDia(String fecha, int t_minima, int t_maxima) {
        this.fecha = fecha;
        this.t_minima = t_minima;
        this.t_maxima = t_maxima;
    }
    public ClimaDia() {

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getT_minima() {
        return t_minima;
    }

    public void setT_minima(int t_minima) {
        this.t_minima = t_minima;
    }

    public int getT_maxima() {
        return t_maxima;
    }

    public void setT_maxima(int t_maxima) {
        this.t_maxima = t_maxima;
    }
}
