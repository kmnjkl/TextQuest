package com.lkjuhkmnop.textquest.tools;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;

/**
 * Class to manage pop-ups (Snackbars).
 * It shows pop-ups (using {@link Snackbar}) stored in the {@link PopupsManager#popupsList} one by one in the special thread.
 * Use method {@link PopupsManager#addPopup} to add a pop-up to the stack.
 * @see Tools
 * */
public class PopupsManager extends Thread {
    private static boolean show = true;

//    View to attach Snackbars
    private static volatile View currentMainView;
    public static View getCurrentMainView() {
        return currentMainView;
    }
    public static void setCurrentMainView(View currentMainView) {
        PopupsManager.currentMainView = currentMainView;
        show = true;
    }

    /**
     * Pop-up includes message text, length and how many times this pop-up had been shown.
     * */
    private static class Popup {
        String message;
        int length;
        int showsCount=0;

        public Popup(String message, int length) {
            this.message = message;
            this.length = length;
        }
    }

    /**
     * Stack with pop-ups.
     * @see PopupsManager#addPopup
     * */
    private volatile LinkedList<Popup> popupsList = new LinkedList<>();
    public synchronized LinkedList<Popup> getPopupsList() {
        return popupsList;
    }
    public synchronized void setPopupsList(LinkedList<Popup> popupsList) {
        this.popupsList = popupsList;
    }

    /**
     * Adds pop-up to the stack.
     * @see PopupsManager#popupsList
     * */
    public void addPopup(String message, int length) {
        getPopupsList().add(new Popup(message, length));
        show = true;
    }

    @NonNull
    public Popup peekPopupToShow() {
        Popup popup = getPopupsList().peekLast();
        if (popup.showsCount < 1) {
            popup.showsCount++;
        } else {
            getPopupsList().removeLast();
        }
        return popup;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
//            If View to attach Snackbars had changed and stack has elements (pop-ups) show pop-ups
            if (show && getPopupsList().size() > 0) {
                for (Popup popup : getPopupsList()) {
                    Log.d("popup", "Show pop-up: " + popup.message + "; attached to: " + getCurrentMainView());
                    Snackbar snackbar = Snackbar.make(getCurrentMainView(), popup.message, popup.length);
                    snackbar.show();
                    popup.showsCount++;
                    if (popup.showsCount >= 2) {
                        getPopupsList().remove();
                    }
                }
                show = false;
//                if (getPopupStack().size() > 0) {
//                    show = true;
//                } else {
//                    show = false;
//                }
            }
        }
    }
}
