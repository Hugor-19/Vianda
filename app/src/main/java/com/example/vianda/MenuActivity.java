package com.example.vianda;
import android.util.Log;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductoAdapter adapter;
    ArrayList<Producto> productos;
    Button btnProfile;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productos = new ArrayList<>();

        // URL de tu API
        String url = "http://192.168.2.14:8082/menus/all";

        // Hacer la solicitud para obtener los productos
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parsear los datos
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject productoObj = response.getJSONObject(i);

                                String nombre = productoObj.getString("nombre");
                                String imagen = productoObj.getString("imagen");
                                String descripcion = productoObj.getString("descripcion");
                                int precio = productoObj.getInt("precio");
                                Log.d("Producto Info", "Nombre: " + nombre + ", Imagen: " + imagen + ", Descripción: " + descripcion + ", Precio: " + precio);
                                // Agregar producto a la lista
                                productos.add(new Producto(nombre, imagen, descripcion, precio));
                            }


                            if (!productos.isEmpty()) {
                                adapter = new ProductoAdapter(MenuActivity.this, productos);
                                recyclerView.setAdapter(adapter);
                            } else {
                                Toast.makeText(MenuActivity.this, "No se encontraron productos.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MenuActivity.this, "Error al obtener los datos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenuActivity.this, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);

        // Evento del botón para ir a la pantalla de Perfil (ProfileActivity)
        btnProfile = findViewById(R.id.btnProfile);
        btnBack = findViewById(R.id.btBack);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pasar los datos de productos a ProfileActivity
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                intent.putParcelableArrayListExtra("productos", productos);  // Ahora se puede pasar correctamente
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pasar los datos de productos a ProfileActivity
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putParcelableArrayListExtra("productos", productos);  // Ahora se puede pasar correctamente
                startActivity(intent);
            }
        });
    }
}