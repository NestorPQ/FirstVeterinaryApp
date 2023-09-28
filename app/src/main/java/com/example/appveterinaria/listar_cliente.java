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

public class listar_cliente extends AppCompatActivity {

    private ListView lvRegistroCliente;
    private ArrayList<String> listaInformacionCliente;
    private ArrayList<Cliente> listaCliente;
    private conexionSQLiteHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cliente);

        // Inicializar elementos de la interfaz de usuario
        initUI();

        // Inicializar la base de datos SQLite
        conexion = new conexionSQLiteHelper(getApplicationContext(), "veterinaria", null, 1);

        // Consultar y mostrar la lista de clientes
        consultarListaCliente();

        // Configurar el adaptador y asignarlo al ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaInformacionCliente);
        lvRegistroCliente.setAdapter(adapter);

        // Configurar el evento de clic en un elemento de la lista
        lvRegistroCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                mostrarDetalleCliente(i);
            }
        });
    }

    // Consultar la lista de clientes desde la base de datos SQLite
    private void consultarListaCliente() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        listaCliente = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM clientes", null);

        while (cursor.moveToNext()) {
            Cliente cliente = new Cliente();
            cliente.setIdcliente(cursor.getInt(0));
            cliente.setApellidocliente(cursor.getString(1));
            cliente.setNombrecliente(cursor.getString(2));
            cliente.setTelefonocliente(cursor.getInt(3));
            cliente.setEmailcliente(cursor.getString(4));
            cliente.setDireccioncliente(cursor.getString(5));
            cliente.setFechanacimientocliente(cursor.getString(6));
            listaCliente.add(cliente);
        }

        // Llenar la lista de información del cliente
        obtenerListaCliente();
    }

    // Crear una lista de información de cliente a partir de la lista de objetos Cliente
    private void obtenerListaCliente() {
        listaInformacionCliente = new ArrayList<>();
        for (Cliente cliente : listaCliente) {
            listaInformacionCliente.add(cliente.getApellidocliente() + " " + cliente.getNombrecliente());
        }
    }

    // Mostrar el detalle del cliente en un mensaje Toast
    private void mostrarDetalleCliente(int position) {
        if (position >= 0 && position < listaCliente.size()) {
            Cliente cliente = listaCliente.get(position);
            String detalle = "Nombre: " + cliente.getNombrecliente() + "\n";
            detalle += "Dirección: " + cliente.getDireccioncliente();
            Toast.makeText(this, detalle, Toast.LENGTH_LONG).show();
        }
    }

    // Inicializar elementos de la interfaz de usuario
    private void initUI() {
        lvRegistroCliente = findViewById(R.id.lvRegistrosClientes);
    }
}


/*package com.example.appveterinaria;

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

public class listar_cliente extends AppCompatActivity {

    ListView lvRegistroCliente;
    ArrayList<String> listaInformacionCliente;
    ArrayList<Cliente> listaCliente;
    conexionSQLiteHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cliente);

        //  Cargar elementos de la interfaz de usuario
        loadUI();

        //  Inicializar la base de datos SQLite
        conexion = new conexionSQLiteHelper(getApplicationContext(), "veterinaria", null, 1);

        //  Consultar la lista de clinetes desde la base de datos
        consultarListaCliente();

        //  Crear un adaptador para mostrar la lista de clientes en el ListView
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacionCliente);

        lvRegistroCliente.setAdapter(adapter);

        //  Configurar el evento de clic en un elemento de la lista
        lvRegistroCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  Mostrar información detallada del cliente en un mensaje Toast al hacer clic en un elemento de la lista
                String msg = "";

                msg += "Nombre:  " + listaCliente.get(i).getNombrecliente() + "\n";
                msg += "Direccion:  " + listaCliente.get(i).getDireccioncliente();

                Toast.makeText(listar_cliente.this,msg,Toast.LENGTH_LONG).show();



            }
        });
    }

    //  Consultar la lista de clientes desde la base de datos SQLite
    private void consultarListaCliente() {

        SQLiteDatabase db = conexion.getReadableDatabase();

        Cliente cliente = null;

        listaCliente = new ArrayList<Cliente>();

        Cursor cursor = db.rawQuery("SELECT * FROM clientes", null);

        while (cursor.moveToNext()) {
            cliente = new Cliente();
            cliente.setIdcliente(cursor.getInt(0));
            cliente.setApellidocliente(cursor.getString(1));
            cliente.setNombrecliente(cursor.getString(2));
            cliente.setTelefonocliente(cursor.getInt(3));
            cliente.setEmailcliente(cursor.getString(4));
            cliente.setDireccioncliente(cursor.getString(5));
            cliente.setFechanacimientocliente(cursor.getString(6));

            listaCliente.add(cliente);
        }

        //  Llenar la lista de información del cliente
        obtenerListaCliente();
    }

    //  Crear una lista de informacion de cliente a partir de la lista de objetos de Cliente
    private void obtenerListaCliente() {
        listaInformacionCliente = new ArrayList<String>();

        for (int i = 0; i < listaCliente.size(); i++) {
            listaInformacionCliente.add(listaCliente.get(i).getApellidocliente() + " " + listaCliente.get(i).getNombrecliente());
        }
    }

    // Cargar elementos de la interfaz de usuario
    private void loadUI() {
        lvRegistroCliente = findViewById(R.id.lvRegistrosClientes);
    }
}*/