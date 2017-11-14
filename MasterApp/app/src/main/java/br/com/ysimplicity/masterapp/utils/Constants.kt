package br.com.ysimplicity.masterapp.utils

/**
 * Created by Ghost on 14/11/2017.
 */
interface Constants {
    companion object {
        val INTENT_URL = "intentUrl"
        val TOOLBAR_TITLE = "TOOLBAR_TITLE"

        val ACTION_REPLACE = "replace"

        val URL_HOME = "/"
        val URL_SIGN_IN = "/users/sign_in"
        val URL_SIGN_UP = "/users/sign_up"
        val URL_MY_RECIPES = "/?search%5Bfilter%5D=my"
        val URL_SIGN_OUT = "/users/sign_out"
        val URL_MY_ACCOUNT = "users/edit"
        val URL_RECIPES = "/recipes"
        val URL_NEW_PASSWORD = "/users/password/new"
    }
}