package com.ayd1.gestoreconomico;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Configura_usuario extends Activity {
	//creacion de botones
	public static Button btn_guardar;
	public static Button btn_cancelar;
	
	public static SQLiteDatabase manipulador_db;
	private EditText nombre;
	private EditText presu;
	//lista de nombres para monedas
	public static Spinner spr_moneda;
	public static String tipos_moneda[] = {"Q","$","€"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configura_usuario);
		db dbHelper = new db(getBaseContext());
		manipulador_db = dbHelper.getWritableDatabase();
		
		 btn_guardar = (Button) findViewById(R.id.btn_guardar);
		 btn_cancelar = (Button) findViewById(R.id.btn_cancelar);
		 
		 nombre = (EditText)findViewById(R.id.nombre);
		 presu = (EditText)findViewById(R.id.presu);
	  spr_moneda = (Spinner)findViewById(R.id.spr_monedas);
	   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tipos_moneda);
        spr_moneda.setAdapter(adapter);
        eventos();
        obtener_datos();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configura_usuario, menu);
		return true;
	}
	
	
	
	//declaracion de eventos
    public void eventos(){
    	//para enviar el activity a travez del evento de Onclick
    	final Activity atv=this;
   
    	//boton aceptar
    	btn_guardar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			         if(!btn_guardar.getText().equals(" ")  && !btn_cancelar.getText().equals(" ")){
			      
			          String dato ="UPDATE usuario SET nombre = '"+nombre.getText()+"';";
			           manipulador_db.execSQL(dato);
			           dato ="UPDATE usuario SET presupuesto = '"+presu.getText()+"';";
				       manipulador_db.execSQL(dato);
				       dato ="UPDATE usuario SET moneda = '"+spr_moneda.getSelectedItem().toString()+"';";
				       manipulador_db.execSQL(dato);
				       
				        	Toast.makeText(getBaseContext(), "DATOS GUARDADOS CORRECTAMENTE", Toast.LENGTH_LONG).show();  
			         }else{
			        	 
			        	 Toast.makeText(getBaseContext(), "TODOS LOS CAMPOS SON OBLIGATORIOS", Toast.LENGTH_LONG).show();
			        	
			         }
				
			}
        });
    	
    	//boton cancelar
    	btn_cancelar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Configura_usuario.this, configuraciones.class );
                setResult( Activity.RESULT_CANCELED, i );
                Configura_usuario.this.finish();
           }
        });
    	
    }
    
  
    
    private void obtener_datos(){
    	Cursor c = manipulador_db.rawQuery("SELECT nombre,presupuesto,moneda FROM usuario",null);
    	String[] datos=null;
    	if (c.moveToFirst()) {
    		datos = new String[3];
    		do{
    		datos[0]=c.getString(0);
    		datos[1]=c.getString(1);
    		datos[2]=c.getString(2);
	}while(c.moveToNext());
    		
    		nombre.setText(datos[0]);
        	presu.setText(datos[1]);
        	
        	if(datos[2].charAt(0)== 'Q'){
        		spr_moneda.setSelection(2);
        	}
        	else if(datos[2].charAt(0)== '$'){
        		spr_moneda.setSelection(2);
        	}
        	else{
        		spr_moneda.setSelection(1);
        	}
    	
    	}
    	
    	
    	
    	
    }
	
	
}
