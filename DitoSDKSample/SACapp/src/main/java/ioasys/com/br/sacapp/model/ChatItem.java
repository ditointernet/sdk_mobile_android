package ioasys.com.br.sacapp.model;

/**
 * Created by ioasys on 26/08/15.
 */
public class ChatItem {
    private boolean isResponding;

    public ChatItem(boolean isResponding, int mImgMsg, String mTxtMsg, boolean isTitle) {
        this.isResponding = isResponding;
        this.mImgMsg = mImgMsg;
        this.mTxtMsg = mTxtMsg;
        this.isTitle = isTitle;
    }

    private int mImgMsg;
    private String mTxtMsg;
    private boolean isTitle;

    public boolean isTitle() {
        return isTitle;
    }

    public void setIsTitle(boolean isTitle) {
        this.isTitle = isTitle;
    }

    public int getmImgMsg() {
        return mImgMsg;
    }

    public void setmImgMsg(int mImgMsg) {
        this.mImgMsg = mImgMsg;
    }

    public String getmTxtMsg() {
        return mTxtMsg;
    }

    public void setmTxtMsg(String mTxtMsg) {
        this.mTxtMsg = mTxtMsg;
    }

    public boolean isResponding() {
        return isResponding;
    }

    public void setIsResponding(boolean isResponding) {
        this.isResponding = isResponding;
    }
}
