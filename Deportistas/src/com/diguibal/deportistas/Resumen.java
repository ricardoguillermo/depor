package com.diguibal.deportistas;

import java.util.Vector;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

public class Resumen extends Activity{
	TextView nombre;
	TextView aciertoTenis;
	TextView aciertoCiclismo;
	TextView aciertoFutbol;
	TextView aciertoBoxeo;
	TextView aciertoAtl;
	TextView aciertoFormula1;
	TextView aciertoVolley;
	TextView aciertonatacion;
	TextView aciertomoto;
	TextView aciertobasquete;
	
	TextView erroresBoxeo;
	TextView erroresLun;
	TextView erroresFormula1;
	
	
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
    aciertoTenis =(TextView) findViewById(R.id.txtresumengeneral);
    aciertoCiclismo=(TextView) findViewById(R.id.txtresumengeo);
    aciertoFutbol = (TextView) findViewById(R.id.txtresumenfutbol);
    aciertoBoxeo =(TextView) findViewById(R.id.txtresumendeportes);
   
    aciertoAtl= (TextView) findViewById(R.id.txtresumenlunfardo);
    
    aciertoFormula1 =(TextView) findViewById(R.id.txtresumenliteratura);
    aciertoVolley=(TextView) findViewById(R.id.txtvolley);
    aciertonatacion =(TextView) findViewById(R.id.aciertonatacion);
    aciertomoto =(TextView) findViewById(R.id.txtmoto);
    aciertobasquete=(TextView) findViewById(R.id.txtbasquete);
    
    
    
   /* erroresTenis=(TextView) findViewById(R.id.txterroresTenis);
	erroresCiclismo= (TextView)findViewById(R.id.txterroresCiclismo);
	erroresFutbol=(TextView)findViewById(R.id.txterroresfutbol);
	erroresBoxeo=(TextView)findViewById(R.id.txterroresBoxeo);
	erroresLun=(TextView)findViewById(R.id.txterroreslunfardo);
	erroresFormula1=(TextView)findViewById(R.id.txterroresFormula1);
	erroresVolley=(TextView)findViewById(R.id.txterrorvolley);*/
    
	dificiles= (TextView) findViewById(R.id.txtdificil);
	medios=(TextView) findViewById(R.id.txtmedias);
	basicos=(TextView) findViewById(R.id.txtbasicas);
	
	segundospromedio=(TextView) findViewById(R.id.txttiempopromedio);
	leerdatos();
	// provisorio
	nombre.setText("Hola estos son mis "+ misdatos);
	
	
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
	
	String provisoriomal;
	String provisoriobien;
	
	
	provisoriobien=valorespuntos[2].substring(1, valorespuntos[2].length()-3)+ " Bien";
	provisoriomal=valorespuntos[3].substring(1, valorespuntos[3].length()-3) + " Mal";
	aciertoBoxeo.setText("Boxeo...."+provisoriobien+"    "+provisoriomal);
		//erroresBoxeo.setText("Boxeo.."+valorespuntos[3].substring(1, valorespuntos[7].length()-3));
	
	
	provisoriobien=valorespuntos[4].substring(1, valorespuntos[4].length()-3)+ " Bien";
	provisoriomal=valorespuntos[5].substring(1, valorespuntos[5].length()-3) + " Mal";
	aciertoFutbol.setText("Fútbol.."+provisoriobien+"    "+provisoriomal);
	//erroresFutbol.setText("Fútbol.."+valorespuntos[5].substring(1, valorespuntos[7].length()-3));
	
	
	provisoriobien=valorespuntos[6].substring(1, valorespuntos[6].length()-3)+ " Bien";
	provisoriomal=valorespuntos[7].substring(1, valorespuntos[7].length()-3) + " Mal";
	aciertoFormula1.setText("Fórmula 1..."+provisoriobien+"    "+provisoriomal);
	//erroresFormula1.setText("Fórmula 1..."+valorespuntos[7].substring(1, valorespuntos[7].length()-3));
	
	provisoriobien=valorespuntos[8].substring(1, valorespuntos[8].length()-3)+ " Bien";
	provisoriomal=valorespuntos[9].substring(1, valorespuntos[9].length()-3) + " Mal";
	aciertoCiclismo.setText("Ciclismo..."+provisoriobien+"    "+provisoriomal);
	
		
	provisoriobien=valorespuntos[10].substring(1, valorespuntos[10].length()-3)+ " Bien";
	provisoriomal=valorespuntos[11].substring(1, valorespuntos[11].length()-3) + " Mal";
	aciertoTenis.setText("Tenis .."+provisoriobien+"    "+provisoriomal);
	//erroresTenis.setText("Tenis .."+valorespuntos[11].substring(1, valorespuntos[11].length()-3));
	
	
	provisoriobien=valorespuntos[14].substring(1, valorespuntos[14].length()-3)+ " Bien";
	provisoriomal=valorespuntos[15].substring(1, valorespuntos[15].length()-3) + " Mal";
	aciertoAtl.setText("Atletismo.."+provisoriobien+"    "+provisoriomal);
	//erroneral.setText("Atletismo .."+valorespuntos[15].substring(1, valorespuntos[15].length()-3));
	
	
	provisoriobien=valorespuntos[12].substring(1, valorespuntos[12].length()-3)+ " Bien";
	provisoriomal=valorespuntos[13].substring(1, valorespuntos[13].length()-3) + " Mal";
	aciertoVolley.setText("Volley .."+provisoriobien+"    "+provisoriomal);
	//err.setText("Volley .."+valorespuntos[13].substring(1, valorespuntos[13].length()-3));
	
	
	
		
	provisoriobien = valorespuntos[16].substring(1, valorespuntos[16].length()-3)+ " Bien";
	provisoriomal = valorespuntos[17].substring(1, valorespuntos[17].length()-3) + " Mal";
	aciertonatacion.setText("Natación .."+provisoriobien+"    "+provisoriomal);
	//erroresGneral.setText("Natación .."+valorespuntos[17].substring(1, valorespuntos[17].length()-3));
	
		
	//eacosta bandes.Resumen.uy gerente de recupero  estela 
	provisoriobien = valorespuntos[18].substring(1, valorespuntos[18].length()-3)+ " Bien";
	provisoriomal = valorespuntos[19].substring(1, valorespuntos[19].length()-3) + " Mal";
	aciertobasquete.setText("Basquetbol .."+provisoriobien+"    "+provisoriomal);
	//aciGeneral.setText("Basquet .."+valorespuntos[18].substring(1, valorespuntos[18].length()-3));
	//erroeneral.setText("Basquet .."+valorespuntos[19].substring(1, valorespuntos[19].length()-3));
	
	provisoriobien = valorespuntos[20].substring(1, valorespuntos[20].length()-3)+ " Bien";
	provisoriomal = valorespuntos[21].substring(1, valorespuntos[21].length()-3) + " Mal";
	aciertomoto.setText("Motociclismo .."+provisoriobien+"    "+provisoriomal);
	//aciertoGenal.setText("Motociclismo .."+valorespuntos[20].substring(1, valorespuntos[20].length()-3));
	//errorsGeneral.setText("Motociclismoiles .."+valorespuntos[21]);
	
	dificiles.setText("Preguntas altas ..."+valorespuntos[22]);
	medios.setText("Preguntas medias ..."+valorespuntos[23]);
	basicos.setText("Preguntas fáciles ..."+valorespuntos[24]);
	
	
	segundospromedio.setText("Promedio de segundos por pregunta .."+valorespuntos[25]);

}

public boolean onKeyDown(int keyCode, KeyEvent event)
{
    if ((keyCode == KeyEvent.KEYCODE_BACK))
    {
        finish();
    }
    return super.onKeyDown(keyCode, event);
}

}
