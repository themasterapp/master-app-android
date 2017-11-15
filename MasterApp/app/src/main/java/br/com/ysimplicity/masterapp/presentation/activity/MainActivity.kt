package br.com.ysimplicity.masterapp.presentation.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.ysimplicity.masterapp.R
import br.com.ysimplicity.masterapp.helper.SessionManager
import br.com.ysimplicity.masterapp.helper.TurbolinksHelper
import br.com.ysimplicity.masterapp.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * Created by Marcelo on 07/11/2017
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit private var turbolinksHelper: TurbolinksHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        turbolinksHelper = TurbolinksHelper(this, this, turbolinksView)

        setupToolbarAndDrawer()
        setupMenuDrawer(navigationView.menu)

        turbolinksHelper.visit(getCurrentLocation())
    }

    override fun onRestart() {
        super.onRestart()
        turbolinksHelper.visit(getCurrentLocation(), true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawer_login -> redirectToSelf(Constants.URL_SIGN_IN, R.string.drawer_login_text)
            R.id.drawer_signup -> redirectToSelf(Constants.URL_SIGN_UP, R.string.drawer_signup_text)
            R.id.drawer_recipes -> redirectToSelf(Constants.URL_HOME, R.string.drawer_recipes_text)
            R.id.drawer_myrecipes -> redirectToSelf(Constants.URL_MY_RECIPES, R.string.drawer_myrecipes_text)
            R.id.drawer_newrecipe -> redirectToSelf(Constants.URL_NEW_RECIPE, R.string.drawer_newrecipe_text)
            R.id.drawer_logout -> {
                SessionManager.logoutUser(this)
                redirectToSelf(Constants.URL_SIGN_OUT, R.string.drawer_recipes_text)
            }
            R.id.drawer_myaccount -> redirectToSelf(Constants.URL_MY_ACCOUNT, R.string.drawer_myaccount_text)
        }
        return false
    }

    private fun redirectToSelf(url: String, idResourceToolbarTitle: Int) {
        turbolinksHelper.redirectToSelf(url, idResourceToolbarTitle)
    }

    private fun getCurrentLocation() : String {
        return if (intent.hasExtra(Constants.INTENT_URL)) {
            intent.getStringExtra(Constants.INTENT_URL)
        } else {
            getString(R.string.base_url)
        }
    }

    private fun setupToolbarAndDrawer() {
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setTitle(R.string.drawer_recipes_text)

        if (intent.hasExtra(Constants.TOOLBAR_TITLE)) {
            actionBar?.title = intent.extras?.getString(Constants.TOOLBAR_TITLE)
        }

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun setupMenuDrawer(menu: Menu) {
        if (!SessionManager.isUserSignedIn(this)) {
            setVisibilityItemsForDrawer(menu, true)
        } else {
            setVisibilityItemsForDrawer(menu, false)
        }
    }

    private fun setVisibilityItemsForDrawer(menu: Menu, isUserNotSignedIn: Boolean) {
        menu.findItem(R.id.drawer_login).isVisible = isUserNotSignedIn
        menu.findItem(R.id.drawer_signup).isVisible = isUserNotSignedIn
        menu.findItem(R.id.drawer_logout).isVisible = !isUserNotSignedIn
        menu.findItem(R.id.drawer_myaccount).isVisible = !isUserNotSignedIn
    }
}