package br.fecap.pi.greenspot;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ApiClient {
    private static final String BASE_URL = "https://projeto1-1vh9.onrender.com/api";
    private static final OkHttpClient client = new OkHttpClient();

    public static void cadastrar(String nome, String email, String senha, Callback callback) {
        // Criptografe a senha antes de enviar
        int shift = senha.length(); // Você pode usar uma abordagem mais segura para o deslocamento
        String encryptedSenha = Criptografia.encrypt(senha, shift);

        RequestBody requestBody = new okhttp3.FormBody.Builder()
                .add("nome", nome)
                .add("email", email)
                .add("senha", encryptedSenha)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/users")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void login(String email, String senha, Callback callback) {
        // Criptografe a senha antes de enviar
        int shift = senha.length(); // Você pode usar uma abordagem mais segura para o deslocamento
        String encryptedSenha = Criptografia.encrypt(senha, shift);

        RequestBody requestBody = new okhttp3.FormBody.Builder()
                .add("email", email)
                .add("senha", encryptedSenha)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/users/login")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void sendComentario(String pointName, String description, float rating, Callback callback) {
        // Crie o corpo da requisição com os dados do comentário
        RequestBody requestBody = new okhttp3.FormBody.Builder()
                .add("nome", pointName)
                .add("descricao", description)
                .add("nota", String.valueOf((int) rating)) // A nota deve ser um inteiro
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/comentarios/") // URL da sua API para criar comentários
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
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