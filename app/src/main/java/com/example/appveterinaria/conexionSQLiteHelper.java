package com.example.appveterinaria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexionSQLiteHelper extends SQLiteOpenHelper {

    final String MASCOTAS = ""+
            "CREATE TABLE 'mascotas'("+
            "'idmascota'    INTEGER NOT NULL,"+
            "'tipo'         TEXT    NOT NULL,"+
            "'raza'         TEXT    NOT NULL,"+
            "'nombre'       TEXT    NOT NULL,"+
            "'peso'         INTEGER NOT NULL,"+
            "'color'        TEXT    NOT NULL,"+
            "PRIMARY KEY ('idmascota' AUTOINCREMENT)"+
            ")";

    public conexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MASCOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mascotas");
    }
}
