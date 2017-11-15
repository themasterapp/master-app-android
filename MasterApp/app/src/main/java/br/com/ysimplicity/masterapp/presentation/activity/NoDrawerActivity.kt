package br.com.ysimplicity.masterapp.presentation.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.ysimplicity.masterapp.R
import br.com.ysimplicity.masterapp.helper.TurbolinksHelper
import br.com.ysimplicity.masterapp.utils.Constants
import kotlinx.android.synthetic.main.activity_no_drawer.*

/**
 * Created by Marcelo on 08/11/2017.
 */
class NoDrawerActivity : AppCompatActivity() {

    lateinit private var turbolinksHelper: TurbolinksHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_drawer)

        setupToolbar()

        turbolinksHelper = TurbolinksHelper(this, this, turbolinksView)
        turbolinksHelper.visit(getCurrentLocation())
    }

    override fun onRestart() {
        super.onRestart()
        turbolinksHelper.visit(getCurrentLocation(), true)
    }

    private fun getCurrentLocation(): String {
        return if (intent.hasExtra(Constants.INTENT_URL)) {
            intent.getStringExtra(Constants.INTENT_URL)
        } else {
            getString(R.string.base_url)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.title = getString(R.string.drawer_recipes_text)

        if (intent.hasExtra(Constants.TOOLBAR_TITLE)) {
            supportActionBar?.title = intent.extras?.getString(Constants.TOOLBAR_TITLE)
        }
    }
}