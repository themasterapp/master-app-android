package br.com.ysimplicity.masterapp.helper

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by Ghost on 14/11/2017.
 */
class SessionManager {
    companion object {
        private val auth_token = "AUTH_TOKEN"

        fun saveToken(context: Context, token: String) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putString(auth_token, token)
            editor.apply()
        }

        fun isUserSignedIn(context: Context): Boolean {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)

            return prefs.getString(auth_token, "").isNotEmpty()
        }

        fun logoutUser(context: Context) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
        }
    }
}