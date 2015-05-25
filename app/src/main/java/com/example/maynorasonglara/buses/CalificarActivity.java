package com.example.maynorasonglara.buses;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.maynorasonglara.buses.controlador.BusControlador;
import com.example.maynorasonglara.buses.controlador.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class CalificarActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calificar_servicio);

        Button enviar= (Button) findViewById(R.id.enviar);

        enviar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                enviarCalificacion();
            }
        });
    }

    private void enviarCalificacion() {
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        TextView txtRatingValue = (TextView) findViewById(R.id.feedback);


        //ENVIANDO A SERVIDOR
        String URL_NEW_PREDICTION = BusControlador.SUBIR_FEEDBACK;
        ServiceHandler serviceClient = new ServiceHandler();

        List<NameValuePair> params = new ArrayList<NameValuePair>();


        params.add(new BasicNameValuePair("calificacion", ratingBar.getRating()+""));
        params.add(new BasicNameValuePair("feedback", txtRatingValue.getText()+""));
        params.add(new BasicNameValuePair("ruta", getIntent().getStringExtra("ruta")));


        String json = serviceClient.makeServiceCall(URL_NEW_PREDICTION,
                ServiceHandler.POST, params);

        //FINAL DE ENVIO , AHORA INFORMANDO A USUARIO


        // Create custom dialog object
        final Dialog dialog = new Dialog(CalificarActivity.this);
        // Include dialog.xml file
       /* dialog.setContentView(R.layout.dialog);
        // Set dialog title
        dialog.setTitle("Gracias por su Opinion");

        // set values for custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText("La opinion ha sido enviada con exito");
        ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
        image.setImageResource(R.drawable.bus2);

        dialog.show();

        Button declineButton = (Button) dialog.findViewById(R.id.declineButton);      // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
                finish();
            }
        });
*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calificar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
