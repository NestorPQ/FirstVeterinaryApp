package com.example.appveterinaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class registroCliente extends AppCompatActivity {

   TextInputEditText etApellido, etNombre, etTelefono, etEmail, etDireccion, etFechaNacimienotCliente;
   Button btRegresarMenu, btBuscarCliente, btRegistrarCliente;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_registro_cliente);

      loadUI();


      btRegresarMenu.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
         }
      });

      btBuscarCliente.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), buscarCliente.class));
         }
      });

      btRegistrarCliente.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            validarCampo();
         }
      });

   }

   private void validarCampo() {
      String apellido, nombre, email, direccion,fechaNaci;
      int telefono;

      apellido = etApellido.getText().toString();
      nombre = etNombre.getText().toString();
      email = etEmail.getText().toString();
      direccion = etDireccion.getText().toString();
      fechaNaci = etFechaNacimienotCliente.getText().toString();


      telefono = (etTelefono.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etTelefono.getText().toString());

      if (apellido.isEmpty() || nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || telefono == 0 || fechaNaci.isEmpty()){
         notificar("Completar el formulario");
      }
      else{
         preguntar();
      }
   }

   private void preguntar() {
      AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

      dialogo.setTitle("VETERINARIA");
      dialogo.setMessage("¿Está seguro de registrar?");
      dialogo.setCancelable(false);

      dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
            registrarCliente();
         }
      });

      dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {

         }
      });

      dialogo.show();
   }

   private void registrarCliente() {
      conexionSQLiteHelper conexion = new conexionSQLiteHelper(this, "veterinaria", null, 1);

      SQLiteDatabase db = conexion.getWritableDatabase();

      ContentValues parametros =  new ContentValues();

      parametros.put("apellido", etApellido.getText().toString());
      parametros.put("nombre", etNombre.getText().toString());
      parametros.put("telefono", etTelefono.getText().toString());
      parametros.put("email", etEmail.getText().toString());
      parametros.put("direccion", etDireccion.getText().toString());
      parametros.put("fechaNacimiento", etFechaNacimienotCliente.getText().toString());

      long idObtenido = db.insert("clientes", "idcliente", parametros);

      notificar("Datos guardados correctamente = " + String.valueOf(idObtenido));
      reiniciar();
      etApellido.requestFocus();

      // Ocultar el teclado virtual
      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(etApellido.getWindowToken(), 0);
   }

   private void reiniciar(){
      etApellido.setText(null);
      etNombre.setText(null);
      etTelefono.setText((null));
      etEmail.setText((null));
      etDireccion.setText((null));
      etFechaNacimienotCliente.setText((null));
   }
   private void notificar(String msg){
      Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
   }
   private void loadUI() {

      etApellido = findViewById(R.id.etapellidocliente);
      etNombre = findViewById(R.id.etnombrecliente);
      etTelefono = findViewById(R.id.ettelefonocliente);
      etEmail = findViewById(R.id.etemailcliente);
      etDireccion = findViewById(R.id.etdireccioncliente);
      etFechaNacimienotCliente = findViewById(R.id.etBuscarFecarNacCliente);

      btBuscarCliente = findViewById(R.id.btnEliminarCliente);
      btRegresarMenu = findViewById(R.id.btnActualizarCliente);
      btRegistrarCliente = findViewById(R.id.btnMenuRegistrarCliente);
   }
}