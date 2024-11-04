package br.fecap.pi.greenspot;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.fecap.pi.greenspot.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private static final String BASE_URL = "https://projeto1-1vh9.onrender.com/api/coordenadas/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);

        fetchCoordenadas();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        // Definindo a primeira coordenada do local (latitude e longitude)
        LatLng Astarte = new LatLng(-23.54926754847192, -46.52485532327169);
        // Adicionando um marcador no primeiro local
        gMap.addMarker(new MarkerOptions().position(Astarte).title("Astarte EcoPonto"));
        // Move a câmera para o marcador padrão
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Astarte, 12));
    }

    private void fetchCoordenadas() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("MainActivity", "Erro ao buscar coordenadas: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao buscar coordenadas", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    Log.d("MainActivity", "Resposta do servidor: " + responseBody);

                    try {
                        // Cria um JSONObject a partir da resposta
                        JSONObject jsonResponse = new JSONObject(responseBody);

                        // Acessa o array de coordenadas dentro do objeto JSON
                        JSONArray coordenadasArray = jsonResponse.getJSONArray("coordenadas");

                        runOnUiThread(() -> {
                            for (int i = 0; i < coordenadasArray.length(); i++) {
                                try {
                                    JSONObject coordenadaObject = coordenadasArray.getJSONObject(i);
                                    double latitude = coordenadaObject.getDouble("latitude");
                                    double longitude = coordenadaObject.getDouble("longitude");
                                    String nome = coordenadaObject.getString("nome");

                                    LatLng coordenada = new LatLng(latitude, longitude);
                                    gMap.addMarker(new MarkerOptions().position(coordenada).title(nome));
                                } catch (JSONException e) {
                                    Log.e("MainActivity", "Erro ao parsear JSON: " + e.getMessage());
                                }
                            }
                        });
                    } catch (JSONException e) {
                        Log.e("MainActivity", "Erro ao processar dados JSON: " + e.getMessage());
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao processar dados", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    Log.e("MainActivity", "Erro na resposta: " + response.code());
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao buscar coordenadas", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}