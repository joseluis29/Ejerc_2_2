package com.example.ejerc_2_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityConsultar extends AppCompatActivity {
    EditText txtID;
    Button btnAtras;
    Button btnBuscar;
    TextView txtInformacion;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        init();

        btnAtras.setOnClickListener(this::onClickGoBack);
        btnBuscar.setOnClickListener(this::onClickBuscar);
    }

    private void onClickBuscar(View view) {
        if (txtID.getText().length() > 0){
            String URL = "https://jsonplaceholder.typicode.com/todos?id=" + txtID.getText();
            JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                    response -> {
                        if (response.length() > 0) {
                            try {
                                JSONObject objeto = response.getJSONObject(0);
                                String info = "userID: " + objeto.get("userId") + "\nID: " + objeto.get("id") +
                                        "\nTitle: " + objeto.get("title") + "\nCompleted: " + objeto.get("completed");
                                txtInformacion.setText(info);
                            } catch (JSONException e) {
                                message(e.getMessage());
                            }
                        }
                    },
                    error -> message(error.getMessage())
            );
            requestQueue.add(jsonRequest);
        } else message("Debe ingresar un ID");
    }

    private void onClickGoBack(View view) {
        Intent ventana = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(ventana);
        finish();
    }

    private void init(){
        txtID = findViewById(R.id.txtID);
        txtInformacion = findViewById(R.id.txtInformacion);
        btnAtras = findViewById(R.id.btnAtras);
        btnBuscar = findViewById(R.id.btnBuscar);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void message(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}