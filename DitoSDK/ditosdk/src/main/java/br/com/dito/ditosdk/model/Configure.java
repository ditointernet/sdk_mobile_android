package br.com.dito.ditosdk.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.util.DitoSDKUtils;

/**
 * Created by gabriel.araujo on 01/04/15.
 */
public class Configure {

    private String apiKey;
    private String secret;
    private String sha1Signature;

    /**
     * @param apiKey
     * @param secret JSON to be sent in the request body
     * @return void
     */
    public Configure(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
        try {
            this.sha1Signature = DitoSDKUtils.SHA1(secret);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecret() {
        return secret;
    }

    public String getSha1Signature() {
        return sha1Signature;
    }
}
