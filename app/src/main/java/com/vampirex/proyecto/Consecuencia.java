package com.vampirex.proyecto;

import android.database.Cursor;

/**
 * Created by VampireX on 10/03/2018.
 */

public class Consecuencia {

    private long id;
    private String factor;
    private int valor;
    private int posicion_estadistica;
    private long idestadistica;

    private final static String tabla = "consecuencia";
    private final static String[] columnas = {"_id", "factor", "valor", "posicion_estadistica", "idestadistica"};

    public Consecuencia(String factor, int valor, int posicion_estadistica, long idestadistica) {
        this.factor = factor;
        this.valor = valor;
        this.posicion_estadistica = posicion_estadistica;
        this.idestadistica = idestadistica;
    }


    public long getId() {
        return id;
    }

    public String getFactor() {
        return factor;
    }

    public int getValor() {
        return valor;
    }

    public long getIdestadistica() {
        return idestadistica;
    }


    public int getPosicion_estadistica() {
        return posicion_estadistica;
    }

    public static Cursor getConsecuenciaPorId(long id) {
        return MainActivity.getDB().query(tabla, columnas, "_id=?", new String[]{"" + id}, null, null, null);
    }

    public static Cursor getAllCursor() {
        return MainActivity.getDB().query(tabla, columnas, null, null, null, null, null);
    }

}
