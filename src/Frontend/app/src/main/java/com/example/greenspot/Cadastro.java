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
        // Criptografa os dados
        String nomeCriptografado = cifraDeCesar(nome, 3);
        String emailCriptografado = cifraDeCesar(email, 3);
        String senhaCriptografada = cifraDeCesar(senha, 3);

        // Crie uma instância do CustomTrustManager
        CustomTrustManager customTrustManager = new CustomTrustManager();

        // Crie um OkHttpClient com o CustomTrustManager
        OkHttpClient client = customTrustManager.getOkHttpClient();

        // Envie os dados criptografados
        RequestBody requestBody = new okhttp3.FormBody.Builder()
                .add("nome", nomeCriptografado)
                .add("email", emailCriptografado)
                .add("senha", senhaCriptografada)
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
    private String cifraDeCesar(String texto, int deslocamento) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            // Criptografa apenas letras
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c + deslocamento - base) % 26 + base);
            }

            resultado.append(c);
        }

        return resultado.toString();
    }
}