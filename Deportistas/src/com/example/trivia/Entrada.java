package com.example.trivia;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author ricardo
 *
 */
public class Entrada extends Activity implements OnClickListener{
	/* botones widgeta*/
	TextView textView;
	Button btnAceptar;
	EditText editText;
	Button btnSalir;
	Button btnMenu;
	int mispuntos;
	String misdatos;
	String categoria;

	Boolean correcto;
	
	/*sonidos aplausos y error*/
	SoundPool soundPool;
	int mal,aplausos;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// elimina barra 
		setContentView(R.layout.entrada);

		// instancio las variables
		textView=(TextView) findViewById(R.id.txtentrada);
		btnAceptar=(Button) findViewById(R.id.btnAceptar);
		btnSalir=(Button) findViewById(R.id.btnsalir);
		editText=(EditText) findViewById(R.id.editTextopp1);
		btnMenu=(Button) findViewById(R.id.btnentrada_cancha);

		// escuchas
		btnAceptar.setOnClickListener(this);
		btnSalir.setOnClickListener(this);
		btnMenu.setOnClickListener(this);

		soundPool = new SoundPool( 5, AudioManager.STREAM_MUSIC , 0);
		aplausos=soundPool.load(this, R.raw.aplausos,0);
		mal=soundPool.load(this, R.raw.error,0);// Poner otro texto
		editText.setVisibility(View.INVISIBLE);
		botonesVisibles(cargartexto());
		
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btnAceptar:
				// creo que correcto no seria necesario pero por las dudas espero probar bien
				if (correcto == true) {
					soundPool.play(aplausos, 1, 1, 1, 0, 1);
					categoria = misdatos;
					MainActivity.almacen.guardarPuntuacion(mispuntos, editText.getText()
							.toString(), fechaCorta(), categoria);
					Intent i = new Intent(this, Puntuaciones.class);
					startActivity(i);}
					break;
			case R.id.btnsalir:
					Context context = getApplicationContext();
					Intent i = new Intent(context, MainActivity.class);
					startActivity(i);
					break;
			case R.id.btnentrada_cancha:
					Intent ie = new Intent(this, Cancha.class);
					startActivity(ie);
					break;
			default:
					break;
			}

	}
	
	/* recibe los valores y decide si entr ao no
	 * en la lista top10)
	 */
	private boolean cargartexto() {
		// lee los datos que vienen de cancha
		Bundle extras = getIntent().getExtras();
		mispuntos = extras.getInt("puntos");// recibe los puntos
		misdatos = extras.getString("misdatos");// recibe los dastos
		String str = "";
		
		// si el valor es muy bajo lo hecho por inútil (por ahora 10 luego subir)
		if (mispuntos<10){
			str = "Lo siento tu puntaje es muy bajo para entrar en la lista \n "
					+ Integer.toString(mispuntos) + "puntos";
			textView.setText(str);
			correcto = false;
		return correcto;	
			
		}
		
		// solicita un método para evaluar el puntaje
		if (MainActivity.almacen.estoyTop(10, mispuntos)) {
				str =  "Felicitaciones ingresa tu nombre entre los mejores.";
				// deja visible el cuadro para escribir
				botonesVisibles(true);
				correcto = true;
		} else {
				str = "Lo siento no da el puntaje para entrar entre los mejores";
				
				// volver a inicio
				btnAceptar.setText("Volver");
				correcto = false;
			}
		return correcto;
		
}

	
	/* esta funcion es para formatear la fecha
	 * actual del sistema
	 * */
	public String fechaCorta() {
		long tim = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyy");
		String curTime = df.format(tim);
		return curTime;
	}
	
	
	/* dejan visible una opcion tue
	 * invisible otra (false)
	 * Se usa para el texto y el boton de pasar a untaje
	 */
	public void botonesVisibles(boolean visible){
		if (visible){
			editText.setVisibility(View.VISIBLE);
			btnAceptar.setVisibility(View.VISIBLE);
		}else{
			editText.setVisibility(View.INVISIBLE);
			btnAceptar.setVisibility(View.INVISIBLE);
		}
	}
	
	
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
