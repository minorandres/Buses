package com.example.maynorasonglara.buses;

/**
 * Created by maynor.a.song.lara on 3/23/2015.
 */
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.maynorasonglara.buses.controlador.BusControlador;
import com.example.maynorasonglara.buses.datos.Bus;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScreenSlidePagerActivity extends FragmentActivity {
    private static int NUM_ITEMS;
    FragmentPagerAdapter adapterViewPager;
    ViewPager vpPager;
    private static Bus miBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        miBus=MainActivity.buscontrolador.getBusPorId(
                Integer.parseInt(getIntent().getStringExtra(Bus.ID_BUS))
        );

        //new LongOperation().execute("");
        bajarImagenes();
        setContentView(R.layout.actividad_imagenes);

        setNumeroDeImagenes();

        vpPager = (ViewPager) findViewById(R.id.pager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ScreenSlidePagerActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        }
    );
    }

    private void bajarImagenes() {
        String Content="";
        final HttpClient Client = new DefaultHttpClient();
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
            e.getMessage();

        } catch (IOException e) {
            e.getMessage();
        }

        Content= Content.split("<!--")[0];
        if(Content.contains("null")){ // si no hay niguna imagen poner una por default
            int res= getResources().getIdentifier("busicon","drawable",getPackageName());
            ImageView imagen= (ImageView) findViewById(R.id.imagenbus);
            imagen.setImageResource(res);
        }else{
            Bus bus=MainActivity.buscontrolador.agregarImagenABus(getIntent().getStringExtra(Bus.ID_BUS),Content);
            // task = (DownloadImageTask) new DownloadImageTask().execute(bus.getListaDeImagenes().get(0).getRutaEnServidor());//
        }
    }

    private void setNumeroDeImagenes() {
        int id= Integer.parseInt(getIntent().getStringExtra(Bus.ID_BUS));
        Bus mi_bus =MainActivity.buscontrolador.getBusPorId(id);
        NUM_ITEMS=mi_bus.getListaDeImagenes().size();
    }

    public void back(View view) {

        vpPager.setCurrentItem(vpPager.getCurrentItem() - 1);
    }

    public void next(View view) {
        vpPager.setCurrentItem(vpPager.getCurrentItem() + 1);
    }



    public static class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {


            return PagerFragment.newInstance(miBus.getListaDeImagenes().get(position),miBus);
            /*
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return PagerFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return PagerFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return PagerFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }*/
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}

/**
 * The number of pages (wizard steps) to show in this demo.
 */
//private static final int NUM_PAGES = 5;

/**
 * The pager widget, which handles animation and allows swiping horizontally to access previous
 * and next wizard steps.
 */
//private ViewPager mPager;

/**
 * The pager adapter, which provides the pages to the view pager widget.
 */
//private PagerAdapter mPagerAdapter;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_imagenes);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        String ruta= this.getIntent().getStringExtra(Bus.NOMBRE_BUS);
    }

    public void getListaDeImagenes(){

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

    public void back(View view) {
        mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }

    public void next(View view) {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     *//*
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new PagerFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public Fragment getItem(int position){
        return  PagerFragment.newInstance(position,getIntent().getStringExtra(Bus.NOMBRE_BUS));
    }*/