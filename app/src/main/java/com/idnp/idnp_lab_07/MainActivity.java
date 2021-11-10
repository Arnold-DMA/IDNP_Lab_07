package com.idnp.idnp_lab_07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etID, etNombre, etFecha, etHora, etLugar, etEstado, etValor;
    private Button btnAgregar, btnBuscar, btnModificar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etID = (EditText) findViewById(R.id.etID);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etFecha = (EditText) findViewById(R.id.etFecha);
        etHora = (EditText) findViewById(R.id.etHora);
        etLugar = (EditText) findViewById(R.id.etLugar);
        etEstado = (EditText) findViewById(R.id.etEstado);
        etValor = (EditText) findViewById(R.id.etValor);
    }

    public void agregar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tutoria", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String id = etID.getText().toString();
        String nombre = etNombre.getText().toString();
        String fecha = etFecha.getText().toString();
        String hora = etHora.getText().toString();
        String lugar = etLugar.getText().toString();
        String estado = etEstado.getText().toString();
        String valor = etValor.getText().toString();

        if(!id.isEmpty() && !nombre.isEmpty() && !fecha.isEmpty() && !hora.isEmpty() && !lugar.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("id", id);
            registro.put("nombre", nombre);
            registro.put("fecha", fecha);
            registro.put("hora", hora);
            registro.put("lugar", lugar);
            registro.put("estado", estado);
            registro.put("valoracion", valor);

            baseDeDatos.insert("sesiones", null, registro);
            baseDeDatos.close();

            etID.setText("");
            etNombre.setText("");
            etFecha.setText("");
            etHora.setText("");
            etLugar.setText("");
            etEstado.setText("");
            etValor.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Se debe llenar los campos de: ID, Nombre, Fecha, Hora y Lugar", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tutoria", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String id = etID.getText().toString();

        if(!id.isEmpty()){
            Cursor fila = baseDeDatos.rawQuery("select nombre, fecha, hora, lugar, estado, valoracion from sesiones where id ="+id, null);

            if(fila.moveToFirst()){
                etNombre.setText(fila.getString(0));
                etFecha.setText(fila.getString(1));
                etHora.setText(fila.getString(2));
                etLugar.setText(fila.getString(3));
                etEstado.setText(fila.getString(4));
                etValor.setText(fila.getString(5));
            }
            else{
                Toast.makeText(this, "No existe una sesión con el ID introducido.", Toast.LENGTH_SHORT).show();
            }
            baseDeDatos.close();
        }
        else{
            Toast.makeText(this, "Se debe de ingresar un ID", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tutoria", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String id = etID.getText().toString();

        if(!id.isEmpty()){
            int cantidad = baseDeDatos.delete("sesiones", "id="+id, null);
            baseDeDatos.close();

            etID.setText("");
            etNombre.setText("");
            etFecha.setText("");
            etHora.setText("");
            etLugar.setText("");
            etEstado.setText("");
            etValor.setText("");

            if(cantidad == 1){
                Toast.makeText(this, "Eliminación exitosa", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "No existe una sesión con el ID introducido.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Se debe de ingresar un ID", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tutoria", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String id = etID.getText().toString();
        String nombre = etNombre.getText().toString();
        String fecha = etFecha.getText().toString();
        String hora = etHora.getText().toString();
        String lugar = etLugar.getText().toString();
        String estado = etEstado.getText().toString();
        String valor = etValor.getText().toString();

        if(!id.isEmpty() && !nombre.isEmpty() && !fecha.isEmpty() && !hora.isEmpty() && !lugar.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("id", id);
            registro.put("nombre", nombre);
            registro.put("fecha", fecha);
            registro.put("hora", hora);
            registro.put("lugar", lugar);
            registro.put("estado", estado);
            registro.put("valoracion", valor);

            int cantidad = baseDeDatos.update("sesiones", registro, "id="+id, null);
            baseDeDatos.close();

            if(cantidad == 1){
                Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "No existe una sesión con el ID introducido.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Se debe llenar los campos de: ID, Nombre, Fecha, Hora y Lugar", Toast.LENGTH_SHORT).show();
        }
    }
}