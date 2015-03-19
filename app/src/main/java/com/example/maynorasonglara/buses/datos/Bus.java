package com.example.maynorasonglara.buses.datos;

/**
 * Created by maynor.a.song.lara on 2/12/2015.
 */
public class Bus {

    private float tarifa,id;
    private String nombre,tiempo,concesionario,precioAdultoMayor;
    private int isFraccionamiento,isRamel;

    public Bus(float tarifa, float id, String nombre, String tiempo, String concesionario, String precioAdultoMayor, int isFraccionamiento, int isRamel) {
        this.tarifa = tarifa;
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.concesionario = concesionario;
        this.precioAdultoMayor = precioAdultoMayor;
        this.isFraccionamiento = isFraccionamiento;
        this.isRamel = isRamel;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }



    public String getConcesionario() {
        return concesionario;
    }

    public void setConcesionario(String concesionario) {
        this.concesionario = concesionario;
    }

    public String getPrecioAdultoMayor() {
        return precioAdultoMayor;
    }

    public void setPrecioAdultoMayor(String precioAdultoMayor) {
        this.precioAdultoMayor = precioAdultoMayor;
    }

    public int isFraccionamiento() {
        return isFraccionamiento;
    }

    public void setFraccionamiento(int isFraccionamiento) {
        this.isFraccionamiento = isFraccionamiento;
    }

    public int isRamel() {
        return isRamel;
    }

    public void setRamel(int isRamel) {
        this.isRamel = isRamel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public float getTarifa() {
        return tarifa;
    }

    public void setTarifa(float tarifa) {
        this.tarifa = tarifa;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
