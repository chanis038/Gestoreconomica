package com.ayd1.gestoreconomico;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Gestor extends Activity {
	//declaracion publica de botones
	public static Button btn_gastos;
	public static Button btn_configura;
	
	//id publicas para activitys
	public static int INT_GASTOS_DIARIOS=0;
	//manipulador de base de datos
	public static SQLiteDatabase manipulador_db;
	
    @Override
    //--
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor);

        //asignacion de botones de activity a variables boton
        btn_gastos = (Button) findViewById(R.id.btn_Mgastos);
        btn_configura = (Button) findViewById(R.id.btn_config);
        //llamada a asignacion de eventos
        eventos();
    }	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gestor, menu);
        return true;
    }
 
    //declaracion de eventos
    public void eventos(){
    	btn_gastos.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//carga activity de gastos diarios
				Intent i = new Intent( Gestor.this,opciones.class );
				//posible opcion de envio de parametros
				//i.putExtra("parametro", "valor");
				Gestor.this.startActivityForResult(i, INT_GASTOS_DIARIOS);
			}
        });
    	
    	btn_configura.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View f) {
				//carga activity de gastos diarios
				Intent i = new Intent( Gestor.this,configuraciones.class );
				//posible opcion de envio de parametros
				//i.putExtra("parametro", "valor");
				Gestor.this.startActivityForResult(i, INT_GASTOS_DIARIOS);
			}
        });
    }
    
  
    
    
  	
  
}
