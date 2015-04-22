package br.com.dito.ditosdksample.ui;

import android.os.Bundle;
import android.widget.TextView;

import br.com.dito.ditosdksample.R;

/**
 * Created by gabriel.araujo on 08/04/15.
 */
public class RequestActivity extends BaseActivity {

    public static final String BUNDLE_JSON_REQUEST = "bundle_json_request";
    public static final String BUNDLE_JSON_RESPONSE = "bundle_json_response";
    public static final String BUNDLE_NAME_METHOD = "name_method";

    private TextView jsonRequest;
    private TextView jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        initViews();
        getBundle();
    }

    private void configActionBar(String title){
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews(){
        jsonRequest = (TextView) findViewById(R.id.json_request);
        jsonResponse = (TextView) findViewById(R.id.json_response);
    }

    private void getBundle(){
        if (getIntent().hasExtra(BUNDLE_JSON_REQUEST)){
            jsonRequest.setText(getIntent().getStringExtra(BUNDLE_JSON_REQUEST));
        }

        if (getIntent().hasExtra(BUNDLE_JSON_RESPONSE)){
            jsonResponse.setText(getIntent().getStringExtra(BUNDLE_JSON_RESPONSE));
        }

        if (getIntent().hasExtra(BUNDLE_NAME_METHOD)){
            configActionBar(getIntent().getStringExtra(BUNDLE_NAME_METHOD));
        }
    }
}
