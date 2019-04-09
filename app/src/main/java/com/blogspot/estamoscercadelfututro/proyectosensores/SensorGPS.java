package com.blogspot.estamoscercadelfututro.proyectosensores;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class SensorGPS implements LocationListener
{
    public double latitud;
    public double longitud;
    public double altitud;
    public LocationManager locMan;

    public SensorGPS(Activity a)
    {
        locMan = (LocationManager)a.getSystemService(Context.LOCATION_SERVICE);
        if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER) != true)
        {
            AlertDialog.Builder alert1 = new AlertDialog.Builder(a);
            alert1.setMessage("Por favor active el GPS.").setTitle("Alerta");
            AlertDialog dialog = alert1.create();
            dialog.show();
        }

        if (ActivityCompat.checkSelfPermission(a, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(a, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            AlertDialog.Builder alert1 = new AlertDialog.Builder(a);
            alert1.setMessage("Tiene que otorgar permisos de acceso a la ubicación").setTitle("Alerta");
            AlertDialog dialog = alert1.create();
            dialog.show();
            return;
        }
        locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        latitud = location.getLatitude();
        longitud = location.getLongitude();
        altitud = location.getAltitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {

    }

    @Override
    public void onProviderEnabled(String s)
    {

    }

    @Override
    public void onProviderDisabled(String s)
    {

    }
}
