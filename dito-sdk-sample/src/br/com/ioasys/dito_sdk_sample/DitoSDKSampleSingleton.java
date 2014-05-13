package br.com.ioasys.dito_sdk_sample;

import android.app.Application;
import br.com.ioasys.dito_sdk_sample.constants.Constants;
import br.com.ioasys.dito_sdk_sample.utils.AppLogUtil;

import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

public class DitoSDKSampleSingleton extends Application {
    private static DitoSDKSampleSingleton instance;

    @Override
    public void onCreate() {
        super.onCreate();

        AppLogUtil.setLogEnabled(Constants.LOG_ENABLED);

        instance = this;

        if (instance == null) {
            throw new RuntimeException("Configure a aplicaçãoo no AndroidManifest.xml");
        }

        setupFacebookSettings();
    }

    private void setupFacebookSettings() {
        Permissions[] permissions = new Permissions[] { Permissions.EMAIL, Permissions.PUBLISH_ACTION, Permissions.PUBLISH_STREAM };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder().setAppId(Constants.Facebook.APP_ID).setNamespace(Constants.Facebook.APP_NAMESPACE).setPermissions(permissions).build();

        SimpleFacebook.setConfiguration(configuration);
    }
}
