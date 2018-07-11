package com.vampirex.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PantallaInicial extends Activity {
    private long idJugador;
    private long idEstadistica;
    private TextView diaNumero;
    private TextView nombreJugador;
    private Cursor cursorJugador;
    private Cursor cursorEstadistica;
    private boolean mañana = false;
    private boolean tarde = false;
    private boolean noche = false;
    private int respuesta;
    private int dia;
    private TextView textoLargoDia;
    private Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);
        // las etiquetas que necesitaremos
        diaNumero = (TextView) findViewById(R.id.tx_DiaNumero);
        nombreJugador = (TextView) findViewById(R.id.tx_nombre_jugador);
        textoLargoDia = (TextView) findViewById(R.id.tx_textoLargoDia);

        // recojemos los extras
        if (getIntent().getExtras() != null) {
            idJugador = getIntent().getExtras().getLong("idJugador");
            idEstadistica = getIntent().getExtras().getLong("idEstadistica");
        } else {
            // tostada anunciando que fallaron los extras
            Toast.makeText(this, "Error al cargar el jugador", Toast.LENGTH_LONG).show();
        }

        // cargamos los datos del jugador
        cursorJugador = Jugador.getJugadorPorId(idJugador);
        if (cursorJugador.getCount() != 0)
            cursorJugador.moveToFirst();

        // cargamos los datos de las estadisticas
        cursorEstadistica = Estadistica.getEstadisticaPorId(idEstadistica);
        if (cursorEstadistica.getCount() != 0)
            cursorEstadistica.moveToFirst();

        // cargamos el dia
        dia = cursorJugador.getInt(4);
        // ponemos el dia y el nombre del jugador en las etiquetas correspondientes
        diaNumero.setText("" + dia);
        nombreJugador.setText(cursorJugador.getString(1));
        // booleano que controla el metodo que carga el texto del dia
        mañana = true;
        // si el dia es  0 se parte del inicio de lo contrario se parte del dia en el que se quedó
        // siempre se parte de la mañana
        if (dia == 0) {
            textoLargoDia.setText(R.string.texto_introduccion);

            dia++;
        }else{
            findViewById(R.id.bt_siguiente).performClick();
        }
    }

    public void clickBotonSiguiente(View view) {
        // cadena de if else que controla el metodo al que se llama
        // si ya han transcurrido los 10 dias, se llama a la actividad que nos mostrara el resultado del partido
        if (dia != 10) {
            if (mañana) {
                datosMañana();
            } else if (tarde) {
                preguntaFinMañana();
            } else if (noche) {
                preguntaFinTarde();
            }
        } else {
            Intent intent = new Intent(PantallaInicial.this, PartidoFinalActivity.class);
            intent.putExtra("idJugador", cursorJugador.getLong(0));
            intent.putExtra("idEstadistica", cursorEstadistica.getLong(0));
            startActivity(intent);
        }
    }

    private void datosMañana() {
        // carga del texto de mañana
        textoLargoDia.setText(R.string.texto_mañana);
        diaNumero.setText("" + dia);
        // cambio de los parametros booleanos para la carga de metodos
        mañana = false;
        tarde = true;
    }

    private void preguntaFinMañana() {
        // esta pregunta es siempre la misma si el jugador quiere entrenar o no
        final Cursor c = Pregunta.getPreguntaPorId(1);
        c.moveToFirst();
        // creamos el alertdialog
        AlertDialog.Builder adb = new AlertDialog.Builder(this,R.style.temaDialogFUNCIONAPLS);
        // ponemos el texto
        adb.setTitle(R.string.dialogEvento);
        // ponemos la pregunta que esta en el cursor
        adb.setMessage(c.getString(1));
        // no se puede cancelar
        adb.setCancelable(false);
        adb.setPositiveButton(R.string.adBotonSi, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // las respuestas siempre vienen en parejas de 2, la de posicion 0 es el SI y la de posicion 1 es el NO
                respuesta = 0;
                // usamos el id para conseguir la respuesta
                Cursor cursorRespuesta = Respuesta.getRespuestaPorIdpregunta(c.getLong(0));
                // movemos a la posicion del si
                cursorRespuesta.moveToPosition(respuesta);
                // usamos la respuesta para cargar el cursor de consecuencia
                Cursor cursorConsecuencia = Consecuencia.getConsecuenciaPorId(cursorRespuesta.getLong(3));
                cursorConsecuencia.moveToFirst();
                // conseguimos la estadistica que modifica esa consecuencia
                int estadistica = cursorEstadistica.getInt(cursorConsecuencia.getInt(3));
                // miramos si suma o resta
                if (cursorConsecuencia.getString(1).equalsIgnoreCase("suma")) {
                    estadistica += cursorConsecuencia.getInt(2);
                } else {
                    estadistica -= cursorConsecuencia.getInt(2);
                }
                // llamada al metodo que se encarga de hacer las modificaciones
                modificarEstadistica(cursorConsecuencia.getInt(3), estadistica);
                // guardamos el resultado en la bd
                cursorEstadistica = Estadistica.getEstadisticaPorId(cursorEstadistica.getLong(0));
                cursorEstadistica.moveToFirst();
                // siguiente metodo
                datosTarde();
            }
        });
        adb.setNeutralButton(R.string.adBotonNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                respuesta = 1;

                Cursor cursorRespuesta = Respuesta.getRespuestaPorIdpregunta(c.getLong(0));
                cursorRespuesta.moveToPosition(respuesta);
                Cursor cursorConsecuencia = Consecuencia.getConsecuenciaPorId(cursorRespuesta.getLong(3));
                cursorConsecuencia.moveToFirst();
                int estadistica = cursorEstadistica.getInt(cursorConsecuencia.getInt(3));
                if (cursorConsecuencia.getString(1).equalsIgnoreCase("suma")) {
                    estadistica += cursorConsecuencia.getInt(2);
                } else {
                    estadistica -= cursorConsecuencia.getInt(2);
                }
                modificarEstadistica(cursorConsecuencia.getInt(3), estadistica);
                cursorEstadistica = Estadistica.getEstadisticaPorId(cursorEstadistica.getLong(0));
                cursorEstadistica.moveToFirst();

                datosTarde();
            }
        });
        adb.show();

    }

    private void datosTarde() {
        // cambiamos los boolean necesarios
        tarde = false;
        noche = true;
        // aprobechamos la respuesta para cargar el mensaje que es adecuado
        if (respuesta == 0) {
            textoLargoDia.setText(R.string.texto_ir_entrenar);

        } else {
            textoLargoDia.setText(R.string.texto_no_ir_entrenar);
        }
    }

    private void preguntaFinTarde() {
        // la codificacion es la misma que la del metodo preguntaFinMañana pero la preugnta ahora es random
        // la pregunta random nunca puede ser la de id = 1 que es la de ir entrenar

        long nrandom = numerorandom();
        System.out.println("El numero random es: " + nrandom);
        final Cursor c = Pregunta.getPreguntaPorId(nrandom);
        c.moveToFirst();
        AlertDialog.Builder adb = new AlertDialog.Builder(this,R.style.temaDialogFUNCIONAPLS);
        adb.setTitle(R.string.dialogEvento);
        adb.setMessage(c.getString(1));
        adb.setCancelable(false);
        adb.setPositiveButton(R.string.adBotonSi, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                respuesta = 0;
                Cursor cursorRespuesta = Respuesta.getRespuestaPorIdpregunta(c.getLong(0));
                cursorRespuesta.moveToPosition(respuesta);
                Cursor cursorConsecuencia = Consecuencia.getConsecuenciaPorId(cursorRespuesta.getLong(3));
                cursorConsecuencia.moveToFirst();
                int estadistica = cursorEstadistica.getInt(cursorConsecuencia.getInt(3));
                if (cursorConsecuencia.getString(1).equalsIgnoreCase("suma")) {
                    estadistica += cursorConsecuencia.getInt(2);
                } else {
                    estadistica -= cursorConsecuencia.getInt(2);
                }
                modificarEstadistica(cursorConsecuencia.getInt(3), estadistica);
                cursorEstadistica = Estadistica.getEstadisticaPorId(cursorEstadistica.getLong(0));
                cursorEstadistica.moveToFirst();
                datosNoche();
            }
        });
        adb.setNeutralButton(R.string.adBotonNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                respuesta = 1;

                Cursor cursorRespuesta = Respuesta.getRespuestaPorIdpregunta(c.getLong(0));
                cursorRespuesta.moveToPosition(respuesta);
                Cursor cursorConsecuencia = Consecuencia.getConsecuenciaPorId(cursorRespuesta.getLong(3));
                cursorConsecuencia.moveToFirst();
                int estadistica = cursorEstadistica.getInt(cursorConsecuencia.getInt(3));
                if (cursorConsecuencia.getString(1).equalsIgnoreCase("suma")) {
                    estadistica += cursorConsecuencia.getInt(2);
                } else {
                    estadistica -= cursorConsecuencia.getInt(2);
                }
                modificarEstadistica(cursorConsecuencia.getInt(3), estadistica);
                cursorEstadistica = Estadistica.getEstadisticaPorId(cursorEstadistica.getLong(0));
                cursorEstadistica.moveToFirst();
                datosNoche();
            }
        });
        adb.show();

    }

    private void modificarEstadistica(int posicion, int estadistica) {
        // dependiendo de a la posicion que pertenece la estadistica se llama a un metodo u otro
        switch (posicion) {
            case 1:
                //actualziarEstadisticas(int tecnica, int resistencia, int moral, int suerte, long id)
                Estadistica.actualziarEstadisticas(estadistica, cursorEstadistica.getInt(2), cursorEstadistica.getInt(3), cursorEstadistica.getInt(4), cursorEstadistica.getInt(0));
                break;
            case 2:
                Estadistica.actualziarEstadisticas(cursorEstadistica.getInt(1), estadistica, cursorEstadistica.getInt(3), cursorEstadistica.getInt(4), cursorEstadistica.getInt(0));
                break;
            case 3:
                Estadistica.actualziarEstadisticas(cursorEstadistica.getInt(1), cursorEstadistica.getInt(2), estadistica, cursorEstadistica.getInt(4), cursorEstadistica.getInt(0));
                break;
            case 4:
                Estadistica.actualziarEstadisticas(cursorEstadistica.getInt(1), cursorEstadistica.getInt(2), cursorEstadistica.getInt(3), estadistica, cursorEstadistica.getInt(0));
                break;
        }
    }

    private long numerorandom() {
        // metodo que elige una pregunta de forma random
        int numero = 0;
        boolean generar = true;
        do {
            numero = r.nextInt(7);
            if (numero > 1 && numero < 7) {
                generar = false;
            }
        } while (generar);
        return (long) numero;
    }

    private void datosNoche() {
        // carga del texto de la noche
        textoLargoDia.setText(R.string.texto_fin_dia);
        // sumamos 1 dia
        dia++;
        //cambio de valores boolean
        noche = false;
        mañana = true;
        // actualizamos todos los datos de la BD
        Jugador.actualizarDia(dia, cursorJugador.getLong(0));
        cursorJugador = Jugador.getJugadorPorId(cursorJugador.getLong(0));
        cursorJugador.moveToFirst();

    }

    // aqui impedimos que cuando viene de la actividad de pedir datos vuelva a ella dandole al boton de atras de android
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PantallaInicial.this, MainActivity.class);
        startActivity(intent);
    }
}
