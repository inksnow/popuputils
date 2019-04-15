package com.inks.inkslibrary.Service;


public class QueryVersionBackBean {
    private String appVersion;
    private String versionMgsEN;
    private String versionMgsCH;
    private String mustUp;
    private String downUri;
    public String getAppVersion() {
        return appVersion;
    }
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getVersionMgsEN() {
        return versionMgsEN;
    }

    public void setVersionMgsEN(String versionMgsEN) {
        this.versionMgsEN = versionMgsEN;
    }

    public String getVersionMgsCH() {
        return versionMgsCH;
    }

    public void setVersionMgsCH(String versionMgsCH) {
        this.versionMgsCH = versionMgsCH;
    }

    public String getMustUp() {
        return mustUp;
    }

    public void setMustUp(String mustUp) {
        this.mustUp = mustUp;
    }

    public String getDownUri() {
        return downUri;
    }

    public void setDownUri(String downUri) {
        this.downUri = downUri;
    }
}
