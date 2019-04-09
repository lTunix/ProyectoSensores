package com.blogspot.estamoscercadelfututro.proyectosensores;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

import static com.blogspot.estamoscercadelfututro.proyectosensores.Localizacion.tb_1min;

public class Localizacion extends Activity implements LocationListener {

    TextView txt_latitud, txt_logitud, txt_altura, txt_aviso;
    LocationManager locman;
    AlertDialog alert = null;
    int estado = 0;
    public static int tiempo;
    Timer timer = new Timer();
    public static boolean valor;
    CoordenadasSqLiteOpenHelper db = new CoordenadasSqLiteOpenHelper(this);
    public static ToggleButton tb_5seg;
    public static ToggleButton tb_10seg;
    public static ToggleButton tb_30seg;
    public static ToggleButton tb_1min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);
        txt_aviso = (TextView) findViewById(R.id.txt_aviso);
        txt_latitud = (TextView) findViewById(R.id.txt_latitud);
        txt_logitud = (TextView) findViewById(R.id.txt_longitud);
        txt_altura = (TextView) findViewById(R.id.txt_altura);
        locman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Button btn_coordenadas = (Button) findViewById(R.id.btn_coordenadas);

        //DETECTAR SI EL GPS ESTÁ ACTIVO O NO
        if(!locman.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertaNoGps();
        }

        //VERIFICAR SI ESTÁN ACTIVOS LOS PERMISOS DE UBICACION EN LA APP
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);

        //Actualizar mensaje de aviso si las coordenadas fueron obtenidas
        if(txt_altura == null && txt_logitud == null && txt_latitud == null){
            txt_aviso.setText("Obteniendo información, por favor espere...");
        }else{
            txt_aviso.setText("Coordenadas obtenidas exitosamente!");
        }

        tb_5seg = (ToggleButton) findViewById(R.id.tb_5s);
        tb_10seg = (ToggleButton) findViewById(R.id.tb_10s);
        tb_30seg = (ToggleButton) findViewById(R.id.tb_30s);
        tb_1min = (ToggleButton) findViewById(R.id.tb_1m);

        //OnClick
        tb_5seg.setOnClickListener(new AdapterView.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tb_10seg.setChecked(false);
                tb_30seg.setChecked(false);
                tb_1min.setChecked(false);
                if(tb_5seg.isChecked())
                {
                    tiempo = 5000;
                    valor = true;
                    tiempoRegistro(tiempo);
                    Toast.makeText(getApplicationContext(),"Registrando Ubicación cada 5 Seg",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    valor = false;
                    Toast.makeText(getApplicationContext(),"Dejando de Registrar Ubicación",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tb_10seg.setOnClickListener(new AdapterView.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tb_5seg.setChecked(false);
                tb_30seg.setChecked(false);
                tb_1min.setChecked(false);
                if (tb_10seg.isChecked())
                {
                    tiempo = 10000;
                    valor = true;
                    tiempoRegistro(tiempo);
                    Toast.makeText(getApplicationContext(),"Registrando Ubicación cada 10 Seg",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    valor = false;
                    Toast.makeText(getApplicationContext(),"Dejando de Registrar Ubicación",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tb_30seg.setOnClickListener(new AdapterView.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tb_5seg.setChecked(false);
                tb_10seg.setChecked(false);
                tb_1min.setChecked(false);
                if (tb_30seg.isChecked())
                {
                    tiempo = 30000;
                    valor = true;
                    tiempoRegistro(tiempo);
                    Toast.makeText(getApplicationContext(),"Registrando Ubicación cada 30 Seg",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    valor = false;
                    Toast.makeText(getApplicationContext(),"Dejando de Registrar Ubicación",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tb_1min.setOnClickListener(new AdapterView.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tb_5seg.setChecked(false);
                tb_10seg.setChecked(false);
                tb_30seg.setChecked(false);
                if (tb_1min.isChecked())
                {
                    tiempo = 60000;
                    valor = true;
                    tiempoRegistro(tiempo);
                    Toast.makeText(getApplicationContext(),"Registrando Ubicación cada 1 Min",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    valor = false;
                    Toast.makeText(getApplicationContext(),"Dejando de Registrar Ubicación",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_coordenadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Localizacion.this, ListarCoordenadas.class);
                startActivity(intent);
            }
        });

    }

    public void RegistrarCoordenadas(){
        try{
            txt_latitud = (TextView) findViewById(R.id.txt_latitud);
            txt_logitud = (TextView) findViewById(R.id.txt_longitud);
            txt_altura = (TextView) findViewById(R.id.txt_altura);
            if(txt_latitud.getText().toString().isEmpty() == true && txt_logitud.getText().toString().isEmpty() == true &&
                    txt_altura.getText().toString().isEmpty() == true){
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Espere a que se obtengan las coordenadas", Toast.LENGTH_SHORT);
                toast1.show();
            }else{
                String latitud = txt_latitud.getText().toString();
                String longitud = txt_logitud.getText().toString();
                String altura = txt_altura.getText().toString();

                CoordenadasSqLiteOpenHelper db = new CoordenadasSqLiteOpenHelper(this);
                db.RegistrarCoordenadas(latitud, longitud, altura);

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Registrando Coordenadas... " + latitud + longitud + altura, Toast.LENGTH_SHORT);
                toast1.show();
                estado = 1;
            }
        }catch (SQLException e){
            AlertDialog.Builder alert3 = new AlertDialog.Builder(this);
            alert3.setMessage("Error: " + e.toString()).setTitle("Aviso");
            AlertDialog dialog = alert3.create();
            dialog.show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        txt_latitud.setText("" + location.getLatitude());
        txt_logitud.setText("" + location.getLongitude());
        txt_altura.setText("" + location.getAltitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    private void AlertaNoGps(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS está desactivado, ¿Desea activarlo?")
                .setCancelable(false).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    public void tiempoRegistro(int tiempo) {
        final Handler handler = new Handler();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                if (Localizacion.valor == true) {
                    handler.post(new Runnable() {
                        public void run() {
                            RegistrarCoordenadas();
                        }
                    });
                } else {
                    timer.cancel();//Finalizar Hilo
                }
            }
        };

        timer = new Timer();
        timer.schedule(tarea, tiempo, tiempo);//Crea Hilo
    }




}


