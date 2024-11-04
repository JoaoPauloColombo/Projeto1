package br.fecap.pi.greenspot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.fecap.pi.greenspot.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    private TextInputLayout emailTextInputLayout;
    private TextInputLayout senhaTextInputLayout;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextInputLayout = findViewById(R.id.emailEditText);
        senhaTextInputLayout = findViewById(R.id.senhaEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextInputLayout.getEditText().getText().toString().trim();
                String senha = senhaTextInputLayout.getEditText().getText().toString().trim();

                if (email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(Login.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Enviar email e senha diretamente, sem criptografia
                RequestBody requestBody = new okhttp3.FormBody.Builder()
                        .add("email", email)
                        .add("senha", senha)
                        .build();

                Request request = new Request.Builder()
                        .url("https://projeto1-1vh9.onrender.com/api/user/login")
                        .post(requestBody)
                        .build();

                // Crie uma instância do CustomTrustManager
                CustomTrustManager customTrustManager = new CustomTrustManager();

                // Crie um OkHttpClient com o CustomTrustManager
                OkHttpClient client = customTrustManager.getOkHttpClient();

                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                        runOnUiThread(() -> {
                            Toast.makeText(Login.this, "Erro ao fazer login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                        String responseBody = response.body() != null ? response.body().string() : "";

                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String token = jsonObject.getString("token");

                                // Salve o token de autenticação em um lugar seguro
                                SharedPreferences sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE);
                                sharedPreferences.edit().putString("token", token).apply();

                                // Redirecione o usuário para a tela principal
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                runOnUiThread(() -> Toast.makeText(Login.this, "Erro ao processar a resposta", Toast.LENGTH_SHORT).show());
                            }
                        } else {
                            runOnUiThread(() -> Toast.makeText(Login.this, "Erro ao fazer login: " + response.message(), Toast.LENGTH_SHORT).show());
                        }
                    }
                });
            }
        });
    }
}