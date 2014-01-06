package com.example.trivia;

import java.util.Vector;
/* esta interface implementa un metodo para guardar los puntos con
 * puntos,nombre, fecha y categoria
 * 
 * listapuntuaciones()
 * 
 * Estoy top min es el top(10,5,etc) y puntos los realizados
 * 
 * Borrarregistros() borra la lista
 * 
 * estadistica 
 * 
 */
public interface AlmacenPuntuaciones {

	public void guardarPuntuacion(int puntos,String nombre,String fecha,String categoria);

    public Vector<String> listaPuntuaciones(int cantidad, String categoria);
    
    public boolean estoyTop(int min,int puntos);
    public void borrarregistros();
    public Vector<String> estadistica(int cantidad);
	
}
