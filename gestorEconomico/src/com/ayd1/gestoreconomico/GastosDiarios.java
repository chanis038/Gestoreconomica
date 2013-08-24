package com.ayd1.gestoreconomico;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GastosDiarios extends Activity {
	//lista de nombres para gasto
	public static Spinner spr_nombre;
	//declaracion publica de botones
	public static Button btn_agregar_nombre;
	public static Button btn_aceptar;
	public static Button btn_cancelar;
	//declaracion publica de txt
	public static EditText txt_agregar_nombre;
	public static EditText txt_costo;
	public static EditText txt_descripcion;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gastos_diarios);
		//asignacion de botones de activity a variables boton
		btn_agregar_nombre = (Button) findViewById(R.id.btn_agregar_nombre);
		btn_cancelar = (Button) findViewById(R.id.btn_cancelar);
		btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
		//asignacion de txt de activity a variables txt
		txt_agregar_nombre = (EditText) findViewById(R.id.txt_agregar_nombre);
		txt_costo = (EditText) findViewById(R.id.txt_costo);
		txt_descripcion = (EditText) findViewById(R.id.txt_descripcion);
		//cargando ID lista nombres a spinner de lista de nombres
		spr_nombre = (Spinner) findViewById(R.id.spr_nombre);
		//llamada a asignacion de eventos
        eventos();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gastos_diarios, menu);
		return true;
	}
	
	//declaracion de eventos
    public void eventos(){
    	//para enviar el activity a travez del evento de Onclick
    	final Activity atv=this;
    	btn_agregar_nombre.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//agregando nombre
				agregar_nombre();
			}
        });
    	//boton aceptar
    	btn_aceptar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				agregar_costo();
			}
        });
    	//boton cancelar
    	btn_cancelar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent( GastosDiarios.this, Gestor.class );
                setResult( Activity.RESULT_CANCELED, i );
                GastosDiarios.this.finish();
           }
        });
    	//llenando lista de nombres de costos
    	set_adapter_nombre(this,"");
    }
    
	//etiquetas creadas por el usuario
    public void set_adapter_nombre(Activity atv, String seleccion){
    	boolean vacio=false;
    	//lista de nombres de gastos
    	String[] nombres=opciones.getNombreCostos("SELECT nombre FROM nombre");
    	//item seleccionado
    	int select=0;
    	
        if(nombres==null)
        	vacio=true;
        else{
        	for(int j=0; j<nombres.length; j++){
        		if(nombres[j].equals(seleccion))
        			select=j;
        	}
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(atv, android.R.layout.simple_list_item_1, nombres);
        spr_nombre.setAdapter(adapter);
        if(vacio){
        	//Lista de nombres de gastos vacia
        	spr_nombre.setVisibility(View.GONE);
        	opciones.mensaje(GastosDiarios.this, null, "", "Base de datos vacia", "");
        	//mensaje
        }else{
        	//Lista de nombres con parametros
        	spr_nombre.setVisibility(View.VISIBLE);
        }
        spr_nombre.setSelection(select, false);
    }
    
    public void agregar_nombre(){
    	String nuevo_nombre=txt_agregar_nombre.getText().toString();
    	final Activity atv=this;
		if(!nuevo_nombre.equals("")){
			//si es un nuevo nombre distinto de vacio, se agrega a la base de datos
			
			ContentValues parametros = new ContentValues();   
		    parametros.put("nombre",nuevo_nombre);
		    boolean resultado=opciones.insert("nombre",parametros);
		    //acutalizando lista de nombres
		    if(resultado)
		    	set_adapter_nombre(atv,nuevo_nombre);
		}else{
			opciones.mensaje(GastosDiarios.this, null, "", "Ingrese un nombre", "");
		}
    }
    
    public void agregar_costo(){
    	String costo=txt_costo.getText().toString();
    	String descripcion=txt_descripcion.getText().toString();
    	String nombre=spr_nombre.getSelectedItem().toString();
    	//agregando a la base de datos
    	final Activity atv=this;
    	//agregando nombre
		String nuevo_nombre=txt_agregar_nombre.getText().toString();
		if(!nuevo_nombre.equals("")){
			//si es un nuevo nombre distinto de vacio, se agrega a la base de datos
			
			ContentValues parametros = new ContentValues();   
		    parametros.put("nombre",nuevo_nombre);
		    boolean resultado=opciones.insert("nombre",parametros);
		    //acutalizando lista de nombres
		    if(resultado)
		    	set_adapter_nombre(atv,nuevo_nombre);
		}
		
		ContentValues parametros = new ContentValues();   
		parametros.put("nombre",nombre);
		parametros.put("tipo","diario");
		parametros.put("costo",costo);
		parametros.put("descripcion",descripcion);
		boolean resultado=opciones.insert("gasto",parametros);
		//cerrando la ventana
		Intent i = new Intent( GastosDiarios.this, Gestor.class );
		if(resultado){
            setResult( Activity.RESULT_OK, i );
		}else{
            setResult( Activity.RESULT_FIRST_USER, i );
		}
		GastosDiarios.this.finish();
    }

}
