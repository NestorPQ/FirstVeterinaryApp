package com.example.appveterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class listar_macotas extends AppCompatActivity {

    ListView lvRegistrosMascotas;
    ArrayList<String> listaInformacionMascota;
    ArrayList<Mascota> listaMascota;
    conexionSQLiteHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_macotas);

        loadUI();

        //  Aperturamos la conexión
        conexion = new conexionSQLiteHelper(getApplicationContext(), "veterinaria", null, 1);

        //  Obtenemos los datos
        consultarListaMascota();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacionMascota);

        lvRegistrosMascotas.setAdapter(adapter);

        lvRegistrosMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mensaje = "";

                mensaje += "Nombre:  " + listaMascota.get(i).getNombremascota() + "\n";
                mensaje += "Raza:  " + listaMascota.get(i).getRazamascota() + "\n";
                mensaje += "Color:   " + listaMascota.get(i).getColormascota();

                Toast.makeText(listar_macotas.this,mensaje,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void consultarListaMascota() {
        SQLiteDatabase db = conexion.getReadableDatabase();

        Mascota mascota = null;

        listaMascota = new ArrayList<Mascota>();

        Cursor cursor = db.rawQuery("SELECT * FROM mascotas", null);

        while (cursor.moveToNext()) {

            mascota = new Mascota();
            mascota.setIdmascota(cursor.getInt(0));
            mascota.setTipomascota(cursor.getString(1));
            mascota.setRazamascota(cursor.getString(2));
            mascota.setNombremascota(cursor.getString(3));
            mascota.setPesomascota(cursor.getInt(4));
            mascota.setColormascota(cursor.getString(5));

            listaMascota.add(mascota);
        }
        obtenerListaMascota();

    }

    private void obtenerListaMascota() {

        listaInformacionMascota = new ArrayList<String>();

        for (int i = 0; i < listaMascota.size(); i++) {
            listaInformacionMascota.add(listaMascota.get(i).getTipomascota() + " " + listaMascota.get(i).getRazamascota());
        }

    }

    private void loadUI() {
        lvRegistrosMascotas = findViewById(R.id.lvRegistrosMascotas);
    }
}