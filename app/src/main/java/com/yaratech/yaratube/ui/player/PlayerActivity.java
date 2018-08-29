package com.yaratech.yaratube.ui.player;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yaratech.yaratube.R;

public class PlayerActivity extends AppCompatActivity {

    private static final String KEY_PRODUCT_FILE = "KEY_PRODUCT_FILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setFragment();
    }

    private void setFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.player_activity_main_content,
                        PlayerFragment.newInstance(getIntent().getExtras().getString(KEY_PRODUCT_FILE)),
                        PlayerFragment.class.getSimpleName())
                .commit();
    }

}
