package com.lkjuhkmnop.textquest.libraryactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.tools.Tools;
import com.lkjuhkmnop.textquest.tqmanager.DBGame;
import com.lkjuhkmnop.textquest.tqmanager.DBQuest;

import java.util.Calendar;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    private Context context;
    private LibraryActivity libraryActivity;
    private DBQuest[] questsData;

    public void setQuestsData(DBQuest[] questsData) {
        this.questsData = questsData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView questId, questTitle, questAuthor;
        ImageView qNewGame, qCloudUpload, qSettings, qDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            questId = itemView.findViewById(R.id.quest_id);
            questTitle = itemView.findViewById(R.id.lib_quest_title);
            questAuthor = itemView.findViewById(R.id.lib_quest_author);
            qNewGame = itemView.findViewById(R.id.lib_quest_new_game);
            qCloudUpload = itemView.findViewById(R.id.lib_quest_cloud_upload);
            qSettings = itemView.findViewById(R.id.lib_quest_settings);
            qDelete = itemView.findViewById(R.id.lib_quest_delete);
        }

        public void setIdText(String idText) {
            questId.setText(idText);
        }

        public void setTitleText(String title) {
            questTitle.setText(title);
        }

        public void setAuthorText(String author) {
            questAuthor.setText(author);
        }

        public View getItemView() {
            return itemView;
        }
    }

    private class CloudMatcher extends Thread {
        private boolean run = true;

        @Override
        public void run() {
            super.run();
            while (run) {
                for (DBQuest quest: questsData) {

                }
            }
        }

        public boolean isRun() {
            return run;
        }

        public void setRun(boolean run) {
            this.run = run;
        }
    }

    public LibraryAdapter(Context context, LibraryActivity libraryActivity,  DBQuest[] quests) {
        this.context = context;
        this.libraryActivity = libraryActivity;
        this.questsData = quests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Set quests description
        holder.setIdText(String.valueOf(questsData[position].getQuestId()));
        holder.setTitleText(questsData[position].getQuestTitle());
        holder.setAuthorText(questsData[position].getQuestAuthor());

//        Set click listeners
//        For description
        holder.getItemView().findViewById(R.id.lib_description).setOnClickListener(v -> Toast.makeText(v.getContext(), questsData[position].getQuestTitle(), Toast.LENGTH_SHORT).show());

//        For the new game button
        ImageView addButton = holder.qNewGame;
        addButton.setOnClickListener(v -> {
            String newGameTitle = questsData[position].getQuestTitle();
            try {
                while (Tools.getTqManager().getGameByTitle(context, newGameTitle) != null) {
                    newGameTitle = newGameTitle + "_n";
                }
                DBGame newGame = new DBGame(questsData[position].getQuestId(), newGameTitle, Calendar.getInstance().getTimeInMillis());
                Tools.getTqManager().addGame(context, newGame);
                Tools.startPlayActivity(libraryActivity, addButton, newGameTitle);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        For the cloud upload button
        holder.qCloudUpload.setOnClickListener(v -> {
            try {
                Tools.getCloudManager().uploadQuest(
                        context,
                        questsData[position].getQuestId(),
                        response -> {
                            holder.qCloudUpload.setVisibility(View.INVISIBLE);
                        });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


//        For the settings button

//        For the delete button
        holder.qDelete.setOnClickListener(v -> {
            try {
                Tools.getTqManager().deleteQuestById(context, questsData[position].getQuestId());
                LibraryActivity.reloadQuestsList();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return questsData == null ? 0 : questsData.length;
    }
}
