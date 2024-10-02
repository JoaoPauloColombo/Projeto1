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
                String nome = nomeEditText.getText().toString().replace("\n", "");
                String email = emailEditText.getText().toString().replace("\n", "");
                String senha = senhaEditText.getText().toString().replace("\n", "");

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                cadastrar(nome, email, senha);

                Intent intent = new Intent(Cadastro.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cadastrar(String nome, String email, String senha) {
        OkHttpClient client = new OkHttpClient();
        System.out.println("Valor de 'nome': " + nome);
        RequestBody requestBody = new okhttp3.FormBody.Builder()
                .add("nome", nome)
                .add("email", email)
                .add("senha", senha)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/user")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Cadastro.this, "Erro ao cadastrar usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // ...
                } else {
                    if (response.code() == 400) {
                        try {
                            String responseBody = response.body().string();
                            System.out.println("Erro ao cadastrar usuário: " + responseBody);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Cadastro.this, "Erro ao cadastrar usuário: " + responseBody, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Cadastro.this, "Erro ao cadastrar usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } else {
                        System.out.println("Erro ao cadastrar usuário: código de resposta " + response.code());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Cadastro.this, "Erro ao cadastrar usuário: código de resposta " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}