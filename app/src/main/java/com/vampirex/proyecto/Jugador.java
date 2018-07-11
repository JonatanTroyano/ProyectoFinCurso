package com.vampirex.proyecto;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by VampireX on 10/03/2018.
 */

public class Jugador {

    private long id;
    private String nombre;
    private String posicion;
    private int numero;
    private int dia_partida;
    private long idequipo;

    private final static String tabla = "jugador";
    private final static String[] columnas = {"_id", "nombre", "posicion", "numero", "dia_paritda", "idequipo"};


    public Jugador(String nombre, String posicion, int numero, int dia_partida, long idequipo) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.numero = numero;
        this.dia_partida = dia_partida;
        this.idequipo = idequipo;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public int getNumero() {
        return numero;
    }

    public int getDia_partida() {
        return dia_partida;
    }

    public long getIdequipo() {
        return idequipo;
    }

    public void guardar() {
        ContentValues cv = new ContentValues();

        cv.put(columnas[1], this.nombre);
        cv.put(columnas[2], this.posicion);
        cv.put(columnas[3], this.numero);
        cv.put(columnas[4], this.dia_partida);
        cv.put(columnas[5], this.idequipo);

        MainActivity.getDB().insert(tabla, null, cv);
    }

    public static void borrarJugadorPorId(long id){
        MainActivity.getDB().delete(tabla,"_id=?",new String[]{""+id});
    }

    public static void actualizarDia(int dia_partida, long id) {
        ContentValues cv = new ContentValues();
        cv.put(columnas[4], dia_partida);
        MainActivity.getDB().update(tabla, cv, "_id=?", new String[]{"" + id});
    }

    public static  Cursor getJugadorPorId(long id){
        return MainActivity.getDB().query(tabla, columnas, "_id=?", new String[]{""+id}, null, null, null);
    }

    public static Cursor getAllCursor() {
        return MainActivity.getDB().query(tabla, columnas, null, null, null, null, null);
    }
}
