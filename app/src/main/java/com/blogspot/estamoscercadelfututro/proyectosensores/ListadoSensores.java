package com.blogspot.estamoscercadelfututro.proyectosensores;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class ListadoSensores extends AppCompatActivity {

    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_sensores);

        salida = (TextView) findViewById(R.id.salida);


        //Obtener listado de sensores.
        SensorManager sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);

        List<Sensor> listaSensores = sensorManager.
                getSensorList(Sensor.TYPE_ALL);

        for(Sensor sensor: listaSensores) {
            log("Nombre del sensor: " + sensor.getName() + " \n \n Detalles: " + sensor.toString());
        }


    }

    private void log(String string) {
        salida.append(string + "\n" + "------------------------------------- \n");
    }
}
