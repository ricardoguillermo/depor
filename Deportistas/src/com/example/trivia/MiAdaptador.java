package com.example.trivia;

import java.util.Vector;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MiAdaptador extends BaseAdapter {
	private final Activity actividad;
    private final Vector<String> lista;
    
    public MiAdaptador(Activity actividad, Vector<String> lista) {
        super();
        this.actividad = actividad;
        this.lista = lista;
    }

	
	public int getCount() {
		return lista.size();
		}
	
	
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lista.elementAt(arg0);
	}
	
	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = actividad.getLayoutInflater();
        View view = inflater.inflate(R.layout.elemento_lista, null, true);
        TextView textView =(TextView)view.findViewById(R.id.titulo);
        
        
        textView.setText(lista.elementAt(position));
        ImageView imageView=(ImageView)view.findViewById(R.id.icono);
        TextView textViewsub =(TextView)view.findViewById(R.id.subtitulo);
        //TextView txtestadistica=(TextView)view.findViewById(R.id.txtestadistica);
        //textViewsub.setTextSize(R.dimen.chiquito);
        //txtestadistica.setTextSize(R.dimen.chiquito);
       // textView.setTypeface(null, Typeface.BOLD);
        
       
        
        // hacer loop
        // en este esquema comente todas las imágenes y ...
        switch (position) {
		case 0:
			textViewsub.setText("Genial sos el uno");
            imageView.setImageResource(R.drawable.trofeo);
            textView.setBackgroundColor(Color.BLUE);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
            textViewsub.setTextSize(15);
            
			break;
		case 1:
			textViewsub.setText("Muy bueno");
			//txtestadistica.setText("Estadística");
			imageView.setImageResource(R.drawable.mateoro);
            textViewsub.setTextSize(9);
            textView.setTextSize(12);
            
            break;
		case 2:
			textViewsub.setText("Adelante");
            imageView.setImageResource(R.drawable.medallabornce);
            //txtestadistica.setText("Estadística");
            textViewsub.setTextSize(9);
            textView.setTextSize(10);
			break;	
		case 3:
			textViewsub.setText("Fuerza che!!");
			//txtestadistica.setText("Estadística");
			textViewsub.setTextSize(9);
			textView.setTextSize(10);
			break;
		case 4:
			textViewsub.setText("Fuerza che!!");
			imageView.setImageResource(R.drawable.diploma);
			textViewsub.setTextSize(9);
			textView.setTextSize(10);
			break;
		case 5:
			textViewsub.setText("Premio Vino Tinto");
			imageView.setImageResource(R.drawable.botellavino);
			//txtestadistica.setText("Estadística");
			textViewsub.setTextSize(9);
			textView.setTextSize(10);
			break;
		case 6:
			textViewsub.setText("Premio Chivito Canadiense");
			imageView.setImageResource(R.drawable.hamburguesa);
			//txtestadistica.setText("Estadística");
			textViewsub.setTextSize(9);
			textView.setTextSize(10);
			break;	
		default:
			textViewsub.setText("No aflojar");
			//txtestadistica.setText("Estadística");
			//textViewsub.setTextSize(R.dimen.chiquito);
			imageView.setImageResource(R.drawable.diploma);
			textViewsub.setTextSize(9);
			textView.setTextSize(10);
			break;
		}
     
        return view;
	}

}
