package com.example.maynorasonglara.buses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maynorasonglara.buses.controlador.BusControlador;
import com.example.maynorasonglara.buses.datos.Bus;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DetalleBus extends FragmentActivity {
    private DownloadImageTask task;
    private String packagename;



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
            ImageView img = (ImageView) findViewById(R.id.imagenbus);
            img.setImageBitmap(result);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_bus);

        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView tarifa = (TextView) findViewById(R.id.tarifa);
        TextView tiempo = (TextView) findViewById(R.id.tiempo);
        TextView concesionario = (TextView) findViewById(R.id.concesionario);

        nombre.setText(getIntent().getStringExtra(Bus.NOMBRE_BUS));
        tiempo.setText(getIntent().getStringExtra(Bus.TIEMPO_BUS));
        tarifa.setText(getIntent().getStringExtra(Bus.TARIFA_BUS));
        concesionario.setText(getIntent().getStringExtra(Bus.CONCESIONARIO_BUS));
        packagename=getApplicationContext().getPackageName();

        /*int res= getResources().getIdentifier(getIntent().getStringExtra("imagen"),"drawable",this.getPackageName());
        ImageView imagen= (ImageView) findViewById(R.id.imagenbus);
        imagen.setImageResource(res);*/

        new LongOperation().execute("");
        addClickParaImagenes();


    }

    private void addClickParaImagenes() {
        LinearLayout imagenes= (LinearLayout) findViewById(R.id.layout_imagenes);
        imagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagenes();
            }
        });

        ImageView imagen= (ImageView) findViewById(R.id.imagenbus);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagenes();
            }
        });

        TextView textoimagen= (TextView) findViewById(R.id.ver_imagenes);
        textoimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagenes();
            }
        });
    }

    private void abrirImagenes() {
        Intent intent = new Intent(this, ScreenSlidePagerActivity.class);
        intent.putExtra(Bus.ID_BUS, getIntent().getStringExtra(Bus.ID_BUS));
        startActivity(intent);
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_bus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.calificar) {
            Intent calificar = new Intent(this, CalificarActivity.class);
            calificar.putExtra("ruta", getIntent().getStringExtra("nombre"));
            startActivity(calificar);
        }

        return super.onOptionsItemSelected(item);
    }



    private class LongOperation  extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {
                HttpPost httppost = new HttpPost(BusControlador.BAJAR_PATH_DE_IMAGENES);
                try{
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("RUTA",getIntent().getStringExtra(Bus.ID_BUS) ));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                }catch (Exception e){ }
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httppost, responseHandler);
            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }
            return null;
        }

        protected void onPostExecute(Void unused) {
            if (Error != null) {
                // uiUpdate.setText("Output : "+Error);
            } else {
                Content=Content.split("<!--")[0];
                if(Content.contains("null")){
                    int res= getResources().getIdentifier("busicon","drawable",packagename);
                    ImageView imagen= (ImageView) findViewById(R.id.imagenbus);
                    imagen.setImageResource(res);
                }else{
                    Bus bus=MainActivity.buscontrolador.agregarImagenABus(getIntent().getStringExtra(Bus.ID_BUS),Content);
                    task = (DownloadImageTask) new DownloadImageTask().execute(bus.getListaDeImagenes().get(0).getRutaEnServidor());//
                }
            }
        }

    }


}
