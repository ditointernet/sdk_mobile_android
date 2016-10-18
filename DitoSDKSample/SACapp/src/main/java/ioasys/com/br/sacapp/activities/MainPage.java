package ioasys.com.br.sacapp.activities;

import android.os.Bundle;

import ioasys.com.br.sacapp.R;

public class MainPage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        setToolbar();
        getSupportActionBar().setTitle(getResources().getString(R.string.client_attendance1));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
