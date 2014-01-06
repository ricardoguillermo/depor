package com.example.trivia;

import com.example.trivia.R.id;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Ajustes extends Activity implements OnClickListener {
	RadioGroup radioGroup;
	RadioButton radioButton0;
	RadioButton radioButton1;
	RadioButton radioButton2;
	Button btnaceptarcambios;
	Button btnCancelar;
	CheckBox checkBox;

	int nivel;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// elimina barra 
		setContentView(R.layout.ajustes);
		// instancio widgets
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioButton0 = (RadioButton) findViewById(R.id.radio0);
		radioButton1 = (RadioButton) findViewById(R.id.radio1);
		radioButton2 = (RadioButton) findViewById(R.id.radio2);
		btnaceptarcambios = (Button) findViewById(R.id.btnaceptarcambios);
		btnCancelar = (Button) findViewById(R.id.btnCancelar);
		checkBox =(CheckBox) findViewById(R.id.checkBox1);

		radioButton0.setOnClickListener(this);
		radioButton1.setOnClickListener(this);
		radioButton2.setOnClickListener(this);
		btnaceptarcambios.setOnClickListener(this);
		btnCancelar.setOnClickListener(this);
		
		colocaranteriores();// ubica el radio en el nivel y aelegido

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnaceptarcambios:
			revisar();
			guardarPreferencias();
			Intent s = new Intent(this, MainActivity.class);
			startActivity(s);
			break;
		case R.id.btnCancelar:
			Intent se = new Intent(this, MainActivity.class);
			startActivity(se);
		default:
			break;
		}
	}

	public void guardarPreferencias() {
		SharedPreferences prefs = getSharedPreferences("preferenciasMiApp",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		editor.putInt("nivel", nivel);
		editor.commit();
	}

	private void revisar() {

		if (radioButton0.isChecked())// nivel de preguntas
			nivel = 1;
		if (radioButton1.isChecked())
			nivel = 2;
		if (radioButton1.isChecked())
			nivel = 3;
		if (checkBox.isChecked())
			MainActivity.almacen.borrarregistros();
	}

	
	private void colocaranteriores(){

		SharedPreferences prefs = getSharedPreferences("preferenciasMiApp",
				Context.MODE_PRIVATE);
		
		// recibe el nivel ya fijado y si no la hay pone 2
		int velo = prefs.getInt("nivel",2);
		switch (velo) {
		case 1:
			radioButton0.setChecked(true);
			break;
		case 2:
			radioButton1.setChecked(true);
			break;
		case 3:
			radioButton2.setChecked(true);
			break;

		default:
			break;
		}
	}
	
}
