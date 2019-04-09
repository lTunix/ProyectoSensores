package com.blogspot.estamoscercadelfututro.proyectosensores;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoordenadasSqLiteOpenHelper extends SQLiteOpenHelper {

    private static SQLiteDatabase BDC;

    public CoordenadasSqLiteOpenHelper(Context c) {
        super(c, "DBCOORDENADAS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //aquí creamos la tabla de coordenadas (latitud, longitud y altura)
        db.execSQL("create table coordenadas(id integer primary key autoincrement, latitud varchar(100), logitud varchar(100)," +
                " altura varchar(100))");

        //Datos de prueba
        //db.execSQL("insert into coordenadas(latitud, logitud, altura) values ('hola', '123', '000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simplest implementation is to drop all old tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS coordenadas" );
        onCreate(db);
    }

    //metodo que permite el ingreso de los datos a la tabla coordenadas
    public String RegistrarCoordenadas (String latitud, String logitud, String altura){
        try{
            BDC = getWritableDatabase();
            String sql = String.format("insert into coordenadas(latitud, logitud, altura) values('%s', '%s', '%s')",
                    latitud, logitud, altura);
            BDC.execSQL(sql);
            return null;
        }catch (SQLException e){
            return e.toString();
        }
    }

    public Cursor listarCoordenadas(){
        BDC = getReadableDatabase();
        return BDC.rawQuery("SELECT * FROM coordenadas", null);
    }

    //Borra la base de datos y crea una nueva.
    //Este metodo es llamado en Main.
    public void deleteAll(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS coordenadas" );
        onCreate(db);
    }
}
