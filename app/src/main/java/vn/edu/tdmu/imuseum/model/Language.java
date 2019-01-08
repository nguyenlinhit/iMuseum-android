package vn.edu.tdmu.imuseum.model;

/**
 * Created by nvulinh on 3/2/18.
 *
 */

public class Language {
    private int mId;
    private String nName;
    private String mCode;


    public Language(int mId, String nName, String mCode) {
        this.mId = mId;
        this.nName = nName;
        this.mCode = mCode;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }
}
