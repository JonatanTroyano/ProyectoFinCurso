package com.vampirex.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private static SQLiteDatabase db;
    private SharedPreferences sp;
    private EditText etNombreJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // llamada a la base de datos local
        if (db == null) {
            db = new ClaseBaseDatosOpenHelper(this).getWritableDatabase();
        }



    }


    public void ClickbtJugar(View v) {
        // si ya existe un jugador es que hay una partida empezada se carga directamente la actividad de la partida
        Cursor c = Jugador.getJugadorPorId((long)1);
        if (c.getCount()==0) {
            // si no hay una partida se carga una actividad donde se piden los datos
            Intent intent = new Intent(MainActivity.this, PedirDatosActivity.class);
            startActivity(intent);
        }else{
            // cargar la actividad de la partida pasando en el extra lo necesario
            c.moveToFirst();
            Cursor ce = Estadistica.getEstadisticaPorId(1);
            // Mover el cursor a la primera posición.
            ce.moveToFirst();
            Intent intent = new Intent(MainActivity.this, PantallaInicial.class);
            intent.putExtra("idJugador", c.getLong(0));
            intent.putExtra("idEstadistica", ce.getLong(0));
            startActivity(intent);
        }

    }

    // metodo para iniciar sesión sin codificar por falta de tiempo

    public void ClickbtLogin(View v) {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.adTituloLogin);
        adb.setView(getLayoutInflater().inflate(R.layout.dialog_login, null));
        adb.setCancelable(false);
        adb.setPositiveButton(R.string.adBotonOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // codigo para hacer el login
                // demomento solo finaliza sin hacer nada

            }
        });
        adb.setNeutralButton(R.string.adBotonCancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        adb.show();


    }

    public void ClickbtSalir(View v) {
        // Cerrar la aplicación
        finish();
    }


    //Metodo que devuelve la variable de la BD local
    public static SQLiteDatabase getDB() {
        return db;
    }
}
