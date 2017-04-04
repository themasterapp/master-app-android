package br.com.ysimplicity.masterapp.helper.jsbridge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

import br.com.ysimplicity.masterapp.helper.SessionManager;
import br.com.ysimplicity.masterapp.helper.jsbridge.JsListener;

/**
 * Created by Indigo on 11/18/16.
 */

public class JsBridge {

    private Context context;
    private JsListener listener;


    public JsBridge(@NonNull Context context, @NonNull JsListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @JavascriptInterface
    public void postMessage(String message) {
        SessionManager.saveToken(context, message);
        listener.loginSuccessful();
    }

}

