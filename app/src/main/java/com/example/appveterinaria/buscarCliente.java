package com.example.appveterinaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class buscarCliente extends AppCompatActivity {
   conexionSQLiteHelper conexion;

   Button btnMenuRegistrarCliente, btnEliminarCliente, btnActualizarCliente, btnBuscarCliente, btnLimpiarCampos;

   TextInputEditText etIdBuscado, etapellido, etnombre, ettelefono, etemail, etdireccion, etfechaNac;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_buscar_cliente);

      loadUI();
      //  Abrimos la conexion
      conexion = new conexionSQLiteHelper(getApplicationContext(), "veterinaria", null, 1);

      btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            buscarcliente();
         }
      });

      btnMenuRegistrarCliente.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), registroCliente.class));
         }
      });

      btnLimpiarCampos.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            reiniciar();
         }
      });

      btnActualizarCliente.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            validarCamposActualizacion();
         }
      });
      
      btnEliminarCliente.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            validarCamposEliminacion();
         }
      });
   }

   private void validarCamposEliminacion() {
      if(validarCampos()){
         mostrarDialogoConfirmacion("Eliminar Cliente", "¿Está seguro de eliminar los datos de cliente?", this::eliminarDatosCliente);
      }
   }

   private void eliminarDatosCliente() {
      SQLiteDatabase db = conexion.getWritableDatabase();

      String[] campoCriterio = {etIdBuscado.getText().toString()};

      int filasEliminadas = db.delete("clientes","idcliente=?", campoCriterio);

      if (filasEliminadas > 0){
         notificar("Datos del cliente han sido eliminados con éxito");
         reiniciar();
      }else {notificar("Error al eliminar los datos del cliente");}
   }

   private void validarCamposActualizacion() {
      if(validarCampos()){
         mostrarDialogoConfirmacion("Actualizar Cliente", "¿Está seguro de actualizar los datos del cliente?", this::actulizarCliente);
      }
   }

   private void actulizarCliente() {
      SQLiteDatabase db = conexion.getWritableDatabase();

      ContentValues valores = new ContentValues();
      valores.put("apellido",etapellido.getText().toString());
      valores.put("nombre",etnombre.getText().toString());
      valores.put("telefono",ettelefono.getText().toString());
      valores.put("email",etemail.getText().toString());
      valores.put("direccion",etdireccion.getText().toString());
      valores.put("fechaNacimiento",etfechaNac.getText().toString());

      String[] campoCriterio = {etIdBuscado.getText().toString()};

      int filasActualizadas = db.update("clientes", valores, "idcliente=?", campoCriterio);

      if (filasActualizadas > 0){
         notificar("Datos Actualizados con éxito");
         reiniciar();
      }else {notificar("Error al actualizar los datos");
      }
   }


   // Método para validar campos
   private boolean validarCampos() {
      String apellido, nombre, email, direccion, fechaNac;
      int telefono;

      apellido = etapellido.getText().toString();
      nombre = etnombre.getText().toString();
      email = etemail.getText().toString();
      direccion = etdireccion.getText().toString();
      fechaNac = etfechaNac.getText().toString();

      telefono = (ettelefono.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(ettelefono.getText().toString());

      if (apellido.isEmpty() || nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || fechaNac.isEmpty() || telefono == 0) {
         notificar("Uno o más campos están vacíos o el ID es inválido");
         return false;
      }

      return true;
   }

   // Método para mostrar el diálogo de confirmación
   private void mostrarDialogoConfirmacion(String titulo, String mensaje, Runnable accionConfirmada) {
      AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
      dialogo.setTitle(titulo);
      dialogo.setMessage(mensaje);
      dialogo.setCancelable(false);

      dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
            accionConfirmada.run();
         }
      });

      dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
            // Opcional: Puedes agregar código para manejar la cancelación aquí
         }
      });

      dialogo.show();
   }



   private void buscarcliente() {
      SQLiteDatabase db = conexion.getReadableDatabase();

      String[] campoCriterio = {etIdBuscado.getText().toString()};

      String[] campos = {"apellido","nombre", "telefono", "email", "direccion", "fechaNacimiento"};

      try {
         Cursor cursor = db.query("clientes", campos, "idcliente=?", campoCriterio, null, null,null);
         cursor.moveToFirst();

         etapellido.setText(cursor.getString(0));
         etnombre.setText(cursor.getString(1));
         ettelefono.setText(cursor.getString(2));
         etemail.setText(cursor.getString(3));
         etdireccion.setText(cursor.getString(4));
         etfechaNac.setText(cursor.getString(5));

         cursor.close();

         // Ocultar el teclado virtual
         InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
         inputMethodManager.hideSoftInputFromWindow(etIdBuscado.getWindowToken(), 0);

      }catch (Exception e){
         notificar("NO EXISTE EL ID BUSCADO");
         reiniciar();
      }

   }

   private void reiniciar() {
      try {
         etIdBuscado.setText(null);
         etapellido.setText(null);
         etnombre.setText(null);
         ettelefono.setText(null);
         etemail.setText(null);
         etdireccion.setText(null);
         etfechaNac.setText(null);
      } catch (Exception e) {
         e.printStackTrace();
         notificar("Error: " + e.getMessage());
      }
   }

   private void reiniciarId() {
      etIdBuscado.setText(null);
   }

   private void notificar(String msg) {
      Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
   }


   private void loadUI() {
      etIdBuscado = findViewById(R.id.etiIdBuscarCliente);
      etapellido = findViewById(R.id.etBuscarApellidoCliente);
      etnombre = findViewById(R.id.etBuscaNombreCliente);
      ettelefono = findViewById(R.id.etBuscarTelefonoCliente);
      etemail = findViewById(R.id.etBuscarEmailCliente);
      etdireccion = findViewById(R.id.etBuscarDireccionCliente);
      etfechaNac = findViewById(R.id.etBuscarFecarNacCliente);


      btnMenuRegistrarCliente = findViewById(R.id.btnMenuRegistrarCliente);
      btnBuscarCliente = findViewById(R.id.btBuscarCliente);
      btnLimpiarCampos = findViewById(R.id.btLimpiarCamposCliente);
      btnEliminarCliente = findViewById(R.id.btnEliminarCliente);
      btnActualizarCliente = findViewById(R.id.btnActualizarCliente);
   }
}