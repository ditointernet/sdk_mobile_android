package ioasys.com.br.sacapp.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ioasys on 27/08/15.
 */
public class NewSessionResponse implements Serializable{
    private static final long serialVersionUID = 6914016505950837674L;

    @SerializedName("header")
    private Header mHeader;

    @SerializedName("session_id")
    private String mSessionId;

    public NewSessionResponse(Header mHeader, String mSessionId) {
        this.mHeader = mHeader;
        this.mSessionId = mSessionId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Header getmHeader() {
        return mHeader;
    }

    public void setmHeader(Header mHeader) {
        this.mHeader = mHeader;
    }

    public String getmSessionId() {
        return mSessionId;
    }

    public void setmSessionId(String mSessionId) {
        this.mSessionId = mSessionId;
    }
}
