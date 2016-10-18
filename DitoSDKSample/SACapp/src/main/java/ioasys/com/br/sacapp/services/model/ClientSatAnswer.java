package ioasys.com.br.sacapp.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ioasys on 27/08/15.
 */
public class ClientSatAnswer implements Serializable{
    private static final long serialVersionUID = 2914016507950837674L;

    public ClientSatAnswer(String mClientSatId, String mClientSatDesc, String mClientSatTypeMin, String mClientSatTypeMax, String mClientSatType, String mClientSatMemo) {
        this.mClientSatId = mClientSatId;
        this.mClientSatDesc = mClientSatDesc;
        this.mClientSatTypeMin = mClientSatTypeMin;
        this.mClientSatTypeMax = mClientSatTypeMax;
        this.mClientSatType = mClientSatType;
        this.mClientSatMemo = mClientSatMemo;
    }

    @SerializedName("csatq_id")
    private String mClientSatId;

    @SerializedName("csatq_description")
    private String mClientSatDesc;

    @SerializedName("cstaq_type_min")
    private String mClientSatTypeMin;

    @SerializedName("cstaq_type_max")
    private String mClientSatTypeMax;

    @SerializedName("cstaq_type")
    private String mClientSatType;

    @SerializedName("csatq_memo")
    private String mClientSatMemo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getmClientSatId() {
        return mClientSatId;
    }

    public void setmClientSatId(String mClientSatId) {
        this.mClientSatId = mClientSatId;
    }

    public String getmClientSatDesc() {
        return mClientSatDesc;
    }

    public void setmClientSatDesc(String mClientSatDesc) {
        this.mClientSatDesc = mClientSatDesc;
    }

    public String getmClientSatTypeMin() {
        return mClientSatTypeMin;
    }

    public void setmClientSatTypeMin(String mClientSatTypeMin) {
        this.mClientSatTypeMin = mClientSatTypeMin;
    }

    public String getmClientSatTypeMax() {
        return mClientSatTypeMax;
    }

    public void setmClientSatTypeMax(String mClientSatTypeMax) {
        this.mClientSatTypeMax = mClientSatTypeMax;
    }

    public String getmClientSatType() {
        return mClientSatType;
    }

    public void setmClientSatType(String mClientSatType) {
        this.mClientSatType = mClientSatType;
    }

    public String getmClientSatMemo() {
        return mClientSatMemo;
    }

    public void setmClientSatMemo(String mClientSatMemo) {
        this.mClientSatMemo = mClientSatMemo;
    }
}
