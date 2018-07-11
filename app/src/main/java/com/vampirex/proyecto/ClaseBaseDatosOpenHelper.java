package com.vampirex.proyecto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by VampireX on 24/02/2018.
 */

public class ClaseBaseDatosOpenHelper extends SQLiteOpenHelper {


    public ClaseBaseDatosOpenHelper(Context context) {
        super(context, "proyecto.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tabla partido
        db.execSQL("CREATE TABLE partido (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, idequipo_local INTEGER NOT NULL, idequipo_visitante INTEGER NOT NULL);");
        // tabla equipo
        db.execSQL("CREATE TABLE equipo (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL);");
        // tabla jugador
        db.execSQL("CREATE TABLE jugador (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, posicion TEXT NOT NULL, numero INT NOT NULL, dia_paritda INT NOT NULL , idequipo INTEGER NOT NULL);");
        // tabla estadistica
        db.execSQL("CREATE TABLE estadistica (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, tecnica INT NOT NULL, resistencia INT NOT NULL, moral INT NOT NULL, suerte INT NOT NULL, idjugador INTEGER NOT NULL);");
        // tabla consecuencia
        db.execSQL("CREATE TABLE consecuencia (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, factor TEXT NOT NULL, valor INT NOT NULL,posicion_estadistica INT NOT NULL, idestadistica INTEGER NOT NULL);");
        // tabla respuesta
        db.execSQL("CREATE TABLE respuesta (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, respuesta TEXT NOT NULL, idpregunta INTEGER NOT NULL, idconsecuencia INTEGER NOT NULL);");
        // tabla pregunta
        db.execSQL("CREATE TABLE pregunta (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, pregunta TEXT NOT NULL);");

        // equipos de futbol de 1º division españa
        db.execSQL("INSERT INTO equipo VALUES (1, 'Barcelona');");
        db.execSQL("INSERT INTO equipo VALUES (2, 'Atlético Madrid');");
        db.execSQL("INSERT INTO equipo VALUES (3, 'Real Madrid');");
        db.execSQL("INSERT INTO equipo VALUES (4, 'Valencia C.F.');");
        db.execSQL("INSERT INTO equipo VALUES (5, 'Sevilla');");
        db.execSQL("INSERT INTO equipo VALUES (6, 'Villarreal');");
        db.execSQL("INSERT INTO equipo VALUES (7, 'Girona');");
        db.execSQL("INSERT INTO equipo VALUES (8, 'Eibar');");
        db.execSQL("INSERT INTO equipo VALUES (9, 'Celta de Vigo');");
        db.execSQL("INSERT INTO equipo VALUES (10, 'Betis');");
        db.execSQL("INSERT INTO equipo VALUES (11, 'Getafe');");
        db.execSQL("INSERT INTO equipo VALUES (12, 'Real Sociedad');");
        db.execSQL("INSERT INTO equipo VALUES (13, 'Leganés');");
        db.execSQL("INSERT INTO equipo VALUES (14, 'Ath. Bilbao');");
        db.execSQL("INSERT INTO equipo VALUES (15, 'RCD Espanyol');");
        db.execSQL("INSERT INTO equipo VALUES (16, 'Alavés');");
        db.execSQL("INSERT INTO equipo VALUES (17, 'Levante');");
        db.execSQL("INSERT INTO equipo VALUES (18, 'U.D. Las Palmas');");
        db.execSQL("INSERT INTO equipo VALUES (19, 'Deportivo');");
        db.execSQL("INSERT INTO equipo VALUES (20, 'Málaga');");

        // datos consecuencias
        // +10 tecnica
        db.execSQL("INSERT INTO consecuencia VALUES (1, 'suma' ,10,1,1);");
        // -10 tecnica
        db.execSQL("INSERT INTO consecuencia VALUES (2, 'resta', 10,1,1);");
        // + 10 resitencia
        db.execSQL("INSERT INTO consecuencia VALUES (3, 'suma', 10,2,1);");
        // - 10 resistencia
        db.execSQL("INSERT INTO consecuencia VALUES (4, 'resta', 10,2,1);");
        // + 10 moral
        db.execSQL("INSERT INTO consecuencia VALUES (5, 'suma', 10,3 ,1);");
        // - 10 moral
        db.execSQL("INSERT INTO consecuencia VALUES (6, 'resta', 10,3,1);");
        // +10 suerte
        db.execSQL("INSERT INTO consecuencia VALUES (7, 'suma', 10,4,1);");
        // - 10 suerte
        db.execSQL("INSERT INTO consecuencia VALUES (8, 'resta', 10,4,1);");


        // preguntas

        db.execSQL("INSERT INTO pregunta VALUES (1, '¿Quieres ir a entrenar hoy?');");
        db.execSQL("INSERT INTO pregunta VALUES (2, 'Te aburres de la rutina. ¿Quieres salir de fiesta?');");
        db.execSQL("INSERT INTO pregunta VALUES (3, 'Te encuentras con un vagabundo. ¿Le das dinero del que tienes suelto?');");
        db.execSQL("INSERT INTO pregunta VALUES (4, 'Ves que tu coche esta viejo, aunque aun sirve, ¿Quieres comprar otro?');");
        db.execSQL("INSERT INTO pregunta VALUES (5, 'Un compañero del equipo te invita a una fiesta. ¿Aceptas su invitacion?');");
        db.execSQL("INSERT INTO pregunta VALUES (6, 'El entrenador te dice que deverias entrenar más,¿Entrenaras más?');");

        // respuestas
        db.execSQL("INSERT INTO respuesta VALUES (1, 'Sí',1,1);");
        db.execSQL("INSERT INTO respuesta VALUES (2, 'No',1,2);");
        db.execSQL("INSERT INTO respuesta VALUES (3, 'Sí',2,4);");
        db.execSQL("INSERT INTO respuesta VALUES (4, 'No',2,3);");
        db.execSQL("INSERT INTO respuesta VALUES (5, 'Sí',3,7);");
        db.execSQL("INSERT INTO respuesta VALUES (6, 'No',3,8);");
        db.execSQL("INSERT INTO respuesta VALUES (7, 'Sí',4,5);");
        db.execSQL("INSERT INTO respuesta VALUES (8, 'No',4,6);");
        db.execSQL("INSERT INTO respuesta VALUES (9, 'Sí',5,3);");
        db.execSQL("INSERT INTO respuesta VALUES (10, 'No',5,7);");
        db.execSQL("INSERT INTO respuesta VALUES (11, 'Sí',6,1);");
        db.execSQL("INSERT INTO respuesta VALUES (12, 'No',6,2);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
