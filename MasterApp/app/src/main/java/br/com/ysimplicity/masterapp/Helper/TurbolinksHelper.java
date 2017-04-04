package br.com.ysimplicity.masterapp.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.basecamp.turbolinks.TurbolinksAdapter;
import com.basecamp.turbolinks.TurbolinksSession;
import com.basecamp.turbolinks.TurbolinksView;

import br.com.ysimplicity.masterapp.helper.jsbridge.JsBridge;
import br.com.ysimplicity.masterapp.helper.jsbridge.JsListener;
import br.com.ysimplicity.masterapp.presentation.activity.MainActivity;
import br.com.ysimplicity.masterapp.presentation.activity.NoDrawerActivity;
import br.com.ysimplicity.masterapp.R;

/**
 * Created by Indigo on 11/19/16.
 */

public class TurbolinksHelper implements JsListener, TurbolinksAdapter {

    private final String INTENT_URL = "intentUrl";
    private final String TOOLBAR_TITLE = "TOOLBAR_TITLE";

    private Context context;
    private Activity activity;
    private TurbolinksView turbolinksView;

    public TurbolinksHelper(Context context, Activity activity, TurbolinksView turbolinksView) {
        this.context = context;
        this.activity = activity;
        this.turbolinksView = turbolinksView;

        setupTurbolinks();
    }

    public void visit(String url) {
        visit(url, false);
    }

    public void visit(String url, boolean withCachedSnapshot) {

        if (withCachedSnapshot) {

            TurbolinksSession.getDefault(context).activity(activity).adapter(this).restoreWithCachedSnapshot(true).view(turbolinksView).visit(url);

        } else {

            TurbolinksSession.getDefault(context).activity(activity).adapter(this).view(turbolinksView).visit(url);

        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupTurbolinks() {
        TurbolinksSession.getDefault(context).setDebugLoggingEnabled(true);
        TurbolinksSession.getDefault(context).getWebView().getSettings().setUserAgentString("TurbolinksDemo+Android");
        TurbolinksSession.getDefault(context).getWebView().getSettings().setJavaScriptEnabled(true);
        TurbolinksSession.getDefault(context).getWebView().addJavascriptInterface(new JsBridge(context, this), "android");
    }

    @Override
    public void loginSuccessful() {
        redirectToSelf("/", R.string.drawer_recipes_text);
    }

    @Override
    public void onPageFinished() {

    }

    @Override
    public void onReceivedError(int errorCode) {

    }

    @Override
    public void pageInvalidated() {

    }

    @Override
    public void requestFailedWithStatusCode(int statusCode) {

    }

    @Override
    public void visitCompleted() {

    }

    @Override
    public void visitProposedToLocationWithAction(String location, String action) {

        if (action.equals("replace")) {

            visit(location, true);

        } else if (location.contains("/recipes/")) {

            visitNoDrawerActivity(R.string.drawer_recipes_text, location);

        } else if (location.contains("/users/sign_up")) {

            redirectToSelf(location, R.string.drawer_signup_text, true);

        } else if (location.contains("/users/password/new")) {

            visitNoDrawerActivity(R.string.forgot_password, location);

        } else {
            visitToNewActivity(location);
        }
    }

    public void redirectToSelf(String url, int idResourceToolbarTitle, boolean isFullUrl) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        if (!isFullUrl) {
            intent.putExtra(INTENT_URL, context.getString(R.string.base_url) + url);
        }  else {
            intent.putExtra(INTENT_URL, url);
        }

        intent.putExtra(TOOLBAR_TITLE, context.getString(idResourceToolbarTitle));

        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        activity.finish();
    }

    public void redirectToSelf(String url, int idResourceToolbarTitle) {
        redirectToSelf(url, idResourceToolbarTitle, false);
    }

    private void visitNoDrawerActivity(int idResourceToolbarTitle, String location) {
        Intent i = new Intent(context, NoDrawerActivity.class);
        i.putExtra(INTENT_URL, location);
        i.putExtra(TOOLBAR_TITLE, context.getString(R.string.drawer_recipes_text));
        activity.startActivity(i);
    }

    private void visitToNewActivity(@NonNull final String url) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(INTENT_URL, url);

        activity.startActivity(intent);
    }
}
