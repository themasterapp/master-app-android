package br.com.ysimplicity.masterapp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Indigo on 11/18/16.
 */

public class SessionManager {

    private final static String AUTH_TOKEN = "AUTH_TOKEN";

    public static void saveToken(Context context, String token) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    public static boolean isUserSignedIn(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (prefs.getString(AUTH_TOKEN, "").isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    public static void logoutUser(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }


}
