package com.example.trivia;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/* esta es la Activity inicial
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	/* defino las variables generales */
	// objetos widgates
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	// Diana Button btnsonido;
	Button btnsalir;

	// variable usada para almacenar el puntaje
	static AlmacenPuntuacionesSQLite almacen;

	int nivel;// usada para las preferencias por ahora no se usa pero nadie se
				// da cuenta :)

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// elimina barra 
		setContentView(R.layout.activity_main);// Set the user interface layout
												// for this Activity

		// instancia las variables
		// Initialize variables so we can manipulate it later
		button1 = (Button) findViewById(R.id.btnaPuntaje);// jugar
		button2 = (Button) findViewById(R.id.btnCancelar);// Opciones
		button3 = (Button) findViewById(R.id.button3);// Puntaje
		button4 = (Button) findViewById(R.id.button4);// Acerca de
		// btnsonido= (Button) findViewById(R.id.btnsonido);// Sonido Diana
		btnsalir = (Button) findViewById(R.id.btnsalir);

		// pongo los Listeners
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		// btnsonido.setOnClickListener(this);Diana
		btnsalir.setOnClickListener(this);

		// inicializa un objeto para almacenar los puntos
		almacen = new AlmacenPuntuacionesSQLite(this, "puntuaciones", null, 1);

		// carga las preferencias (dificultad y musica)
		cargarpreferencias();

	}

	// esto no iria en esta activity
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * Relaciona el boton apretado Los nombres no son los apropiados pero
	 * funcionan
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnaPuntaje:// lanza el juego
			lanzarJuego();
			break;
		case R.id.btnCancelar:// ajustes nuvel del juego y borra el puntaje
			lanzarAjustes();
			break;
		case R.id.button3:// lanzar las puntuaciones
			lanzarPuntuaciones();
			break;
		case R.id.button4:// lanzar Acerca de ...( Conversar esta modificado por
							// test)
			lanzarAcercade();
			break;
		case R.id.btnsalir:// prende o apaga el sonido
			finish();
			break;
		}
	}

	/*
	 * Los métodos que siguen corresponden a los botones principales y envian
	 * otra activity
	 */
	// A la cancha
	private void lanzarJuego() {
		Intent i = new Intent(this, Cancha.class);
		startActivity(i);
	}

	// Ajustes Nivel del juego
	private void lanzarAjustes() {
		Intent i = new Intent(this, Ajustes.class);
		startActivity(i);
	}

	private void lanzarPuntuaciones() {
		Intent i = new Intent(this, Puntuaciones.class);
		startActivity(i);
	}

	private void lanzarAcercade() {
		Intent i = new Intent(this, Otras_apps.class);// cambie Otras_apps
		startActivity(i);
	}

	// Este método carga las preferencias o por defecto pone nivel medio y
	// musica
	private void cargarpreferencias() {
		SharedPreferences prefs = getSharedPreferences("preferenciasMiApp",
				Context.MODE_PRIVATE);

		// por si no hay coloca vel 
		nivel = prefs.getInt("nivel", 2);// 1=basico 2=medio 3=alto
	}

	private void mensaje(String mensaje) {
		Context context = getApplicationContext();
		String text = mensaje;
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

}
