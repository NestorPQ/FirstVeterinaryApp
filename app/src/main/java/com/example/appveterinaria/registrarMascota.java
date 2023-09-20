package com.example.appveterinaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registrarMascota extends AppCompatActivity {

    EditText ettipo, etraza, etnombre, etpeso, etcolor;

    Button btRegistrarMascota, btabrirbuscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);

        loadUI();
        btRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        btabrirbuscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),buscar_mascota.class));
            }
        });


    }

    private void validarCampos(){
        String tipo, raza, nombre, color;
        int peso;

        tipo = ettipo.getText().toString();
        raza = etraza.getText().toString();
        nombre = etnombre.getText().toString();
        color = etcolor.getText().toString();

        peso = (etpeso.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etpeso.getText().toString());

        if (tipo.isEmpty() || raza.isEmpty() || nombre.isEmpty() || color.isEmpty() || peso == 0){
            notificar("Completar el formulario");
        }
        else{
            preguntar();
        }
    }

    private void preguntar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("VETERINARIA");
        dialogo.setMessage("¿Está seguro de registrar?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarMascota();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    private void registrarMascota(){
        conexionSQLiteHelper conexion = new conexionSQLiteHelper(this, "bdmascotas", null, 1);

        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues parametros =  new ContentValues();

        parametros.put("tipo", ettipo.getText().toString());
        parametros.put("raza", etraza.getText().toString());
        parametros.put("nombre", etnombre.getText().toString());
        parametros.put("peso", etpeso.getText().toString());
        parametros.put("color", etcolor.getText().toString());

        long idObtenido = db.insert("mascotas", "idmascota", parametros);

        notificar("Datos guardados correctamente = " + String.valueOf(idObtenido));
        reiniciar();
        etraza.requestFocus();

    }


    private void reiniciar(){
        ettipo.setText(null);
        etraza.setText((null));
        etnombre.setText((null));
        etpeso.setText((null));
        etcolor.setText((null));
    }
    private void notificar(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void loadUI(){
        ettipo = findViewById(R.id.ettipomascota);
        etraza = findViewById(R.id.etraza);
        etnombre = findViewById(R.id.etnombre);
        etpeso = findViewById(R.id.etpeso);
        etcolor = findViewById(R.id.etcolor);


        btabrirbuscador = findViewById(R.id.btabribuscador);
        btRegistrarMascota = findViewById(R.id.btregistrarmascotaa);


    }
}