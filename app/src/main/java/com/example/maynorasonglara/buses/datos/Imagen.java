package com.example.maynorasonglara.buses.datos;

/**
 * Created by maynor.a.song.lara on 3/24/2015.
 */
public class Imagen {
    private String detalle,rutaEnServidor,lugar;

    public Imagen(String detalle, String rutaEnServidor, String lugar) {
        this.detalle = detalle;
        this.rutaEnServidor = rutaEnServidor;
        this.lugar = lugar;
    }

    public String getRutaEnServidor() {
        return rutaEnServidor;
    }

    public void setRutaEnServidor(String rutaEnServidor) {
        this.rutaEnServidor = rutaEnServidor;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
