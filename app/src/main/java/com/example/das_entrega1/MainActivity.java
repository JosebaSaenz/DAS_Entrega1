package com.example.das_entrega1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button boton;
    private ListView listView;
    private EditText textField;
    private List<String> lista = new ArrayList<>();
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = findViewById(R.id.boton);
        boton.setOnClickListener(this);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        textField = findViewById(R.id.textField);

        // Definicion del canal de notificaciones (de Android 8.0 en adelante)
        NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(this, "IdCanal");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("id_canal", "nombre_canal",
                    NotificationManager.IMPORTANCE_DEFAULT);
            elManager.createNotificationChannel(elCanal);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.boton:
                String texto = textField.getText().toString().trim();
                System.out.println("Texto a insertar: " + '"' + texto + '"');
                if (texto.isEmpty()) {

                    // El elemento que se quiere sumar a la lista esta vacio, por lo que se muestra un dialogo y se envia una notificacion
                    System.out.println("Texto vacio");

                    // Definicion del dialog: titulo, mensaje y boton
                    AlertDialog.Builder builderDialog = new AlertDialog.Builder(this);
                    builderDialog.setTitle("Alerta");
                    builderDialog.setMessage("No puedes introducir un elemento sin texto");
                    builderDialog.setPositiveButton("Aceptar", null);

                    AlertDialog dialog = builderDialog.create();
                    dialog.show();

                    // Definicion de la notificacion: icono, titulo y texto
                    NotificationCompat.Builder builderNotificacion = new NotificationCompat.Builder(this, "id_canal")
                            .setSmallIcon(R.drawable.icono_notificacion)
                            .setContentTitle("Elemento no válido")
                            .setContentText("Has intentado introducir un elemento sin texto :(")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                    // Llamar a la notificacion
                    notificationManager.notify(1, builderNotificacion.build());


                } else {
                    // El elemento no esta vacio y se suma al listView
                    lista.add(texto);
                    textField.getText().clear();
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
                    listView.setAdapter(adapter);
                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
        // Metodo que muestra un toast cuando se toca sobre un elemento del listView e indica su posicion
        Toast.makeText(this, "Elemento seleccionado: " + posicion, Toast.LENGTH_SHORT).show();
    }
}