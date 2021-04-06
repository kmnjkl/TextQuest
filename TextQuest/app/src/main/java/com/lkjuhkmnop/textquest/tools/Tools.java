package com.lkjuhkmnop.textquest.tools;

import com.lkjuhkmnop.textquest.tqmanager.TQManager;

public class Tools {
    private static final TQManager TQM = new TQManager();

    public static TQManager getTqManager() {
        if (TQM.getState() == Thread.State.NEW) {
            TQM.start();
        }
        return TQM;
    }
}
