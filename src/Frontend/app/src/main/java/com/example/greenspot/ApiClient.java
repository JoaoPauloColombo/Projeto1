package com.example.greenspot;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class ApiClient {
    private static final String BASE_URL = "https://projeto1-1vh9.onrender.com/api";
    private static final OkHttpClient client = new OkHttpClient();

    public static void cadastrar(String nome, String email, String senha, Callback callback) {
        RequestBody requestBody = new okhttp3.FormBody.Builder()
                .add("nome", nome)
                .add("email", email)
                .add("senha", senha)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/user")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void login(String email, String senha, Callback callback) {
        RequestBody requestBody = new okhttp3.FormBody.Builder()
                .add("email", email)
                .add("senha", senha)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/user/login")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }
}