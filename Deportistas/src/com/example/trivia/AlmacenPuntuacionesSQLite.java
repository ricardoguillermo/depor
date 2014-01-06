package com.example.trivia;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AlmacenPuntuacionesSQLite extends SQLiteOpenHelper implements AlmacenPuntuaciones  {

	public AlmacenPuntuacionesSQLite(Context context, String name,
			CursorFactory factory, int version) {
		
		super(context, "puntuaciones", null, 1);
		// TODO Auto-generated constructor stub
	}

	/* Agrega un nuevo registro*/
	public void guardarPuntuacion(int puntos, String nombre, String fecha,
			String categoria) {
		 SQLiteDatabase db = getWritableDatabase();

         db.execSQL("INSERT INTO puntuaciones VALUES ( null, "+

                       puntos+", '" + nombre + "', '" + fecha + "', '" + categoria +"')");
	}

	
	/* Carga un vector string con todos los datos 
	 * la variable categoria está al ...*/
	public Vector<String> listaPuntuaciones(int cantidad, String categoria) {
		Vector<String> result = new Vector<String>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT puntos, nombre, fecha FROM " +
         "puntuaciones  ORDER BY puntos DESC LIMIT " +cantidad, null);

        while (cursor.moveToNext()){

                      result.add(cursor.getInt(0)+" " +cursor.getString(1)+"  "+cursor.getString(2));

         }

        cursor.close();

        return result;

	}

	/* ¿Es Top x o no? 
	 * Las variables son el top(min) y los puntos*/
	public boolean estoyTop(int min, int puntos) {
		SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT puntos, nombre FROM " +
         "puntuaciones  ORDER BY puntos DESC LIMIT " +min, null);
        
        // preguntar si es distinto de vacio o del valor a estimar
        if (cursor.getCount()<min){
        	return true;
        }
        while (cursor.moveToNext()){

            if(cursor.getInt(0)<puntos){
            	cursor.close();
            	return true;
            }

        }

        cursor.close();
   	
		return false;
	}

	/* borra la tabla*/
	public void borrarregistros() {
		
		SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM puntuaciones");
	}

	/* catego es importante aqui
	 * es una clave delos tipos de error*/
	public Vector<String> estadistica(int cantidad) {
		Vector<String> resultado = new Vector<String>();
    	
    	SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT puntos, nombre, catego FROM " +
         "puntuaciones  ORDER BY puntos DESC LIMIT " +cantidad, null);

        while (cursor.moveToNext()){

                      resultado.add(cursor.getInt(0)+" " +cursor.getString(1)+"  "+cursor.getString(2));

         }

        cursor.close();

        return resultado;
	}

	/* Método que crea la tabla de puntuaciones*/
	
	public void onCreate(SQLiteDatabase db) {
		//crea la tabla puntuacione  clave puntos nombre fecha y categoque 
		db.execSQL("CREATE TABLE puntuaciones ("+

                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+

                        "puntos INTEGER, nombre TEXT, fecha TEXT, catego TEXT)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 // En caso de una nueva versión habría que actualizar las tablas
		
	}

}
