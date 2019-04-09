package com.blogspot.estamoscercadelfututro.proyectosensores;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LeerSensores extends Activity implements SensorEventListener, AdapterView.OnItemSelectedListener {

    //Importar SensorManager
    private SensorManager sm = null;
    //Se importan los elementos de la interfaz.
    private Spinner spin = null;
    private TextView name = null;
    private TextView vendor = null;
    private TextView type = null;
    private GridView results = null;
    //Listado de Sensores del dispositivo.
    private List<Sensor> sensorsList;
    private int selected = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_sensores);
        //llamar al SensorManager
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Obtener elementos de la interfaz.
        spin = (Spinner) findViewById(R.id.sensorsList);
        name = (TextView) findViewById(R.id.nameValue);
        type = (TextView) findViewById(R.id.typeValue);
        vendor = (TextView) findViewById(R.id.vendorValue);
        results = (GridView) findViewById(R.id.sensorValues);
        //Settear el Listener del Spinner
        spin.setOnItemSelectedListener(this);
        //Rellenar el Spinner
        sensorsList = sm.getSensorList(Sensor.TYPE_ALL);
        ArrayList<String> als = new ArrayList<String>();
        for(Sensor s : sensorsList)
        {
            als.add(s.getType() + " : " + s.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, als);
        spin.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        sm.unregisterListener(this);
        // Seleccionar Item
        Sensor sensor = sensorsList.get(pos);
        selected = pos;
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        // Escribir detalles del sensor.
        name.setText(sensor.getName());
        vendor.setText(sensor.getVendor());
        type.setText(String.valueOf(sensor.getType()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void onStop()
    {
        sm.unregisterListener(this);
        super.onStop();
    }

    protected void onPause()
    {
        super.onPause();
        sm.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event)
    {
        String []vals = new String[event.values.length];
        for(int j=0;j<vals.length;j++)
        {
            vals[j] = j + " : " + event.values[j];
        }
        // Escribir datos del sensor.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vals);
        results.setAdapter(adapter);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
