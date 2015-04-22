package br.com.dito.ditosdksample.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * Created by gabriel.araujo on 08/04/15.
 */
public class BaseActivity extends ActionBarActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createProgressDialog();
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Aguarde...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    public void showProgressDialog(boolean isShow) {
        if (progressDialog != null) {
            if (isShow) {
                progressDialog.show();
            } else {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }
    }

    public void openActivity(Context context, String nameMethod, String jsonRequest, String jsonResponse){
        Intent intent = new Intent();
        intent.setClass(context, RequestActivity.class);
        intent.putExtra(RequestActivity.BUNDLE_NAME_METHOD, nameMethod);
        intent.putExtra(RequestActivity.BUNDLE_JSON_REQUEST, jsonRequest);
        intent.putExtra(RequestActivity.BUNDLE_JSON_RESPONSE, jsonResponse);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
