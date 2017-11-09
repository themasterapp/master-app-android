package br.com.ysimplicity.masterapp.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import br.com.ysimplicity.masterapp.R
import br.com.ysimplicity.masterapp.helper.jsbridge.JsBridge
import br.com.ysimplicity.masterapp.helper.jsbridge.JsListener
import br.com.ysimplicity.masterapp.presentation.activity.MainActivity
import br.com.ysimplicity.masterapp.presentation.activity.NoDrawerActivity
import br.com.ysimplicity.masterapp.utils.Constants
import com.basecamp.turbolinks.TurbolinksAdapter
import com.basecamp.turbolinks.TurbolinksSession
import com.basecamp.turbolinks.TurbolinksView

/**
 * Created by Marcelo on 08/11/2017
 */
class TurbolinksHelper(private val context: Context,
                       private val activity: Activity,
                       private val turbolinksView: TurbolinksView) : JsListener, TurbolinksAdapter {

    init {
        setupTurbolinks()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupTurbolinks() {
        TurbolinksSession.getDefault(context).setDebugLoggingEnabled(true)
        TurbolinksSession.getDefault(context).webView.settings.userAgentString = "TurbolinksDemo+Android"
        TurbolinksSession.getDefault(context).webView.settings.javaScriptEnabled = true
        TurbolinksSession.getDefault(context).webView.addJavascriptInterface(JsBridge(context, this), "android")
    }

    override fun loginSuccessful() {
        redirectToSelf(Constants.URL_HOME, R.string.drawer_recipes_text)
    }

    override fun visitProposedToLocationWithAction(location: String, action: String) {
        when {
            action.equals(Constants.ACTION_REPLACE) -> visit(location, true)
            location.contains(Constants.URL_RECIPES) -> visitNoDrawerActivity(location)
            location.contains(Constants.URL_SIGN_UP) -> redirectToSelf(location, R.string.drawer_signup_text, true)
            location.contains(Constants.URL_NEW_PASSWORD) -> visitNoDrawerActivity(location)
            else -> visitToNewActivity(location)
        }
    }

    override fun onPageFinished() {}

    override fun onReceivedError(errorCode: Int) {}

    override fun pageInvalidated() {}

    override fun requestFailedWithStatusCode(statusCode: Int) {}

    override fun visitCompleted() {}

    fun visit(url: String) = visit(url, false)

    fun visit(url: String, withCachedSnapshot: Boolean) {
        if (withCachedSnapshot) {
            TurbolinksSession.getDefault(context)
                    .activity(activity)
                    .adapter(this)
                    .restoreWithCachedSnapshot(true)
                    .view(turbolinksView)
                    .visit(url)
        } else {
            TurbolinksSession.getDefault(context)
                    .activity(activity)
                    .adapter(this)
                    .view(turbolinksView)
                    .visit(url)
        }
    }

    fun redirectToSelf(url: String, idResourceToolbarTitle: Int, isFullUrl: Boolean) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        if (!isFullUrl) {
            intent.putExtra(Constants.INTENT_URL, context.getString(R.string.base_url) + url)
        } else {
            intent.putExtra(Constants.INTENT_URL, url)
        }

        intent.putExtra(Constants.TOOLBAR_TITLE, context.getString(idResourceToolbarTitle))

        activity.startActivity(intent)
        activity.overridePendingTransition(0, 0)
        activity.finish()
    }

    fun redirectToSelf(url: String, idResourceToolbarTitle: Int) {
        redirectToSelf(url, idResourceToolbarTitle, false)
    }

    private fun visitNoDrawerActivity(location: String) {
        val intent = Intent(context, NoDrawerActivity::class.java)
        intent.putExtra(Constants.INTENT_URL, location)
        intent.putExtra(Constants.TOOLBAR_TITLE, context.getString(R.string.drawer_recipes_text))
        activity.startActivity(intent)
    }

    private fun visitToNewActivity(url: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(Constants.INTENT_URL, url)

        activity.startActivity(intent)
    }

}