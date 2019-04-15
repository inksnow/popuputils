package com.inks.inkslibrary.Service;

public class BTBean implements Cloneable {
    String mac;
    String name;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BTBean clone() throws CloneNotSupportedException
    {
        BTBean object = (BTBean) super.clone();
        return object;
    }
}
