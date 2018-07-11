package com.vampirex.proyecto;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by VampireX on 10/03/2018.
 */

public class Estadistica {

    private long id;
    private int tecnica;
    private int resistencia;
    private int moral;
    private int suerte;
    private long idjugador;

    private final static String tabla = "estadistica";
    private final static String[] columnas = {"_id", "tecnica", "resistencia", "moral", "suerte", "idjugador"};


    public Estadistica(int tecnica, int resistencia, int moral, int suerte, long idjugador) {
        this.tecnica = tecnica;
        this.resistencia = resistencia;
        this.moral = moral;
        this.suerte = suerte;
        this.idjugador = idjugador;
    }

    public long getId() {
        return id;
    }

    public int getTecnica() {
        return tecnica;
    }

    public int getResistencia() {
        return resistencia;
    }

    public int getMoral() {
        return moral;
    }

    public int getSuerte() {
        return suerte;
    }

    public long getIdjugador() {
        return idjugador;
    }

    public void guardar() {
        ContentValues cv = new ContentValues();

        cv.put(columnas[1], this.tecnica);
        cv.put(columnas[2], this.resistencia);
        cv.put(columnas[3], this.moral);
        cv.put(columnas[4], this.suerte);
        cv.put(columnas[5], this.idjugador);

        MainActivity.getDB().insert(tabla, null, cv);
    }

    public static void borrarEstadisticaPorId(long id){
        MainActivity.getDB().delete(tabla,"_id=?",new String[]{""+id});
    }

    public static void actualziarEstadisticas(int tecnica, int resistencia, int moral, int suerte, long id) {
        ContentValues cv = new ContentValues();

        cv.put(columnas[1], tecnica);
        cv.put(columnas[2], resistencia);
        cv.put(columnas[3], moral);
        cv.put(columnas[4], suerte);

        MainActivity.getDB().update(tabla, cv, "_id=?", new String[]{"" + id});
    }

    public static  Cursor getEstadisticaPorId(long id){
        return MainActivity.getDB().query(tabla, columnas, "_id=?", new String[]{""+id}, null, null, null);
    }

    public static Cursor getAllCursor() {
        return MainActivity.getDB().query(tabla, columnas, null, null, null, null, null);
    }


}
