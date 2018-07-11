package com.vampirex.proyecto;

import android.database.Cursor;

/**
 * Created by VampireX on 10/03/2018.
 */

public class Respuesta {

    private long id;
    private String respuesta;
    private long idpregunta;
    private long idconsecuencia;

    private final static String tabla = "respuesta";
    private final static String[] columnas = {"_id", "respuesta", "idpregunta", "idconsecuencia"};


    public Respuesta(String respuesta, long idpregunta, long idconsecuencia){
        this.respuesta=respuesta;
        this.idpregunta=idpregunta;
        this.idconsecuencia=idconsecuencia;
    }


    public long getId() {
        return id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public long getIdpregunta() {
        return idpregunta;
    }

    public long getIdconsecuencia() {
        return idconsecuencia;
    }

    public static Cursor getRespuestaPorIdpregunta(long idpregunta){
        return MainActivity.getDB().query(tabla, columnas, "idpregunta=?", new String[]{""+idpregunta}, null, null, null);
    }

    public static Cursor getAllCursor() {
        return MainActivity.getDB().query(tabla, columnas, null, null, null, null, null);
    }
}
