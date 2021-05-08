package com.lkjuhkmnop.textquest.tqmanager;


import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.lkjuhkmnop.textquest.tools.Tools;

public class CloudManager {
    public static final int RESPONSE_OK = 1;

    private static final String FIRESTORE_COLLECTION = "tqlibrary";
    private static final String DBQUEST_ID_FIELD_NAME = "questId";
    private static final String DBQUEST_TITLE_FIELD_NAME = "questTitle";
    private static final String DBQUEST_AUTHOR_FIELD_NAME = "questAuthor";

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public static class CMResponse {
         private int responseCode;
         private String cloudQuestId;

        public CMResponse(int responseCode, String cloudQuestId) {
            this.responseCode = responseCode;
            this.cloudQuestId = cloudQuestId;
        }
    }

    public interface CMResponseListener {
        void onCMResponse(CMResponse response);
    }

    public void uploadQuest(Context context, int localQuestId, CMResponseListener cmResponseListener) throws InterruptedException {
        DBQuest quest = Tools.tqManager().getQuestById(context, localQuestId);
        quest.setUploadedToCloud(true);
        quest.setUploaderUserId(Tools.authTools().getUser().getUid());
        firestore.collection(FIRESTORE_COLLECTION)
                .add(quest)
                .addOnSuccessListener(documentReference -> {
                    cmResponseListener.onCMResponse(new CMResponse(RESPONSE_OK, documentReference.getId()));
                });
    }


    public CMResponse matchQuest(DBQuest quest) {
        firestore.collection(FIRESTORE_COLLECTION)
                .whereEqualTo(DBQUEST_TITLE_FIELD_NAME, quest.getQuestTitle())
                .whereEqualTo(DBQUEST_AUTHOR_FIELD_NAME, quest.getQuestAuthor())
    }
}
