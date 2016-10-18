package ioasys.com.br.sacapp.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ioasys on 27/08/15.
 */
public class RoutingTable implements Serializable {
    private static final long serialVersionUID = 6914416507950837144L;

    public RoutingTable(String mRoutingId, String mChannelId, String mRoutingAdress, String mRoutingDesc, String mQueueEwt, String mQueueLengh) {
        this.mRoutingId = mRoutingId;
        this.mChannelId = mChannelId;
        this.mRoutingAdress = mRoutingAdress;
        this.mRoutingDesc = mRoutingDesc;
        this.mQueueEwt = mQueueEwt;
        this.mQueueLengh = mQueueLengh;

    }

    @SerializedName("routing_id")
    private String mRoutingId;

    @SerializedName("channel_id")
    private String mChannelId;

    @SerializedName("routing_address")
    private String mRoutingAdress;

    @SerializedName("routing_description")
    private String mRoutingDesc;

    @SerializedName("queue_ewt")
    private String mQueueEwt;

    @SerializedName("queue_length")
    private String mQueueLengh;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getmRoutingId() {
        return mRoutingId;
    }

    public void setmRoutingId(String mRoutingId) {
        this.mRoutingId = mRoutingId;
    }

    public String getmChannelId() {
        return mChannelId;
    }

    public void setmChannelId(String mChannelId) {
        this.mChannelId = mChannelId;
    }

    public String getmRoutingAdress() {
        return mRoutingAdress;
    }

    public void setmRoutingAdress(String mRoutingAdress) {
        this.mRoutingAdress = mRoutingAdress;
    }

    public String getmRoutingDesc() {
        return mRoutingDesc;
    }

    public void setmRoutingDesc(String mRoutingDesc) {
        this.mRoutingDesc = mRoutingDesc;
    }

    public String getmQueueEwt() {
        return mQueueEwt;
    }

    public void setmQueueEwt(String mQueueEwt) {
        this.mQueueEwt = mQueueEwt;
    }

    public String getmQueueLengh() {
        return mQueueLengh;
    }

    public void setmQueueLengh(String mQueueLengh) {
        this.mQueueLengh = mQueueLengh;
    }
}
