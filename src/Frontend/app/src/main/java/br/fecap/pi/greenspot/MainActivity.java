package br.fecap.pi.greenspot;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

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
        gMap.setInfoWindowAdapter(this);
        gMap.setOnMarkerClickListener(this::onMarkerClick);
    }

    private void fetchCoordenadas() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        Log.d("MainActivity", "Iniciando a busca de coordenadas...");

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("MainActivity", "Erro ao buscar coordenadas: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao buscar coordenadas", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                Log.d("MainActivity", "Código de resposta: " + response.code());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    Log.d("MainActivity", "Resposta do servidor: " + responseBody);

                    try {
                        JSONArray coordenadasArray = new JSONArray(responseBody); // Ajuste conforme a estrutura do JSON

                        runOnUiThread(() -> {
                            for (int i = 0; i < coordenadasArray.length(); i++) {
                                try {
                                    JSONObject coordenadaObject = coordenadasArray.getJSONObject(i);
                                    double latitude = coordenadaObject.getDouble("latitude");
                                    double longitude = coordenadaObject.getDouble("longitude");
                                    String nome = coordenadaObject.getString("nome");

                                    Log.d("MainActivity", "Adicionando marcador: " + nome + " - Lat: " + latitude + ", Lng: " + longitude);
                                    addMarkerToMap(latitude, longitude, nome);
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
                    String errorBody = response.body() != null ? response.body().string() : "Sem corpo de resposta";
                    Log.e("MainActivity", "Erro na resposta: " + response.code() + " - " + errorBody);
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao buscar coordenadas", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
    private void addMarkerToMap(double latitude, double longitude, String nome) {
        LatLng coordenada = new LatLng(latitude, longitude);
        gMap.addMarker(new MarkerOptions().position(coordenada).title(nome));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenada, 12)); // Ajuste o zoom conforme necessário
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null; // Retorna null para usar o layout padrão do InfoWindow
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null; // Retorna null para usar o layout padrão do InfoWindow
    }

    private void showCommentDialog(String pointName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comentar sobre " + pointName);

        View dialogView = getLayoutInflater().inflate(R.layout.item_comentario, null);
        builder.setView(dialogView);

        EditText descriptionInput = dialogView.findViewById(R.id.description_input);
        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        LinearLayout layoutComentarios = dialogView.findViewById(R.id.layout_comentarios);

        // Carregar comentários existentes ao abrir o diálogo
        fetchComentarios(pointName, layoutComentarios);

        builder.setPositiveButton("Enviar", (dialog, which) -> {
            String description = descriptionInput.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (!description.isEmpty() && rating > 0) {
                sendComentario(pointName, description, rating, layoutComentarios);
            } else {
                Toast.makeText(MainActivity.this, "Por favor, insira um comentário e uma classificação.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void sendComentario(String pointName, String description, float rating, LinearLayout layoutComentarios) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://projeto1-1vh9.onrender.com/api/comentario/"; // URL para enviar o comentário

        // Criar um JSON para enviar
        JSONObject comentarioJson = new JSONObject();
        try {
            comentarioJson.put("nome", pointName);
            comentarioJson.put("descricao", description);
            comentarioJson.put("nota", rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(comentarioJson.toString(), MediaType.parse("application/json; charset=utf-8")))
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Erro ao enviar comentário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Comentário enviado com sucesso!", Toast.LENGTH_SHORT).show();
                        // Adiciona o novo comentário ao layout
                        addComentarioToLayout(description, rating, layoutComentarios, pointName);
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Erro ao enviar comentário: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void addComentarioToLayout(String descricao, float nota, LinearLayout layoutComentarios, String nomeEcoponto) {
        // Cria um TextView para o comentário
        TextView comentarioView = new TextView(this);
        comentarioView.setText(descricao + " - Nota: " + nota);
        comentarioView.setPadding(16, 16, 16, 16);
        comentarioView.setTextSize(16);

        // Adiciona um OnClickListener
        comentarioView.setOnClickListener(v -> {
            // Filtra e mostra os comentários do ecoponto específico
            mostrarComentariosDoEcoponto(nomeEcoponto);
        });

        // Adiciona o TextView ao layout
        layoutComentarios.addView(comentarioView);
    }
    private void fetchComentarios(String pointName, LinearLayout layoutComentarios) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://projeto1-1vh9.onrender.com/api/comentario/"; // URL da sua API para obter comentários

        client.newCall(new Request.Builder().url(url).build()).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("MainActivity", "Erro ao buscar comentários: " + e.getMessage());
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Erro ao buscar comentários. Verifique o log para mais detalhes.", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : "";
                    Log.d("MainActivity", "Resposta do servidor: " + responseBody); // Log da resposta

                    try {
                        // Converter a resposta em JSONObject
                        JSONObject jsonObject = new JSONObject(responseBody);
                        // Extrair o array de comentários
                        JSONArray jsonArray = jsonObject.getJSONArray("comentarios");

                        runOnUiThread(() -> {
                            layoutComentarios.removeAllViews(); // Limpa comentários anteriores
                            displayComentarios(jsonArray, layoutComentarios, pointName);
                        });
                    } catch (JSONException e) {
                        Log.e("MainActivity", "Erro ao processar os comentários: " + e.getMessage());
                        runOnUiThread(() -> {
                            Toast.makeText(MainActivity.this, "Erro ao processar os comentários. Verifique o log para mais detalhes.", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    Log.e("MainActivity", "Erro ao buscar comentários: " + response.message());
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Erro ao buscar comentários. Verifique o log para mais detalhes.", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void displayComentarios(JSONArray comentarios, LinearLayout layoutComentarios, String pointName) {
        // Verifica se há comentários a serem exibidos
        if (comentarios.length() == 0) {
            Toast.makeText(this, "Nenhum comentário encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Adiciona cada comentário ao layout
        for (int i = 0; i < comentarios.length(); i++) {
            try {
                JSONObject comentario = comentarios.getJSONObject(i);
                String descricao = comentario.optString("descricao", "Descrição não disponível"); // Use optString para evitar JSONException
                float nota = (float) comentario.optDouble("nota", 0.0); // Use optDouble para evitar JSONException

                // Usando optString para evitar JSONException se a chave não existir
                String comentarioPointName = comentario.optString("pointName", "");

                if (comentarioPointName.equals(pointName)) {
                    addComentarioToLayout(descricao, nota, layoutComentarios, pointName);
                }
            } catch (JSONException e) {
                Log.e("MainActivity", "Erro ao processar comentário na posição " + i + ": " + e.getMessage());
                int finalI = i;
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Erro ao processar comentário na posição " + finalI + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    public boolean onMarkerClick(Marker marker) {
        // Chama o método para mostrar o diálogo de comentários ao clicar no marcador
        showCommentDialog(marker.getTitle());
        return true; // Retorna true para evitar que o InfoWindow padrão seja exibido
    }

    private void mostrarComentariosDoEcoponto(String nomeEcoponto) {
        // Aqui você pode implementar a lógica para mostrar os comentários filtrados
        // Por exemplo, você pode abrir um novo diálogo ou atualizar o layout existente
        Toast.makeText(this, "Mostrando comentários de: " + nomeEcoponto, Toast.LENGTH_SHORT).show();
        // Você pode chamar fetchComentarios aqui para obter os comentários desse ecoponto específico
    }
}