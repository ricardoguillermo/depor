package com.example.trivia;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Puntuaciones extends ListActivity implements OnClickListener{
	
	MiAdaptador adapter=new MiAdaptador(this,
            MainActivity.almacen.listaPuntuaciones(10,"chinito sabe"));
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String posible=String.valueOf(position);
		Intent i = new Intent(this, Resumen.class);
		i.putExtra("posicion", posible);
		startActivity(i);
	}

	Button menuinicial;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// elimina barra 
        setContentView(R.layout.puntuaciones);
        setListAdapter(adapter);
    }

	
	/* 
	 falta el boton de home?
	 */
	public void onClick(View v) {
	/*switch (v.getId()) {
	case R.id.btnVolverMenu:
		Context context = getApplicationContext();
		Intent i = new Intent(context, MainActivity.class);
		startActivity(i);
		break;
	
	default:
		break;
	}
*/}
	
	// cerrar la activity
	protected void onPause() {
		super.onPause();
		finish();
	}
	
}