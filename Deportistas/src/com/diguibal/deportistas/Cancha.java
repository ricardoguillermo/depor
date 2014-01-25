package com.diguibal.deportistas;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.CapturedViewProperty;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Cancha extends Activity implements OnClickListener {

	// variables de los widgets
	TextView secpregunta, sector1, sector2, sector3, sector4;
	ImageView respuesta;
	TextView tiempo, placard;
	Button btnVolver;// Aca elimine button aPuntaje
	Utiles calculos;// clase para auxiliares; sortea el orden de los aciertos,
					// etc

	// variables de aciertos
	String cierto = "";
	String error1 = "";
	String error2 = "";
	String error3 = "";
	String pregunta = "";

	// Diana variable para la música
	public MediaPlayer mp;
	String musica;

	int acierto;// era string
	String temasortedo;
	String nivel;

	HashMap<String, Integer> listaProductos = new HashMap<String, Integer>();

	int puntos = 0; // puntaje inicial
	int errores = 0;// cantidad de errores a
	int cantidaderrores;// previstos en configuración
	int lecturapondrada;

	// contadores de estadistica
	int contadoraciertotenis, contadorerrortenis;
	int contadoraciertoboxeo, contadorerrorboxeo;
	int contadoraciertocic, contadorerrorcic;
	int contadoraciertofut, contadorerrorfut;
	int contadoraciertofor, contadorerrorfor;
	int contadoraciertorall, contadorerrorrall;
	int contadoraciertovol, contadorerrorvol;
	int contadoraciertoatl, contadorerroratl;
	int contadoraciertonat, contadorerrornat;
	int contadoraciertobas, contadorerrorbas;
	int contadoraciertomot, contadorerrormot;

	int altas, medias, basicas;
	int contadorsegundos;
	int cantidadpreguntas;

	String misdatos = "";// junta los aciertos y errores etc
	String orden;// almacena el orden de colocar los aciertos
	String linea = null;

	// sonidos cortos
	SoundPool soundPool;
	int bien, mal;

	// Variables de tiempo y preferencias
	int time_elegido;// ojo aqui podria venir el tiempo leido
	Timer timer;
	Handler handler = new Handler();
	int rate = 1000; // repite cada segundo
	int time = 0; // contador de tiemp

	int cantidaddepreguntas;

	int estado;// para ver si arreglo un bug

	String nomarchivo;
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// elimina barra
		setContentView(R.layout.cancha);

		// instancia las variables con los recursos
		secpregunta = (TextView) findViewById(R.id.txtPregunta);// pregunta
		sector1 = (TextView) findViewById(R.id.txtsector1);// errores
		sector2 = (TextView) findViewById(R.id.txtsector2);
		sector3 = (TextView) findViewById(R.id.txtsector3);
		sector4 = (TextView) findViewById(R.id.txtsector4);

		respuesta = (ImageView) findViewById(R.id.imageRespuesta);// Diana

		tiempo = (TextView) findViewById(R.id.txtTiempo);
		placard = (TextView) findViewById(R.id.txtPlacard);
		// aPuntaje = (Button) findViewById(R.id.btnaPuntaje); Diana
		btnVolver = (Button) findViewById(R.id.btnVolver);

		// coloca los escucha (listener)
		sector1.setOnClickListener(this);
		sector2.setOnClickListener(this);
		sector3.setOnClickListener(this);
		sector4.setOnClickListener(this);

		// aPuntaje.setOnClickListener(this);Diana

		// Diana inicializa variable mp m+usica de fondo
		mp = MediaPlayer.create(this, R.raw.musicadeportes); // agrega musica
																// al mp

		// Diana ejecuta la musica si o si.
		mp.start();

		// instancia auxiliar para calculos
		calculos = new Utiles();

		// leer tiempo_elegido y nivel ...
		cargarPreferencias();

		// comentar la linea al separar por categoria
		// nomarchivo="deportes_numerados";
		id = getResources().getIdentifier(nomarchivo, "raw", getPackageName());

		// tiempo
		timer = new Timer("Temporizador");
		Tarea tarea = new Tarea();
		timer.scheduleAtFixedRate(tarea, 0, rate);

		// sonido
		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		bien = soundPool.load(this, R.raw.bien, 0);
		mal = soundPool.load(this, R.raw.error, 0);

		// calcula la cantidad de líneas ojo si es mas de 1 archivo
		cantidaddepreguntas = totaldepreguntas();

		deshabilitabotones();

		// la primera vez
		nueva_jugada();

		estado = 1;

	}

	class Tarea extends TimerTask {
		@Override
		public void run() {
			Runnable cambiaTexto = new Cambia();
			handler.post(cambiaTexto);
		}
	}

	class Cambia implements Runnable {
		public void run() {

			// solo empieza a procesar si time es mayor que cero y
			// estado =1 por las dudas
			if (estado == 0) {// ?
				return;
			}

			time = time + 1;

			if (time == 1) {
				// limpi alos colores del fondo
				sector1.setBackgroundColor(Color.TRANSPARENT);// deja
																// transparente
				sector2.setBackgroundColor(Color.TRANSPARENT);// Diana
				sector3.setBackgroundColor(Color.TRANSPARENT);// Diana
				sector4.setBackgroundColor(Color.TRANSPARENT);// Diana
				respuesta.setVisibility(View.GONE);

				// sortea lugares de cierto y errores
				orden = calculos.sortearPosibilidad();

				// distribuye los string en funcion del orden anterior
				colocar(orden);// coloca en funcion del orden

				return;
			}

			// coloca el reloj
			if (time > 1) {
				int m = (time_elegido - time);
				String cadena = Integer.toString(m);
				tiempo.setText(cadena);

				// Se acabo el tiempo sin contestar
				if (time == time_elegido) {
					errores += 1;// si ya hay 3 errores
					estado = 0;
					puntaje(-10);// metodo sobreescrito
				}
			}
		}
	}

	/*
	 * responde al clik del boton
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		// en los sectores compara variables string, manda a puntaje y suena

		case R.id.txtsector1:// clik en el sector 1
			if (acierto == 1) {
				puntaje(10, sector1);
				soundPool.play(bien, 1, 1, 1, 0, 1);
			} else {
				errores += 1;
				soundPool.play(mal, 1, 1, 1, 0, 1);
				puntaje(-10, sector1);
			}
			// nueva_jugada();
			break;
		case R.id.txtsector2:// clik en sector 2,
			if (acierto == 2) {
				puntaje(10, sector2);
				soundPool.play(bien, 1, 1, 1, 0, 1);
			} else {
				errores += 1;
				soundPool.play(mal, 1, 1, 1, 0, 1);
				puntaje(-10, sector2);
			}
			// nueva_jugada();
			break;
		case R.id.txtsector3:// clik en sector 3
			if (acierto == 3) {
				puntaje(10, sector3);
				soundPool.play(bien, 1, 1, 1, 0, 1);
			} else {
				errores += 1;
				soundPool.play(mal, 1, 1, 1, 0, 1);
				puntaje(-10, sector3);
			}
			// nueva_jugada();
			break;
		case R.id.txtsector4:// corresponde al 4 sector luego cambiar nombre
			if (acierto == 4) {
				puntaje(10, sector4);
				soundPool.play(bien, 1, 1, 1, 0, 1);
			} else {
				errores += 1;
				soundPool.play(mal, 1, 1, 1, 0, 1);
				puntaje(-10, sector4);
			}
			// nueva_jugada();
			break;
		case R.id.btnVolver:// vulve a el menu inicio,
			Context context = getApplicationContext();
			Intent i = new Intent(context, MainActivity.class);
			startActivity(i);
			break;

		}
		deshabilitabotones();
	}

	/*
	 * las 2 puntaje cuentan los puntos en el primero van los puntos y el sector
	 * que se pincho y en el segundo solo los puntos no hay necesidad de pintar
	 * rojo vino por no contestar a tiempo
	 */
	private void puntaje(int valor, TextView sector) {
		puntos += valor;// suma el valor recibido
		time = 0;// para la cuenta del tiempo

		estado = 0;// ?
		// coloca el nuevo puntaje en el placard
		String str = Integer.toString(puntos);
		placard.setText(str);

		// si es acierto o no valor positivo o negativo
		if (valor > 0) {
			sector.setBackgroundColor(Color.GREEN);// dio bien pinta verde
			respuesta.setVisibility(View.VISIBLE);// Diana
			respuesta.setImageResource(getBaseContext().getResources()
					.getIdentifier("correct", "drawable", getPackageName()));
			estado = 1;// ?
			guardaraciertos(1, nivel, temasortedo);
		} else {
			sector.setBackgroundColor(Color.RED);// dio mal pinta rojo
			respuesta.setVisibility(View.VISIBLE);// Diana
			respuesta.setImageResource(getBaseContext().getResources()
					.getIdentifier("wrong", "drawable", getPackageName()));// Diana
			revisarelcorrecto();// pinta el correcto
		}
		// si la cantidad de errores es inadmisible
		// empieza el camino a Entrada
		if (errores > cantidaderrores) {
			estado = 0;
			guardaraciertos(0, nivel, temasortedo);
			pasar_final();
			return;
		} else {
			nueva_jugada();
			estado = 1;// ?
		}

	}

	// esta sobreescrita por si no hay click y si demora
	private void puntaje(int valor) {
		puntos += valor;// suma el valor que es negativo

		deshabilitabotones();

		time = 0;// para la cuenta
		estado = 0;// ?
		guardaraciertos(0, nivel, temasortedo);// siempre guarda el error

		String str = Integer.toString(puntos);// placard
		placard.setText(str);

		revisarelcorrecto();// pinta el correcto en verde
		// ponermensaje("errores =" + errores);
		if (errores > cantidaderrores) {// si son mas de los errores permitidos
										// va a otra activity
			estado = 0;// ?
			errores = 0;
			pasar_final();
			return;
		} else {
			estado = 1;// ?
			nueva_jugada();
		}
	}

	/*
	 * coloca verde en la pregunta correcta Diana si queres podes hacer un verde
	 * mas transparente o un marco A Betty y a mi nos pareció correcto que
	 * exista
	 */
	private void revisarelcorrecto() {

		if (acierto == 1) {
			sector1.setBackgroundColor(Color.GREEN);// Diana
			return;
		}
		if (acierto == 2) {
			sector2.setBackgroundColor(Color.GREEN);// Diana
			return;
		}
		if (acierto == 3) {
			sector3.setBackgroundColor(Color.GREEN);// Diana
			return;
		}
		if (acierto == 4) {
			sector4.setBackgroundColor(Color.GREEN);// Diana
			return;
		}
	}

	/*
	 * pasa una activity Entrada porque hay errore demais
	 */
	public void pasar_final() {
		// me avisa de los errores
		// Diana si queres cambiar con la imarall de un lloron totalmente de
		// acuerdo
		ponermensaje("Lo siento lleguaste al máximo de errores");

		errores = 0;// por las dudas

		//estadistica();// llena la variable misdatos

		time = -3;// por las dudas no se superponga con la rutina de tiempo
		estado = 0;// por las dudas con el uso se vera su necesidad

		Intent te = new Intent(this, Entrada.class);
		te.putExtra("puntos", puntos);// envia puntos del juego
		te.putExtra("misdatos", estadistica()); // envia estadística de lo contestado
		startActivity(te);
		mp.stop();// Diana para la musica
		finish();
	}

	/*
	 * prepara una nueva pregunta llenando nuevamente las variables pregunta
	 * cuerto error etc
	 */
	public void nueva_jugada() {
		estado = 1;// ?
		acierto = 0; // limpia el valor por las dudas
		desarmar(leerlinea());// pone en variables
		time = -3;// permite empezar a contar -3 porque da unos segundos para
					// ver lo anterior
	}

	/*
	 * método para colocar los strings en las ventanas y definir con la variable
	 * acierto(1,2,3,4) cual es la correcta
	 */
	public void colocar(String orden) {
		// coloca la pregunta leida
		secpregunta.setText(pregunta);// coloca la pregunta
		habilitabotones();
		// en funcion de la ubicación de la letra "c" y ..
		// analizamos cada ubicacion de los digitos
		// colocamos las string en los text y si corresponde el acierto
		String primeraletra = orden.substring(0, 1);
		if (primeraletra.equals("c")) {
			sector1.setText(cierto);
			acierto = 1;// "sector1";
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
			acierto = 2;// "sector2";
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
			acierto = 3;// "sector3";
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
			acierto = 4;// "sector4";
		}
		if (cuartaletra.equals("1"))
			sector4.setText(error1);
		if (cuartaletra.equals("2"))
			sector4.setText(error2);
		if (cuartaletra.equals("3"))
			sector4.setText(error3);
	}

	/*
	 * Aqui se leeria de acuerdo al nuivel elegido es nuevo
	 */

	private String leerlinea() {

		int lineaelegida = 0;
		int numlineas = cantidaddepreguntas;

		lineaelegida = (int) (Math.random() * numlineas + 1);

		// lee un archivo de nombre cuatrorespuestas previamente colocado
		try {
			InputStream fraw = getResources().openRawResource(id);
			// R.raw.cuatrorespuestas);// mundiales es el archovo de texto
			// que lee
			int a = 0;
			BufferedReader brin = new BufferedReader(
					new InputStreamReader(fraw));
			while ((linea = brin.readLine()) != null) {
				a += 1;
				if (a == lineaelegida) {
					fraw.close();
					return linea;
				}
			}
			fraw.close();
		} catch (Exception ex) {
			Log.e("Ficheros", "Error al leer fichero desde recurso raw");// para
																			// la
																			// depuracion
		}
		return linea;
	}

	// transforma la linea en una matriz y llena variables String parts
	private void desarmar(String datoleido) {
		String[] parts = datoleido.split(";");
		// 0 es numero de pregunta 1 la pregunta ...
		cierto = parts[2]; // cierto
		error1 = parts[3];// error1
		error2 = parts[4];// error2
		error3 = parts[5];// error3
		nivel = parts[7];
		pregunta = parts[1];
		temasortedo = parts[6];
	}

	/*
	 * En funcion del nivel elegido y rescatado en las preferencias se define el
	 * tiempo y la cantidad de errores El nivel lo d ael archivo de txt que lee
	 */

	public void cargarPreferencias() {
		SharedPreferences prefs = getSharedPreferences("preferenciasMiApp",
				Context.MODE_PRIVATE);
		int nivel_general = prefs.getInt("nivel", 2);
		switch (nivel_general) {
		case 1:
			time_elegido = 40;
			cantidaderrores = 4;
			nomarchivo = "deporbasico";
			break;
		case 2:
			time_elegido = 30;
			cantidaderrores = 3;
			nomarchivo = "depormedio";
			break;
		case 3:
			time_elegido = 20;
			cantidaderrores = 2;
			nomarchivo = "deportes_numerados";
			break;
		default:
			time_elegido = 30;
			cantidaderrores = 3;
			nomarchivo = "depormedio";
			break;
		}

	}

	/*
	 * calcula la cantidad de preguntas Al usar mas archivos tengo que modificar
	 * el metodo en funcion del nivel elegido
	 */
	private int totaldepreguntas() {
		// calcula el numero de lineas en el archivo cuatropreguntas y/o
		// mundiales ojos
		int a = 0;
		try {
			InputStream fraw = getResources().openRawResource(id);

			BufferedReader brin = new BufferedReader(
					new InputStreamReader(fraw));
			while ((linea = brin.readLine()) != null) {
				a += 1;
			}
			fraw.close();
		} catch (Exception ex) {
			Log.e("Ficheros", "Error al leer fichero desde recurso raw");
		}
		return a;
	}

	private void ponermensaje(CharSequence mensaje) {
		/* es auxiliar para poner mensaje en pantalla */
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(this, mensaje, duration);
		toast.show();
	}

	/*
	 * Los métodos siguientes evitan el click consecutivo No se si con la imarall
	 * superpuesta de Diana son ahora necesario spero...
	 */

	private void deshabilitabotones() {

		sector1.setEnabled(false);
		sector2.setEnabled(false);
		sector3.setEnabled(false);
		sector4.setEnabled(false);
	}

	private void habilitabotones() {

		sector1.setEnabled(true);
		sector2.setEnabled(true);
		sector3.setEnabled(true);
		sector4.setEnabled(true);

	}

	/*
	 * es una forma que invente para clasificar los niveles de la pregunta
	 * cantida de aciertos o errores por tema y tiempo promedio luego en otro
	 * metodo se usa esta informacion (Estadística)
	 */
	private void guardaraciertos(int acierto, String nivel, String temasortedo) {

		// Estadisticas tenis
		if (temasortedo.equals("T")) {
			if (acierto == 1) {
				contadoraciertotenis += 1;
			} else {
				contadorerrortenis += 1;
			}
		}

		// Estadisticas Boxeo
		if (temasortedo.equals("Box")) {
			if (acierto == 1) {
				contadoraciertoboxeo += 1;
			} else {
				contadorerrorboxeo += 1;
			}
		}

		// Estadisticas Ciclismo
		if (temasortedo.equals("Cic")) {
			if (acierto == 1) {
				contadoraciertocic += 1;
			} else {
				contadorerrorcic += 1;
			}
		}

		// Estadisticas Fútbol
		if (temasortedo.equals("Fut")) {
			if (acierto == 1) {
				contadoraciertofut += 1;
			} else {
				contadorerrorfut += 1;
			}
		}

		// Estadisticas Formula 1
		if (temasortedo.equals("For")) {
			if (acierto == 1) {
				contadoraciertofor += 1;
			} else {
				contadorerrorfor += 1;
			}
		}

		// Estadisticas Rally
		if (temasortedo.equals("Rall")) {
			if (acierto == 1) {
				contadoraciertorall += 1;
			} else {
				contadorerrorrall += 1;
			}
		}

		// Estadisticas volley
		if (temasortedo.equals("Vol")) {
			if (acierto == 1) {
				contadoraciertovol += 1;
			} else {
				contadorerrorvol += 1;
			}
		}

		// Estadisticas Atletismo
		if (temasortedo.equals("ATL")) {
			if (acierto == 1) {
				contadoraciertoatl += 1;
			} else {
				contadorerroratl += 1;
			}
		}

		// Estadisticas Natacion
		if (temasortedo.equals("Nat")) {
			if (acierto == 1) {
				contadoraciertonat += 1;
			} else {
				contadorerrornat += 1;
			}
		}

		// Estadisticas Bas
		if (temasortedo.equals("Bas")) {
			if (acierto == 1) {
				contadoraciertobas += 1;
			} else {
				contadorerrorbas += 1;
			}
		}

		// Estadisticas motociclismo
		if (temasortedo.equals("Rot")) {
			if (acierto == 1) {
				contadoraciertomot += 1;
			} else {
				contadorerrormot += 1;
			}

		}

		// dificultad

		if (nivel.equals("A")) {
			altas += 1;
		}

		if (nivel.equals("M")) {
			medias += 1;
		}

		if (nivel.equals("B")) {
			basicas += 1;
		}

		// talvez el 20 cambiar por l avelocidad real
		contadorsegundos += (20 - Integer.parseInt((String) tiempo.getText()));
		cantidadpreguntas += 1;

	}

	/*
	 * Crea una string con los datos ya guardados
	 */
	private String estadistica() {
		// 0 o 1 por acierto cantidad y tema mas coma

		// aciertos y errores Rally
		String arall = "1" + contadoraciertorall + "Ral";
		String errorrall = "0" + contadorerrorrall + "Ral";

		// aciertos y errores boxeo
		String abox = "1" + contadoraciertoboxeo + "Box";
		String errorbox = "0" + contadorerrorboxeo + "Box";

		// aciertos y errores futbol
		String afut = "1" + contadoraciertofut + "Fut";
		String errorfut = "0" + contadorerrorfut + "Fut";

		// aciertos y errores Formula 1
		String afor = "1" + contadoraciertofor + "For";
		String errorfor = "0" + contadorerrorfor + "For";

		// aciertos y errores Ciclismo
		String acic = "1" + contadoraciertocic + "Cic";
		String errorcic = "0" + contadorerrorcic + "Cic";

		// aciertos y errores tenis
		String aten = "1" + contadoraciertotenis + "Ten";
		String errorten = "0" + contadorerrortenis + "Ten";

		// aciertos y errore volley
		String avol = "1" + contadoraciertovol + "Vol";
		String errorvol = "0" + contadorerrorvol + "Vol";

		// aciertos y errore atletismo
		String aatl = "1" + contadoraciertoatl + "Atl";
		String erroratl = "0" + contadorerroratl + "ATL";

		// aciertos y errore Natacion
		String anat = "1" + contadoraciertonat + "Nat";
		String errornat = "0" + contadorerrornat + "Nat";

		// aciertos y errores basquete
		String abas = "1" + contadoraciertobas + "Bas";
		String errorbas = "0" + contadorerrorbas + "Bas";

		// aciertos y errore motociclismo
		String amot = "1" + contadoraciertoatl + "Mot";
		String errormot = "0" + contadorerroratl + "Mot";

		// dificultad de las preguntas
		
		
		// promedios
		contadorsegundos=(contadorsegundos/cantidadpreguntas);

		misdatos = arall + "," + errorrall + "," + abox + "," + errorbox + ","
				+ afut + "," + errorfut + "," + afor + "," + errorfor + ","
				+ acic + "," + errorcic + "," + aten + "," + errorten + ","
				+ avol + "," +errorvol + ","+ aatl+ ","+ erroratl + ","
				+ anat + "," +errornat + ","+ abas + ","+ errorbas + ","
				+ amot + "," +errormot + ","
				+ altas + "," + medias + "," + basicas + "," + contadorsegundos;
		 //ponermensaje(misdatos);

		return misdatos;

	}

	/*
	 * En un futuro si suena el phone o se cambi ael sentido de la orientacion
	 * se guardarían aca los datos
	 */
	protected void onPause() {

		// TODO Auto-generated method stub
		super.onPause();
		if (mp.isPlaying())
			;// si esta prendida apaga la musica Revisar
		mp.stop();
		finish();// termina la activity para no dejar algun residuo
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