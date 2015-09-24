package br.com.dito.ditosdksample.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.com.dito.ditosdk.DitoSDK;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.model.DitoAccount;
import br.com.dito.ditosdk.model.DitoCredentials;
import br.com.dito.ditosdksample.Constants;
import br.com.dito.ditosdksample.R;
import br.com.dito.ditosdksample.ui.adapter.ItemListAdapter;


public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private DitoCredentials credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView) findViewById(R.id.list);
        ItemListAdapter adapter = new ItemListAdapter(this, 0, Arrays.asList(getResources().getStringArray(R.array.options)));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        DitoSDK.configureEnvironment(br.com.dito.ditosdk.constant.Constants.EnvironmentType.PRODUCTION);
        try {
            DitoSDK.configure(this, Constants.API_KEY, Constants.API_SECRET);
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }
        credentials = new DitoCredentials("123", null, null, null, null);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                identify();
                break;
            case 1:
                registerToken();
                break;
            case 2:
                unregisterToken();
                break;
            case 3:
                notificationRead();
                break;
            case 4:
                alias();
                break;
            case 5:
                unAlias();
                break;
            case 6:
                track();
                break;
            case 7:
                requestGet();
                break;
        }
    }

    private void identify() {
        try {
            showProgressDialog(true);
            DitoSDK.identify(credentials, null, Constants.JSON_STATIC_REQUEST.JSON_IDENTIFY, new DitoSDKCallback() {
                @Override
                public void onSuccess(String s) {
                    showProgressDialog(false);
                    if (!TextUtils.isEmpty(s)) {
                        openActivity(HomeActivity.this, "Identify", Constants.JSON_STATIC_VIEW.JSON_IDENTIFY, s);
                    }
                }

                @Override
                public void onError(Exception e) {
                    showToast(e.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }
    }

    private void registerToken() {
        try {
            showProgressDialog(true);
            DitoSDK.registerDevice(credentials, "12094253890", new DitoSDKCallback() {
                @Override
                public void onSuccess(String s) {
                    showProgressDialog(false);
                    if (!TextUtils.isEmpty(s)) {
                        openActivity(HomeActivity.this, "Register Token", Constants.JSON_STATIC_VIEW.JSON_REGISTER_TOKEN, s);
                    }
                }

                @Override
                public void onError(Exception e) {
                    showProgressDialog(false);
                    showToast(e.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showProgressDialog(false);
            showToast(e.getMessage());
        }
    }

    private void unregisterToken() {
        try {
            showProgressDialog(true);
            DitoSDK.unregisterDevice(credentials, "12094253890", new DitoSDKCallback() {
                @Override
                public void onSuccess(String s) {
                    showProgressDialog(false);
                    if (!TextUtils.isEmpty(s)) {
                        openActivity(HomeActivity.this, "UnRegister Token", Constants.JSON_STATIC_VIEW.JSON_UNREGISTER_TOKEN, s);
                    }
                }

                @Override
                public void onError(Exception e) {
                    showProgressDialog(false);
                    showToast(e.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showProgressDialog(false);
            showToast(e.getMessage());
        }
    }

    private void notificationRead() {
        try {
            showProgressDialog(true);
            DitoSDK.notificationRead(credentials, Constants.JSON_STATIC_REQUEST.JSON_READ_NOTIFICATION, new DitoSDKCallback() {
                @Override
                public void onSuccess(String s) {
                    showProgressDialog(false);
                    if (!TextUtils.isEmpty(s)) {
                        openActivity(HomeActivity.this, "Notification Read", Constants.JSON_STATIC_VIEW.JSON_READ_NOTIFICATION, s);
                    }
                }

                @Override
                public void onError(Exception e) {
                    showProgressDialog(false);
                    showToast(e.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showProgressDialog(false);
            showToast(e.getMessage());
        }
    }

    private void alias() {
        List<DitoAccount> accountsList = new ArrayList<>();
        accountsList.add(new DitoAccount("1574730112774792", "CAAWC8vPa5uEBAFs59ouBWCkkA0cZCy8i8yaVFPZBni77N818DJhRTW8HLGBVGGqlXRsIigQldtbl2EAMGgPp5TjUg0AkIeNCyZCkC8mVAQnynUjWlktHIK2v9BrDjS4fIDASIMZBjCCdjucAtfJkSCxvjHhIa0AQ5Xqx2kjhK4ZBRpHQI2r8BZBmpZC4WnZCZA7YFn1zmI6VB2JRmuya8cg77Vi9xUhPIomZCxsu0nvQL3ygZDZD", br.com.dito.ditosdk.constant.Constants.AccountsType.FACEBOOK));
        try {
            showProgressDialog(true);
            DitoSDK.alias(credentials, accountsList, new DitoSDKCallback() {
                @Override
                public void onSuccess(String s) {
                    showProgressDialog(false);
                    if (!TextUtils.isEmpty(s)) {
                        openActivity(HomeActivity.this, "Alias", Constants.JSON_STATIC_VIEW.JSON_ALIAS, s);
                    }
                }

                @Override
                public void onError(Exception e) {
                    showProgressDialog(false);
                    showToast(e.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showProgressDialog(false);
            showToast(e.getMessage());
        }
    }

    private void unAlias() {
        List<DitoAccount> accountsList = new ArrayList<>();
        accountsList.add(new DitoAccount("1574730112774792", br.com.dito.ditosdk.constant.Constants.AccountsType.FACEBOOK));
        try {
            showProgressDialog(true);
            DitoSDK.unalias(credentials, accountsList, new DitoSDKCallback() {
                @Override
                public void onSuccess(String s) {
                    showProgressDialog(false);
                    if (!TextUtils.isEmpty(s)) {
                        openActivity(HomeActivity.this, "UnAlias", Constants.JSON_STATIC_VIEW.JSON_UNALIAS, s);
                    }
                }

                @Override
                public void onError(Exception e) {
                    showProgressDialog(false);
                    showToast(e.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showProgressDialog(false);
            showToast(e.getMessage());
        }
    }

    private void track() {
        try {
            showProgressDialog(true);
            DitoSDK.track(credentials, Constants.JSON_STATIC_REQUEST.JSON_TRACK, new DitoSDKCallback() {
                @Override
                public void onSuccess(String s) {
                    showProgressDialog(false);
                    if (!TextUtils.isEmpty(s)) {
                        openActivity(HomeActivity.this, "Track", Constants.JSON_STATIC_VIEW.JSON_TRACK, s);
                    }
                }

                @Override
                public void onError(Exception e) {
                    showProgressDialog(false);
                    showToast(e.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showProgressDialog(false);
            showToast(e.getMessage());
        }
    }

    private void requestGet() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("parametro", "valor");
        params.put("parametro2", "valor2");
        params.put("parametro3", "valor3");

        try {
            showProgressDialog(true);
            DitoSDK.request("login", "/app", params, br.com.dito.ditosdk.constant.Constants.HttpTypes.GET, new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showProgressDialog(false);
                    if (!TextUtils.isEmpty(response)) {
                        openActivity(HomeActivity.this, "Request", Constants.JSON_STATIC_VIEW.JSON_REQUEST_GET, response);
                    }
                }

                @Override
                public void onError(Exception ex) {
                    showProgressDialog(false);
                    showToast(ex.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showProgressDialog(false);
            showToast(e.getMessage());
        }
    }


    private void showToast(String mensage) {
        Toast.makeText(this, mensage, Toast.LENGTH_LONG).show();
    }
}
