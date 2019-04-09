package com.blogspot.estamoscercadelfututro.proyectosensores;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;


public class ListarCoordenadas extends FragmentActivity implements OnMapReadyCallback{

    ListView lv;
    LinkedList<String> coordenadas;
    MarkerOptions options;

    //Atributos para la ubicacion en Google Maps
    CoordenadasSqLiteOpenHelper bd;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_coordenadas);
        lv = (ListView) findViewById(R.id.ListarCoordenadas);
        listaCoordenadas(getCurrentFocus());


        //Crea objeto que llama al fragment del maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //Metodo que lista las coordenadas obtenidas de la base de datos
    public void listaCoordenadas(View view){
        bd = new CoordenadasSqLiteOpenHelper(this);
        coordenadas = new LinkedList<>();

        //Cursor(ResultSet)
        Cursor c = bd.listarCoordenadas();
        while(c.moveToNext())
        {
            coordenadas.add(c.getInt(c.getColumnIndex("id")) + ") Latitud: " +
                    c.getString(c.getColumnIndex("latitud")) + "°\n     Longitud: " +
                    c.getString(c.getColumnIndex("logitud")) + "°\n     Altura: " +
                    c.getString(c.getColumnIndex("altura")));
        }
        c.close();

        //Arreglo de lista
        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,coordenadas);
        lv.setAdapter(aa1);
    }

    //Método que marca las coordenadas en obtenidas en el mapa
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*//Datos de prueba
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        bd = new CoordenadasSqLiteOpenHelper(this);

        //Cursor(ResultSet)
        //Permite obtener todas las coordenadas registradas porla app y las muestra en el mapa
        Cursor c = bd.listarCoordenadas();
        int i = 1;
        while(c.moveToNext()){

            double lat = Float.valueOf(c.getString(c.getColumnIndex("latitud")));
            double longit = Float.valueOf(c.getString(c.getColumnIndex("logitud")));
            LatLng coor = new LatLng(lat, longit);

            int numero = (int)(Math.random()*10);
            if(numero == 1)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
            if(numero == 2)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
            if(numero == 3)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
            if(numero == 4)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
            if(numero == 5)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
            if(numero == 6)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
            if(numero == 7)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
            if(numero == 8)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
            if(numero == 9)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
            if(numero == 10)
            {
                googleMap.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).title("Ubicación: "+c.getInt(c.getColumnIndex("id"))).snippet("Latitud: "+ lat +" Longitud: "+ longit));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            }
        }
        c.close();
    }
}
