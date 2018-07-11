package com.vampirex.proyecto;

import android.database.Cursor;

/**
 * Created by VampireX on 10/03/2018.
 */

public class Equipo {

    private long id;
    private String nombre;

    private final static String tabla = "equipo";
    private final static String[] columnas = {"_id", "nombre"};

    public Equipo(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public static  Cursor getEquipoPorId(long id){
        return MainActivity.getDB().query(tabla, columnas, "_id=?", new String[]{""+id}, null, null, null);
    }

    public static Cursor getAllCursor() {
        return MainActivity.getDB().query(tabla, columnas, null, null, null, null, null);
    }
}
