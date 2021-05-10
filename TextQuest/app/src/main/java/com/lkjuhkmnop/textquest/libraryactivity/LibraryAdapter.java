package com.lkjuhkmnop.textquest.libraryactivity;

import android.content.Context;
import android.util.Log;
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
import com.lkjuhkmnop.textquest.tqmanager.CloudManager;
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
        ImageView qNewGame, qCloudButton, qSettings, qDelete;
        String qIdText = "", qIdAddedText = "";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            questId = itemView.findViewById(R.id.quest_id);
            questTitle = itemView.findViewById(R.id.lib_quest_title);
            questAuthor = itemView.findViewById(R.id.lib_quest_author);
            qNewGame = itemView.findViewById(R.id.lib_quest_new_game);
            qCloudButton = itemView.findViewById(R.id.lib_quest_cloud_button);
            qSettings = itemView.findViewById(R.id.lib_quest_settings);
            qDelete = itemView.findViewById(R.id.lib_quest_delete);
        }

        public void setIdText() {
            questId.setText(qIdText + qIdAddedText);
        }

        public void setIdText(String idText) {
            qIdText = idText;
            questId.setText(idText + qIdAddedText);
        }

        public void setIdAddedText(String qIdAddedText) {
            this.qIdAddedText = qIdAddedText;
            setIdText();
        }

        public void setTitleText(String title) {
            questTitle.setText(title);
        }

        public void setAuthorText(String author) {
            questAuthor.setText(author);
        }

        public void setCloudUploadVisibility(int visibility) {
            qCloudButton.setVisibility(visibility);
        }

        public void setCloudButtonImage(int resourceId) {
            qCloudButton.setImageResource(resourceId);
        }

        public void setCloudButtonOnClickListener(View.OnClickListener onClickListener) {
            qCloudButton.setOnClickListener(onClickListener);
        }

        public View getItemView() {
            return itemView;
        }
    }

    private void matchQuest(DBQuest localQuest, ViewHolder lqViewHolder) {
        Log.d("LKJD", "mathQuest");
        if (localQuest.getQuestCloudId() != null && !localQuest.getQuestCloudId().equals("")
                && localQuest.getQuestUploaderUserId() != null && !localQuest.getQuestUploaderUserId().equals("")) {
            Log.d("LKJD", "cloud matchQuest");
            Tools.cloudManager().matchQuest(localQuest, response -> {
                if (response.getResponseCode() == CloudManager.OK && response.getData() == CloudManager.QUEST_MATCH) {
                    Log.d("LKJD", "ok matchQuest: " + response);
                    displayUploaded(localQuest, lqViewHolder);
                } else {
                    Log.d("LKJD", "no matchQuest: " + response);
                    localQuest.setQuestCloudId(null);
                    localQuest.setQuestUploaderUserId(null);
                    try {
                        Tools.tqManager().updateQuest(context, localQuest);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    enableUpload(localQuest, lqViewHolder);
                }
            });
        } else {
            Log.d("LKJD", "no cloud mathQuest needed");
            enableUpload(localQuest, lqViewHolder);
        }
    }

    private void enableUpload(DBQuest localQuest, ViewHolder lqViewHolder) {
        lqViewHolder.setAuthorText("Anonymous");
        lqViewHolder.setCloudUploadVisibility(View.VISIBLE);
//            Set on click listener for the cloud upload button
        lqViewHolder.qCloudButton.setOnClickListener(v -> {
            try {
                Tools.cloudManager().uploadQuest(context, localQuest.getQuestId(),
                        response -> {
                            matchQuest(localQuest, lqViewHolder);
                        });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void displayUploaded(DBQuest localQuest, ViewHolder holder) {
        holder.setCloudButtonImage(R.drawable.ic_cloud_delete);
        holder.setCloudButtonOnClickListener(v -> {
            try {
                Tools.cloudManager().deleteQuest(context, localQuest);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            matchQuest(localQuest, holder);
        });

//        Set author's name
        Tools.cloudManager().getUserDisplayName(localQuest.getQuestUploaderUserId(), new CloudManager.OnCMResponseListener<String>() {
            @Override
            public void onCMResponse(CloudManager.CMResponse<String> response) {
                holder.setAuthorText(response.getData());
            }
        });
//        Append quest id
        String questCloudId = localQuest.getQuestCloudId();
        holder.setIdAddedText("\n[CId: " + questCloudId.substring(0, 4) + "..." + questCloudId.substring(questCloudId.length() - 3) + "]");
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
        holder.setIdText(libraryActivity.getString(R.string.lib_quest_id_prefix) + " " + questsData[position].getQuestId());
        holder.setTitleText(questsData[position].getQuestTitle());

//        Set click listeners
//        For description
        holder.getItemView().findViewById(R.id.lib_description).setOnClickListener(v -> {
            Toast.makeText(v.getContext(), questsData[position].getQuestTitle(), Toast.LENGTH_SHORT).show();
            matchQuest(questsData[position], holder);
        });

//        For the new game button
        ImageView addButton = holder.qNewGame;
        addButton.setOnClickListener(v -> {
            String newGameTitle = questsData[position].getQuestTitle();
            try {
                while (Tools.tqManager().getGameByTitle(context, newGameTitle) != null) {
                    newGameTitle = newGameTitle + "_n";
                }
                DBGame newGame = new DBGame(questsData[position].getQuestId(), newGameTitle, Calendar.getInstance().getTimeInMillis());
                Tools.tqManager().addGame(context, newGame);
                Tools.startPlayActivity(libraryActivity, addButton, newGameTitle);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        For the settings button

//        For the delete button
        holder.qDelete.setOnClickListener(v -> {
            try {
                Tools.tqManager().deleteQuestById(context, questsData[position].getQuestId());
                LibraryActivity.reloadQuestsList();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        matchQuest(questsData[position], holder);
    }

    @Override
    public int getItemCount() {
        return questsData == null ? 0 : questsData.length;
    }
}
