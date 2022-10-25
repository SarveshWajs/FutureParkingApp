package com.example.testing;

import android.widget.EditText;

public class dataholder {
    private String phone,mail,complain,pimage;

    public dataholder(String phone, String mail,String complain, String pimage) {

        this.phone = phone;
        this.mail = mail;
        this.complain = complain;
        this.pimage = pimage;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}

