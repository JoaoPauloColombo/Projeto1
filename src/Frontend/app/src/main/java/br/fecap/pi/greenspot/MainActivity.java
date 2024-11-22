package br.fecap.pi.greenspot;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
    private static final String AUTH_PREFS = "auth";
    private static final String TOKEN_KEY = "token";

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
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray coordenadasArray = jsonObject.getJSONArray("coordenadas");

                        runOnUiThread(() -> {
                            for (int i = 0; i < coordenadasArray.length(); i++) {
                                try {
                                    JSONObject coordenadaObject = coordenadasArray.getJSONObject(i);
                                    double latitude = coordenadaObject.getDouble("latitude");
                                    double longitude = coordenadaObject.getDouble("longitude");
                                    int id = coordenadaObject.getInt("id");
                                    String nome = coordenadaObject.getString("nome");

                                    Log.d("MainActivity", "Adicionando marcador: " + nome + " - Lat: " + latitude + ", Lng: " + longitude);
                                    addMarkerToMap(latitude, longitude, nome, id);
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

    private void addMarkerToMap(double latitude, double longitude, String nome, int id) {
        LatLng coordenada = new LatLng(latitude, longitude);
        Marker marker = gMap.addMarker(new MarkerOptions().position(coordenada).title(nome));
        marker.setTag(id);
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

    private void showCommentDialog(String pointName, int coordenadaId, double latitude, double longitude) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Cria um TextView para o título
        TextView titleTextView = new TextView(this);
        titleTextView.setText("Comentar sobre " + pointName);
        titleTextView.setTextSize(20);
        titleTextView.setTextColor(getResources().getColor(R.color.black));
        titleTextView.setTypeface(null, Typeface.BOLD);
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setPadding(16, 16, 16, 16);

        // Define o título personalizado
        builder.setCustomTitle(titleTextView);

        View dialogView = getLayoutInflater().inflate(R.layout.item_comentario, null);
        builder.setView(dialogView);

        EditText descriptionInput = dialogView.findViewById(R.id.description_input);
        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        LinearLayout layoutComentarios = dialogView.findViewById(R.id.layout_comentarios);
        Button buttonOpenMap = dialogView.findViewById(R.id.button_open_map); // Referência ao botão

        // Carregar comentários existentes ao abrir o diálogo
        fetchComentarios(coordenadaId, layoutComentarios); // Passa o coordenadaId correto

        // Exibir coordenadas em DMS
        String latitudeDMS = convertToDMS(latitude, true);
        String longitudeDMS = convertToDMS(longitude, false);

        // Configurar o botão para abrir o Google Maps
        buttonOpenMap.setOnClickListener(v -> {
            openGoogleMaps(latitude, longitude);
        });

        builder.setPositiveButton("Enviar", (dialog, which) -> {
            String description = descriptionInput.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (!description.isEmpty() && rating > 0) {
                sendComentario(pointName, description, rating, coordenadaId, layoutComentarios);
            } else {
                Toast.makeText(MainActivity.this, "Por favor, insira um comentário e uma classificação.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        // Customize o fundo do diálogo
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(d -> {
            // Estilizando os botões...
        });

        dialog.show();
    }


    private void sendComentario(String pointName, String description, float rating, int coordenadaId, LinearLayout layoutComentarios) {
        if (pointName == null || pointName.isEmpty() || description == null || description.isEmpty() || rating < 0 || coordenadaId <= 0) {
            Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("SendComentario", "coordenadaId: " + coordenadaId);

        OkHttpClient client = new OkHttpClient();
        String url = "https://projeto1-1vh9.onrender.com/api/comentarios/";

        // Criar um JSON para enviar
        JSONObject comentarioJson = new JSONObject();
        try {
            comentarioJson.put("nome", pointName);
            comentarioJson.put("descricao", description);
            comentarioJson.put("nota", rating);
            comentarioJson.put("coordenadaId", coordenadaId); // Certifique-se de que isso esteja correto
            Log.d("SendComentario", "JSON enviado: " + comentarioJson.toString());
        } catch (JSONException e) {
            Log.e("SendComentario", "Erro ao criar JSON: " + e.getMessage(), e);
            Toast.makeText(MainActivity.this, "Erro ao criar JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody requestBody = RequestBody.create(comentarioJson.toString(), MediaType.get("application/json; charset=utf-8"));

        // Recuperar o token das SharedPreferences
        String token = getToken();
        Log.d("SendComentario", "Token: " + token); // Log do token

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + token) // Adiciona o token no cabeçalho
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("SendComentario", "Erro ao enviar comentário: " + e.getMessage(), e);
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
                    String errorBody = response.body() != null ? response.body().string() : "Erro desconhecido";
                    Log.e("SendComentario", "Erro: " + response.code() + " - " + errorBody);
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Erro ao enviar comentário: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });

    }

    private void addComentarioToLayout(String descricao, float nota, LinearLayout layoutComentarios, String nomeEcoponto) {
        // Cria um LinearLayout para o cartão de comentário
        LinearLayout cardView = new LinearLayout(this);
        cardView.setOrientation(LinearLayout.VERTICAL);
        cardView.setPadding(24, 24, 24, 24); // Padding interno
        cardView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Define a borda do cartão
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(getResources().getColor(R.color.Principal)); // Cor de fundo personalizada
        drawable.setStroke(8, getResources().getColor(R.color.black)); // Aumenta a largura da borda
        drawable.setCornerRadius(8); // Cantos arredondados
        cardView.setBackground(drawable);

        // Adiciona margem inferior para espaçamento entre comentários
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
        params.setMargins(0, 0, 0, 16); // Margem inferior de 16dp
        cardView.setLayoutParams(params);

        // Cria um TextView para o comentário
        TextView comentarioView = new TextView(this);
        comentarioView.setText("Descrição: " + descricao);
        comentarioView.setTextSize(16);
        comentarioView.setTextColor(getResources().getColor(android.R.color.white));
        comentarioView.setTypeface(null, Typeface.BOLD); // Texto em negrito
        cardView.addView(comentarioView); // Adiciona o TextView ao cartão

        // Cria um TextView para a nota
        TextView notaView = new TextView(this);
        notaView.setText("Nota: " + nota);
        notaView.setTextSize(14);
        notaView.setTextColor(getResources().getColor(android.R.color.white));
        notaView.setTypeface(null, Typeface.BOLD); // Texto em negrito para a nota
        cardView.addView(notaView); // Adiciona o TextView da nota ao cartão

        // Adiciona um OnClickListener
        cardView.setOnClickListener(v -> {
            // Filtra e mostra os comentários do ecoponto específico
            mostrarComentariosDoEcoponto(nomeEcoponto);
        });

        // Adiciona o cartão ao layout
        layoutComentarios.addView(cardView);
    }

    private void fetchComentarios(int coordenadaId, LinearLayout layoutComentarios) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://projeto1-1vh9.onrender.com/api/comentarios/coordenadas/" + coordenadaId + "/comentarios"; // URL ajustada

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
                    Log.d("MainActivity", "Resposta do servidor: " + responseBody);

                    try {
                        // Aqui, mudamos para JSONObject
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray comentariosArray = jsonObject.optJSONArray("comentarios"); // Extrai o array de comentários

                        runOnUiThread(() -> {
                            layoutComentarios.removeAllViews(); // Limpa comentários anteriores
                            if (comentariosArray != null) {
                                displayComentarios(comentariosArray, layoutComentarios); // Chama o método para exibir comentários
                            } else {
                                Toast.makeText(MainActivity.this, "Nenhum comentário encontrado.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        Log.e("MainActivity", "Erro ao processar os comentários: " + e.getMessage());
                        runOnUiThread(() -> {
                            Toast.makeText(MainActivity.this, "Erro ao processar os comentários. Verifique o log para mais detalhes.", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    String errorBody = response.body() != null ? response.body().string() : "Corpo da resposta vazio";
                    Log.e("MainActivity", "Erro ao buscar comentários: " + response.message() + " (Código: " + response.code() + ") - Corpo: " + errorBody);
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Erro ao buscar comentários. Verifique o log para mais detalhes.", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
    private void displayComentarios(JSONArray comentarios, LinearLayout layoutComentarios) {
        if (comentarios.length() == 0) {
            Toast.makeText(this, "Nenhum comentário encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < comentarios.length(); i++) {
            try {
                JSONObject comentario = comentarios.getJSONObject(i);
                String descricao = comentario.optString("descricao", "Descrição não disponível");
                float nota = (float) comentario.optDouble("nota", 0.0);

                // Passando o nome do ecoponto como quarto parâmetro
                addComentarioToLayout(descricao, nota, layoutComentarios, comentario.optString("nomeEcoponto", "Ecoponto desconhecido"));
            } catch (JSONException e) {
                Log.e("MainActivity", "Erro ao processar comentário na posição " + i + ": " + e.getMessage());
            }
        }
    }

    private void openGoogleMaps(double latitude, double longitude) {
        // Converte as coordenadas para DMS
        String latitudeDMS = convertToDMS(latitude, true);
        String longitudeDMS = convertToDMS(longitude, false);

        // Formata a URI para abrir no Google Maps
        String uri = String.format("https://www.google.com/maps?q=%s,%s", latitudeDMS, longitudeDMS);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        // Verifica se há um aplicativo de navegador disponível
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Nenhum aplicativo de navegador disponível.", Toast.LENGTH_SHORT).show();
        }
    }

    private String convertCommaToPoint(String value) {
        return value.replace(',', '.');
    }

    public boolean onMarkerClick(Marker marker) {
        int coordenadaId = (int) marker.getTag(); // Recupera o ID do marcador
        LatLng position = marker.getPosition();
        double latitude = position.latitude;
        double longitude = position.longitude;

        // Mostra o diálogo de comentários com as coordenadas
        showCommentDialog(marker.getTitle(), coordenadaId, latitude, longitude); // Passa latitude e longitude
        return true; // Retorna true para evitar que o InfoWindow padrão seja exibido
    }

    private String convertToDMS(double decimal, boolean isLatitude) {
        String direction;

        if (isLatitude) {
            direction = decimal < 0 ? "S" : "N"; // Sul ou Norte
        } else {
            direction = decimal < 0 ? "W" : "E"; // Oeste ou Leste
        }

        decimal = Math.abs(decimal);
        int degrees = (int) decimal;
        decimal = (decimal - degrees) * 60;
        int minutes = (int) decimal;
        double seconds = (decimal - minutes) * 60;

        // Formata os segundos com um ponto decimal
        String formattedSeconds = convertCommaToPoint(String.format("%.1f", seconds));

        // Retorna o formato DMS sem vírgulas
        return String.format("%d°%d'%s\"%s", degrees, minutes, formattedSeconds, direction);
    }

    private void mostrarComentariosDoEcoponto(String nomeEcoponto) {
        // Aqui você pode implementar a lógica para mostrar os comentários filtrados
        Toast.makeText(this, "Mostrando comentários de: " + nomeEcoponto, Toast.LENGTH_SHORT).show();
        // Você pode chamar fetchComentarios aqui para obter os comentários desse ecoponto específico
        // Por exemplo:
        LinearLayout layoutComentarios = findViewById(R.id.layout_comentarios); // Ajuste conforme necessário
        fetchComentarios(Integer.parseInt(nomeEcoponto), layoutComentarios);
    }

    private String getToken() {
        // Recupera o token armazenado nas SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(AUTH_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN_KEY, null); // Retorna null se o token não existir
    }
}