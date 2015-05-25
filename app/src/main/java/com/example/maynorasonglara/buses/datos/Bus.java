package com.example.maynorasonglara.buses.datos;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maynor.a.song.lara on 2/12/2015.
 */
public class Bus {

    private float tarifa;
    private String nombre,tiempo,concesionario,precioAdultoMayor;
    private int isFraccionamiento,isRamel,id;
    private ArrayList<Imagen> listaDeImagenes=new ArrayList<>();

    /* variables estaticas de atributos de bus para que al viajar de una actividad a otra los datos se recojan de la misma manera sin problema*/
    public static String NOMBRE_BUS="nombre";
    public static String ID_BUS="id";
    public static String TIEMPO_BUS="tiempo";
    public static String CONCESIONARIO_BUS="concesionario";
    public static String TARIFA_BUS="tarifa";
    public static String ADULTO_MAYOR_BUS="precioAdultoMayor";



    public Bus(float tarifa, int id, String nombre, String tiempo, String concesionario, String precioAdultoMayor, int isFraccionamiento, int isRamel) {
        this.tarifa = tarifa;
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.concesionario = concesionario;
        this.precioAdultoMayor = precioAdultoMayor;
        this.isFraccionamiento = isFraccionamiento;
        this.isRamel = isRamel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Imagen> getListaDeImagenes() {
        return listaDeImagenes;
    }

    public void setListaDeImagenes(JSONArray listaDeImagenes2) {
        try {
            for (int i = 0; i < listaDeImagenes2.length(); i++) {
                JSONObject imagen_bus = null;
                imagen_bus = listaDeImagenes2.getJSONObject(i);
                listaDeImagenes.add(
                        new Imagen(imagen_bus.getString("RUTA"), imagen_bus.getString("RUTA_EN_SERVIDOR"), imagen_bus.getString("LUGAR"))
                );
            }
        } catch (JSONException e) {
                e.printStackTrace();
         }

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
