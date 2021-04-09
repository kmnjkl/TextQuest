package com.lkjuhkmnop.textquest.libraryactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.tqmanager.DBQuest;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    private final DBQuest[] quests;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView questId, questTitle, questAuthor;
        ImageView questNewGame, questSettings, questDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            questId = itemView.findViewById(R.id.quest_id);
            questTitle = itemView.findViewById(R.id.lib_quest_title);
            questAuthor = itemView.findViewById(R.id.lib_quest_author);
            questNewGame = itemView.findViewById(R.id.lib_quest_new_game);
            questSettings = itemView.findViewById(R.id.lib_quest_settings);
            questDelete = itemView.findViewById(R.id.lib_quest_delete);
        }

        public void setId(int id) {
            questId.setText(id);
        }

        public void setTitle(String title) {
            questTitle.setText(title);
        }

        public void setAuthor(String author) {
            questAuthor.setText(author);
        }

        public View getItemView() {
            return itemView;
        }
    }

    public LibraryAdapter(DBQuest[] quests) {
        this.quests = quests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTitle(quests[position].getQuestTitle());
        holder.setAuthor(quests[position].getQuestAuthor());
        holder.getItemView().findViewById(R.id.lib_description).setOnClickListener(v -> Toast.makeText(v.getContext(), quests[position].getQuestTitle(), Toast.LENGTH_SHORT).show());
        holder.getItemView().findViewById(R.id.lib_quest_new_game).setOnClickListener(v -> Toast.makeText(v.getContext(), quests[position].getQuestTitle() + "\nNEW GAME", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return quests == null ? 0 : quests.length;
    }
}
