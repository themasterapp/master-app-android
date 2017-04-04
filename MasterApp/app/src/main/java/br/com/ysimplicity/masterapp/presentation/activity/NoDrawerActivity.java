package br.com.ysimplicity.masterapp.presentation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.basecamp.turbolinks.TurbolinksView;

import br.com.ysimplicity.masterapp.R;
import br.com.ysimplicity.masterapp.helper.TurbolinksHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.ysimplicity.masterapp.utils.Constants.INTENT_URL;
import static br.com.ysimplicity.masterapp.utils.Constants.TOOLBAR_TITLE;

public class NoDrawerActivity extends AppCompatActivity {

    @BindView(R.id.nodrawer_toolbar)
    Toolbar toolbar;

    @BindView(R.id.tnodrawer_urbolinks_view)
    TurbolinksView turbolinksView;

    private TurbolinksHelper turbolinksHelper;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_drawer);
        ButterKnife.bind(this);

        setupToolbar();

        location = getIntent().hasExtra(INTENT_URL) ? getIntent().getStringExtra(INTENT_URL) : getString(R.string.base_url);
        turbolinksHelper = new TurbolinksHelper(this, this, turbolinksView);
        turbolinksHelper.visit(location);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        turbolinksHelper.visit(location, true);
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) {
            return;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getString(R.string.drawer_recipes_text));

        if (getIntent().hasExtra(TOOLBAR_TITLE)) {
            getSupportActionBar().setTitle(getIntent().getExtras().getString(TOOLBAR_TITLE));
        }

    }
}
