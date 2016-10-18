package br.com.dito.ditosdk.model;

import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;

/**
 * Created by gabriel.araujo on 07/04/15.
 */
public class DitoAccount {

    private String id;
    private String accessToken;
    private Object data;
    private Constants.AccountsType type;

    /**
     * @param id
     * @param accessToken
     * @param type
     * @return void
     */
    public DitoAccount(String id, String accessToken, Constants.AccountsType type) {
        this.id = id;
        this.accessToken = accessToken;
        this.type = type;
    }

    /**
     * @param id
     * @param type
     * @return void
     */
    public DitoAccount(String id, Constants.AccountsType type) {
        this.id = id;
        this.type = type;
    }

    /**
     * @param id
     * @param data
     * @param type
     * @return void
     */
    public DitoAccount(String id, Object data, Constants.AccountsType type) {
        this.id = id;
        this.data = data;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Constants.AccountsType getType() {
        return type;
    }

    public void setType(Constants.AccountsType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
