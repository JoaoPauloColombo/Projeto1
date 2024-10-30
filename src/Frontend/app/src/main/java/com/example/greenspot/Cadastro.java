package com.example.greenspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Cadastro extends AppCompatActivity {

    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText senhaEditText;
    private Button cadastrarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nomeEditText = ((TextInputLayout) findViewById(R.id.textInputNome)).getEditText();
        emailEditText = ((TextInputLayout) findViewById(R.id.textInputEmail)).getEditText();
        senhaEditText = ((TextInputLayout) findViewById(R.id.textInputSenha)).getEditText();
        cadastrarButton = findViewById(R.id.buttonCadastro);

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString().trim();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                cadastrar(nome, email, senha);
            }
        });
    }

    private void cadastrar(String nome, String email, String senha) {
        // Crie uma instância do CustomTrustManager
        CustomTrustManager customTrustManager = new CustomTrustManager();

        // Crie um OkHttpClient com o CustomTrustManager
        OkHttpClient client = customTrustManager.getOkHttpClient();

        // Defina um deslocamento baseado no comprimento da senha
        int shift = senha.length(); // Você pode usar qualquer método para determinar o deslocamento

        // Criptografe os dados
        String encryptedNome = Criptografia.encrypt(nome, shift);
        String encryptedEmail = Criptografia.encrypt(email, shift);
        String encryptedSenha = Criptografia.encrypt(senha, shift);

        RequestBody requestBody = new okhttp3.FormBody.Builder()
                .add("nome", encryptedNome)
                .add("email", encryptedEmail)
                .add("senha", encryptedSenha)
                .build();

        Request request = new Request.Builder()
                .url("https://projeto1-1vh9.onrender.com/api/user")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(Cadastro.this, "Erro ao cadastrar usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Cadastro bem-sucedido
                    runOnUiThread(() -> {
                        Toast.makeText(Cadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        // Inicie a atividade de login
                        Intent intent = new Intent(Cadastro.this, Login.class);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    String errorMessage = response.body() != null ? response.body().string() : "Erro desconhecido";
                    runOnUiThread(() -> Toast.makeText(Cadastro.this, "Erro ao cadastrar usuário: " + errorMessage, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    public static class Criptografia {

        // Método para criptografar
        public static String encrypt(String text, int shift) {
            StringBuilder result = new StringBuilder();

            for (char character : text.toCharArray()) {
                if (Character.isLetter(character)) {
                    char base = Character.isLowerCase(character) ? 'a' : 'A';
                    character = (char) ((character - base + shift) % 26 + base);
                }
                result.append(character);
            }
            return result.toString();
        }

    }
}