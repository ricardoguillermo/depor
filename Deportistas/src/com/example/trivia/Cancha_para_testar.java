package com.example.trivia;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.example.trivia.R.id;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Cancha_para_testar extends Activity implements OnClickListener {

	// variables de los widgets
	TextView secpregunta, sector1, sector2, sector3, sector4;
	ImageView respuesta;
	TextView tiempo, placard;
	Button btntestear;//Aca elimine button aPuntaje
	Utiles calculos;// clase para auxiliares; sortea el orden de los aciertos,
					// etc
	Button btnprobar;
	
	
	
	
	// variables de aciertos
	String cierto = "";
	String error1 = "";
	String error2 = "";
	String error3 = "";
	String pregunta = "";
	
	
	

	int acierto;// era string
	String temasortedo;
	String nivel;

	HashMap<String, Integer> listaProductos = new HashMap<String, Integer>();

	int puntos = 0; // puntaje inicial
	int errores = 0;// cantidad de errores a
	int cantidaderrores;// previstos en configuración
	int lecturapondrada;

	

	
	
	String misdatos = "";// junta los aciertos y errores etc
	String orden;// almacena el orden de colocar los aciertos
	String linea = null;

	// sonidos cortos
	SoundPool soundPool;
	int bien, mal;
	

	

	int estado;// para ver si arreglo un bug

	int numeropregunta;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canchatestear);

		// instancia las variables con los recursos
		secpregunta = (TextView) findViewById(R.id.txtPregunta);// pregunta
		sector1 = (TextView) findViewById(R.id.txtsector1);// errores
		sector2 = (TextView) findViewById(R.id.txtsector2);
		sector3 = (TextView) findViewById(R.id.txtsector3);
		sector4 = (TextView) findViewById(R.id.txtsector4);
		
		respuesta = (ImageView)findViewById(R.id.imageRespuesta);//Diana
		
		// coloca los escucha (listener)
		sector1.setOnClickListener(this);
		sector2.setOnClickListener(this);
		sector3.setOnClickListener(this);
		sector4.setOnClickListener(this);
		
		btntestear =(Button) findViewById(R.id.btntestear);
		btntestear.setOnClickListener(this);
		
		
		calculos = new Utiles();
		
		placard=(TextView)findViewById(R.id.txtPlacard);//
		
		Bundle extras = getIntent().getExtras();
		String numeropreguntastring;
		numeropreguntastring = extras.getString("numeropregunta");// recibe
		numeropregunta=Integer.parseInt(numeropreguntastring);
		//numeropregunta=1;
		nueva_jugada();
		
		
		
		// instancia auxiliar para calculos
		

		// leer tiempo_elegido y nivel ...
		//cargarPreferencias();
		
		
							
			// sonido
				soundPool = new SoundPool( 5, AudioManager.STREAM_MUSIC , 0);
				bien = soundPool.load(this, R.raw.bien, 0);
				mal = soundPool.load(this, R.raw.error, 0);
				
			
			/*
				
			// calcula la cantidad de líneas
				//cantidaddepreguntas=totaldepreguntas();
				
				deshabilitabotones();
				*/
	
	}

	

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// en los sectores compara variables string, manda a puntaje y suena
	
		case R.id.txtsector1:// clik en el sector 1 
			if (acierto==1) {
				puntaje(10, sector1);
				 soundPool.play(bien, 1, 1, 1, 0, 1);
			} else {
				errores += 1;
				soundPool.play(mal, 1, 1, 1, 0, 1);
				puntaje(-10, sector1);
			}
			nueva_jugada();
			break;
		case R.id.txtsector2:// clik en sector 2,
			if (acierto==2) {
				puntaje(10, sector2);
				soundPool.play(bien, 1, 1, 1, 0, 1);
			} else {
				errores += 1;
				soundPool.play(mal, 1, 1, 1, 0, 1);
				puntaje(-10, sector2);
			}
			nueva_jugada();
			break;
		case R.id.txtsector3:// clik en sector 3
			if (acierto==3) {
				puntaje(10, sector3);
				soundPool.play(bien, 1, 1, 1, 0, 1);
			} else {
				errores += 1;
				soundPool.play(mal, 1, 1, 1, 0, 1);
				puntaje(-10, sector3);
			}
			nueva_jugada();
			break;
		case R.id.txtsector4:// corresponde al 4 sector luego cambiar nombre
			if (acierto==4) {
				puntaje(10, sector4);
				soundPool.play(bien, 1, 1, 1, 0, 1);
			} else {
				errores += 1;
				soundPool.play(mal, 1, 1, 1, 0, 1);
				puntaje(-10, sector4);
			}
			nueva_jugada();
			break;
		
		case R.id.btntestear:// next
			
			nueva_jugada();//no
			break;
			
		}
		deshabilitabotones();
	}
	
	
	
		/* las 2 puntaje cuentan los puntos en el primero
		 * van lo spuntos y el sector que se pincho y en el segundo solo
		 * los puntos no hay necesidad de pintar rojo
		*/
		private void puntaje(int valor, TextView sector) {
			puntos += valor;// suma el valor recibido
			//time=0;// para la cuenta
			
			estado=0;//?
			// coloca el puntaje en el placard
			String str = Integer.toString(puntos);// placard
			placard.setText(str);
						
			// si es acierto o no valor positivo o negativo
			if(valor>0){
				sector.setBackgroundColor(Color.GREEN);// dio bien pinta verde
				respuesta.setVisibility(View.VISIBLE);//Diana
				respuesta.setImageResource(getBaseContext().getResources().getIdentifier("correct", "drawable", getPackageName()));
				estado=1;//?
				//guardaraciertos(1,nivel,temasortedo);
			}else{
				sector.setBackgroundColor(Color.RED);// dio mal pinta rojo 
				respuesta.setVisibility(View.VISIBLE);//Diana
				respuesta.setImageResource(getBaseContext().getResources().getIdentifier("wrong", "drawable", getPackageName()));//Diana
				revisarelcorrecto();// pinta el correcto
				/*if (errores>cantidaderrores){
					estado=0;
					guardaraciertos(0,nivel,temasortedo);
					pasar_final();
					return;
				}*/
			}
		}

		// esta sobreescrita por si no hay click y si demora
		/*private void puntaje(int valor) {
			puntos += valor;// suma el valor que es negativo
			
			deshabilitabotones();
			
			time=0;// para la cuenta
			estado=0;//?
			guardaraciertos(0,nivel,temasortedo);
			
			String str = Integer.toString(puntos);// placard
			placard.setText(str);
						
			revisarelcorrecto();// pinta el correcto en verde
			
			if (errores>cantidaderrores){// si son mas de los  errores permitidos  va a otra activity 
				estado=0;//?
				pasar_final();
				return;
			}else{
				estado=1;//?
				nueva_jugada();
			}
		}
	*/

	
	//ojo aca pare para cambiar variables  strings?
	private void revisarelcorrecto(){
		
		if (acierto==1){
			sector1.setBackgroundColor(Color.GREEN);//Diana
			return;
		}
		if (acierto==2){
			sector2.setBackgroundColor(Color.GREEN);// Diana
			return;
		}
		if (acierto==3){
			sector3.setBackgroundColor(Color.GREEN);//Diana
			return;
		}
		if (acierto==4){
			sector4.setBackgroundColor(Color.GREEN);//Diana
			return;
		}
	}

	public void pasar_final(){
		
		ponermensaje("Lo siento lleguaste al máximo de errores");
		// me avisa de los errores y pasa a entrada de nombre		
		errores=0;
		// pasa y envia el dato
		//estadistica();
		//time=-4;// esta linea y la proxima aseguran que no cuente cuando pasa a entra
		estado=0;		
		Intent te = new Intent( this, Entrada.class);
		te.putExtra("puntos", puntos);
		te.putExtra("misdatos", misdatos);
		startActivity(te);
		//mp.stop();//Diana
	}

	
	
	public void nueva_jugada(){
		estado=1;//?
		acierto=0; //limpia el valor por las dudas
		desarmar(leerlinea());
		sector1.setBackgroundColor(Color.TRANSPARENT);// deja transparente
		sector2.setBackgroundColor(Color.TRANSPARENT);//Diana
		sector3.setBackgroundColor(Color.TRANSPARENT);//Diana
		sector4.setBackgroundColor(Color.TRANSPARENT);//Diana
		respuesta.setVisibility(View.GONE);
	
		
		orden = calculos.sortearPosibilidad();// sortea el orden
		colocar(orden);// coloca en funcion del orden 
	}
	

	// método para colocar los strings en las ventanas
		public void colocar(String orden) {
				// coloca la pregunta leida			
				secpregunta.setText(pregunta);// coloca la pregunta
				habilitabotones();
				// en funcion de la ubicación de la letra "c" y ..
				// analizamos cada ubicacion de los digitos
				//colocamos las string en los text y si corresponde el acierto
				String primeraletra = orden.substring(0, 1);
				if (primeraletra.equals("c")) {
					sector1.setText(cierto);
					acierto = 1;//"sector1";
				}
				if (primeraletra.equals("1"))
					sector1.setText(error1);
				if (primeraletra.equals("2"))
					sector1.setText(error2);
				if (primeraletra.equals("3"))
					sector1.setText(error3);//
				// segunda letra
				String segundaletra = orden.substring(1, 2);
				if (segundaletra.equals("c")) {
					sector2.setText(cierto);
					acierto = 2;//"sector2";
				}
				if (segundaletra.equals("1"))
					sector2.setText(error1);
				if (segundaletra.equals("2"))
					sector2.setText(error2);
				if (segundaletra.equals("3"))
					sector2.setText(error3);
				// tercera letra
				String terceraletra = orden.substring(2, 3);
				if (terceraletra.equals("c")) {
					sector3.setText(cierto);
					acierto = 3;//"sector3";
				}
				if (terceraletra.equals("1"))
					sector3.setText(error1);
				if (terceraletra.equals("2"))
					sector3.setText(error2);
				if (terceraletra.equals("3"))
					sector3.setText(error3);
				// cuarta letra
				String cuartaletra = orden.substring(3, 4);
				if (cuartaletra.equals("c")) {
					sector4.setText(cierto);
					acierto = 4;//"sector4";
				}
				if (cuartaletra.equals("1"))
					sector4.setText(error1);
				if (cuartaletra.equals("2"))
					sector4.setText(error2);
				if (cuartaletra.equals("3"))
					sector4.setText(error3);
			}
		
		/* lee un archivo por sectores ponderados o no*/
		
		
		
		/* Función sobrescrita sin ponderar
		 *  el sector de la pregunta
		 *  */
		
	private String leerlinea(){
				
		int lineaelegida=0;
		//int numlineas =cantidaddepreguntas;
		numeropregunta++;
		
		lineaelegida= numeropregunta;
		
		
		
				
	// lee un archivo de nombre cuatrorespuestas previamente colocado
	try
	{
	    InputStream fraw = getResources().openRawResource(R.raw.deportes_numerados);//mundiales es el archovo de texto que lee
	    int a =0;
	    BufferedReader brin =  new BufferedReader(new InputStreamReader(fraw));
	    while ((linea = brin.readLine()) != null)   {
            a+=1;
            if (a==lineaelegida){
            	fraw.close();
            	return linea;
            }
        }
        fraw.close();
	}
	catch (Exception ex)
	{
	    Log.e("Ficheros", "Error al leer fichero desde recurso raw");// para la depuracion
	}
	return linea;
	}
	
		
		
		
		
		// transforma la linea en una matriz y pone llena variables String 
		private void desarmar(String datoleido){
			String[] parts = datoleido.split(";");
			//0 es numero de pregunta 1 la pregunta ...
			cierto=parts[2]; 
			error1=parts[3];//cierto
			error2=parts[4];// error1
			error3=parts[5];// error2
			nivel=parts[7];
			pregunta=parts[1];
			temasortedo=parts[6];
			placard.setText(parts[0]);
		}
		
		
	
	
	/* En funcion del nivel elegido 
	 * y rescatado en las preferencias 
	 * se define el tiempo y la cantidad de errores
	 * FALTA EL NIVEL DE LA PREGUNTA*/
	 
	
	
	
	private void ponermensaje(CharSequence mensaje){
		/* es auxiliar para poner mensaje en pantalla*/
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(this, mensaje, duration);
			toast.show();
		}
	
	/* Los métodos siguientes 
	 * evitan el click consecutivo*/
	
	private void deshabilitabotones(){
		
		sector1.setEnabled(false);
		sector2.setEnabled(false);
		sector3.setEnabled(false);
		sector4.setEnabled(false);
	}
	
	private void habilitabotones(){
		
		sector1.setEnabled(true);
		sector2.setEnabled(true);
		sector3.setEnabled(true);
		sector4.setEnabled(true);
	
	}
	/*
	private void guardaraciertos(int acierto,String nivel,String temasortedo){
		
		//Estadisticas Lunfardos
		if (temasortedo.equals("Lun")){
			if (acierto==1){
				contadoraciertolun += 1;
			}else{
				contadorerrorlun += 1;
			}
		}
		
		//Estadisticas Deportes
		if (temasortedo.equals("D")){
			if (acierto==1){
				contadoraciertodepor += 1;
			}else{
				contadorerrordepor += 1;
			}
		}
		
		//Estadisticas Geografia
		if (temasortedo.equals("G")){
			if (acierto==1){
				contadoraciertogeo += 1;
			}else{
				contadorerrorgeo += 1;
			}
		}
		
		//Estadisticas Fútbol
		if (temasortedo.equals("F")){
			if (acierto==1){
				contadoraciertofut += 1;
			}else{
				contadorerrorfut += 1;
			}
		}
		
		//Estadisticas literatura
		if (temasortedo.equals("L")){
			if (acierto==1){
				contadoraciertolit += 1;
			}else{
				contadorerrorlit += 1;
			}
		}
		
		//Estadisticas General
		if (temasortedo.equals("Gen")){
			if (acierto==1){
				contadoraciertogen += 1;
			}else{
				contadorerrorgen += 1;
			}
		}
		
		// dificultad
		
		if (nivel.equals("A")){
			altas+=1;
		}
		
		if (nivel.equals("M")){
			medias+=1;
		}
		
		if (nivel.equals("B")){
			basicas+=1;
		}
		
		// talvez el 20 cambiar por l avelocidad real
		contadorsegundos += (20- Integer.parseInt((String) tiempo.getText()));
		cantidadpreguntas+=1;

}
*/
	/*private String estadistica(){
		// 0 o 1 por acierto cantidad y tema mas coma 
		
		// aciertos y errores general
		String agen="1"+contadoraciertogen+"Gen";
		String errorgen= "0"+contadorerrorgen+"Gen";
		
		// aciertos y errores deportes
		String adep="1"+contadoraciertodepor+"D";
		String errordep= "0"+contadorerrordepor+"D";
		
		// aciertos y errores futbol
		String afut="1"+contadoraciertofut+"F";
		String errorfut= "0"+contadorerrorfut+"F";
		
		// aciertos y errores literatura
		String alit="1"+contadoraciertolit+"L";
		String errorlit= "0"+contadorerrorlit+"L";
		
		// aciertos y errores geografia
		String ageo="1"+contadoraciertogeo+"G";
		String errorgeo= "0"+contadorerrorgeo+"G";
		
		// aciertos y errores lunfardo
		String alun="1"+contadoraciertolun+"Lun";
		String errorlun= "0"+contadorerrorlun+"Lun";
		
		//dificultad de las preguntas
		// promedios
		//contadorsegundos=(contadorsegundos/cantidadpreguntas);
		
		misdatos= agen + "," + errorgen + "," + adep + "," + errordep +
				","+ afut +","+ errorfut + "," + alit + "," + errorlit +
				"," + ageo +"," + errorgeo +"," + alun +","+ errorlun+
				","+altas+","+medias+","+basicas+","+ contadorsegundos;
		//ponermensaje(misdatos);
					
				return misdatos;
		
	}
*/
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		finish();// termina la activity para no dejar algun residuo
	}
}
