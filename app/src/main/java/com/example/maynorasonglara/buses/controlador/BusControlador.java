package com.example.maynorasonglara.buses.controlador;

import android.util.Log;
import android.widget.Toast;

import com.example.maynorasonglara.buses.datos.Bus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maynor.a.song.lara on 2/12/2015.
 */

public class BusControlador {

    /**********************************
     *  CONEXION CON EL SERVIDOR
     *
     * *///////////////////////////
       public static String BAJAR_RUTAS=  "http://androidcr.webuda.com/obtenerBuses.php";//"http://200.122.164.173:81/rutas/rutas.html?method=getRoutesData";//
       public static String SUBIR_FEEDBACK=  "http://androidcr.webuda.com/enviarfeedback.php";
       public static String BAJAR_PATH_DE_IMAGENES=  "http://androidcr.webuda.com/getImagenesPorRuta.php";
       public static String SUBIR_PATH_DE_IMAGENES=  "http://androidcr.webuda.com/insertarImagen.php";

     /**////////////////////////////////////////


    public  List<Bus> listabuses=new ArrayList();



    public List<Bus> getListabuses() {
        return listabuses;
    }

    public void setListabuses(ArrayList listabuses) {
        this.listabuses = listabuses;
    }

    public void agregarBuses(String content) {

        content=content.split("<!--")[0]; // removiendo spam que viene con el JSON

        try{
            JSONArray buses=new JSONArray(content);
            for(int i=0;i< buses.length();i++){
                JSONObject bus= buses.getJSONObject(i);

                listabuses.add(
                       new Bus(Float.parseFloat(bus.get("PRECIO")+""),Integer.parseInt(bus.get("ID")+""),bus.getString("NAME"),
                       bus.getString("TIEMPO_ESTIMADO"),bus.getString("CONSESIONARIO"),bus.getString("PRECIO_ORO"),bus.getInt("ISFRACCIONAMIENTO"),bus.getInt("ISRAMEL"))
                );
            }
        }catch (Exception  e){
            Log.e("My App", "Could not parse malformed JSON: \"" + e.getMessage() + "\"");
        }

    }

    public Bus agregarImagenABus(String id_bus,String json_imgs){
        int size=listabuses.size();
        int bus_id= Integer.parseInt(id_bus);
        for(int i=0;i<size;i++ ){
            if(bus_id == listabuses.get(i).getId()){
                try {
                    listabuses.get(i).setListaDeImagenes(new JSONArray(json_imgs));
                    return listabuses.get(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Bus getBusPorId(int id){
        int size=listabuses.size();
        for(int i=0;i<size;i++ ){
            if(id == listabuses.get(i).getId()){
                    return listabuses.get(i);
            }
        }
        return null;
    }
}
