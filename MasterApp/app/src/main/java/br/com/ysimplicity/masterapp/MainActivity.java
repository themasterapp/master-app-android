package br.com.ysimplicity.masterapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.basecamp.turbolinks.TurbolinksView;

import br.com.ysimplicity.masterapp.Helper.SessionManager;
import br.com.ysimplicity.masterapp.Helper.TurbolinksHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.ysimplicity.masterapp.Constants.INTENT_URL;
import static br.com.ysimplicity.masterapp.Constants.TOOLBAR_TITLE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.turbolinks_view)
    TurbolinksView turbolinksView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private Menu menu;
    private String location;
    private TurbolinksHelper turbolinksHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        turbolinksHelper = new TurbolinksHelper(this, this, turbolinksView);

        setupToolbarAndDrawer();
        setupMenuDrawer();

        location = getIntent().hasExtra(INTENT_URL) ? getIntent().getStringExtra(INTENT_URL) : getString(R.string.base_url);

        turbolinksHelper.visit(location);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        turbolinksHelper.visit(location, true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.drawer_login:
                redirectToSelf("/users/sign_in", R.string.drawer_login_text);
                break;

            case R.id.drawer_signup:
                redirectToSelf("/users/sign_up", R.string.drawer_signup_text);
                break;

            case R.id.drawer_recipes:
                redirectToSelf("/", R.string.drawer_recipes_text);
                break;

            case R.id.drawer_myrecipes:
                redirectToSelf("/?search%5Bfilter%5D=my", R.string.drawer_myrecipes_text);
                break;

            case R.id.drawer_logout:
                SessionManager.logoutUser(this);
                redirectToSelf("/users/sign_out", R.string.drawer_recipes_text);
                break;

            case R.id.drawer_myaccount:
                redirectToSelf("users/edit", R.string.drawer_myaccount_text);
                break;
        }


        return false;
    }

    private void redirectToSelf(String url, int idResourceToolbarTitle) {
        turbolinksHelper.redirectToSelf(url, idResourceToolbarTitle);
    }

    // region Toolbar / Drawer

    private void setupToolbarAndDrawer() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) {
            return;
        }

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.drawer_recipes_text);

        if (getIntent().hasExtra(TOOLBAR_TITLE)) {
            actionBar.setTitle(getIntent().getExtras().getString(TOOLBAR_TITLE));
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        menu = navigationView.getMenu();
    }

    private void setupMenuDrawer() {
        if (!SessionManager.isUserSignedIn(this)) {
            setVisibilityItemsForDrawer(true);
        } else {
            setVisibilityItemsForDrawer(false);
        }
    }

    private void setVisibilityItemsForDrawer(boolean isUserNotSignedIn) {
        menu.findItem(R.id.drawer_login).setVisible(isUserNotSignedIn);
        menu.findItem(R.id.drawer_signup).setVisible(isUserNotSignedIn);
        menu.findItem(R.id.drawer_logout).setVisible(!isUserNotSignedIn);
        menu.findItem(R.id.drawer_myaccount).setVisible(!isUserNotSignedIn);
    }

    // endregion
}