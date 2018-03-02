package com.technocarrot.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Anns on 20/02/18.
 */

public class LoadingDialog {
    public static ProgressDialog show(Context context, String message)
    {
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(message);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;

    }
}
