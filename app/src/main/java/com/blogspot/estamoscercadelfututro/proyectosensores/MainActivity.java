package com.blogspot.estamoscercadelfututro.proyectosensores;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CoordenadasSqLiteOpenHelper db = new CoordenadasSqLiteOpenHelper(this);
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        //Salir de la aplicación.
        final Button botonSalir = (Button)findViewById(R.id.salirapp);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checkPermission();


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] letra = {"Listar Sensores del Dispositivo","Leer Sensores del Smartphone","Detectar Coordenadas",
                "Ir a la Brújula"};
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item_new, letra));


        final Button botonEntrar = (Button)findViewById(R.id.btn_entrar);
        botonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItem().toString().equals("Listar Sensores del Dispositivo")){
                    //Entrar a listar los sensores
                    Intent intent =  new Intent(MainActivity.this, ListadoSensores.class);
                    startActivity(intent);
                }else if(spinner.getSelectedItem().toString().equals("Leer Sensores del Smartphone")){
                    //Entrar a leer los sensores
                    Intent intent =  new Intent(MainActivity.this, LeerSensores.class);
                    startActivity(intent);
                }else if(spinner.getSelectedItem().toString().equals("Detectar Coordenadas")){
                    //Llama al método que borra la base de datos
                    db.deleteAll();
                    //Entrar a detectar las coordenadas
                    Intent intent = new Intent(MainActivity.this, Localizacion.class);
                    startActivity(intent);
                }else if(spinner.getSelectedItem().toString().equals("Ir a la Brújula")){
                    //Entrar a la app de brujula
                    Intent intent =  new Intent(MainActivity.this, Compass.class);
                    startActivity(intent);
                }
            }
        });

    }
    public void checkPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "This version is not Android 6 or later " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);

                Toast.makeText(this, "Requesting permissions", Toast.LENGTH_LONG).show();

            }else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this, "The permissions are already granted ", Toast.LENGTH_LONG).show();


            }

        }

        return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "OK Permissions granted ! 🙂 " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Permissions are not granted ! 🙁 " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
