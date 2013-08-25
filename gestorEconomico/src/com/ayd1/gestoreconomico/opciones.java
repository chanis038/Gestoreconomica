package com.ayd1.gestoreconomico;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class opciones extends Activity {
	//declaracion publica de botones
	public static Button btn_gastos_diarios;
	public TextView presupuesto;
	public TextView saldo;
	//id publicas para activitys
	public static int INT_GASTOS_DIARIOS=0;
	//manipulador de base de datos
	public static SQLiteDatabase manipulador_db;
	
    @Override
    //--
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        //cargando la base de datos
        db dbHelper = new db(getBaseContext());
        manipulador_db = dbHelper.getWritableDatabase();
        Toast.makeText(getBaseContext(), "Base de datos preparada", Toast.LENGTH_LONG).show();
        //asignacion de botones de activity a variables boton
        btn_gastos_diarios = (Button) findViewById(R.id.btn_gastosdiarios);
        presupuesto = (TextView) findViewById(R.id.muestra_presu);
        saldo = (TextView) findViewById(R.id.saldo);
        //llamada a asignacion de eventos
        eventos();
        muestr_presu();
    }	
 // metodo para mostar presupuesto en pantalla
    private void muestr_presu(){
    	String[] presu = getNombreCostos("Select presupuesto  From usuario");
    	if(presu != null){
    	presupuesto.setText(presu[0]);
    	}
    	else{
    		btn_gastos_diarios.setEnabled(false);
        presupuesto.setText("No configurado");	
        saldo.setText("No configurado");	
    	}
    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;
    }*/
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Idata) {
         if ( requestCode == INT_GASTOS_DIARIOS ){
              if ( resultCode == Activity.RESULT_OK ){
            	  mensaje(this, null, "", "ingreso de Gastos Diarios exitoso", "");
              }else if(resultCode==Activity.RESULT_CANCELED){
            	  mensaje(this, null, "", "Cancelo ingreso de Gastos Diarios", "");
              }else if(resultCode==Activity.RESULT_FIRST_USER){
            	  mensaje(this, null, "", "Fallo ingreso de Gastos Diarios", "");
              }
         }
    }
    
    //declaracion de eventos
    public void eventos(){
    	btn_gastos_diarios.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//carga activity de gastos diarios
				Intent i = new Intent( opciones.this, GastosDiarios.class );
				//posible opcion de envio de parametros
				//i.putExtra("parametro", "valor");
				opciones.this.startActivityForResult(i, INT_GASTOS_DIARIOS);
			}
        });
    }
    
  //metodos para recuperar datos
    public static String[] getNombreCostos(String query){
    	Cursor c = manipulador_db.rawQuery(query,null);
    	//Nos aseguramos de que existe al menos un registro
    	int registros=c.getCount();
    	int puntero=0;
    	String[] vector=null;
    	if(registros>0)
    		vector=new String[registros];
    	if (c.moveToFirst()) {
    	     //Recorremos el cursor hasta que no haya más registros
    	     do {
    	          vector[puntero] = c.getString(0);
    	          puntero++;
    	     } while(c.moveToNext());
    	}
    	return vector;
    }
    
    
    //insertar informacion en base de datos
    public static boolean insert(String tabla, ContentValues parametros){
    	return (manipulador_db.insert(tabla, null, parametros) > 0); 
    }
    
    //crea un mensaje en pantalla
  	public static void mensaje(final Activity atv, DialogInterface.OnClickListener listen, String title, String mensaje, String boton){
  		if(listen==null && !title.equals("") && !boton.equals("") && !mensaje.equals("")){
  			toad(atv, title, mensaje, boton);
  		}else if(listen==null && title.equals("") && boton.equals("") && !mensaje.equals("")){
  			simpletoad(atv, mensaje);
  		}else if(!mensaje.equals("")){
  			AlertDialog.Builder builder = new AlertDialog.Builder(atv);
  	        builder.setCancelable(false);
  	        builder.setTitle(title);
  	    	builder.setMessage(mensaje);
  	    	builder.setNeutralButton(boton, listen);
  	    	AlertDialog alert = builder.create();
  	    	alert.show();
  		}
  	}
  	
  	
  	
  	//implemantado
  	public static void toad(Activity atv, String title, String mensaje, String boton){
		new AlertDialog.Builder(atv).setTitle(title).setMessage(mensaje).setNeutralButton(boton, null).show();
	}
	
	public static void simpletoad(Activity atv, String mensaje){
		Toast toast = Toast.makeText(atv.getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
		toast.show();
	}

}
