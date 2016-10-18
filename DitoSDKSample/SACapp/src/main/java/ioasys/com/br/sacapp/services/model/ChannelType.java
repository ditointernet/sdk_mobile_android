package ioasys.com.br.sacapp.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ioasys on 27/08/15.
 */
public class ChannelType implements Serializable{
    private static final long serialVersionUID = 6914016507950834144L;

    @SerializedName("channeltype_name")
    private String mChannelTypeName;

    @SerializedName("channeltype_display")
    private String mChannelTypeDisplay;

    @SerializedName("channel_address")
    private String mChannelAdress;

    public ChannelType(String mChannelTypeName, String mChannelTypeDisplay, String mChannelAdress) {
        this.mChannelTypeName = mChannelTypeName;
        this.mChannelTypeDisplay = mChannelTypeDisplay;
        this.mChannelAdress = mChannelAdress;
    }


    public String getmChannelTypeName() {
        return mChannelTypeName;
    }

    public void setmChannelTypeName(String mChannelTypeName) {
        this.mChannelTypeName = mChannelTypeName;
    }

    public String getmChannelTypeDisplay() {
        return mChannelTypeDisplay;
    }

    public void setmChannelTypeDisplay(String mChannelTypeDisplay) {
        this.mChannelTypeDisplay = mChannelTypeDisplay;
    }

    public String getmChannelAdress() {
        return mChannelAdress;
    }

    public void setmChannelAdress(String mChannelAdress) {
        this.mChannelAdress = mChannelAdress;
    }
}
