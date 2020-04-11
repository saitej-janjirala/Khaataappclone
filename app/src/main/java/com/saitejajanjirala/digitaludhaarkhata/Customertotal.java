package com.saitejajanjirala.digitaludhaarkhata;

import java.util.ArrayList;
import java.util.HashMap;

public class Customertotal {
    String mid;
    String mphonenumber;
    int mtotalgave;
    int mtotalgot;
    ArrayList<Chatdata> chatdata;

    public String getMid() {
        return mid;
    }

    public String getMphonenumber() {
        return mphonenumber;
    }

    public int getMtotalgot() {
        return mtotalgot;
    }

    public int getMtotalgave() {
        return mtotalgave;
    }

    public ArrayList<Chatdata> getChatdata() {
        return chatdata;
    }

    //gavelist to mtotalgave and show it on you'll get(get amount text)
    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setMphonenumber(String mphonenumber) {
        this.mphonenumber = mphonenumber;
    }

    public void setMtotalgot(int mtotalgot) {
        this.mtotalgot = mtotalgot;
    }

    public void setMtotalgave(int mtotalgave) {
        this.mtotalgave = mtotalgave;
    }

    public void setChatdata(ArrayList<Chatdata> chatdata) {
        this.chatdata = chatdata;
    }
}
