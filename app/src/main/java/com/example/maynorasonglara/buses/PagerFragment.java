package com.example.maynorasonglara.buses;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maynorasonglara.buses.R;
import com.example.maynorasonglara.buses.datos.Bus;
import com.example.maynorasonglara.buses.datos.Imagen;

import java.net.URL;

public class PagerFragment extends Fragment {

    // Store instance variables
    private String descripcion,ruta_en_servidor,lugar;
    private DownloadImageTask task;
    private boolean hayImagen;

    // newInstance constructor for creating fragment with arguments
    public static PagerFragment newInstance(Imagen imagen,Bus miBus) {
        PagerFragment fragmentFirst = new PagerFragment();
        Bundle args = new Bundle();

        //Info de la IMG que va en el fragment actual
        args.putString("ruta_en_servidor", imagen.getRutaEnServidor());
        args.putString("descripcion", imagen.getDetalle());
        args.putString("lugar", imagen.getLugar());

        // Enviar info del bus, que se va actualizar en cada fragment per o va ser la misma
        // lo se, no es eficiente pero es la unica manera que he encontrado para hacerlo :(
        args.putString(Bus.NOMBRE_BUS,miBus.getNombre());
        args.putString(Bus.TIEMPO_BUS,miBus.getTiempo() );
        args.putString(Bus.TARIFA_BUS,miBus.getTarifa()+"" );
        args.putString(Bus.CONCESIONARIO_BUS,miBus.getConcesionario());

        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        descripcion = getArguments().getString("descripcion");
        ruta_en_servidor = getArguments().getString("ruta_en_servidor");
        lugar = getArguments().getString("lugar");

        TextView nombre = (TextView) getView().findViewById(R.id.nombre);
        TextView tarifa = (TextView) getView().findViewById(R.id.tarifa);
        TextView tiempo = (TextView) getView().findViewById(R.id.tiempo);
        TextView concesionario = (TextView) getView().findViewById(R.id.concesionario);

        nombre.setText(getArguments().getString(Bus.NOMBRE_BUS));
        tiempo.setText(getArguments().getString(Bus.TIEMPO_BUS));
        tarifa.setText(getArguments().getString(Bus.TARIFA_BUS));
        concesionario.setText(getArguments().getString(Bus.CONCESIONARIO_BUS));
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentview, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.detalle_imagen);
        tvLabel.setText(lugar + " -- " + descripcion);
        task = (DownloadImageTask) new DownloadImageTask().execute(ruta_en_servidor);
        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                mIcon11 = BitmapFactory.decodeStream(new URL(urldisplay).openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            ImageView img = (ImageView) getView().findViewById(R.id.imagenbus);
            img.setImageBitmap(result);
        }
    }
}