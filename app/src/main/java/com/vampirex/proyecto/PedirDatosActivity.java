package com.vampirex.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

public class PedirDatosActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private EditText etNombreJugador;
    private Spinner spEquipos;
    private Long idEquipoSeleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_datos);

        etNombreJugador = (EditText) findViewById(R.id.etNombrJugador);
        spEquipos = (Spinner) findViewById(R.id.sp_equipos);
        spEquipos.setOnItemSelectedListener(this);
        //carga de equipos
        cargarEquipos();

    }

    private void cargarEquipos() {
        // cargamos los equipos
        Cursor c = Equipo.getAllCursor();
        String[] from = new String[]{"nombre"};
        int[] to = new int[]{R.id.tvItemNombreEquipo};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.item_equipo, c, from, to, 0);
        spEquipos.setAdapter(sca);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        // cada vez que cambia el equipo guardamos el id del mismo en un field
        idEquipoSeleccion = id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void clickBotonSiguienePedirDatos(View view) {
        Random r = new Random();

        if (etNombreJugador.getText().toString().length() == 0) {
            Toast.makeText(this, "Introduce el nombre Del jugador", Toast.LENGTH_LONG).show();

        } else {
            // se crea el partido en la BD

            Partido p = new Partido(idEquipoSeleccion, equipoAleatorio());
            p.guardar();

            // se crea el jugador en la BD
            Jugador j = new Jugador(etNombreJugador.getText().toString(), "Delantero Centro", r.nextInt(11), 0, idEquipoSeleccion);
            j.guardar();

            Cursor cursorJugador = Jugador.getAllCursor();
            cursorJugador.moveToFirst();

            // Se crean las estadisticas y se asocian al jugador
            Estadistica e = new Estadistica(50, 50, 50, 50, cursorJugador.getLong(0));
            e.guardar();

            Cursor cursorEstadistica = Estadistica.getAllCursor();
            cursorEstadistica.moveToFirst();

            // se lanza la actividad donde se desarrolla el juego
            Intent intent = new Intent(PedirDatosActivity.this, PantallaInicial.class);
            intent.putExtra("idJugador", cursorJugador.getLong(0));
            intent.putExtra("idEstadistica", cursorEstadistica.getLong(0));
            startActivity(intent);
        }
    }

    private long equipoAleatorio() {
        // genera de forma aleatoria el equipo al que se enfrenta , al que pertenecemos nosotros
        Random r = new Random();
        int equipo = 0;
        boolean generacion = true;
        do {
            equipo = r.nextInt(21);
            if (equipo != idEquipoSeleccion && equipo < 21 && equipo > 0) {
                generacion=false;
                System.out.println(equipo);
            }
        }while (generacion);
        return (long)equipo;

    }
}
