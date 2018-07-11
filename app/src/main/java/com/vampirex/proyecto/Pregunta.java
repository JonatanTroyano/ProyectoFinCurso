package com.vampirex.proyecto;

import android.database.Cursor;

/**
 * Created by VampireX on 10/03/2018.
 */

public class Pregunta {

    //db.execSQL("CREATE TABLE pregunta (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, pregunta TEXT NOT NULL);");
    private long id;
    private String pregunta;


    private final static String tabla = "pregunta";
    private final static String[] columnas = {"_id", "pregunta"};

    public Pregunta (String pregunta){
        this.pregunta=pregunta;
    }

    public long getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public static Cursor getPreguntaPorId(long id){
        return MainActivity.getDB().query(tabla, columnas, "_id=?", new String[]{""+id}, null, null, null);
    }

    public static Cursor getAllCursor() {
        return MainActivity.getDB().query(tabla, columnas, null, null, null, null, null);
    }
}
