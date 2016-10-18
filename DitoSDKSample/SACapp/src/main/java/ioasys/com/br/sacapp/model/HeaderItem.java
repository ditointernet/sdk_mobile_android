package ioasys.com.br.sacapp.model;

import java.util.List;

/**
 * Created by ioasys on 26/08/15.
 */
public class HeaderItem {
    private boolean mIsResponding;

    public boolean ismIsResponding() {
        return mIsResponding;
    }

    public void setmIsResponding(boolean mIsResponding) {
        this.mIsResponding = mIsResponding;
    }

    private int mImgTitle;
    private List<String> mText;

    public int getmImgTitle() {
        return mImgTitle;
    }

    public void setmImgTitle(int mImgTitle) {
        this.mImgTitle = mImgTitle;
    }

    public List<String> getmText() {
        return mText;
    }

    public void setmText(List<String> mText) {
        this.mText = mText;
    }

    public HeaderItem(int mImgTitle, List<String> mText) {
        this.mImgTitle = mImgTitle;
        this.mText = mText;
    }
}
