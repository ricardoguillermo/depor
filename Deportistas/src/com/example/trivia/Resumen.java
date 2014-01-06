package com.example.trivia;

import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Resumen extends Activity{
	TextView nombre;
	TextView aciertoGeneral;
	TextView aciertoGeo;
	TextView aciertoFutbol;
	TextView aciertoDeportes;
	TextView aciertoLun;
	TextView aciertoLiteratura;
	
	TextView erroresGeneral;
	TextView erroresGeo;
	TextView erroresFutbol;
	TextView erroresDeportes;
	TextView erroresLun;
	TextView erroresLiteratura;
	
	TextView dificiles;
	TextView medios;
	TextView basicos;
	
	TextView segundospromedio;
	
	String posicion;
	String misdatos;
		
	Vector<String> listita;
	
	
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);// elimina barra 
	setContentView(R.layout.resumen);
	
	
	
    listita = MainActivity.almacen.estadistica(10);

    nombre=(TextView) findViewById(R.id.txtresumennobre);
    aciertoGeneral =(TextView) findViewById(R.id.txtresumengeneral);
    aciertoGeo=(TextView) findViewById(R.id.txtresumengeo);
    aciertoFutbol = (TextView) findViewById(R.id.txtresumenfutbol);
    aciertoDeportes =(TextView) findViewById(R.id.txtresumendeportes);
    aciertoLun= (TextView) findViewById(R.id.txtresumenlunfardo);
    aciertoLiteratura =(TextView) findViewById(R.id.txtresumenliteratura);
    
    erroresGeneral=(TextView) findViewById(R.id.txterroresgeneral);
	erroresGeo= (TextView)findViewById(R.id.txterroresgeo);
	erroresFutbol=(TextView)findViewById(R.id.txterroresfutbol);
	erroresDeportes=(TextView)findViewById(R.id.txterroresdeportes);
	erroresLun=(TextView)findViewById(R.id.txterroreslunfardo);
	erroresLiteratura=(TextView)findViewById(R.id.txterroresliteratura);
    
	dificiles= (TextView) findViewById(R.id.txtdificil);
	medios=(TextView) findViewById(R.id.txtmedias);
	basicos=(TextView) findViewById(R.id.txtbasicas);
	
	segundospromedio=(TextView) findViewById(R.id.txttiempopromedio);
	leerdatos();
   colocardatos();
}

private void leerdatos(){
	Bundle extras = getIntent().getExtras();
	posicion =extras.getString("posicion");
	misdatos = extras.getString("misdatos");
}

private void colocardatos(){
	
	int posi=Integer.valueOf(posicion);	
	String[] parts = listita.get(posi).split(" ");
	
		
	nombre.setText("Estos son los datos de tu   puntaje. " );
	//nombre.setText(parts[(parts.length-1)]);
	
	
	String[] valorespuntos = parts[(parts.length-1)].split(",");
	
	aciertoGeneral.setText("Lunfardo .."+valorespuntos[10].substring(1, valorespuntos[10].length()-3));
	erroresGeneral.setText("Lunfardo .."+valorespuntos[11].substring(1, valorespuntos[11].length()-3));
	
	aciertoDeportes.setText("Deportes...."+valorespuntos[2].substring(1, valorespuntos[6].length()-1));
	erroresDeportes.setText("Deportes.."+valorespuntos[3].substring(1, valorespuntos[7].length()-1));
	
	aciertoFutbol.setText("Fútbol uruguayo.."+valorespuntos[4].substring(1, valorespuntos[6].length()-1));
	erroresFutbol.setText("Fútbol uruguayo.."+valorespuntos[5].substring(1, valorespuntos[7].length()-1));
	
	aciertoLiteratura.setText("Literarura nacional..."+valorespuntos[6].substring(1, valorespuntos[8].length()-1));
	erroresLiteratura.setText("Literarura nacional..."+valorespuntos[7].substring(1, valorespuntos[9].length()-1));
	
	aciertoGeo.setText("Geografía...."+valorespuntos[8].substring(1, valorespuntos[6].length()-1));
	erroresGeo.setText("Geografía.."+valorespuntos[9].substring(1, valorespuntos[7].length()-1));
	
	
	aciertoLun.setText("General y costumbres.."+valorespuntos[0].substring(1, valorespuntos[0].length()-3));//lun
	erroresLun.setText("General y costumbres.."+valorespuntos[1].substring(1, valorespuntos[1].length()-3));
	
	dificiles.setText("Preguntas dificiles .."+valorespuntos[12]);
	medios.setText("Preguntas medias ..."+valorespuntos[13]);
	basicos.setText("Preguntas fáciles ..."+valorespuntos[14]);
	
	segundospromedio.setText("Promedio de segundos por pregunta .."+valorespuntos[15]);
	
}

}
