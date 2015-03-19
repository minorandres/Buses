package com.example.maynorasonglara.buses;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DetalleBus extends FragmentActivity {
    private DownloadImageTask task;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private static final int NUM_PAGES = 5;


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
            //ImageView img = (ImageView) findViewById(R.id.imagenbus);
            //img.setImageBitmap(result);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_bus);

        /*TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView tarifa = (TextView) findViewById(R.id.tarifa);
        TextView tiempo = (TextView) findViewById(R.id.tiempo);
        TextView concesionario = (TextView) findViewById(R.id.concesionario);

        nombre.setText(getIntent().getStringExtra("nombre"));
        tiempo.setText(getIntent().getStringExtra("tiempo"));
        tarifa.setText(getIntent().getStringExtra("tarifa"));
        concesionario.setText(getIntent().getStringExtra("concesionario"));*/


      /*  int res= getResources().getIdentifier(getIntent().getStringExtra("imagen"),"drawable",this.getPackageName());
        ImageView imagen= (ImageView) findViewById(R.id.imagenbus);
        imagen.setImageResource(res);*/

        task = (DownloadImageTask) new DownloadImageTask().execute("http://parkcinema.az/uploads/structures/movies/images/xickok_poster1_resized.jpg");//

// Instantiate a ViewPager and a PagerAdapter.
      /*  mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(DetalleBus.this,getIntent().getStringExtra("id"));
        mPager.setAdapter(mPagerAdapter);*/


    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends PagerAdapter {

        Context context;
        String busid;
        LayoutInflater inflater;

        public ScreenSlidePagerAdapter(Context context, String busid) {
            this.context = context;
            this.busid = busid;
        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }



        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

// Declare Variables
            TextView txtrank;
            TextView txtcountry;
            TextView txtpopulation;
            ImageView imgflag;

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.fragment_screen_slide_page, container,
                    false);

// Locate the TextViews in viewpager_item.xml
            txtrank = (TextView) itemView.findViewById(R.id.rank);
            txtcountry = (TextView) itemView.findViewById(R.id.country);
            txtpopulation = (TextView) itemView.findViewById(R.id.population);

// Capture position and set to the TextViews
            txtrank.setText("texto del rank");
            txtcountry.setText("texto del pais");
            txtpopulation.setText("texto de poblacion");

// Locate the ImageView in viewpager_item.xml
           // imgflag = (ImageView) itemView.findViewById(R.id.flag);
// Capture position and set to the ImageView
            //imgflag.setImageResource(flag[position]);

// Add viewpager_item.xml to ViewPager
            ((ViewPager) container).addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
// Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((RelativeLayout) object);

        }
    }


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




}
