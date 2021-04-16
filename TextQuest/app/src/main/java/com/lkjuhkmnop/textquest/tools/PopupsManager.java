package com.lkjuhkmnop.textquest.tools;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;

/**
 * Class to manage pop-ups (Snackbars).
 * It shows pop-ups stored in the {@link PopupsManager#popupStack} one by one in .
 * Snackbars are stored in the {@link PopupsManager#popupStack}. Each Snackbar is wrapped in {@link Popup}.
 * Use method {@link PopupsManager#addPopup} to add a pop-up to the stack.
 * */
public class PopupsManager extends Thread {
    private static View currentMainView;
    private static boolean currViewChanged;
    public static View getCurrentMainView() {
        return currentMainView;
    }
    public static void setCurrentMainView(View currentMainView) {
        PopupsManager.currentMainView = currentMainView;
        currViewChanged = true;
    }

    private static class Popup {
        String message;
        int length;
        int showsCount=0;

        public Popup(String message, int length) {
            this.message = message;
            this.length = length;
        }
    }

    private volatile LinkedList<Popup> popupStack = new LinkedList<>();

    public void addPopup(String message, int length) {
        popupStack.add(new Popup(message, length));
    }

    @NonNull
    public Popup peekPopupToShow() {
        Popup popup = popupStack.peekLast();
        if (popup.showsCount < 1) {
            popup.showsCount++;
        } else {
            popupStack.removeLast();
        }
        return popup;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (currViewChanged && popupStack.size() > 0) {
                for (Popup popup: popupStack) {
                    Snackbar snackbar = Snackbar.make(getCurrentMainView(), popup.message, popup.length);
                    snackbar.show();
                }
                currViewChanged = false;
            }
        }
    }
}
