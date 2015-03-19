package com.example.maynorasonglara.buses;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maynorasonglara.buses.controlador.BusControlador;
import com.example.maynorasonglara.buses.controlador.ServiceHandler;
import com.example.maynorasonglara.buses.datos.Bus;


public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    BusControlador buscontrolador=new BusControlador();
    private SearchView mSearchView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_main);

        // Server Request URL
        String serverURL =BusControlador.BAJAR_RUTAS;// "http://androidexample.com/media/webservice/getPage.php";//

        // Create Object and call AsyncTask execute Method
        new LongOperation().execute(serverURL);



        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ArrayAdapter<Bus>(this,
                android.R.layout.simple_list_item_1,
                buscontrolador.getListabuses()));
        mListView.setTextFilterEnabled(true);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {


                Bus bus=(Bus) parent.getAdapter().getItem(position);
                abrirBusDetallado(bus);
            }
        });

    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Ruta");
    }


    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }



    // Class with extends AsyncTask class
    private class LongOperation  extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);

        //TextView uiUpdate = (TextView) findViewById(R.id.textView);

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
           // uiUpdate.setText("Output : ");
            Dialog.setMessage("Descargando Rutas..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by GET method
                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);

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

            Dialog.dismiss();

            if (Error != null) {

               // uiUpdate.setText("Output : "+Error);

            } else {
              //  Content=Content.split("<!--")[0];
              //  uiUpdate.setText("Output : "+Content);
               // refrescarPantalla();
                buscontrolador.agregarBuses(Content);
                setupSearchView();

            }
        }

    }

    private void abrirBusDetallado(Bus bus) {

        Intent intent = new Intent(this, DetalleBus.class);
        intent.putExtra("nombre",bus.getNombre());
        intent.putExtra("tarifa",bus.getTarifa()+"");
        intent.putExtra("tiempo",bus.getTiempo());
        intent.putExtra("concesionario",bus.getConcesionario());
        startActivity(intent);
    }


   /* private void refrescarPantalla() {
        ArrayAdapter<Bus> adaptador= new ArrayAdapter<Bus>(this,android.R.layout.simple_list_item_1,buscontrolador.getListabuses());
        setListAdapter(adaptador);
    }*/




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
