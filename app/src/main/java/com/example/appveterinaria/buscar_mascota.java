package com.example.appveterinaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class buscar_mascota extends AppCompatActivity {

    conexionSQLiteHelper conexion;

    EditText etIdBuscado, etTipo, etRaza, etNombre,etPeso, etColor;

    Button btBuscar, btClean,btActualizarMascota, btEliminarDatosMascota, btRegresarActivityRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_mascota);

        loadUI();

        //  Abrimos la conexion
        conexion = new conexionSQLiteHelper(getApplicationContext(),"veterinaria", null,1);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarMascota();
            }
        });

        btClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciar();
            }
        });

        btActualizarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCamposActualizacion();
            }
        });
        
        btEliminarDatosMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCamposEliminacion();
            }
        });

        btRegresarActivityRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),registrarMascota.class));
            }
        });
    }

    private void validarCamposEliminacion() {
        String tipo, raza, nombre, color;
        int peso, idbuscareliminar;

        tipo = etTipo.getText().toString();
        nombre = etNombre.getText().toString();
        raza = etRaza.getText().toString();
        color = etColor.getText().toString();

        peso = (etPeso.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etPeso.getText().toString());
        idbuscareliminar = (etIdBuscado.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etIdBuscado.getText().toString());

        if (tipo.isEmpty() || nombre.isEmpty() || raza.isEmpty()|| color.isEmpty() || peso == 0 || idbuscareliminar == 0){
            notificar("No hay ID por eliminar");
            etIdBuscado.requestFocus();
        }else {
            preguntarEliminacion();
        }
    }

    private void preguntarEliminacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Esta seguro de eliminar los datos de la mascota?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminarDatosMascota();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    private void eliminarDatosMascota() {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String[] campoCriterio = {etIdBuscado.getText().toString()};

        int filasEliminadas = db.delete("mascotas","idmascota=?", campoCriterio);

        if (filasEliminadas > 0){
            notificar("Datos de la mascota eliminados con éxito");
            reiniciar();
        }else {notificar("Error al eliminar los datos de mascota");}

    }

    private void validarCamposActualizacion() {
        String tipo, raza, nombre, color;
        int peso, idbuscaractualizar;

        tipo = etTipo.getText().toString();
        nombre = etNombre.getText().toString();
        raza = etRaza.getText().toString();
        color = etColor.getText().toString();

        peso = (etPeso.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etPeso.getText().toString());
        idbuscaractualizar = (etIdBuscado.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etIdBuscado.getText().toString());

        if (tipo.isEmpty() || nombre.isEmpty() || raza.isEmpty()|| color.isEmpty() || peso == 0 || idbuscaractualizar == 0){
            notificar("No hay ID por actualizar");
            etIdBuscado.requestFocus();
        }else {
            preguntarActualizacion();
        }
    }

    private void preguntarActualizacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Esta seguro de actualizar los datos de la mascota?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actualizarMascota();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }


    private void actualizarMascota() {
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("tipo",etTipo.getText().toString());
        valores.put("raza",etRaza.getText().toString());
        valores.put("nombre",etNombre.getText().toString());
        valores.put("peso",etPeso.getText().toString());
        valores.put("color",etColor.getText().toString());

        String[] campoCriterio = {etIdBuscado.getText().toString()};

        int filasActualizadas = db.update("mascotas", valores, "idmascota=?", campoCriterio);

        if (filasActualizadas > 0){
            notificar("Datos Actualizados con éxito");
            reiniciar();
        }else {notificar("Error al actualizar los datos");
        }
    }


    private void buscarMascota(){
        SQLiteDatabase db = conexion.getReadableDatabase();

        String[] campoCriterio = {etIdBuscado.getText().toString()};

        String[] campos = {"tipo","raza", "nombre", "peso", "color"};

        try {
            Cursor cursor = db.query("mascotas", campos, "idmascota=?", campoCriterio, null, null,null);
            cursor.moveToFirst();

            etTipo.setText(cursor.getString(0));
            etRaza.setText(cursor.getString(1));
            etNombre.setText(cursor.getString(2));
            etPeso.setText(cursor.getString(3));
            etColor.setText(cursor.getString(4));

            cursor.close();

            // Ocultar el teclado virtual
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(etIdBuscado.getWindowToken(), 0);

        }catch (Exception e){
            notificar("NO EXISTE EL ID BUSCADO");
            reiniciar();
        }


    }

    private void reiniciar(){
        try {
            etIdBuscado.setText(null);
            etTipo.setText(null);
            etRaza.setText(null);
            etNombre.setText(null);
            etPeso.setText(null);
            etColor.setText(null);
        } catch (Exception e){
            e.printStackTrace();
            notificar("Error: " + e.getMessage());
        }
    }

    private void reiniciarId(){
        etIdBuscado.setText(null);
    }
    private void notificar(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void loadUI(){
        etTipo = findViewById(R.id.etTipoMascotaBuscar);
        etRaza = findViewById(R.id.etRazaMascotaBuscar);
        etNombre = findViewById(R.id.etNombreMascotaBuscar);
        etPeso = findViewById(R.id.etPesoMascotaBuscar);
        etColor = findViewById(R.id.etColorMascotaBuscar);

        etIdBuscado = findViewById(R.id.etIdBucar);

        btBuscar = findViewById(R.id.btBuscarMascota);
        btClean = findViewById(R.id.btLimpiarCampos);
        btActualizarMascota = findViewById(R.id.btactualizarmascota);
        btEliminarDatosMascota = findViewById(R.id.bteliminarmascota);
        btRegresarActivityRegistrar = findViewById(R.id.btabriActivityRegistrar);
    }
}