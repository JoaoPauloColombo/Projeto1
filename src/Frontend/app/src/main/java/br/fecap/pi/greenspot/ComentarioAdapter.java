package br.fecap.pi.greenspot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {

    private JSONArray comentarios;

    public ComentarioAdapter(JSONArray comentarios) {
        this.comentarios = comentarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comentario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject comentario = comentarios.getJSONObject(position);
            String nome = comentario.getString("nome");
            String descricao = comentario.getString("descricao");
            int nota = comentario.getInt("nota");

            holder.textViewNome.setText(nome);
            holder.textViewDescricao.setText(descricao);
            holder.ratingBar.setRating(nota);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return comentarios.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNome;
        public TextView textViewDescricao;
        public RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textInputNome);
            textViewDescricao = itemView.findViewById(R.id.description_input);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }
}