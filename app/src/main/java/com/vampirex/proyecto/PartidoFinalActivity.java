package com.vampirex.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PartidoFinalActivity extends Activity {
    private long idJugador;
    private long idEstadistica;
    private Cursor cursorJugador;
    private Cursor cursorEstadistica;
    private Cursor cursorPartido;
    private Cursor cursorEquipoLocal;
    private Cursor getCursorEquipoVisitante;
    private TextView txequipoLocal;
    private TextView txequipoVisitante;
    private TextView txparte1;
    private TextView txparte2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido_final);

        txequipoLocal = (TextView) findViewById(R.id.tx_equipolocal);
        txequipoVisitante = (TextView) findViewById(R.id.tx_equipoVisitante);
        txparte1 = (TextView) findViewById(R.id.tx_textoparte1);
        txparte2 = (TextView) findViewById(R.id.tx_textoparte2);

        // se recogen los extras
        if (getIntent().getExtras() != null) {
            idJugador = getIntent().getExtras().getLong("idJugador");
            idEstadistica = getIntent().getExtras().getLong("idEstadistica");
        } else {
            Toast.makeText(this, "Error al cargar el jugador", Toast.LENGTH_LONG).show();
        }

        // se cargan todos los cursores
        cursorJugador = Jugador.getJugadorPorId(idJugador);
        if (cursorJugador.getCount() != 0)
            cursorJugador.moveToFirst();


        cursorEstadistica = Estadistica.getEstadisticaPorId(idEstadistica);
        if (cursorEstadistica.getCount() != 0)
            cursorEstadistica.moveToFirst();


        cursorPartido = Partido.getAllCursor();
        if (cursorPartido.getCount() != 0)
            cursorPartido.moveToFirst();

        cursorEquipoLocal = Equipo.getEquipoPorId(cursorPartido.getLong(1));
        if (cursorEquipoLocal.getCount() != 0)
            cursorEquipoLocal.moveToFirst();

        getCursorEquipoVisitante = Equipo.getEquipoPorId(cursorPartido.getLong(2));
        if (getCursorEquipoVisitante.getCount() != 0)
            getCursorEquipoVisitante.moveToFirst();

        // se ponen los valores de las etiquetas
        txequipoLocal.setText(cursorEquipoLocal.getString(1));
        txequipoVisitante.setText(getCursorEquipoVisitante.getString(1));

        // carga de datos finales
        ponerDatosFinales();

    }

    private void ponerDatosFinales() {
        // dependiendo de si marcamos 0 goles, 1 gol o 2 goles, habra un final u otro
        switch (generarResultado()){
            case 0:
                txparte1.setText(R.string.final_0_goles_parte1);
                txparte2.setText(R.string.final_0_goles_parte2);
                break;
            case 1:
                txparte1.setText(R.string.final_1_gol_parte1);
                txparte2.setText(R.string.final_1_gol_parte2);
                break;
            case 2:
                txparte1.setText(R.string.final_2_goles_parte1);
                txparte2.setText(R.string.final_2_goles_parte2);
                break;
        }

    }

    private int generarResultado() {
        // metodo chorra que genera el radon de los goles teniendo en cuenta la media de las estadisitcas
        Random r = new Random();
        int numero = 0;
        int estadistica1 = cursorEstadistica.getInt(1);
        int estadistica2 = cursorEstadistica.getInt(2);
        int estadistica3 = cursorEstadistica.getInt(3);
        int estadistica4 = cursorEstadistica.getInt(4);
        
        int suma = estadistica1+estadistica2+estadistica3+estadistica4;
        int media= suma/4;

        if (estadistica1==150){
            numero=2;
        }else {

            if (media >= 60) {
                numero = r.nextInt(3);
            } else if (media >= 40 && media < 50) {
                numero = r.nextInt(2);
            } else if (media < 40) {
                numero = 0;
            }
        }
        return numero;
    }

    public void clickBotonFinal(View view) {
        // limpia las tablas de la BD
        Jugador.borrarJugadorPorId(cursorJugador.getLong(0));
        Estadistica.borrarEstadisticaPorId(cursorEstadistica.getLong(0));
        Partido.borrarPartidoPorId(cursorPartido.getLong(0));
        // lanza la actividad main para empezar de nuevo
        Intent intent = new Intent(PartidoFinalActivity.this, MainActivity.class);
        startActivity(intent);

    }

    // impedimos que regrese a la actividad donde se desarrolla el juego
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PartidoFinalActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
