package ioasys.com.br.sacapp.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ioasys.com.br.sacapp.R;
import ioasys.com.br.sacapp.fragments.Balance;
import ioasys.com.br.sacapp.services.Requests.GeneralRequest;

public class MainScreen extends BaseActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        mToolbar = setToolbar();
        this.setUpActionBar();

        setupFragment();

        GeneralRequest.getClientSat("21E9F31D-A57E-43C0-9A5E-1D3A8CCC8897", 1);
        //GeneralRequest.newSession("21E9F31D-A57E-43C0-9A5E-1D3A8CCC8897", 99, 1, 1, 1, "teste", "Perda_Roubo", "fddsd", "32224434545", "dfsf", "dfds", "dfs", "fsd", "df", "dfds");
        //GeneralRequest.newInteraction("21E9F31D-A57E-43C0-9A5E-1D3A8CCC8897", 1, "11987650876", "2a43a1c2eb9f3e31", "Android", "4.0.0", "SM-A500M");

        /**
         * testes
         */
        startActivity(new Intent(this, Chat.class));
    }

    private void setupFragment(){
        FragmentManager m = getSupportFragmentManager();
        FragmentTransaction ft = m.beginTransaction();
        ft.add(R.id.container, new Balance(), "Tag1");
        ft.commit();
    }

    private void setUpActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.client_attendance));
    }

    public void changeColor(View view){
        mToolbar.setBackgroundColor(getResources().getColor(R.color.blue));
    }
}
