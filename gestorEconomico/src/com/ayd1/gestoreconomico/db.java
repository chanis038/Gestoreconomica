package com.ayd1.gestoreconomico;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class db extends SQLiteOpenHelper{
//campos de la base de datos que se creararan en la tabla
    private static final String DATABASE_NAME = "gestor.economico.db";
     
    public db(Context context) {
        super(context, DATABASE_NAME, null, 1);
        }
    @Override
    public void onCreate(SQLiteDatabase db) {
    	//se crea la tabla inicial gasto
        db.execSQL("CREATE TABLE gasto (_id  INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, tipo TEXT, costo REAL, descripcion TEXT);");
        //tabla nombre del gasto
        db.execSQL("CREATE TABLE nombre (nombre TEXT PRIMARY KEY);");
        
        db.execSQL("CREATE TABLE usuario (nombre TEXT PRIMARY KEY, presupuesto TEXT, moneda TEXT);");
      
        //se insertan valores a la tabla
        db.execSQL("INSERT INTO nombre(nombre) VALUES('Almuerzo')");
        db.execSQL("INSERT INTO nombre(nombre) VALUES('Desayuno')");
        db.execSQL("INSERT INTO nombre(nombre) VALUES('Cena')");
        db.execSQL("INSERT INTO nombre(nombre) VALUES('Merienda')");
        db.execSQL("INSERT INTO nombre(nombre) VALUES('Coca cola')");
        db.execSQL("INSERT INTO nombre(nombre) VALUES('Pasaje')");
        
      
        db.execSQL("INSERT INTO usuario(nombre,presupuesto,moneda) VALUES('user','0.0','Q')");
      
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
    	// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS nombre");
        db.execSQL("DROP TABLE IF EXISTS gasto");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        // Create tables again
        onCreate(db);
    }
    
}