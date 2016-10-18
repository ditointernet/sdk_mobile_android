package ioasys.com.br.sacapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ioasys.com.br.sacapp.R;

/**
 * Created by Rodolfo on 19/08/2015.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private boolean mKillActivityOnBackBtn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Toolbar setToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
        }
        return mToolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                if (mKillActivityOnBackBtn){
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
