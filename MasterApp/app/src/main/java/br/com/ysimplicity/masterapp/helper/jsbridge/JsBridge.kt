package br.com.ysimplicity.masterapp.helper.jsbridge

import android.content.Context
import android.webkit.JavascriptInterface
import br.com.ysimplicity.masterapp.helper.SessionManager

/**
 * Created by Ghost on 14/11/2017.
 */
class JsBridge(val context: Context, val listener: JsListener) {
    @JavascriptInterface
    fun postMessage(message: String) {
        SessionManager.saveToken(context, message)
        listener.loginSuccessful()
    }
}