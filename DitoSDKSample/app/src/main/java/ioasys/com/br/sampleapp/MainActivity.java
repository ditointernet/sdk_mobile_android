package ioasys.com.br.sampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ioasys.com.br.sacapp.SACappSDK;
import ioasys.com.br.sacapp.app.SACappSDKCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        try {
            SACappSDK.startProcess(this, "json", new SACappSDKCallback() {
                @Override
                public void onSucess(String response) {

                }
                @Override
                public void onError(Exception ex) {

                }
            });
        } catch (Exception ex) {

        }
    }
}
