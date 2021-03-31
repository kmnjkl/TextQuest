package com.lkjuhkmnop.textquest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lkjuhkmnop.textquest.tqmanager.TQGame;

import java.util.Calendar;
import java.util.Locale;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    private TQGame[] games;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView gameTitle, gameTime;
        ImageView gameNewGame, gameSettings, gameDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            gameTitle = itemView.findViewById(R.id.game_title);
            gameTime = itemView.findViewById(R.id.game_time);
            gameNewGame = itemView.findViewById(R.id.game_new_game);
            gameSettings = itemView.findViewById(R.id.game_settings);
            gameDelete = itemView.findViewById(R.id.game_delete);
        }

        public void setTitle(String title) {
            gameTitle.setText(title);
        }

        public void setTime(String time) {
            gameTime.setText(time);
        }

        public View getItemView() {
            return itemView;
        }
    }

    public GamesAdapter(TQGame[] games) {
        this.games = games;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTitle(games[position].getGameTitle());
        holder.setTime(String.valueOf(games[position].getGameTime().get(Calendar.HOUR_OF_DAY)));
        holder.getItemView().findViewById(R.id.game_description).setOnClickListener(v -> Toast.makeText(v.getContext(), games[position].getGameTitle(), Toast.LENGTH_SHORT).show());
        holder.getItemView().findViewById(R.id.game_new_game).setOnClickListener(v -> Toast.makeText(v.getContext(), games[position].getGameTitle() + "\nNEW GAME", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return games.length;
    }
}
