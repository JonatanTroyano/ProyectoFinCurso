package com.vampirex.proyecto;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by VampireX on 10/03/2018.
 */

public class Partido {

    // db.execSQL("CREATE TABLE partido (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, goles_jugador INT NOT NULL, idequipo_local INTEGER NOT NULL, idequipo_visitante INTEGER NOT NULL);");
    private long id;
    // el equipo local es al que pertenece el jugador
    private long idequipo_local;
    private long idequipo_visitante;

    private final static String tabla = "partido";
    private final static String[] columnas = {"_id", "idequipo_local", "idequipo_visitante"};

    public Partido(long idequipo_local, long idequipo_visitante){
        this.idequipo_local=idequipo_local;
        this.idequipo_visitante=idequipo_visitante;
    }


    public long getId() {
        return id;
    }

    public long getIdequipo_local() {
        return idequipo_local;
    }

    public long getIdequipo_visitante() {
        return idequipo_visitante;
    }

    public void guardar() {
        ContentValues cv = new ContentValues();

        cv.put(columnas[1], this.idequipo_local);
        cv.put(columnas[2], this.idequipo_visitante);

        MainActivity.getDB().insert(tabla, null, cv);
    }

    public static void borrarPartidoPorId(long id){
        MainActivity.getDB().delete(tabla,"_id=?",new String[]{""+id});
    }

    public static  Cursor getPartidoPorId(long id){
        return MainActivity.getDB().query(tabla, columnas, "_id=?", new String[]{""+id}, null, null, null);
    }

    public static Cursor getAllCursor() {
        return MainActivity.getDB().query(tabla, columnas, null, null, null, null, null);
    }
}
