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
                    System.out.println("Texto vacio");

                    AlertDialog.Builder builderDialog = new AlertDialog.Builder(this);
                    builderDialog.setTitle("Alerta");
                    builderDialog.setMessage("No puedes introducir un elemento sin texto");
                    builderDialog.setPositiveButton("Aceptar", null);

                    AlertDialog dialog = builderDialog.create();
                    dialog.show();

                    NotificationCompat.Builder builderNotificacion = new NotificationCompat.Builder(this, "id_canal")
                            .setSmallIcon(R.drawable.icono_notificacion)
                            .setContentTitle("Elemento no válido")
                            .setContentText("Has intentado introducir un elemento sin texto :(")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builderNotificacion.build());


                } else {
                    lista.add(texto);
                    textField.getText().clear();
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
                    listView.setAdapter(adapter);
                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
        Toast.makeText(this, "Elemento seleccionado: " + posicion, Toast.LENGTH_SHORT).show();
    }
}